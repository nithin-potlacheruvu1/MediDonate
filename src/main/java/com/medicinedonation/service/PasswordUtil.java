package com.medicinedonation.service;

import java.util.Base64;

public class PasswordUtil {

    // Encode password to Base64
    public static String encodePassword(String plainPassword) {
        return Base64.getEncoder().encodeToString(plainPassword.getBytes());
    }

    // Decode password from Base64
    public static String decodePassword(String encodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes);
    }
}
