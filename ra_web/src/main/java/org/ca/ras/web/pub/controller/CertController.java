package org.ca.ras.web.pub.controller;

import org.ca.ras.cert.api.CertApi;
import org.ca.ras.cert.dto.EnrollCertRequestDto;
import org.ca.ras.cert.dto.EnrollCertResponseDto;
import org.ca.ras.cert.dto.IssueCertRequestDto;
import org.ca.ras.user.vo.User;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.string.encode.HashHelper;
import org.ligson.fw.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/26.
 */
@RequestMapping("/cert")
@Controller
public class CertController extends BaseController {
    @Resource
    private CertApi certApi;

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
        Result<EnrollCertResponseDto> result = certApi.enrollCert(requestDto);
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
}
