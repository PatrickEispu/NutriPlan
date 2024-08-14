package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.service.diario.DiarioService;
import com.fourcamp.NutriPlan.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiarioController {

    @Autowired
    DiarioService diarioService;

    @Autowired
    JwtUtils jwtUtils;


}
