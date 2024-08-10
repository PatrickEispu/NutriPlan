package com.fourcamp.NutriPlan.utils;

import com.fourcamp.NutriPlan.dto.JwtData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;

public class JwtUtils {

    public static JwtData decodeToken(String token) {
        Key chaveSecreta = JwtConfig.getChaveSecreta();

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(chaveSecreta)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = jws.getBody();

            JwtData jwtData = new JwtData();
            jwtData.setNome(claims.get("nome", String.class));
            jwtData.setEmail(claims.get("email", String.class));
            jwtData.setGenero(claims.get("genero", String.class));
            jwtData.setPeso(claims.get("peso", Double.class));
            jwtData.setPesoDesejado(claims.get("peso_desejado", Double.class));
            jwtData.setAltura(claims.get("altura", Double.class));
            jwtData.setDataNascimento(claims.get("data_nascimento" , Date.class));
            jwtData.setSenha(claims.get("senha",String.class));
            jwtData.setCategoria(claims.get("categoria", String.class));
            jwtData.setTempoMeta(claims.get("tempo_meta",String.class));

            return jwtData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
