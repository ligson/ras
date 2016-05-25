package org.ca.ras.web.pub.controller;

import org.ca.cas.cert.api.CertApi;
import org.ca.cas.cert.dto.ImportCaCertRequestDto;
import org.ca.cas.cert.dto.ImportCaCertResponseDto;
import org.ca.cas.offlineca.api.OfflineCaApi;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/26.
 */
@Controller
public class PubController extends BaseController {


    @Resource
    private CertApi certApi;

    @RequestMapping("/index.html")
    public String index() {
        return "pub/index";
    }

    @RequestMapping("/uploadCaCert.html")
    public String toUploadCaCert() {
        return "pub/cert/uploadCaCert";
    }

    @RequestMapping("/uploadCaCert.do")
    public String uploadCaCert(ImportCaCertRequestDto requestDto, @RequestParam("certFile") CommonsMultipartFile certFile) {
        String adminId = session.getAttribute("initUserId").toString();
        requestDto.setAdminId(adminId);
        requestDto.setCertBuf(new String(certFile.getBytes()));
        Result<ImportCaCertResponseDto> importResult = certApi.importCaCert(requestDto);
        if (importResult.isSuccess()) {
            return redirect("/admin/login.html");
        } else {
            model.addAttribute("errorMsg", importResult.getFailureMessage());
            return redirect("/uploadCaCert.html");
        }
    }
}
