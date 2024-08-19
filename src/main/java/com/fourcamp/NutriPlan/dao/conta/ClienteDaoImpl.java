package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.model.conta.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class ClienteDaoImpl implements ClienteDao {
    private static final String CRIAR_CLIENTE = "SELECT public.criar_cliente(?, ?, ?, ?, ?, ?, ?, ?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ClienteDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class ClienteMapper implements RowMapper<Cliente> {
        @Override
        public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Cliente(
                    rs.getInt("fk_nr_id_conta"),
                    rs.getString("ds_genero").charAt(0),
                    rs.getDouble("nr_peso"),
                    rs.getDouble("nr_altura"),
                    rs.getString("ds_data_nascimento"),
                    rs.getDouble("nr_tmb"),
                    rs.getDouble("nr_get"),
                    rs.getInt("fk_nr_id_categoria")
            );
        }
    }

    @Override
    public String criarCliente(Cliente cliente) {
        try{
            String sql = CRIAR_CLIENTE;
            jdbcTemplate.update(sql, cliente.getIdConta(), String.valueOf(cliente.getGenero()), cliente.getPeso(), cliente.getAltura(), cliente.getDataNascimento(), cliente.getTmb(), cliente.getGet(), cliente.getIdCategoria());
            return "Cliente" + cliente.getNome() + "Criado com sucesso";
        }catch (Exception e){
            return "Erro ao salvar o cliente" + e.getMessage();
        }

    }

    @Override
    public Cliente buscarClientePorId(int idConta) {
        String sql = "SELECT * FROM cliente WHERE fk_nr_id_conta = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idConta}, new ClienteMapper());
    }

    @Override
    public List<Cliente> buscarTodosClientes() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new ClienteMapper());
    }

    @Override
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET ds_genero = ?, nr_peso = ?, nr_altura = ?, ds_data_nascimento = ?, nr_tmb = ?, nr_get = ?, fk_nr_id_categoria = ? WHERE fk_nr_id_conta = ?";
        jdbcTemplate.update(sql, String.valueOf(cliente.getGenero()), cliente.getPeso(), cliente.getAltura(), cliente.getDataNascimento(), cliente.getTmb(), cliente.getGet(), cliente.getIdCategoria(), cliente.getIdConta());
    }

}
