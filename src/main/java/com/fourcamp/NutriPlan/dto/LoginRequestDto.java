package com.fourcamp.NutriPlan.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoginRequestDto {

    private String email;
    private String senha;

}
