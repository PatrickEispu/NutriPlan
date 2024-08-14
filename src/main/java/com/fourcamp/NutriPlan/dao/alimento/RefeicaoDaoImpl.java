package com.fourcamp.NutriPlan.dao.alimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefeicaoDaoImpl implements RefeicaoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void salvarDiario(String email, String alimento, double quantidade, double kcal, double carboidrato, double proteina, double gordura, Date data) {
        String sql = "INSERT INTO diario (email, alimento, quantidade, kcal, carboidrato, proteina, gordura, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, email, alimento, quantidade, kcal, carboidrato, proteina, gordura, data);
    }
}
