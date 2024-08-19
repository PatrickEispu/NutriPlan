package com.fourcamp.NutriPlan.dao.alimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DispensaDaoImpl implements DispensaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer quantidade) {
        String sql = "SELECT adicionar_alimento_na_dispensa(?,?,?)";
        jdbcTemplate.update(sql,idDispensa,idAlimento,quantidade);
    }

    public Integer criarDispensa(Integer idConta) {
        String sql = "SELECT criar_dispensa (?)";
        return jdbcTemplate.update(sql,idConta);

    }
}
