package com.example.demo.config;


import com.example.demo.pojo.JavaCache;
import com.example.demo.util.JWTUtil;
import com.example.demo.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

       if (token == null) {
           response.getWriter().print("401");
           response.setStatus(401);
           return false;
       }
        try {
//            if((JavaCache.get("token").equals(token))) {
//                Map<String, Object> stringObjectMap = JWTUtil.verifyToken(token);
//
//                ThreadLocalUtil.set(stringObjectMap);
//                return true;
//            }else
//            {
//                return false;
//            }

            Map<String, Object> stringObjectMap = JWTUtil.verifyToken(token);

                ThreadLocalUtil.set(stringObjectMap);
                return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
           return false;
        }

    }

        @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
// 操作ModelAndView
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
// 资源清理
        ThreadLocalUtil.remove();
    }
}
