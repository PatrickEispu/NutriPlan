package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.dao.mapper.CategoriaAlimentoRowMapper;
import com.fourcamp.NutriPlan.model.alimento.CategoriaAlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CategoriaAlimentoDaoImpl implements CategoriaAlimentoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public CategoriaAlimentoEntity buscarCategoriaAlimentoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_categoria_alimento_por_nome(?)";
        return jdbcTemplate.queryForObject(sql, new Object[] { nome }, new CategoriaAlimentoRowMapper());
    }
}
