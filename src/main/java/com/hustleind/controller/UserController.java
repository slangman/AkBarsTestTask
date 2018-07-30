package com.hustleind.controller;

import com.hustleind.User;
import com.hustleind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getEditUserPage(HttpSession session, Model model) {
        User currentUser = userService.getActiveUser();
        model.addAttribute("currentUser", currentUser);
        return "editProfile";
    }

    @RequestMapping(value="/editProfile", method=RequestMethod.POST)
    public String editProfile(@RequestBody MultiValueMap<String, String> incParam,
                              HttpSession session,
                              Model model) {
        /*User currentUser = userService.getActiveUser();
        model.addAttribute(currentUser);*/
        Object[] messages = userService.editUser(incParam);
        List<String> passwordErrorMessages = (List) messages[0];
        List<String> nameErrorMessages = (List) messages[1];
        List<String> userPicErrorMessages = (List) messages[2];
        if (passwordErrorMessages.size()>0) {
            model.addAttribute("passwordErrors", passwordErrorMessages);
            return "editProfile";
        }
        if (nameErrorMessages.size()>0) {
            model.addAttribute("nameErrors", nameErrorMessages);
            return "editProfile";
        }
        if (userPicErrorMessages.size()>0) {
            model.addAttribute("userPicErrors", userPicErrorMessages);
            return "editProfile";
        }
        User currentUser = userService.getActiveUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("updateSuccessfull", "User updated successfully.");
        return "editProfile";
    }

}
