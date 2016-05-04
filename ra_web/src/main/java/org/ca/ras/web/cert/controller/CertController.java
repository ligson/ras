package org.ca.ras.web.cert.controller;

import org.ca.ras.cert.api.CertApi;
import org.ca.ras.cert.dto.IssueCertRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/26.
 */
@RequestMapping("/cert")
@Controller
public class CertController {
    @Resource
    private CertApi certApi;

    @RequestMapping("/index.html")
    public String index() {
        return "cert/index";
    }

    @RequestMapping("/enroll.html")
    public String toEnroll() {
        return "cert/enroll";
    }
    public String enroll(IssueCertRequestDto requestDto){
        //certApi.issueCert()
        return null;
    }
}
