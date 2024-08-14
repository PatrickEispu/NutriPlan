package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.Alimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Service
public class AlimentoDaoImpl implements AlimentoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void criarAlimento(Double kcal, Double carboidrato, Double proteina, Double gordura, Double quantidade, String nome) {
        String sql = "CALL criar_alimento(?, ?, ?, ?, ?, ?)";
        Object[] params = {kcal,carboidrato,proteina,gordura,quantidade,nome};
        int[] types = {Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.VARCHAR};

        jdbcTemplate.update(sql, params, types);
    }

    public List<Alimento> listarAlimentos() {
        return jdbcTemplate.query("SELECT * FROM listar_alimentos()", (rs, rowNum) -> {
            Alimento alimento = new Alimento(rs.getDouble("kcal"), rs.getDouble("carboidrato"), rs.getDouble("proteina"),
                    rs.getDouble("gordura"), rs.getDouble("quantidade"), rs.getString("nome"));
            return alimento;
        });
    }

    public Alimento buscarAlimentoPorNome(String nome) {
        String sql = "SELECT * FROM alimento WHERE nome = ?";
        List<Alimento> alimentos = jdbcTemplate.query(sql, new Object[]{nome}, new RowMapper<Alimento>() {
            @Override
            public Alimento mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Alimento(
                        rs.getDouble("kcal"),
                        rs.getDouble("carboidrato"),
                        rs.getDouble("proteina"),
                        rs.getDouble("gordura"),
                        rs.getDouble("quantidade"),
                        rs.getString("nome")
                );
            }
        });

        if (alimentos.isEmpty()) {
            throw new IllegalArgumentException("Alimento não encontrado: " + nome);
        }

        return alimentos.get(0);
    }
}
