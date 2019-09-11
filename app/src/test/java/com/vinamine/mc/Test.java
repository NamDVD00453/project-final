package com.vinamine.mc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    static final String PASSWORD = "123123aa";
    static final String SALT = "3a0f8fd4e533b5d9";
    static final String HASHPASSWORD = "$SHA$3a0f8fd4e533b5d9$de55aeec92efcf154c7a8e4f01ecd65aedeb1e9cbc020cd64ff3ea759d256c3e";
    public static void main(String[] args) {
        System.out.println(isValid(PASSWORD, SALT, HASHPASSWORD));
        System.out.println(hashPassword(PASSWORD, SALT));
    }

    static String hash(String password){
        MessageDigest digest = null;
        byte[] encodedhash = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            encodedhash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytesToHex(encodedhash);
    }

    static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    static String hashPassword(String pass, String salt){
        return "$SHA$" + salt + "$" + hash(hash(pass) + salt);
    }

    static Boolean isValid(String pass,String salt, String hashPass){
        System.out.println(hashPassword(pass, salt));
        System.out.println(hashPass);
        System.out.println("=====");
        return (hashPassword(pass, salt).equals(hashPass));
    }
}
