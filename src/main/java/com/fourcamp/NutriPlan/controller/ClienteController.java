package com.fourcamp.NutriPlan.controller;

import com.fourcamp.NutriPlan.dto.conta.ClienteDto;
import com.fourcamp.NutriPlan.model.conta.Cliente;
import com.fourcamp.NutriPlan.service.conta.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/criar-cliente")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(this.clienteService.criarCliente(cliente));
    }

    @GetMapping("/{idConta}")
    public Cliente buscarClientePorId(@PathVariable int idConta) {
        return clienteService.buscarClientePorId(idConta);
    }

    @GetMapping("/todos")
    public List<Cliente> buscarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }


    @PutMapping("/{idConta}")
    public void atualizarCliente(@PathVariable int idConta, @RequestBody Cliente cliente) {
        cliente.setIdConta(idConta);
        clienteService.atualizarCliente(cliente);
    }
}
