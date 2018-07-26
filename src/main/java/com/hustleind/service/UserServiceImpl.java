package com.hustleind.service;

import com.hustleind.User;
import com.hustleind.UserDao;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl (UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getActiveUser() {
        org.springframework.security.core.userdetails.User activeUser =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUserByEmail(activeUser.getUsername());
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public boolean checkIfUserExistsByEmail(String email) {
        return getUserByEmail(email)!=null;
    }

    @Override
    public Object[] addUserByParams(MultiValueMap<String, String> incParam) {
        Object[] result = new Object[3];
        if (incParam == null || incParam.isEmpty()) {
            return result;
        }
        List<String> emailErrorMessages = new ArrayList<>();
        List<String> passwordErrorMessages = new ArrayList<>();
        List<String> otherMessages = new ArrayList<>();
        result[0] = emailErrorMessages;
        result[1] = passwordErrorMessages;
        result[2] = otherMessages;
        String email = incParam.get("email").get(0);
        String password = incParam.get("password").get(0);
        String repeatPassword = incParam.get("repeatPassword").get(0);
        if (!password.equals(repeatPassword)) {
            passwordErrorMessages.add("Passwords must be equal.");
            return result;
        }
        if (checkPasswordStrength(password) == false || checkPasswordLength(password) == false) {
            passwordErrorMessages.add("Password must be from 6 to 20 characters containing both letters and numbers.");
            return result;
        }
        if (checkEmailValidity(email) == false) {
            emailErrorMessages.add("Email address is not valid.");
            return result;
        }
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(CryptService.encrypt(password));
        userDao.addUser(user);
        return result;
    }


    private boolean checkEmailValidity(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean checkPasswordStrength(String password) {
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        boolean containsDigit = false;
        for (char ch: password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                containsUpperCase = true;
            }
            if (Character.isLowerCase(ch)) {
                containsLowerCase = true;
            }
            if (Character.isDigit(ch)) {
                containsDigit = true;
            }
        }
        return containsUpperCase&&containsLowerCase&&containsDigit;
    }

    private boolean checkPasswordLength(String password) {
        return (password.length() > 6 && password.length() < 20);
    }

    /*private boolean checkNameValidity(String name) {
        String pattern = "^\\D*$";
        return name.matches(pattern);
    }

    private boolean checkNameLength(String name) {
        return  name.length()<=128;
    }*/
}
