package com.fourcamp.NutriPlan.dao.alimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefeicaoDaoImpl implements RefeicaoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;


//    public void salvarDiario(String email, String alimento, double quantidade, double kcal, double carboidrato, double proteina, double gordura, Date data) {
//        String sql = "INSERT INTO diario (email, alimento, quantidade, kcal, carboidrato, proteina, gordura, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql, email, alimento, quantidade, kcal, carboidrato, proteina, gordura, data);
//    }


//    public void salvarRefeicao(String email, String alimento, double quantidade, double kcal, double carboidrato, double proteina, double gordura, Date data)
//    {
//        String sql = "INSERT INTO diario (email, alimento, quantidade, kcal, carboidrato, proteina, gordura, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql, email, alimento, quantidade, kcal, carboidrato, proteina, gordura, data);
//    }

    public Integer criarRefeicao(Integer idConta) {
        String sqlRefeicao = "SELECT criar_refeicao (?)";
        return jdbcTemplate.queryForObject(sqlRefeicao, Integer.class, idConta);
    }

    public Integer adicionarAlimentoNaRefeicao(Integer idRefeicao, Integer idAlimento, Integer quantidade) {

        String sqlAlimento = "SELECT adicionar_alimento_na_refeicao(?,?,?)";
        jdbcTemplate.update(sqlAlimento, idRefeicao, idAlimento, quantidade);
        return idRefeicao;
    }
}
