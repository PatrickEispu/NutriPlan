package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dtos.conta.ClienteDto;
import com.fourcamp.NutriPlan.dtos.conta.ContaDto;
import com.fourcamp.NutriPlan.service.conta.ContaService;
import com.fourcamp.NutriPlan.utils.Constantes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Operation(description = "Criar Conta do Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação da conta realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha na criação da conta, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/criar-conta")
    public ResponseEntity<String> criaConta(@RequestBody ClienteDto clienteDto) {
        String response = String.valueOf(contaService.criarConta(clienteDto));
        return ResponseEntity.ok(Constantes.MSG_CRIACAO_CONTA_SUCESSO);
    }
}
