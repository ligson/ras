package org.ca.ras.web.user.controller;

import org.ca.ras.user.api.UserApi;
import org.ca.ras.user.dto.RegisterRequestDto;
import org.ca.ras.user.dto.RegisterResponseDto;
import org.ca.ras.web.common.controller.BaseController;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.string.encode.HashHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by ligson on 2016/4/26.
 * 用户
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserApi userApi;

    @RequestMapping("/login.html")
    public String toLogin() {
        logger.info("to login.........");
        return "user/login";
    }

    @RequestMapping("/login.do")
    public void login() {
    }

    @RequestMapping("/register.html")
    public String toReg() {
        logger.info("to login.........");
        return "user/register";
    }

    @RequestMapping("/register.do")
    public String register(RegisterRequestDto requestDto) {
        //requestDto.setPassword(HashHelper.md5(requestDto.getPassword()));
        requestDto.setVersion("----------");
        if (!requestDto.validate()) {
            logger.error("参数不正确:{}", requestDto.getErrorFieldMap());
            request.setAttribute("errorFieldMap", requestDto.getErrorFieldMap());
            return forward("/user/register.html");
        }
        //requestDto.is
        Result<RegisterResponseDto> result = userApi.register(requestDto);
        if (result.isSuccess()) {
            return redirect("/user/login.html");
        } else {
            request.setAttribute("errorMsg", result.getFailureMessage());
            return forward("/user/register.html");
        }
    }

}
