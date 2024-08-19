package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.dao.mapper.AlimentoRowMapper;
import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
        return jdbcTemplate.query(sql, new AlimentoRowMapper());
    }

    @Override
    @Transactional
    public AlimentoEntity buscarAlimentoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_alimento_por_nome(?)";
        return jdbcTemplate.queryForObject(sql, new Object[] { nome }, new AlimentoRowMapper());
    }

    @Override
    @Transactional
    public boolean verificarAlimentoExistente(String nome) {
        String sql = "SELECT verificar_alimento_existente(?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, nome);
    }
    public Integer getIdAlimentoPorNome(String nome) {
       String sql = "SELECT busca_id_alimento (?::varchar)";
       return jdbcTemplate.queryForObject(sql, Integer.class, nome, new AlimentoRowMapper());
    }
}