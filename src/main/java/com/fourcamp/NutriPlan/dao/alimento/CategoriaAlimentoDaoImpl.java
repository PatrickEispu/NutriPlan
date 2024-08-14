package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.CategoriaAlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoriaAlimentoDaoImpl implements CategoriaAlimentoDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public CategoriaAlimentoEntity buscarCategoriaAlimentoPorId(int idCategoriaAlimento) {
        String sql = "SELECT * FROM buscar_categoria_alimento_por_id(?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CategoriaAlimentoEntity.class), idCategoriaAlimento);
    }

    @Override
    @Transactional
    public CategoriaAlimentoEntity buscarCategoriaAlimentoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_categoria_alimento_por_nome(?)";
        List<CategoriaAlimentoEntity> categorias = jdbcTemplate.query(sql, new Object[]{nome}, new RowMapper<CategoriaAlimentoEntity>() {
            @Override
            public CategoriaAlimentoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CategoriaAlimentoEntity(
                        rs.getInt("nr_id_categoria_alimento"),
                        rs.getString("nm_categoria")
                );
            }
        });

        if (categorias.isEmpty()) {
            throw new IllegalArgumentException("Categoria não encontrada: " + nome);
        }
        return categorias.get(0);
    }
}
