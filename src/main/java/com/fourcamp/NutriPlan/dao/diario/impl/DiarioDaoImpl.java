package com.fourcamp.NutriPlan.dao.diario.impl;

import com.fourcamp.NutriPlan.dtos.diario.DiarioDto;
import com.fourcamp.NutriPlan.dao.diario.DiarioDao;
import com.fourcamp.NutriPlan.model.diario.DiarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiarioDaoImpl implements DiarioDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public DiarioEntity salvarDiario(DiarioDto diario) {
        String sql="SELECT criar_diario(?,?,?)";
      return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(DiarioEntity.class),diario.getFkIdConta(),diario.getData(),diario.getFkIdRefeicao());
    }

    @Override
    public List<DiarioEntity> getDiarioList(Integer idConta) {
        String sql= "SELECT * FROM diario WHERE fk_nr_id_conta = ?";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                {
                       DiarioEntity diario = new DiarioEntity();
                       diario.setFkIdConta(idConta);
                       diario.setData(rs.getString("ds_data"));
                       diario.setFkIdRefeicao(rs.getInt("fk_nr_id_refeicao"));
                       return diario;
                },idConta);
    }
}
