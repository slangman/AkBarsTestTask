package com.hustleind.controller;

import com.hustleind.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    private static Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login() {
        logger.info("User logged in");
        return "loginPage";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "dashboard";
    }
}
