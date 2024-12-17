package com.fourcamp.NutriPlan.dao.alimento.impl;

import com.fourcamp.NutriPlan.dao.alimento.AlimentoDao;
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
    public AlimentoEntity criarAlimento(AlimentoEntity alimento, Integer idCategoriaAlimento) {
        String sql = "SELECT criar_alimento(?, ?, ?, ?, ?, ?, ?)";
             jdbcTemplate.queryForObject(sql, new Object[]{
                    idCategoriaAlimento, alimento.getKcal(), alimento.getCarboidrato(), alimento.getProteina(), alimento.getGordura(), alimento.getQuantidade(), alimento.getNome()},
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
       String sql = "SELECT busca_id_alimento (?)";
       return jdbcTemplate.queryForObject(sql, Integer.class,nome);
    }

    @Override
    public String getAlimentoNomePorId(Integer fkNrIdAlimento) {
        String sql = "SELECT nm_nome FROM alimento WHERE nr_id_alimento = ?";
         return jdbcTemplate.queryForObject(sql, String.class,fkNrIdAlimento);
    }

    @Override
    public Integer getIdCategoriaPorNome(String categoria) {
        String sql= "SELECT nr_id_categoria_alimento FROM categoria_alimento WHERE nm_categoria = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class,categoria);

    }

    @Override
    public Integer getAlimentoRecomendado(Integer idAlimento, Integer categoria) {
        String sql = "SELECT buscar_outro_alimento_mesma_categoria (?,?)";
        return jdbcTemplate.queryForObject(sql, Integer.class,idAlimento,categoria);


    }
}