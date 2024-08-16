package com.fourcamp.NutriPlan.dao.diario;

import com.fourcamp.NutriPlan.dto.diario.DiarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DiarioDaoImpl implements  DiarioDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void salvarDiario(DiarioDto diario) {
        String sql="SELECT criar_diario(?,?,?)";
        jdbcTemplate.update(sql,diario.getFkIdConta(),diario.getData(),diario.getFkIdRefeicao());
    }
}
