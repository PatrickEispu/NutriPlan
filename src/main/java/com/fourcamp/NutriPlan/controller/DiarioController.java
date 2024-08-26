package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.service.conta.ClienteService;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import com.fourcamp.NutriPlan.service.diario.DiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class DiarioController {

    @Autowired
    DiarioService diarioService;
    @Autowired
    ContaService contaService;

@GetMapping("/{email}/acessarDiario")
    public ResponseEntity<String> acessarDiario(@PathVariable("email")String email)
{
    String msg = diarioService.getDiarioListToString(email);
    return ResponseEntity.ok(msg);
}

}
