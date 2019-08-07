package com.huaxh.oa.global;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 登录拦截器
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //获取当前请求的路径
        String url = httpServletRequest.getRequestURI();
        //查询是否包含login关键字 忽略大小写直接转换为小写
        if (url.toLowerCase().indexOf("login") >= 0) {
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("employee") != null) return true;

        httpServletResponse.sendRedirect("to_login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
