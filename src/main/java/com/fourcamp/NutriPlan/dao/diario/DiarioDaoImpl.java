package com.fourcamp.NutriPlan.dao.diario;

import com.fourcamp.NutriPlan.dto.diario.DiarioDto;
import com.fourcamp.NutriPlan.model.diario.DiarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DiarioDaoImpl implements  DiarioDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public DiarioEntity salvarDiario(DiarioDto diario) {
        String sql="SELECT criar_diario(?,?,?)";
      return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(DiarioEntity.class),diario.getFkIdConta(),diario.getData(),diario.getFkIdRefeicao());
    }
}
