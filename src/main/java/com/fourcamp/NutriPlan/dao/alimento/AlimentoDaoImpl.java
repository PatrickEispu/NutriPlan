package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
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
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{idCategoriaAlimento, kcal, carboidrato, proteina, gordura, quantidade, nome}, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar alimento: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public List<AlimentoEntity> listarTodosAlimentos() {
        String sql = "SELECT * FROM listar_todos_alimentos()";
        return jdbcTemplate.query(sql, new RowMapper<AlimentoEntity>() {
            @Override
            public AlimentoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                AlimentoEntity alimento = new AlimentoEntity();
                alimento.setIdAlimento(rs.getInt("nr_id_alimento")); // Use o nome exato da coluna no banco de dados
                alimento.setIdCategoriaAlimento(rs.getInt("fk_nr_id_categoria_alimento"));
                alimento.setKcal(rs.getDouble("nr_kcal"));
                alimento.setCarboidrato(rs.getDouble("nr_carboidrato"));
                alimento.setProteina(rs.getDouble("nr_proteina"));
                alimento.setGordura(rs.getDouble("nr_gordura"));
                alimento.setQuantidade(rs.getDouble("nr_quantidade"));
                alimento.setNome(rs.getString("nm_nome"));
                return alimento;
            }
        });
    }


    @Override
    @Transactional
    public AlimentoEntity buscarAlimentoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_alimento_por_nome(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(AlimentoEntity.class), nome);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Alimento não encontrado: " + nome);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar alimento por nome: " + e.getMessage());
        }
    }
}