package org.ca.ras.web.admin.controller;

import org.ligson.fw.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ligson on 2016/5/6.
 */
@Controller
@RequestMapping("/admin/certMgr")
public class CertMgrController extends BaseController {
    @RequestMapping("/index.html")
    public String index() {
        return null;
    }
}
