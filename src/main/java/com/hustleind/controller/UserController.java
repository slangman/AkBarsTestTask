package com.hustleind.controller;

import com.hustleind.User;
import com.hustleind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public String getEditUserPage(HttpSession session, Model model) {
        User currentUser = userService.getActiveUser();
        model.addAttribute("currentUser", currentUser);
        return "editProfile";
    }

}
