package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dto.JwtData;
import com.fourcamp.NutriPlan.dto.RefeicaoRequest;
import com.fourcamp.NutriPlan.exception.AlimentoNotFoundException;
import com.fourcamp.NutriPlan.service.DiarioService;
import com.fourcamp.NutriPlan.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiarioController {

    @Autowired
    DiarioService diarioService;

    @PostMapping("/adicionar-refeicao")
    public ResponseEntity<String> adicionarRefeicao(@RequestHeader("Authorization") String token, @RequestBody RefeicaoRequest refeicaoRequest) {
        JwtData jwtData = JwtUtils.decodeToken(token);
        String mensagem = diarioService.adicionarRefeicao(jwtData, refeicaoRequest);
        return ResponseEntity.ok(mensagem);
    }
}