package com.hustleind.controller.filter;

import com.hustleind.User;
import com.hustleind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StartHandlerInterceptor implements HandlerInterceptor {
    private UserService userService;

    @Autowired
    public StartHandlerInterceptor(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("entered_user_id") == null) {
            User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            addStartAttributeToSession(session, user);
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
        return true;
    }

    private void addStartAttributeToSession(HttpSession session, User user) {
        session.setAttribute("entered_user_id", user.getId());
        session.setAttribute("entered_email", user.getEmail());
        session.setAttribute("entered_role", "ROLE_USER");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
