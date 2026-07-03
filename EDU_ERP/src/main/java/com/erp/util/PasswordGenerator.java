package com.erp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "trainer123";   // 👈 change password here

        String encryptedPassword = encoder.encode(rawPassword);

        System.out.println("Plain Password  : " + rawPassword);
        System.out.println("Encrypted Hash  : " + encryptedPassword);
    }
}
