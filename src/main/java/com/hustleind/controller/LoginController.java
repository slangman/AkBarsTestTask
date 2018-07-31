package com.hustleind.controller;

import com.hustleind.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    private static Logger fileLogger = Logger.getLogger("file");

    @ApiOperation(value = "Login page")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        fileLogger.info("Someone opened login page");
        return "loginPage";
    }

    @ApiOperation(value = "Dashboard page")
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        fileLogger.info("User " + userService.getActiveUser().getEmail() + " logged in.");
        return "dashboard";
    }

    @ApiOperation(value = "Logout")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        fileLogger.info("User " + userService.getActiveUser().getEmail() + " logged out.");
        if (session.getAttribute("entered_user_id") != null) {
            session.removeAttribute("entered_email");
            session.removeAttribute("entered_role");
            session.removeAttribute("entered_user_id");
        }
        return "redirect:/j_spring_security_logout";
    }
}
