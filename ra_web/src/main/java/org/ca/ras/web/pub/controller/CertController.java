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
        User user = (User) session.getAttribute("user");
        org.ca.ras.cert.dto.QueryCertRequestDto requestDto = new org.ca.ras.cert.dto.QueryCertRequestDto();
        requestDto.setUserId(user.getId());
        requestDto.setPageAble(false);
        Result<org.ca.ras.cert.dto.QueryCertResponseDto> queryResult = raCertApi.queryCert(requestDto);
        if (queryResult.isSuccess()) {
            if (queryResult.getData().getCert() != null) {
                request.setAttribute("message", "证书已申请");
                return "pub/cert/message";
            }
        }
        return "pub/cert/enroll";
    }

    @RequestMapping("/enroll.do")
    public String enroll(EnrollCertRequestDto requestDto) {
        String o = request.getParameter("o");
        String ou = request.getParameter("ou");
        String cn = request.getParameter("cn");
        String subjectDn = "O=" + o + ",OU=" + ou + ",CN=" + cn;
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
    public String toEnrollCert() {
        //MIICVjCCAT4CAQAwEzERMA8GA1UECgwIbGVjeGU7b3UwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCC0OJdAyIrSg/8QFJPvFZL9cx+kf+31PuKkH1de2nk/0zXRWDxC8r7k8R53cW0Bx9wSFfInKFza3uiRts8x5drnThn2ecvktNpRMHgEgu9C6+y1+thJlZ4pCrxMX82bwmNJHTZ81xmau7Kb3Z3aPVTWX3ryDFHMPho8TcYWFQgucFczr+V+XQ5CR/mB6RR/uXZoVWUVCiCnwJclt8osvKNtRYf7QvlhWiD0lzyt87SlhYIZi+zbtdstlSONSOJggsCSxiBzwfIA6DeGWbWAo3OSMm1n8aetc02y05xcVEjE9QtCEmYC3S860xU6L6Yq/UzWoFXhK25JwUW5lUL8DpzAgMBAAEwDQYJKoZIhvcNAQEFBQADggEBAD6FRqmyUWH9yUYSI8KCbzN5tPCY3+BNaZjI8zTDapgUpG+Nyjtq15L84LKOH85sMS4IIKWMG+OuI8sn2El5ppYpPL8FPLiF88TRSkNLT5qYkpYtxL/xP4y9uy5rw2QZ4QvGd2TPPrmxENb91U79Z7uFzmBNQGL027sE+L0yh2qCCnOktkyChDGoUg15xcXwnJcp6xE4YLToUyjpQLKQT1o0hlIF1R13fpuBStgFuPc24dX65c9Tur2GCYVX4iqYax8nMwHwXxj3tujc9TGCbhdqMfmPtbbSSxoK0KhsgBDBRzXSP7KZWGorIRtQGzVB6aPPAMfTSeLWFS0m03o6lek=
        return "pub/cert/enrollCert";
    }

    @RequestMapping("/enrollCert.do")
    public String enrollCert(String csr) {
        User user = (User) session.getAttribute("user");
        QueryUserRequestDto queryUserRequestDto = new QueryUserRequestDto();
        queryUserRequestDto.setId(user.getId());
        queryUserRequestDto.setPageAble(false);
        Result<QueryUserResponseDto> queryUserResult = userApi.query(queryUserRequestDto);
        user = queryUserResult.getData().getUser();


        String fid = user.getFatherUserId();
        queryUserRequestDto = new QueryUserRequestDto();
        queryUserRequestDto.setId(fid);
        queryUserRequestDto.setPageAble(false);
        queryUserResult = userApi.query(queryUserRequestDto);
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
        enrollCertRequestDto.setIssueDn(issueCert.getSubjectDn());
        enrollCertRequestDto.setIssueDnHashMd5(issueCert.getSubjectDnHashMd5());
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
        org.ca.ras.cert.dto.QueryCertRequestDto requestDto = new org.ca.ras.cert.dto.QueryCertRequestDto();
        requestDto.setPageAble(false);
        requestDto.setId(certId);
        Result<org.ca.ras.cert.dto.QueryCertResponseDto> queryResult = raCertApi.queryCert(requestDto);
        if (queryResult.isSuccess()) {
            if (queryResult.getData().getSuccess()) {
                org.ca.ras.cert.vo.Cert cert = queryResult.getData().getCert();
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

    @RequestMapping("/view.html")
    public String toViewUserCert() {
        if (session.getAttribute("cert") == null) {
            request.setAttribute("message", "用户证书不存在");
            return "pub/cert/message";
        }
        return "pub/cert/view";
    }


}
