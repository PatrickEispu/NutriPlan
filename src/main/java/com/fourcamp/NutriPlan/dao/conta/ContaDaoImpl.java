package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.ContaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ContaDaoImpl implements ContaDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public boolean verificarEmailExistente(String email) {
       String sql = "SELECT verificar_email_existente(?)";
       return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }

    @Override
    @Transactional
    public ContaEntity criarConta(ContaEntity conta) {
        String sql = "SELECT criar_conta(?, ?, ?, ?)";
        jdbcTemplate.queryForObject(sql, new Object[]{
                       conta.getNome(), conta.getEmail(), conta.getSenha(),  conta.getTipoConta()},
                Integer.class);
        return conta;
    }

    @Override
    @Transactional
    public Integer getIdContaPorEmail(String email) {
        String sql=("select buscar_id_conta_por_email (?::varchar)");
        System.out.println(jdbcTemplate.queryForObject(sql, Integer.class,email)) ;

        return jdbcTemplate.queryForObject(sql, Integer.class,email) ;

    }

    @Override
    @Transactional
    public ContaEntity buscarContaPorId(int idConta) {
        String sql = "SELECT * FROM buscar_conta_por_id(?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ContaEntity.class), idConta);
    }
}
