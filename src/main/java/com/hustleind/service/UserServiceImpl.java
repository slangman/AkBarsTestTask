package com.hustleind.service;

import com.hustleind.User;
import com.hustleind.UserDao;
import org.apache.commons.validator.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

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
            logger.warn("Unable to create user. Password is out of allowed length or too weak.");
            passwordErrorMessages.add("Password must be from 6 to 20 characters containing lowercase, uppercase letters and numbers.");
            return result;
        }
        if (checkEmailValidity(email) == false) {
            logger.warn("Unable to create user. Email address is incorrect.");
            emailErrorMessages.add("Email address is not valid.");
            return result;
        }
        if (userDao.getUserByEmail(email)!=null) {
            logger.warn("Unable to create user. User with email " + email + " already exists.");
            emailErrorMessages.add("User with that email already exists");
            return result;
        }
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(CryptService.encrypt(password));
        user.setEnabled(1);
        userDao.addUser(user);
        logger.info("User with email " + email + " successfully created");
        return result;
    }

    public Object[] updateUser(MultiValueMap<String, String> incParam) {
        Object[] result = new Object[3];
        return null;
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
        return (password.length() >= 6 && password.length() <= 20);
    }

    private boolean checkURLValidity(String url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            return(con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*private boolean checkNameValidity(String name) {
        String pattern = "^\\D*$";
        return name.matches(pattern);
    }

    private boolean checkNameLength(String name) {
        return  name.length()<=128;
    }*/
}
