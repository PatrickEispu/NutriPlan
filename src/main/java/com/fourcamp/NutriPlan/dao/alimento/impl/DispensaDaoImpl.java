package com.fourcamp.NutriPlan.dao.alimento.impl;

import com.fourcamp.NutriPlan.dao.alimento.DispensaDao;
import com.fourcamp.NutriPlan.dtos.alimento.DispensaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DispensaDaoImpl implements DispensaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer quantidade) {
        String sql = "SELECT adicionar_alimento_na_dispensa(?,?,?)";
         jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(DispensaDto.class),idDispensa,idAlimento,quantidade);
    }

    public Integer criarDispensa(Integer idConta) {
        String sql = "SELECT criar_dispensa (?)";
        return jdbcTemplate.queryForObject(sql, Integer.class,idConta);

    }

    @Override
    public Integer dispensaExiste(Integer idConta) {
        String sql=("select contar_dispensas_por_conta(?)");
        return jdbcTemplate.queryForObject(sql, Integer.class,idConta);
    }

    @Override
    public Integer getDispensa(Integer idConta) {
        String sql = "select nr_id_dispensa from buscar_dispensa_por_id_conta(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class,idConta);
    }
}
