package com.hustleind.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login() {
        logger.info("User logged in");
        return "loginPage";
    }


}
