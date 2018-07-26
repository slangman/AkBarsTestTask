package com.hustleind.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CryptService {
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private CryptService() {
    }

    public static String encrypt(String pswrd) {
        return passwordEncoder.encode(pswrd);
    }

    public static boolean isMatches(String pswrd, String hash) {
        return passwordEncoder.matches(pswrd, hash);
    }
}
