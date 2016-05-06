package org.ca.ras.web.admin.controller;

import org.ca.common.user.enums.LoginNameType;
import org.ca.common.user.enums.UserRole;
import org.ca.ras.user.api.UserApi;
import org.ca.ras.user.dto.LoginRequestDto;
import org.ca.ras.user.dto.LoginResponseDto;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.string.encode.HashHelper;
import org.ligson.fw.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/5/6.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Resource
    private UserApi userApi;

    @RequestMapping("/login.html")
    public String toLogin() {
        logger.info("to login.........");
        String errorMsg = request.getParameter("errorMsg");
        if (errorMsg != null) {
            request.setAttribute("errorMsg", errorMsg);
        }
        return "admin/login";
    }

    @RequestMapping("/login.do")
    public ModelAndView login(LoginRequestDto requestDto) {
        ModelAndView modelAndView = new ModelAndView();
        String loginName = requestDto.getLoginName();
        requestDto.setLoginNameType(LoginRequestDto.getByLoginName(loginName));
        requestDto.setPassword(HashHelper.md5(requestDto.getPassword()));

        if (!requestDto.validate()) {
            String errorMsg = "";
            for (String e : requestDto.getErrorFieldMap().values()) {
                errorMsg += e + "<br/>";
            }
            modelAndView.setViewName("redirect:/admin/login.html");
            modelAndView.addObject("errorMsg", errorMsg);
            return modelAndView;
        }

        Result<LoginResponseDto> result = userApi.login(requestDto);
        if (result.isSuccess()) {
            Integer role = result.getData().getUser().getRole();
            if (!role.equals(UserRole.RA_ADMIN.getCode())) {
                modelAndView.setViewName("redirect:/admin/login.html");
                modelAndView.addObject("errorMsg", "没有权限");
                return modelAndView;
            } else {
                session.setAttribute("adminUser", result.getData().getUser());
            }
        } else {
            modelAndView.setViewName("redirect:/admin/login.html");
            modelAndView.addObject("errorMsg", result.getFailureMessage());
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/admin/certMgr/index.html");
        modelAndView.addObject("errorMsg", result.getFailureMessage());
        return modelAndView;
    }
}
