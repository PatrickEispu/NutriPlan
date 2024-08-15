package com.fourcamp.NutriPlan.dao.alimento;

import com.fourcamp.NutriPlan.model.alimento.CategoriaAlimentoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CategoriaAlimentoDaoImpl implements CategoriaAlimentoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public CategoriaAlimentoEntity buscarCategoriaAlimentoPorNome(String nome) {
        String sql = "SELECT * FROM buscar_categoria_alimento_por_nome(?)";
        return jdbcTemplate.execute(sql, (PreparedStatementCallback<CategoriaAlimentoEntity>) ps -> {
            setPreparedStatementParameters(ps, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategoriaAlimentoEntity(rs);
                } else {
                    throw new IllegalArgumentException("Categoria não encontrada: " + nome);
                }
            }
        });
    }

    private void setPreparedStatementParameters(PreparedStatement ps, String nome) throws SQLException {
        ps.setString(1, nome);
    }

    private CategoriaAlimentoEntity mapResultSetToCategoriaAlimentoEntity(ResultSet rs) throws SQLException {
        CategoriaAlimentoEntity categoria = new CategoriaAlimentoEntity();
        categoria.setIdCategoriaAlimento(rs.getInt(1));  // Supondo que o ID seja a primeira coluna
        categoria.setNomeCategoria(rs.getString(2));  // Supondo que o nome da categoria seja a segunda coluna
        return categoria;
    }
}
