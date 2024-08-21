package com.fourcamp.NutriPlan.dao.conta;

import com.fourcamp.NutriPlan.dao.mapper.ClienteMapper;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import com.fourcamp.NutriPlan.model.conta.ContaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Repository
public class ClienteDaoImpl implements ClienteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ClienteEntity criarConta(ClienteEntity cliente) {
        String sql = "SELECT criar_cliente(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.queryForObject(sql, new Object[]{
                        cliente.getGenero(), cliente.getPeso(), cliente.getAltura(), cliente.getDataNascimento(), cliente.getTmb(), cliente.getGet(), cliente.getIdCategoria(), cliente.getIdConta()},
                Integer.class);
//cliente.setIdConta(idConta);  // Atualiza o ID da conta no objeto cliente
        return cliente;
    }
//    private static final String CRIAR_CLIENTE = "SELECT public.criar_cliente(?, ?, ?, ?, ?, ?, ?, ?)";
//
//    public ClienteDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    private static final class ClienteMapper implements RowMapper<Cliente> {
//        @Override
//        public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return new Cliente(
//                    rs.getInt("fk_nr_id_conta"),
//                    rs.getString("ds_genero").charAt(0),
//                    rs.getDouble("nr_peso"),
//                    rs.getDouble("nr_altura"),
//                    rs.getString("ds_data_nascimento"),
//                    rs.getDouble("nr_tmb"),
//                    rs.getDouble("nr_get"),
//                    rs.getInt("fk_nr_id_categoria")
//            );
//        }
//    }
//
//    @Override
//    public String criarCliente(Cliente cliente) {
//        try{
//            String sql = CRIAR_CLIENTE;
//            jdbcTemplate.update(sql, cliente.getIdConta(), String.valueOf(cliente.getGenero()), cliente.getPeso(), cliente.getAltura(), cliente.getDataNascimento(), cliente.getTmb(), cliente.getGet(), cliente.getIdCategoria());
//            return "Cliente" + cliente.getNome() + "Criado com sucesso";
//        }catch (Exception e){
//            return "Erro ao salvar o cliente" + e.getMessage();
//        }
//
//    }

    @Override
    public ClienteEntity buscarClientePorId(int idConta) {
        String sql = "SELECT * FROM cliente WHERE fk_nr_id_conta = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idConta}, new ClienteMapper());
    }

    @Override
    public List<ClienteEntity> buscarTodosClientes() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new ClienteMapper());
    }

}
