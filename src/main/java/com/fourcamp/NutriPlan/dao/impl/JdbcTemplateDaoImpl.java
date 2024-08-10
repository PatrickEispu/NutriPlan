package com.fourcamp.NutriPlan.dao.impl;

import com.fourcamp.NutriPlan.dao.JdbcTemplateDao;
import com.fourcamp.NutriPlan.dto.MacrosDto;
import com.fourcamp.NutriPlan.model.Alimento;
import com.fourcamp.NutriPlan.model.Cliente;
import com.fourcamp.NutriPlan.model.Diario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JdbcTemplateDaoImpl implements JdbcTemplateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void criarCliente(String nome, String email, String genero, double peso, double pesoDesejado, double altura, Date dataNascimento, String senha, String categoria, String tempoMeta) {
        String sql = "CALL criar_cliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {nome, email, genero, peso, pesoDesejado, altura, new java.sql.Date(dataNascimento.getTime()), senha, categoria , tempoMeta};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.NUMERIC, Types.NUMERIC, Types.DATE, Types.VARCHAR , Types.NULL, Types.NULL};

        jdbcTemplate.update(sql, params, types);
    }

    public Cliente buscarClientePorEmail(String email) {
        String query = "SELECT * FROM clientes WHERE email = ?";
        @SuppressWarnings("deprecation")
        List<Cliente> clientes = jdbcTemplate.query(query, new Object[] { email }, (rs, rowNum) -> {
            return new Cliente(rs.getString("nome"), rs.getString("email"), rs.getString("genero"), rs.getDouble("peso"),
                    rs.getDouble("peso_desejado"),rs.getDouble("altura"), rs.getDate("data_nascimento"), rs.getString("senha"), rs.getString("categoria"), rs.getString("tempo_meta"));
        });

        if (!clientes.isEmpty()) {
            return clientes.get(0);
        } else {
            return null;
        }
    }

    public void atualizarPesoCliente(String email, double novoPeso) {
        String callProcedure = "CALL atualizar_peso_cliente(?, ?)";
        jdbcTemplate.update(callProcedure, email, novoPeso);
    }

    public void formularioObjetivo(String email, String categoria, String tempoMeta) {
        String callProcedure = "CALL formulario_objetivo(?, ?, ?)";
        jdbcTemplate.update(callProcedure,email,categoria,tempoMeta);
    }

    public Map<String, Object> buscarDadosCliente(String email) {
        String sql = "SELECT id, peso, altura, genero, data_nascimento FROM clientes WHERE email = ?";
        return jdbcTemplate.queryForMap(sql, new Object[]{email});
    }

    public void salvarTMBGET(String email, double tmb, double get) {
        String sql = "CALL salvar_tmb_get(?, ?, ?)";
        Map<String, Object> cliente = buscarDadosCliente(email);
        Integer clienteId = (Integer) cliente.get("id");
        jdbcTemplate.update(sql, clienteId, tmb, get);
    }

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

    public void salvarDiario(String email, String alimento, double quantidade, double kcal, double carboidrato, double proteina, double gordura, Date data) {
        String sql = "INSERT INTO diario (email, alimento, quantidade, kcal, carboidrato, proteina, gordura, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, email, alimento, quantidade, kcal, carboidrato, proteina, gordura, data);
    }

    public List<Diario> buscarPlanoCliente(String email) {
        String sql = "SELECT * FROM diario WHERE email = ? AND data = ? ORDER BY data DESC";
        Date currentDate = new Date(System.currentTimeMillis());
        return jdbcTemplate.query(sql, new Object[]{email, new java.sql.Date(currentDate.getTime())}, (rs, rowNum) -> {
            Diario diario = new Diario();
            diario.setAlimento(rs.getString("alimento"));
            diario.setQuantidade(rs.getDouble("quantidade"));
            diario.setKcal(rs.getDouble("kcal"));
            diario.setCarboidrato(rs.getDouble("carboidrato"));
            diario.setProteina(rs.getDouble("proteina"));
            diario.setGordura(rs.getDouble("gordura"));
            diario.setData(rs.getDate("data"));
            return diario;
        });
    }
}
