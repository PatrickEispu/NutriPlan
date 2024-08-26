package com.fourcamp.NutriPlan.dao.conta.impl;

import com.fourcamp.NutriPlan.dao.conta.CategoriaClienteDao;
import com.fourcamp.NutriPlan.dao.mapper.CategoriaClienteRowMapper;
import com.fourcamp.NutriPlan.model.conta.CategoriaClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CategoriaClienteDaoImpl implements CategoriaClienteDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public CategoriaClienteEntity buscarCategoriaPorNome(String nome) {
        String sql = "SELECT * FROM buscar_categoria_por_nome(?)";
        return jdbcTemplate.queryForObject(sql, new Object[] { nome }, new CategoriaClienteRowMapper());
    }
}
