package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dtos.conta.ClienteDto;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import com.fourcamp.NutriPlan.service.conta.ClienteService;
import com.fourcamp.NutriPlan.utils.Constantes;
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

    @PostMapping("/criar-cliente")
    public ResponseEntity<String> criarCliente(@RequestBody ClienteDto clienteDto) {
        String response = String.valueOf(clienteService.criarCliente(clienteDto));
        return ResponseEntity.ok(Constantes.MSG_CRIACAO_CLIENTE_SUCESSO);
    }

    @GetMapping("/{idConta}")
    public ClienteEntity buscarClientePorId(@PathVariable int idConta) {
        return clienteService.buscarClientePorId(idConta);
    }

    @GetMapping("/todos")
    public List<ClienteEntity> buscarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }


    @PutMapping("/{idConta}")
    public void atualizarCliente(@PathVariable int idConta, @RequestBody Cliente cliente) {
        cliente.setIdConta(idConta);
        clienteService.atualizarCliente(cliente);
    }
}
