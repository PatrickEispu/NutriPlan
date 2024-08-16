package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlimentoDaoImpl implements AlimentoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public AlimentoEntity criarAlimento(AlimentoEntity alimento) {
        String sql = "SELECT criar_alimento(?, ?, ?, ?, ?, ?, ?)";
             jdbcTemplate.queryForObject(sql, new Object[]{
                    alimento.getIdCategoriaAlimento(), alimento.getKcal(), alimento.getCarboidrato(), alimento.getProteina(), alimento.getGordura(), alimento.getQuantidade(), alimento.getNome()},
                    Integer.class);
        return alimento;
    }


    @Override
    @Transactional
    public List<AlimentoEntity> listarTodosAlimentos() {
        String sql = "SELECT * FROM listar_todos_alimentos()";
        return jdbcTemplate.execute(sql, (PreparedStatementCallback<List<AlimentoEntity>>) ps -> {
            // Não precisamos configurar parâmetros específicos aqui, já que estamos listando todos os alimentos.
            try (ResultSet rs = ps.executeQuery()) {
                List<AlimentoEntity> alimentos = new ArrayList<>();
                while (rs.next()) {
                    AlimentoEntity alimento = new AlimentoEntity();
                    setResultSetParameters(rs, alimento);  // Método para preencher o objeto AlimentoEntity
                    alimentos.add(alimento);
                }
                return alimentos;
            }
        });
    }

    private void setResultSetParameters(ResultSet rs, AlimentoEntity alimento) throws SQLException {
        alimento.setIdAlimento(rs.getInt(1));  // Assume que o ResultSet está configurado corretamente
        alimento.setIdCategoriaAlimento(rs.getInt(2));
        alimento.setKcal(rs.getDouble(3));
        alimento.setCarboidrato(rs.getDouble(4));
        alimento.setProteina(rs.getDouble(5));
        alimento.setGordura(rs.getDouble(6));
        alimento.setQuantidade(rs.getDouble(7));
        alimento.setNome(rs.getString(8));
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