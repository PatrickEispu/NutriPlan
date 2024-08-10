package com.fourcamp.NutriPlan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testeController {

    @GetMapping("/health")
    public String TesteServidor(){
        return "Ok";
    }

}
