package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dto.alimento.RefeicaoRequest;
import com.fourcamp.NutriPlan.service.diario.DiarioService;
import com.fourcamp.NutriPlan.security.jwt.JwtUtils;
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

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/adicionar-refeicao")
    public ResponseEntity<String> adicionarRefeicao(@RequestHeader("Authorization") String token, @RequestBody RefeicaoRequest refeicaoRequest) {
        String jwtToken = token.replace("Bearer ", "");
        String email = jwtUtils.getUserNameFromJwtToken(jwtToken);
        String mensagem = diarioService.adicionarRefeicao(email, refeicaoRequest);
        return ResponseEntity.ok(mensagem);
    }
}
