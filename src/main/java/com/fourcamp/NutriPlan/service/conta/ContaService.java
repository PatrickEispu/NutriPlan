package com.fourcamp.NutriPlan.service.conta;

import com.fourcamp.NutriPlan.dao.conta.ContaDao;
import com.fourcamp.NutriPlan.dao.conta.TipoContaDao;
import com.fourcamp.NutriPlan.dto.conta.ContaDto;
import com.fourcamp.NutriPlan.enuns.TipoContaEnum;
import com.fourcamp.NutriPlan.exception.ContaException;
import com.fourcamp.NutriPlan.exception.EmailException;
import com.fourcamp.NutriPlan.exception.SenhaException;
import com.fourcamp.NutriPlan.exception.TipoContaException;
import com.fourcamp.NutriPlan.model.conta.ContaEntity;
import com.fourcamp.NutriPlan.model.conta.TipoContaEntity;
import com.fourcamp.NutriPlan.utils.Constantes;
import com.fourcamp.NutriPlan.utils.EmailValidador;
import com.fourcamp.NutriPlan.utils.SenhaValidador;
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
                throw new TipoContaException(Constantes.MSG_TIPO_CONTA_NAO_ENCONTRADA + tipoConta);
            }

            // Verificar se o email NÃO é válido
            if (!EmailValidador.validarEmail(contaDto.getEmail())) {
                throw new EmailException(Constantes.MSG_EMAIL_INVALIDO);
            }

            // Verificar se a senha NÃO é válida
            if (!SenhaValidador.isValidPassword(contaDto.getSenha())) {
                throw new SenhaException(Constantes.MSG_SENHA_INVALIDO);
            }

            // Verificar se o email já existe na base de dados
            Boolean emailExiste = contaDao.verificarEmailExistente(contaDto.getEmail());
            if (emailExiste != null && emailExiste) {
                throw new EmailException(Constantes.MSG_EMAIL_JA_CADASTRADO);
            }

        try {
            ContaEntity conta = ContaEntity.builder()
                    .nome(contaDto.getNome())
                    .email(contaDto.getEmail())
                    .senha(contaDto.getSenha())
                    .TipoConta(tipoConta.getIdTipoConta())
                    .build();

            ContaEntity contaSalva = contaDao.criarConta(conta);
            return mapearAlimento(tipoConta, contaSalva);
        } catch (Exception e){
            throw new ContaException(Constantes.MSG_ERRO_CRIACAO_CONTA + e.getMessage());
       }
    }

    private ContaDto mapearAlimento(TipoContaEntity tipoConta, ContaEntity contaSalva){
        return ContaDto.builder()
                .nome(contaSalva.getNome())
                .email(contaSalva.getEmail())
                .senha(contaSalva.getSenha())
                .TipoConta(String.valueOf(TipoContaEnum.valueOf(tipoConta.getNomeTipoConta())))
                .build();
    }

    public Integer getIdContaPorEmail(String email) {
        return contaDao.getIdContaPorEmail(email);

    }
}
