package org.ca.ras.web.pub.interceptor;

import org.ca.ras.web.admin.controller.CertMgrController;
import org.ca.ras.web.pub.controller.CertController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by ligson on 2016/4/26.
 */
public class WebInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(WebInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String methodName = method.getName();
            Object bean = handlerMethod.getBean();
            logger.debug("请求url:{}", request.getRequestURI());
            if (bean instanceof CertController) {
                Object token = request.getSession().getAttribute("user");
                if (token == null) {
                    response.sendRedirect("/user/login.html");
                    return false;
                }
            }
            if (bean instanceof CertMgrController) {
                Object token = request.getSession().getAttribute("adminUser");
                if (token == null) {
                    response.sendRedirect("/admin/login.html");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
