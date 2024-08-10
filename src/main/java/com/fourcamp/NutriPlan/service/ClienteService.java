package com.fourcamp.NutriPlan.service;

import com.fourcamp.NutriPlan.dao.JdbcTemplateDao;
import com.fourcamp.NutriPlan.dao.impl.JdbcTemplateDaoImpl;
import com.fourcamp.NutriPlan.exception.DataException;
import com.fourcamp.NutriPlan.exception.EmailException;
import com.fourcamp.NutriPlan.exception.SenhaException;
import com.fourcamp.NutriPlan.model.Cliente;
import com.fourcamp.NutriPlan.utils.*;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

@Service
public class ClienteService {

    @Autowired
    private JdbcTemplateDao jdbcTemplateDao;

    @Autowired
    private EmailValidador emailValidador;

    @Autowired
    private SenhaValidador senhaValidador;

    @Autowired
    private CalculoIdade calculoIdade;

    public String criarCliente(String nome, String email, String genero, Double peso, Double pesoDesejado, Double altura, Date dataNascimento, String senha, String categoria, String tempoMeta){

        if (!EmailValidador.isValidEmail(email)) {
            throw new EmailException();
        }

        if (!SenhaValidador.isValidPassword(senha)) {
            throw new SenhaException();
        }

        LocalDate dataNascimentoLocalDate = calculoIdade.convertToLocalDate(dataNascimento);
        int idade = calculoIdade.calcularIdade(dataNascimentoLocalDate);
        if (idade < 18) {
            throw new DataException();
        }

        jdbcTemplateDao.criarCliente(nome, email, genero, peso, pesoDesejado, altura, dataNascimento, senha, categoria , tempoMeta);
        return Constantes.MSG_CRIACAO_CLIENTE_SUCESSO;
    }

    public String login(String email, String senha) {
        Cliente cliente = jdbcTemplateDao.buscarClientePorEmail(email);
        if (cliente != null && cliente.getSenha().equals(senha)) {

            Key chaveSecreta = JwtConfig.getChaveSecreta();

            String token = Jwts.builder().claim("nome", cliente.getNome()).claim("email", cliente.getEmail())
                    .claim("genero", cliente.getGenero())
                    .claim("peso", cliente.getPeso()).claim("peso_desejado", cliente.getPesoDesejado())
                    .claim("altura",cliente.getAltura())
                    .claim("data_nascimento", cliente.getDataNascimento()).claim("senha", cliente.getSenha())
                    .claim("categoria", cliente.getCategoria()) .claim("tempo_meta", cliente.getTempoMeta())

                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)).signWith(chaveSecreta).compact();
            return token;
        } else { 
            return null;
        }
    }

    public String alterarPeso(String email, double novoPeso) {
        Cliente cliente = jdbcTemplateDao.buscarClientePorEmail(email);

        cliente.setPeso(novoPeso);
        jdbcTemplateDao.atualizarPesoCliente(email, novoPeso);
        return Constantes.MSG_PESO_ALTERADO_SUCESSO;
    }

    public String formularioObjetivo(String email, String categoria, String tempoMeta) {
        Cliente cliente = jdbcTemplateDao.buscarClientePorEmail(email);

        cliente.setCategoria(categoria);
        cliente.setTempoMeta(tempoMeta);
        jdbcTemplateDao.formularioObjetivo(email,categoria,tempoMeta);
        return Constantes.MSG_FORMULARIO_SUCESSO;
    }
}
