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
        return "user/login";
    }

    @RequestMapping("/login.do")
    public String login(LoginRequestDto requestDto) {
        String loginName = requestDto.getLoginName();
        requestDto.setLoginNameType(LoginRequestDto.getByLoginName(loginName));
        requestDto.setPassword(HashHelper.md5(requestDto.getPassword()));

        if (!requestDto.validate()) {
            String errorMsg = "";
            for (String e : requestDto.getErrorFieldMap().values()) {
                errorMsg += e + "<br/>";
            }
            return redirect("/admin/login.html?errorMsg=" + errorMsg);
        }

        Result<LoginResponseDto> result = userApi.login(requestDto);
        if (result.isSuccess()) {
            if (result.getData().getUser().getRole() != UserRole.RA_ADMIN.getCode()) {
                return redirect("/admin/login.html?errorMsg=没有权限");
            } else {
                session.setAttribute("adminUser", result.getData().getUser());
            }
        } else {
            return redirect("/admin/login.html?errorMsg=" + result.getFailureMessage());
        }
        return redirect("/admin/certMgr/index.html");
    }
}
