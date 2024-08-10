package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dto.AlimentoDto;
import com.fourcamp.NutriPlan.model.Alimento;
import com.fourcamp.NutriPlan.service.AlimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @PostMapping("/criar-alimento")
    @Operation(description = "Criar alimentos no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação do alimento com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha na criação do alimento, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> criarAlimento(@RequestBody AlimentoDto alimento) {
        String mensagem = alimentoService.criarAlimento(alimento.getKcal(), alimento.getCarboidrato(), alimento.getProteina(), alimento.getGordura(), alimento.getQuantidade(), alimento.getNome());

        return ResponseEntity.ok().body(mensagem);
    }

    @GetMapping("/mostrar-alimentos")
    @Operation(description = "Lista todos os alimentos do banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os alimentos"),
            @ApiResponse(responseCode = "400", description = "Falha no retorno dos alimentos, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<Alimento> mostrarAlimentos() {
        return alimentoService.visualizarAlimentos();
    }
}
