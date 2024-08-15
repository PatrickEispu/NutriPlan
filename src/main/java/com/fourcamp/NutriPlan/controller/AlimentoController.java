package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dto.alimento.AlimentoDto;
import com.fourcamp.NutriPlan.dto.alimento.RefeicaoDto;
import com.fourcamp.NutriPlan.dto.alimento.RefeicaoRequest;
import com.fourcamp.NutriPlan.model.alimento.Alimento;
import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import com.fourcamp.NutriPlan.service.alimento.AlimentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class AlimentoController {
    @Autowired
    private AlimentoService alimentoService;
    @Autowired
    RefeicaoService refeicaoService;


    @Operation(description = "Criar alimentos no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação do alimento com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha na criação do alimento, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/criar-alimento")
    public ResponseEntity<String> criarAlimento( @RequestBody AlimentoDto alimento) {
        String mensagem = alimentoService.criarAlimento(
                alimento.getIdCategoriaAlimento(),
                alimento.getKcal(),
                alimento.getCarboidrato(),
                alimento.getProteina(),
                alimento.getGordura(),
                alimento.getQuantidade(),
                alimento.getNome()
        );
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/mostrar-alimentos")
    @Operation(description = "Lista todos os alimentos do banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os alimentos"),
            @ApiResponse(responseCode = "400", description = "Falha no retorno dos alimentos, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<AlimentoEntity> mostrarAlimentos() {
        return alimentoService.visualizarAlimentos();
    }

    @PostMapping("/adicionar-refeicao")
    public ResponseEntity<String> adicionarRefeicao(@RequestHeader("Authorization") String token, @RequestBody List<AlimentoDto> alimentoDtoList) {
        String jwtToken = token.replace("Bearer ", "");
        String email = jwtUtils.getUserNameFromJwtToken(jwtToken);
        String mensagem = refeicaoService.adicionarRefeicao(email, alimentoDtoList);
        return ResponseEntity.ok(mensagem);
    }
}
