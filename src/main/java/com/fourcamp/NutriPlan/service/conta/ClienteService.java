//package com.fourcamp.NutriPlan.service.conta;
//import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
//import com.fourcamp.NutriPlan.dto.JwtData;
//import com.fourcamp.NutriPlan.exception.CategoriaException;
//import com.fourcamp.NutriPlan.exception.DataException;
//import com.fourcamp.NutriPlan.exception.EmailException;
//import com.fourcamp.NutriPlan.exception.SenhaException;
//import com.fourcamp.NutriPlan.model.conta.Cliente;
//import com.fourcamp.NutriPlan.utils.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Objects;
//
//@Service
//public class ClienteService {
//
//    @Autowired
//    private ClienteDao clienteDao;
//
//    @Autowired
//    private EmailValidador emailValidador;
//
//    @Autowired
//    private SenhaValidador senhaValidador;
//
//    @Autowired
//    private CalculoIdade calculoIdade;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public String criarCliente(String nome, String email, String genero, Double peso, Double pesoDesejado, Double altura, Date dataNascimento, String senha, String categoria, String tempoMeta){
//
//        if (!EmailValidador.isValidEmail(email)) {
//            throw new EmailException();
//        }
//
//        if (!SenhaValidador.isValidPassword(senha)) {
//            throw new SenhaException();
//        }
//
//        LocalDate dataNascimentoLocalDate = calculoIdade.convertToLocalDate(dataNascimento);
//        int idade = calculoIdade.calcularIdade(dataNascimentoLocalDate);
//        if (idade < 18) {
//            throw new DataException();
//        }
//
//        //Codifica a senha antes de salvar no banco de dados
//        String encodedPassword = passwordEncoder.encode(senha);
//
//        clienteDao.criarCliente(nome, email, genero, peso, pesoDesejado, altura, dataNascimento, encodedPassword, categoria , tempoMeta);
//        return Constantes.MSG_CRIACAO_CLIENTE_SUCESSO;
//    }
//
//    /**
//     * Autentica o usuário utilizando Spring Security e retorna um token JWT
//     *
//     * @param email o email do usuário
//     * @param senha a senha do usuário
//     * @return um token JWT válido ou null se a autenticação falhar
//     */
//    public String login(String email, String senha) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(email, senha));
//
//            //Define o contexto de segurança com a autenticação bem-sucedida
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        //Gera o token JWT utilizando as informaçoes do usuário autenticado
//        String jwt = jwtUtils.generateJwtToken(authentication);
//
//        return jwt;
//    }
//
//    public String alterarPeso(String email, double novoPeso) {
//        Cliente cliente = clienteDao.buscarClientePorEmail(email);
//
//        cliente.setPeso(novoPeso);
//        clienteDao.atualizarPesoCliente(email, novoPeso);
//        return Constantes.MSG_PESO_ALTERADO_SUCESSO;
//    }
//
//    public String formularioObjetivo(String email, String categoria, String tempoMeta) {
//        Cliente cliente = clienteDao.buscarClientePorEmail(email);
//
//        cliente.setCategoria(categoria);
//        cliente.setTempoMeta(tempoMeta);
//        clienteDao.formularioObjetivo(email,categoria,tempoMeta);
//        return Constantes.MSG_FORMULARIO_SUCESSO;
//    }
//
//    public JwtData obterDadosDoUsuario(String token) {
//        String email = jwtUtils.getUserNameFromJwtToken(token);
//        Cliente cliente = clienteDao.buscarClientePorEmail(email);
//
//        if (cliente != null) {
//            JwtData jwtData = new JwtData();
//            jwtData.setEmail(cliente.getEmail());
//            jwtData.setCategoria(cliente.getCategoria());
//            jwtData.setTempoMeta(cliente.getTempoMeta());
//            jwtData.setPeso(cliente.getPeso());
//            jwtData.setAltura(cliente.getAltura());
//            jwtData.setDataNascimento(new Date(cliente.getDataNascimento().getTime()));
//            jwtData.setGenero(cliente.getGenero());
//            return jwtData;
//        } else {
//            throw new RuntimeException("Cliente não encontrado com o email: " + email);
//        }
//    }
//    public double calcularGETSalvar(String email, String categoria, String tempoMeta, String genero, double peso, double altura, Date dataNascimento, String categoriaAtividade) {
//        double tmb = calcularTaxaMetabolica(genero, peso, altura, dataNascimento);
//        double get = calcularGET(tmb, categoriaAtividade);
//        String categoriaUpper = categoria.trim().toUpperCase(Locale.ROOT);
//        String tempoCategoriaUpper = tempoMeta.trim().toUpperCase(Locale.ROOT);
//
//        switch (categoriaUpper) {
//            case "PERDER PESO":
//                if ("RAPIDO".equals(tempoCategoriaUpper)) {
//                    get -= 1000;
//                } else if ("MEDIO".equals(tempoCategoriaUpper)) {
//                    get -= 600;
//                } else if ("LONGO PRAZO".equals(tempoCategoriaUpper)) {
//                    get -= 400;
//                }
//                break;
//            case "MANUTENCAO":
//                // Mantém o GET como está
//                break;
//            case "HIPERTROFIA":
//                if ("RAPIDO".equals(tempoCategoriaUpper)) {
//                    get += 800;
//                } else if ("MEDIO".equals(tempoCategoriaUpper)) {
//                    get += 500;
//                } else if ("LONGO PRAZO".equals(tempoCategoriaUpper)) {
//                    get += 300;
//                }
//                break;
//            default:
//                throw new CategoriaException();
//        }
//
//        clienteDao.salvarTMBGET(email, tmb, get);
//        return get;
//    }
//
//    private double calcularTaxaMetabolica(String genero, double peso, double altura, Date dataNascimento) {
//        double calculo = 0;
//
//        LocalDate nascimentoLocalDate = calculoIdade.convertToLocalDate(dataNascimento);
//        int idade = calculoIdade.calcularIdade(nascimentoLocalDate);
//
//        if (Objects.equals(genero, "M")) {
//            calculo = 66.5 + (13.75 * peso) + (5.003 * (altura * 100)) - (6.75 * idade);
//        } else if (Objects.equals(genero, "F")) {
//            calculo = 655.1 + (9.563 * peso) + (1.850 * (altura * 100)) - (4.676 * idade);
//        }
//
//        return calculo;
//    }
//
//    private double calcularGET(double tmb, String categoriaAtividade) {
//        double fatorAtividade;
//
//        switch (categoriaAtividade) {
//            case "nao_muito_ativo":
//                fatorAtividade = 1.2;
//                break;
//            case "levemente_ativo":
//                fatorAtividade = 1.375;
//                break;
//            case "ativo":
//                fatorAtividade = 1.55;
//                break;
//            case "bastante_ativo":
//                fatorAtividade = 1.725;
//                break;
//            default:
//                throw new UnsupportedOperationException("Categoria de atividade desconhecida: " + categoriaAtividade);
//        }
//
//        return tmb * fatorAtividade;
//    }
//
//}
