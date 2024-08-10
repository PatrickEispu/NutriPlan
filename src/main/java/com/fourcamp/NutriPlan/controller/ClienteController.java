package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dto.ClienteDto;
import com.fourcamp.NutriPlan.dto.JwtData;
import com.fourcamp.NutriPlan.dto.LoginRequestDto;
import com.fourcamp.NutriPlan.dto.PesoDto;
import com.fourcamp.NutriPlan.dto.CategoriaAtividadeRequestDto;
import com.fourcamp.NutriPlan.model.Cliente;
import com.fourcamp.NutriPlan.service.ClienteService;
import com.fourcamp.NutriPlan.service.ObjetivoService;
import com.fourcamp.NutriPlan.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ObjetivoService objetivoService;

    @PostMapping("/create/account")
    @Operation(description = "Criação da conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação da conta com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha na criação da conta, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente) {
        Date dataSql = new Date(cliente.getDataNascimento().getTime());
        String mensagem = clienteService.criarCliente(
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getGenero(),
                cliente.getPeso(),
                cliente.getPesoDesejado(),
                cliente.getAltura(),
                dataSql,
                cliente.getSenha(),
                cliente.getCategoria(),
                cliente.getTempoMeta()
        );

        return ResponseEntity.ok().body(mensagem);
    }

    @PostMapping("/login")
    @Operation(description = "Fazer o login da conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha no login, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public String login(@RequestBody LoginRequestDto loginRequest) {
        return clienteService.login(loginRequest.getEmail(), loginRequest.getSenha());
    }

    @PostMapping("/atualizar-peso")
    @Operation(description = "Atualiza o peso do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização do peso com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha na atualização do peso, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "401", description = "Não autorizado, token inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> alterarPeso(@RequestHeader("Authorization") String token, @RequestBody PesoDto novoPeso) {
        JwtData jwtData = JwtUtils.decodeToken(token);

        String mensagem = clienteService.alterarPeso(jwtData.getEmail(), novoPeso.getNovoPeso());
        return ResponseEntity.ok(mensagem);
    }

    @PostMapping("/formulario")
    @Operation(description = "Formulario do objetivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formulario respondido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Falha no preenchimento do formulario, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "401", description = "Não autorizado, token inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> formularioObjetivo(@RequestHeader("Authorization") String token, @RequestBody ClienteDto cliente) {
        JwtData jwtData = JwtUtils.decodeToken(token);

        String mensagem = clienteService.formularioObjetivo(jwtData.getEmail(), cliente.getCategoria(), cliente.getTempoMeta());
        return ResponseEntity.ok(mensagem);
    }

    @PostMapping("/Geb")
    @Operation(description = "Visualizar o gasto energético basal do cliente logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno do GET"),
            @ApiResponse(responseCode = "400", description = "Falha no retorno do GET, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "401", description = "Não autorizado, token inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Double> visualizarTMB(@RequestHeader("Authorization") String token, @RequestBody CategoriaAtividadeRequestDto request) {
        JwtData jwtData = JwtUtils.decodeToken(token);

        return ResponseEntity.ok(objetivoService.calcularGETSalvar(jwtData, request.getCategoriaAtividade()));
    }

    @GetMapping("acessar-plano")
    @Operation(description = "Visualizar o plano nutricional do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno do plano nutricional"),
            @ApiResponse(responseCode = "400", description = "Falha no retorno do plano nutricional, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "401", description = "Não autorizado, token inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> acessarPlano(@RequestHeader("Authorization") String token,
                                               @RequestParam("categoriaAtividade") String categoriaAtividade) {
        JwtData jwtData = JwtUtils.decodeToken(token);

        String mensagem = objetivoService.acessarPlano(jwtData, categoriaAtividade);

        return ResponseEntity.ok(mensagem);
    }

}