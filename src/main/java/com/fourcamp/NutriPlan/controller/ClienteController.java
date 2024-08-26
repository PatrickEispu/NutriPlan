package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dtos.conta.ClienteDto;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import com.fourcamp.NutriPlan.service.conta.ClienteService;
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
@RequestMapping("/v1")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @Operation(description = "Criar cliente no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro do cliente realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar cadastro do cliente, dados inválidos ou formato incorreto"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/criar-cliente")
    public ResponseEntity<String> criarCliente(@RequestBody ClienteDto clienteDto) {
        String response = String.valueOf(clienteService.criarCliente(clienteDto));
        return ResponseEntity.ok(Constantes.MSG_CRIACAO_CLIENTE_SUCESSO);
    }


    @GetMapping("/{idConta}")
    public ClienteEntity buscarClientePorId(@PathVariable int idConta) {
        return clienteService.buscarClientePorId(idConta);
    }

    @Operation(description = "Listar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao listar cliente!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/listar")
    public List<ClienteEntity> buscarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }

    @Operation(description = "Atualizar cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar cliente!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/{idConta}")
    public ResponseEntity atualizarCliente(@PathVariable int idConta, @RequestBody ClienteDto cliente) {
        clienteService.atualizarCliente(cliente);
        return ResponseEntity.ok(Constantes.MSG_ATUALIZAR_CLIENTE);
    }
}
