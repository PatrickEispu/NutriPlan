package com.fourcamp.NutriPlan.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtConfig {

    private static final Key CHAVE_SECRETA = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static Key getChaveSecreta() {
        return CHAVE_SECRETA;
    }
}
