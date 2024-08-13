package com.fourcamp.NutriPlan.service;

import com.fourcamp.NutriPlan.dao.ClienteDao;
import com.fourcamp.NutriPlan.dto.JwtData;
import com.fourcamp.NutriPlan.exception.DataException;
import com.fourcamp.NutriPlan.exception.EmailException;
import com.fourcamp.NutriPlan.exception.SenhaException;
import com.fourcamp.NutriPlan.model.Cliente;
import com.fourcamp.NutriPlan.security.jwt.JwtUtils;
import com.fourcamp.NutriPlan.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private EmailValidador emailValidador;

    @Autowired
    private SenhaValidador senhaValidador;

    @Autowired
    private CalculoIdade calculoIdade;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        //Codifica a senha antes de salvar no banco de dados
        String encodedPassword = passwordEncoder.encode(senha);

        clienteDao.criarCliente(nome, email, genero, peso, pesoDesejado, altura, dataNascimento, encodedPassword, categoria , tempoMeta);
        return Constantes.MSG_CRIACAO_CLIENTE_SUCESSO;
    }

    /**
     * Autentica o usuário utilizando Spring Security e retorna um token JWT
     *
     * @param email o email do usuário
     * @param senha a senha do usuário
     * @return um token JWT válido ou null se a autenticação falhar
     */
    public String login(String email, String senha) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha));

            //Define o contexto de segurança com a autenticação bem-sucedida
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Gera o token JWT utilizando as informaçoes do usuário autenticado
        String jwt = jwtUtils.generateJwtToken(authentication);

        return jwt;
    }

    public String alterarPeso(String email, double novoPeso) {
        Cliente cliente = clienteDao.buscarClientePorEmail(email);

        cliente.setPeso(novoPeso);
        clienteDao.atualizarPesoCliente(email, novoPeso);
        return Constantes.MSG_PESO_ALTERADO_SUCESSO;
    }

    public String formularioObjetivo(String email, String categoria, String tempoMeta) {
        Cliente cliente = clienteDao.buscarClientePorEmail(email);

        cliente.setCategoria(categoria);
        cliente.setTempoMeta(tempoMeta);
        clienteDao.formularioObjetivo(email,categoria,tempoMeta);
        return Constantes.MSG_FORMULARIO_SUCESSO;
    }

    public JwtData obterDadosDoUsuario(String token) {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        Cliente cliente = clienteDao.buscarClientePorEmail(email);

        if (cliente != null) {
            JwtData jwtData = new JwtData();
            jwtData.setEmail(cliente.getEmail());
            jwtData.setCategoria(cliente.getCategoria());
            jwtData.setTempoMeta(cliente.getTempoMeta());
            jwtData.setPeso(cliente.getPeso());
            jwtData.setAltura(cliente.getAltura());
            jwtData.setDataNascimento(new Date(cliente.getDataNascimento().getTime()));
            jwtData.setGenero(cliente.getGenero());
            return jwtData;
        } else {
            throw new RuntimeException("Cliente não encontrado com o email: " + email);
        }
    }
}
