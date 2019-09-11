package com.vinamine.mc.config;

import com.vinamine.mc.entity.User;

import java.util.HashMap;

public class Seeder {

    public static HashMap<String, String> accountList = new HashMap<String, String>();

    public static HashMap<String, User> userList = new HashMap<String, User>();

    public static void seed(){
        accountList.put(
                "dnzakmin@gmail.com",
                "123123aa"
        );

        User user = new User(
                "dnzakmin@gmail.com",
                "DNZakmin",
                "https://i.imgur.com/H2qAtxq.jpg",
                "Admin",
                1
        );

        userList.put(
                "dnzakmin@gmail.com",
                user
        );

    }
}
