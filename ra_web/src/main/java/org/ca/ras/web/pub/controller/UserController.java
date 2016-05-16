package org.ca.ras.web.pub.controller;

import org.ca.cas.user.api.UserApi;
import org.ca.cas.user.dto.*;
import org.ca.common.user.enums.LoginNameType;
import org.ca.cas.user.vo.User;
import org.ca.common.user.enums.UserRole;
import org.ligson.fw.core.facade.base.result.Result;
import org.ligson.fw.string.encode.HashHelper;
import org.ligson.fw.string.validator.EmailValidator;
import org.ligson.fw.string.validator.PhoneValidator;
import org.ligson.fw.web.controller.BaseController;
import org.ligson.fw.web.vo.WebResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Enumeration;

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
        return "pub/user/login";
    }

    @RequestMapping("/login.do")
    public String login(LoginRequestDto requestDto) {
        String loginName = requestDto.getLoginName();
        if (PhoneValidator.isMobile(loginName)) {
            requestDto.setLoginNameType(LoginNameType.MOBILE);
        } else if (EmailValidator.isValidEmail(loginName)) {
            requestDto.setLoginNameType(LoginNameType.EMAIL);
        } else {
            requestDto.setLoginNameType(LoginNameType.NAME);
        }
        requestDto.setPassword(HashHelper.md5(requestDto.getPassword()));

        if (!requestDto.validate()) {
            String errorMsg = "";
            for (String e : requestDto.getErrorFieldMap().values()) {
                errorMsg += e + "<br/>";
            }
            model.addAttribute("errorMsg", errorMsg);
            return redirect("/user/login.html");
        }

        Result<LoginResponseDto> result = userApi.login(requestDto);
        if (result.isSuccess()) {
            User user = result.getData().getUser();
            session.setAttribute("user", user);
            return redirect("/cert/index.html");
        } else {
            model.addAttribute("errorMsg", result.getFailureMessage());
            return redirect("/user/login.html");
        }
    }

    @RequestMapping("/register.html")
    public String toReg() {
        logger.info("to login.........");
        Enumeration<String> names = request.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            Object value = request.getAttribute(name);
            logger.info("name={},value={},valueType={}", name, value, value.getClass().getName());
        }
        return "pub/user/register";
    }

    @RequestMapping("/register.do")
    public String register(RegisterRequestDto requestDto) {
        requestDto.setVersion("----------");
        if (!requestDto.validate()) {
            logger.error("参数不正确:{}", requestDto.getErrorFieldMap());
            model.addAttribute("errorMsg", "参数不正确格式无效");
            return redirect("/user/register.html");
        }
        requestDto.setPassword(HashHelper.md5(requestDto.getPassword()));
        Result<RegisterResponseDto> result = userApi.register(requestDto);
        if (result.isSuccess()) {
            ModifyUserRequestDto modifyUserRequestDto = new ModifyUserRequestDto();
            modifyUserRequestDto.setRole(UserRole.USER.getCode());
            modifyUserRequestDto.setId(result.getData().getId());
            Result<ModifyUserResponseDto> modifyResult = userApi.modify(modifyUserRequestDto);
            logger.debug("modify result:{}", modifyResult);
            return redirect("/user/login.html");
        } else {
            model.addAttribute("errorMsg", result.getFailureMessage());
            return redirect("/user/register.html");
        }
    }

    @ResponseBody
    @RequestMapping("/checkLoginName.json")
    public WebResult checkLoginName(QueryUserRequestDto requestDto) {
        Result<QueryUserResponseDto> result = userApi.query(requestDto);
        if (result.isSuccess()) {
            boolean valid = result.getData().getUserList().size() == 0;
            webResult.setSuccess(true);
            webResult.put("valid", valid);
        } else {
            webResult.setSuccess(false);
            webResult.put("valid", false);
        }
        return webResult;
    }

}
