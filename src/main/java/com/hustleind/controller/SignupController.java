package com.hustleind.controller;

import com.hustleind.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private static Logger logger = Logger.getLogger(SignupController.class);

    private String signupPage = "signup";
    private UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method= RequestMethod.GET)
    public String pageHandler() {
        return signupPage;
    }

    @RequestMapping(method=RequestMethod.POST)
    public String signupFormHandler(@RequestBody MultiValueMap<String, String> incParam, Model model) {
        Object[] messages = userService.addUserByParams(incParam);
        List<String> emailErrorMessages = (List) messages[0];
        List<String> passwordErrorMessages = (List) messages[1];
        if (emailErrorMessages.size()>0) {
            model.addAttribute("emailError", emailErrorMessages.get(0));
            return signupPage;
        }
        if (passwordErrorMessages.size()>0) {
            model.addAttribute("passwordError", passwordErrorMessages.get(0));
            return signupPage;
        }
        return "redirect:/login?signup=true";
    }
}