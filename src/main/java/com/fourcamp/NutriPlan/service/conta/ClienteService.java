package com.fourcamp.NutriPlan.service.conta;
import com.fourcamp.NutriPlan.dao.conta.CategoriaClienteDao;
import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dtos.conta.ClienteDto;
import com.fourcamp.NutriPlan.enuns.CategoriaClienteEnum;
import com.fourcamp.NutriPlan.exception.CategoriaClienteException;
import com.fourcamp.NutriPlan.exception.ContaException;
import com.fourcamp.NutriPlan.model.conta.CategoriaClienteEntity;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import com.fourcamp.NutriPlan.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private CategoriaClienteDao categoriaClienteDao;

    public ClienteDto criarCliente(ClienteDto clienteDto){

        // Buscar a categoria cliente pelo nome
        CategoriaClienteEntity categoriaCliente = categoriaClienteDao.buscarCategoriaPorNome(clienteDto.getIdCategoria());

        if (categoriaCliente == null) {
            throw new CategoriaClienteException(Constantes.MSG_ERRO_CATEGORIA_CLIENTE + categoriaCliente);
        }

        try {
            ClienteEntity cliente = ClienteEntity.builder()
                    .idConta(clienteDto.getIdConta())
                    .genero(clienteDto.getGenero())
                    .peso(clienteDto.getPeso())
                    .altura(clienteDto.getAltura())
                    .dataNascimento(clienteDto.getDataNascimento())
                    .tmb(clienteDto.getTmb())
                    .get(clienteDto.getGet())
                    .idCategoria(categoriaCliente.getIdCategoria())
                    .build();

            ClienteEntity clienteSalvo = clienteDao.criarConta(cliente);
            return mapearCliente(categoriaCliente, clienteSalvo);
        } catch (Exception e){
            throw new ContaException(Constantes.MSG_ERRO_CLIENTE + e.getMessage());
        }
    }

    private ClienteDto mapearCliente(CategoriaClienteEntity categoriaCliente, ClienteEntity clienteSalvo){
        return ClienteDto.builder()
                .idConta(clienteSalvo.getIdConta())
                .genero(clienteSalvo.getGenero())
                .peso(clienteSalvo.getPeso())
                .altura(clienteSalvo.getAltura())
                .dataNascimento(clienteSalvo.getDataNascimento())
                .tmb(clienteSalvo.getTmb())
                .get(clienteSalvo.getGet())
                .idCategoria(String.valueOf(CategoriaClienteEnum.valueOf(categoriaCliente.getNomeCategoria())))
                .build();
    }

    public ClienteEntity buscarClientePorId(int idConta) {
        return clienteDao.buscarClientePorId(idConta);
    }

    public List<ClienteEntity> buscarTodosClientes() {
        return clienteDao.buscarTodosClientes();
    }

}
