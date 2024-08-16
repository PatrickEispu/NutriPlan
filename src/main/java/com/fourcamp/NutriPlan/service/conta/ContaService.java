package com.fourcamp.NutriPlan.service.conta;

import com.fourcamp.NutriPlan.dao.conta.ContaDao;
import com.fourcamp.NutriPlan.dao.conta.TipoContaDao;
import com.fourcamp.NutriPlan.dto.conta.ContaDto;
import com.fourcamp.NutriPlan.enuns.TipoContaEnum;
import com.fourcamp.NutriPlan.model.conta.ContaEntity;
import com.fourcamp.NutriPlan.model.conta.TipoContaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    @Autowired
    ContaDao contaDao;

    @Autowired
    TipoContaDao tipoContaDao;

    public ContaDto criarConta(ContaDto contaDto) {
        // Buscar a tipo conta pelo nome
        TipoContaEntity tipoConta = tipoContaDao.buscarTipoContaPorNome(contaDto.getTipoConta());

        if (tipoConta == null) {
            throw new IllegalArgumentException("O tipo conta não encontrada: " + tipoConta);
        }

        ContaEntity conta = ContaEntity.builder()
                .nome(contaDto.getNome())
                .email(contaDto.getEmail())
                .senha(contaDto.getSenha())
                .TipoConta(tipoConta.getIdTipoConta())
                .build();
        // Criar o alimento usando o ID da categoria encontrada
        ContaEntity contaSalva = contaDao.criarConta(conta);
        return mapearAlimento(tipoConta, contaSalva);
    }

    private ContaDto mapearAlimento(TipoContaEntity tipoConta, ContaEntity contaSalva){
        return ContaDto.builder()
                .nome(contaSalva.getNome())
                .email(contaSalva.getEmail())
                .senha(contaSalva.getSenha())
                .TipoConta(String.valueOf(TipoContaEnum.valueOf(tipoConta.getNomeTipoConta())))
                .build();
    }
}
