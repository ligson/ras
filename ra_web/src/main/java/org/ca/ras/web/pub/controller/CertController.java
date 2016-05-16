package org.ca.ras.web.pub.controller;

import org.apache.commons.codec.binary.Base64;
import org.ca.cas.cert.api.CertApi;
import org.ca.cas.cert.dto.QueryCertRequestDto;
import org.ca.cas.cert.dto.QueryCertResponseDto;
import org.ca.cas.cert.vo.Cert;
import org.ca.cas.user.api.UserApi;
import org.ca.cas.user.dto.QueryUserRequestDto;
import org.ca.cas.user.dto.QueryUserResponseDto;
import org.ca.common.cert.enums.CertType;
import org.ca.ras.cert.api.RaCertApi;
import org.ca.ras.cert.dto.EnrollCertRequestDto;
import org.ca.ras.cert.dto.EnrollCertResponseDto;
import org.ca.cas.user.vo.User;
import org.ca.ras.cert.dto.ModifyCertRequestDto;
import org.ca.ras.cert.dto.ModifyCertResponseDto;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.string.encode.HashHelper;
import org.ligson.fw.web.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * Created by ligson on 2016/4/26.
 */
@RequestMapping("/cert")
@Controller
public class CertController extends BaseController {
    @Resource
    private RaCertApi raCertApi;
    @Resource
    private CertApi certApi;
    @Resource
    private UserApi userApi;

    @RequestMapping("/index.html")
    public String index() {
        return "pub/cert/index";
    }

    @RequestMapping("/enroll.html")
    public String toEnroll() {
        return "pub/cert/enroll";
    }

    @RequestMapping("/enroll.do")
    public String enroll(EnrollCertRequestDto requestDto) {
        String o = request.getParameter("o");
        String ou = request.getParameter("ou");
        String cn = request.getParameter("cn");
        String subjectDn = "o=" + o + ";ou=" + ou + ";cn=" + cn;
        String subjectDnHashMd5 = HashHelper.md5(subjectDn);
        requestDto.setSubjectDn(subjectDn);
        requestDto.setSubjectDnHashMd5(subjectDnHashMd5);
        User user = (User) session.getAttribute("user");
        requestDto.setUserId(user.getId());
        Result<EnrollCertResponseDto> result = raCertApi.enrollCert(requestDto);
        String errorMsg;

        if (result.isSuccess()) {
            if (result.getData().getSuccess()) {
                request.setAttribute("message", "证书申请提交成功,请等待邮件通知!");
                return "pub/cert/message";
            } else {
                errorMsg = "证书申请提交失败,请联系管理员";
            }
        } else {
            errorMsg = "证书申请提交失败,错误代码:" + result.getFailureCode() + ";错误原因:" + result.getFailureMessage();
        }
        model.addAllAttributes(request.getParameterMap());
        model.addAttribute("errorMsg", errorMsg);
        return redirect("/cert/enroll.html");
    }

    @RequestMapping("/enrollCert.html")
    public void toEnrollCert() {

    }

    @RequestMapping("/enrollCert.do")
    public String enrollCert(String csr) {
        User user = (User) session.getAttribute("user");
        String fid = user.getFatherUserId();
        QueryUserRequestDto queryUserRequestDto = new QueryUserRequestDto();
        queryUserRequestDto.setId(fid);
        queryUserRequestDto.setPageAble(false);
        Result<QueryUserResponseDto> queryUserResult = userApi.query(queryUserRequestDto);
        User fUser = queryUserResult.getData().getUser();
        QueryCertRequestDto queryCertRequestDto = new QueryCertRequestDto();
        queryCertRequestDto.setCertType(CertType.SIGN.getCode());
        queryCertRequestDto.setUserId(fUser.getId());
        queryCertRequestDto.setPageAble(false);
        Result<QueryCertResponseDto> queryCertResult = certApi.queryCert(queryCertRequestDto);
        Cert issueCert = queryCertResult.getData().getCert();

        org.ca.ras.cert.dto.QueryCertRequestDto queryCertRequestDto1 = new org.ca.ras.cert.dto.QueryCertRequestDto();
        queryCertRequestDto1.setUserId(user.getId());
        queryCertRequestDto1.setPageAble(false);
        Result<org.ca.ras.cert.dto.QueryCertResponseDto> raQueryCertResult = raCertApi.queryCert(queryCertRequestDto1);
        org.ca.ras.cert.vo.Cert userCert = raQueryCertResult.getData().getCert();
        org.ca.cas.cert.dto.EnrollCertRequestDto enrollCertRequestDto = new org.ca.cas.cert.dto.EnrollCertRequestDto();
        enrollCertRequestDto.setCsr(csr);
        enrollCertRequestDto.setStartDate(new Date());
        enrollCertRequestDto.setIssueDn(issueCert.getIssuerDn());
        enrollCertRequestDto.setIssueDnHashMd5(issueCert.getIssuerDnHashMd5());
        enrollCertRequestDto.setUserId(user.getId());
        enrollCertRequestDto.setCertPin(userCert.getCertPin());
        enrollCertRequestDto.setSubjectDn(userCert.getSubjectDn());
        enrollCertRequestDto.setSubjectDnHashMd5(userCert.getSubjectDnHashMd5());
        Result<org.ca.cas.cert.dto.EnrollCertResponseDto> enrollCert = certApi.enrollCert(enrollCertRequestDto);
        if (enrollCert.isSuccess()) {
            String caCertId = enrollCert.getData().getCertId();
            QueryCertRequestDto queryCertRequestDto2 = new QueryCertRequestDto();
            queryCertRequestDto2.setPageAble(false);
            queryCertRequestDto2.setId(caCertId);
            queryCertResult = certApi.queryCert(queryCertRequestDto2);
            Cert caUserCert = queryCertResult.getData().getCert();
            ModifyCertRequestDto modifyCertRequestDto = new ModifyCertRequestDto();
            BeanUtils.copyProperties(caUserCert, modifyCertRequestDto);
            modifyCertRequestDto.setId(userCert.getId());
            Result<ModifyCertResponseDto> modifyResult = raCertApi.modifyCert(modifyCertRequestDto);
            if (modifyResult.isSuccess()) {
                return redirect("/cert/download.html?certId=" + userCert.getId());
            }
        }
        return redirect("/cert/enrollCert.html");
    }

    @RequestMapping("/download.html")
    public String toDownload(String certId) {
        request.setAttribute("certId", certId);
        return "pub/cert/download";
    }

    @RequestMapping("/download.do")
    public void download(String certId) throws IOException {
        QueryCertRequestDto requestDto = new QueryCertRequestDto();
        requestDto.setPageAble(false);
        requestDto.setId(certId);
        Result<QueryCertResponseDto> queryResult = certApi.queryCert(requestDto);
        if (queryResult.isSuccess()) {
            if (queryResult.getData().getSuccess()) {
                Cert cert = queryResult.getData().getCert();
                byte[] certBuf = Base64.decodeBase64(cert.getSignBuf());
                response.setContentType("application/x-x509-ca-cert");
                response.setHeader("Content-Disposition", "attachment;fileName=" + cert.getSerialNumber() + ".crt");
                response.getWriter().print(cert.getSignBuf());
                return;
                //application/x-x509-ca-cert
            }
        }
        response.setContentType("text/html");
        response.getOutputStream().println("证书下载失败:" + queryResult.getFailureMessage());
    }


}
