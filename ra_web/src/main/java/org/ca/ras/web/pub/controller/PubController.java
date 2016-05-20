package org.ca.ras.web.pub.controller;

import org.ca.cas.offlineca.api.OfflineCaApi;
import org.ligson.fw.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/26.
 */
@Controller
public class PubController extends BaseController {

    @Resource
    private OfflineCaApi offlineCaApi;

    @RequestMapping("/index.html")
    public String index() {
        logger.info("----------------{}",offlineCaApi);
        return "pub/index";
    }
}
