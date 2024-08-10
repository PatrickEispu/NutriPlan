package com.fourcamp.NutriPlan.utils;

import org.springframework.stereotype.Service;

@Service
public class SenhaValidador {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%&])[A-Za-z\\d@#$%&]{8,}$";

    public static boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }
}
