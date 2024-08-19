package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dto.conta.ContaDto;
import com.fourcamp.NutriPlan.dto.meta.MetaDto;
import com.fourcamp.NutriPlan.service.meta.MetaService;
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
public class MetaController {

    @Autowired
    private MetaService metaService;

    @Operation(description = "Criação da meta do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação da meta realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha na criação da meta, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/criar-meta")
    public ResponseEntity<String> criaMeta(@RequestBody MetaDto metaDto) {
        String response = String.valueOf(metaService.criarMeta(metaDto));
        return ResponseEntity.ok(Constantes.MSG_META_SUCESSO);
    }
}