package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.TipoContaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TipoContaDaoImpl implements TipoContaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public TipoContaEntity buscarTipoContaPorNome(String nome) {
        String sql = "SELECT * FROM buscar_tipo_conta_por_nome(?)";
        return jdbcTemplate.execute(sql, (PreparedStatementCallback<TipoContaEntity>) ps -> {
            setPreparedStatementParameters(ps, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTipoContaEntity(rs);
                } else {
                    throw new IllegalArgumentException("Categoria não encontrada: " + nome);
                }
            }
        });
    }

    private void setPreparedStatementParameters(PreparedStatement ps, String nome) throws SQLException {
        ps.setString(1, nome);
    }

    private TipoContaEntity mapResultSetToTipoContaEntity(ResultSet rs) throws SQLException {
        TipoContaEntity tipoConta = new TipoContaEntity();
        tipoConta.setIdTipoConta(rs.getInt(1));  // Supondo que o ID seja a primeira coluna
        tipoConta.setNomeTipoConta(rs.getString(2));  // Supondo que o nome da categoria seja a segunda coluna
        return tipoConta;
    }
}
