package com.hustleind.service;

import com.hustleind.User;
import org.springframework.util.MultiValueMap;

public interface UserService {
    User getActiveUser();
    User getUserById(int id);
    User getUserByEmail(String email);
    boolean checkIfUserExistsByEmail(String email);
    Object[] addUserByParams(MultiValueMap<String, String> incParam);
}
