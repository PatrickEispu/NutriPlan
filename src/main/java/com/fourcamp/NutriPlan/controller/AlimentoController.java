package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dtos.alimento.AlimentoDto;
import com.fourcamp.NutriPlan.dtos.alimento.DispensaDto;
import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import com.fourcamp.NutriPlan.service.alimento.AlimentoService;

import com.fourcamp.NutriPlan.service.alimento.DispensaService;
import com.fourcamp.NutriPlan.service.alimento.RefeicaoService;
import com.fourcamp.NutriPlan.utils.Constantes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1")
public class AlimentoController {
    @Autowired
    private AlimentoService alimentoService;

    @Autowired
    RefeicaoService refeicaoService;

    @Autowired
    DispensaService dispensaService;


    @Operation(description = "Criar alimentos no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação do alimento com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha na criação do alimento, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/criar-alimento")
    public ResponseEntity<String> criarAlimento(@RequestBody AlimentoDto alimentoDto) {
        String response = String.valueOf(alimentoService.criarAlimento(alimentoDto));
        return ResponseEntity.ok(Constantes.MSG_CRIACAO_ALIMENTO_SUCESSO);
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

    @PostMapping("/{email}/adicionar-refeicao")
    public ResponseEntity<String> adicionarRefeicao(@PathVariable ("email")String email, @RequestBody List<AlimentoDto> alimentoDtoList) {
      //  String jwtToken = token.replace("Bearer ", "");
      //  String email = jwtUtils.getUserNameFromJwtToken(jwtToken);
        String mensagem = refeicaoService.adicionarRefeicao(email, alimentoDtoList);
        return ResponseEntity.ok(mensagem);
    }

    @PostMapping("/{email}/adicionar-alimento-dispensa")
    public ResponseEntity<String> adicionarAlimentoNaDispensa(@PathVariable("email") String email, @RequestBody List<DispensaDto> dispensaDtoList) {
        String msg = dispensaService.addAlimentoNaDispensa(email, dispensaDtoList);
        return ResponseEntity.ok(msg);
    }

}
