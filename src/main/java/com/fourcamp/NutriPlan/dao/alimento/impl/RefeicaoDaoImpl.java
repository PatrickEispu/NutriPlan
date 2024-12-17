package com.fourcamp.NutriPlan.dao.alimento.impl;

import com.fourcamp.NutriPlan.dao.alimento.RefeicaoDao;
import com.fourcamp.NutriPlan.model.alimento.AlimentoEntity;
import com.fourcamp.NutriPlan.model.alimento.RefeicaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RefeicaoDaoImpl implements RefeicaoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public Integer criarRefeicao(Integer idConta) {
        String sqlRefeicao = "SELECT criar_refeicao (?)";
        return jdbcTemplate.queryForObject(sqlRefeicao, Integer.class, idConta);
    }

    @Override
    public List<RefeicaoEntity> getRefeicao(Integer fkIdRefeicao) {
        String sql = "SELECT * from refeicao_alimento WHERE fk_nr_id_refeicao = ?";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                {
                    RefeicaoEntity refeicao = new RefeicaoEntity();
                    refeicao.setFkNrIdRefeicao(fkIdRefeicao);
                    refeicao.setFkNrIdAlimento(rs.getInt("fk_nr_id_alimento"));
                    refeicao.setNrQuantidade(rs.getInt("nr_quantidade"));
                    return refeicao;
                },
                fkIdRefeicao);
    }

    @Override
    public List<AlimentoEntity> getRefeicaoAlimentoList(Integer fkNrIdRefeicao) {
        String sql = "SELECT nm_nome, " +
                "nr_kcal, " +
                "nr_carboidrato, " +
                "nr_proteina, " +
                "nr_gordura, " +
                "a.nr_quantidade, " +
                "nm_categoria " +
                "From refeicao_alimento " +
                "inner join alimento a " +
                "on fk_nr_id_alimento = a.nr_id_alimento " +
                "inner join categoria_alimento ca " +
                "on fk_nr_id_categoria_alimento = nr_id_categoria_alimento " +
                "WHERE fk_nr_id_refeicao = ?";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                {
                    AlimentoEntity alimento = new AlimentoEntity();
                    alimento.setNome(rs.getString("nm_nome"));
                    alimento.setKcal(rs.getDouble("nr_kcal"));
                    alimento.setProteina(rs.getDouble( "nr_proteina"));
                    alimento.setCarboidrato(rs.getDouble("nr_carboidrato"));
                    alimento.setGordura(rs.getDouble("nr_gordura"));
                    alimento.setQuantidade(rs.getDouble("nr_quantidade"));
                    alimento.setCategoriaAlimento(rs.getString("nm_categoria"));
                    return alimento;
                },fkNrIdRefeicao);
    }

    public RefeicaoEntity adicionarAlimentoNaRefeicao(Integer idRefeicao, Integer idAlimento, Integer quantidade) {

        String sqlAlimento = "SELECT adicionar_alimento_na_refeicao(?,?,?)";
    return jdbcTemplate.queryForObject(sqlAlimento, new BeanPropertyRowMapper<>(RefeicaoEntity.class) ,idRefeicao, idAlimento, quantidade);
    }

}
