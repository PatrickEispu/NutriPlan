package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.Alimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AlimentoDaoImpl implements AlimentoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public int criarAlimento(int idCategoriaAlimento, double kcal, double carboidrato, double proteina, double gordura, double quantidade, String nome) {
        String sql = "SELECT criar_alimento(?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, idCategoriaAlimento, kcal, carboidrato, proteina, gordura, quantidade, nome);
    }

    @Override
    @Transactional
    public List<Alimento> listarTodosAlimentos() {
        String sql = "SELECT * FROM listar_todos_alimentos()";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Alimento.class));
    }

    @Override
    @Transactional
    public Alimento buscarAlimentoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_alimento_por_nome(?)";
        List<Alimento> alimentos = jdbcTemplate.query(sql, new Object[]{nome}, new RowMapper<Alimento>() {
            @Override
            public Alimento mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Alimento(
                        rs.getInt("nr_id_alimento"),
                        rs.getInt("fk_nr_id_categoria_alimento"),
                        rs.getDouble("nr_kcal"),
                        rs.getDouble("nr_carboidrato"),
                        rs.getDouble("nr_proteina"),
                        rs.getDouble("nr_gordura"),
                        rs.getDouble("nr_quantidade"),
                        rs.getString("nm_nome")
                );
            }
        });

        if (alimentos.isEmpty()) {
            throw new IllegalArgumentException("Alimento não encontrado: " + nome);
        }

        return alimentos.get(0);
    }
}
