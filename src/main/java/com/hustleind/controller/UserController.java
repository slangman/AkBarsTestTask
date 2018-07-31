package com.hustleind.controller;

import com.hustleind.User;
import com.hustleind.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
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

    private static Logger fileLogger = Logger.getLogger("file");

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Edit profile page")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getEditUserPage(HttpSession session, Model model) {
        User currentUser = userService.getActiveUser();
        model.addAttribute("currentUser", currentUser);
        fileLogger.info("User " + currentUser.getEmail() + " opened profile edit page.");
        return "editProfile";
    }

    @ApiOperation(value = "Edit profile page")
    @RequestMapping(value="/editProfile", method=RequestMethod.POST)
    public String editProfile(@RequestBody MultiValueMap<String, String> incParam,
                              HttpSession session,
                              Model model) {
        User currentUser = userService.getActiveUser();
        Object[] messages = userService.editUser(incParam);
        List<String> passwordErrorMessages = (List) messages[0];
        List<String> nameErrorMessages = (List) messages[1];
        List<String> userPicErrorMessages = (List) messages[2];
        if (passwordErrorMessages.size()>0) {
            model.addAttribute("passwordErrors", passwordErrorMessages);
            fileLogger.info("User " + currentUser.getEmail() + " tried to update profile with no result because of password errors.");
            return "editProfile";
        }
        if (nameErrorMessages.size()>0) {
            model.addAttribute("nameErrors", nameErrorMessages);
            fileLogger.info("User " + currentUser.getEmail() + " tried to update profile with no result because of name errors.");
            return "editProfile";
        }
        if (userPicErrorMessages.size()>0) {
            model.addAttribute("userPicErrors", userPicErrorMessages);
            fileLogger.info("User " + currentUser.getEmail() + " tried to update profile with no result because of userPic errors.");
            return "editProfile";
        }
        //User currentUser = userService.getActiveUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("updateSuccessfull", "User updated successfully.");
        fileLogger.info("User " + currentUser.getEmail() + " updated profile data.");
        return "editProfile";
    }

}
