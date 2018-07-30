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
    public UserServiceImpl(UserDao userDao) {
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
        return getUserByEmail(email) != null;
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
            logger.warn("Unable to create user. Entered passwords are not equal");
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
        if (userDao.getUserByEmail(email) != null) {
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

    @Override
    public Object[] editUser(MultiValueMap<String, String> incParam) {
        Object[] result = new Object[3];
        if (incParam == null || incParam.isEmpty()) {
            logger.error("incParam is null. User data fields are not presented.");
            return result;
        }
        List<String> passwordErrorMessage = new ArrayList<>();
        List<String> nameErrorMessage = new ArrayList<>();
        List<String> userPicErrorMessage = new ArrayList<>();
        result[0] = passwordErrorMessage;
        result[1] = nameErrorMessage;
        result[2] = userPicErrorMessage;
        String newPassword = incParam.get("newPassword").get(0);
        String repeatNewPassword = incParam.get("repeatNewPassword").get(0);
        String email = incParam.get("email").get(0);
        String fName = incParam.get("fName").get(0);
        String mName = incParam.get("mName").get(0);
        String lName = incParam.get("lName").get(0);
        String userPicURL = incParam.get("userPicURL").get(0);
        User user = userDao.getUserByEmail(email);
        if ((newPassword != null && !newPassword.isEmpty()) || (repeatNewPassword != null && !repeatNewPassword.isEmpty())) {
            logger.info("Attempt to update password. New password value is " + newPassword + ", repeated password value is " + repeatNewPassword);
            if (!newPassword.equals(repeatNewPassword)) {
                passwordErrorMessage.add("Passwords must be equal.");
                logger.warn("Unable to update user. Entered passwords are not equal");
                return result;
            }
            if (checkPasswordLength(newPassword) == false || checkPasswordStrength(newPassword) == false) {
                passwordErrorMessage.add("Password must be from 6 to 20 characters containing lowercase, uppercase letters and numbers.");
                return result;
            }
            user.setPasswordHash(CryptService.encrypt(newPassword));
        }
        if (fName != null) {
            if (!fName.isEmpty()) {
                if (checkNameValidity(fName) == false || checkNameLength(fName) == false) {
                    nameErrorMessage.add("First name must be from 1 to 128 characters containing only letters");
                    return result;
                }
                user.setFName(fName);
            } else {
                user.setFName(fName);
            }
        }
        if (mName != null) {
            if (!mName.isEmpty()) {
                if (checkNameValidity(mName) == false || checkNameLength(mName) == false) {
                    nameErrorMessage.add("Middle name must be from 1 to 128 characters containing only letters");
                    return result;
                }
                user.setMName(mName);
            } else {
                user.setMName(mName);
            }
        }
        if (lName != null) {
            if (!lName.isEmpty()) {
                if (checkNameValidity(lName) == false || checkNameLength(lName) == false) {
                    nameErrorMessage.add(("Last name must be from 1 to 128 characters containing only letters"));
                    return result;
                }
                user.setLName(lName);
            } else {
                user.setLName(lName);
            }
        }
       /* if (nameErrorMessage.size() > 0) {
            return result;
        }*/
        if (userPicURL!=null) {
            if (!userPicURL.isEmpty()) {
                if (checkURLIsImage(userPicURL) == false) {
                    userPicErrorMessage.add("User picture url must be valid and lead to picture with one of these extensions: *.jpg, *.jpeg, *.bmp, *.gif, *.png");
                    return result;
                }
                user.setUserPicURL(userPicURL);
            } else {
                user.setUserPicURL(userPicURL);
            }
        }
        userDao.updateUser(user);
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
        for (char ch : password.toCharArray()) {
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
        return containsUpperCase && containsLowerCase && containsDigit;
    }

    private boolean checkPasswordLength(String password) {
        return (password.length() >= 6 && password.length() <= 20);
    }

    private boolean checkUserPicURL(String url) {
        return checkURLValidity(url)&&checkURLIsImage(url);
    }

    private boolean checkURLValidity(String url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkURLIsImage(String url) {
        boolean result=false;
        String[] extensions = {".jpg", ".jpeg", ".png", ".gif", ".png"};
        for (int i = 0; i < extensions.length; i++) {
            if (url.endsWith(extensions[i])) {
                result=true;
            }
        }
        return result;
    }

    private boolean checkNameValidity(String name) {
        String pattern = "^\\D*$";
        return name.matches(pattern);
    }

    private boolean checkNameLength(String name) {
        return (0 < name.length() && name.length() <= 128);
    }
}
