package org.ca.ras.web.pub.controller;

import org.ligson.fw.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ligson on 2016/4/26.
 */
@Controller
public class PubController extends BaseController {

    @RequestMapping("/index.html")
    public String index() {
        return "pub/index";
    }
}
