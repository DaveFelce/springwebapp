package com.davidfelce;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

public class PerformanceInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("HelloStinkyPre");
        System.out.println(System.getProperty("java.class.path"));
        return true;
    }

    public boolean postHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("HelloStinkyPost");
        return true;
    }

    public boolean afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("HelloStinkyAfterCompletion");
        return true;
    }
}
