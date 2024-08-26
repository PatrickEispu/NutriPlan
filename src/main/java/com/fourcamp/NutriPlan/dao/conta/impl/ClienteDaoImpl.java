package com.fourcamp.NutriPlan.dao.conta.impl;
import com.fourcamp.NutriPlan.dao.conta.ClienteDao;
import com.fourcamp.NutriPlan.dao.mapper.ClienteRowMapper;
import com.fourcamp.NutriPlan.dtos.conta.ClienteDto;
import com.fourcamp.NutriPlan.model.conta.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class ClienteDaoImpl implements ClienteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ClienteEntity criarConta(ClienteEntity cliente) {
        String sql = "SELECT criar_cliente(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.queryForObject(sql, new Object[]{
                        cliente.getDsGenero(), cliente.getNrPeso(), cliente.getNrAltura(), cliente.getDsDataNascimento(), cliente.getNrTmb(), cliente.getNrGet(), cliente.getFkNrIdCategoria(), cliente.getFkNrIdConta()},
                Integer.class);
        return cliente;
    }

    @Override
    @Transactional
    public ClienteEntity buscarClientePorId(int idConta) {
        String sql = "SELECT * FROM cliente WHERE fk_nr_id_conta = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ClienteEntity.class), idConta);
    }

    @Override
    @Transactional
    public List<ClienteEntity> buscarTodosClientes() {
        String sql = "SELECT * FROM listar_todos_clientes()";
        return jdbcTemplate.query(sql, new ClienteRowMapper());
    }

    public void atualizarCliente(ClienteDto cliente) {
        String sql = "CALL atualizar_cliente_procedure(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.execute(sql, (CallableStatementCallback<Object>) cs -> {
            cs.setInt(1, cliente.getIdConta());
            cs.setString(2, cliente.getGenero());
            cs.setDouble(3, cliente.getPeso());
            cs.setDouble(4, cliente.getAltura());
            cs.setString(5, cliente.getDataNascimento());
            cs.setDouble(6, cliente.getTmb());
            cs.setDouble(7, cliente.getGet());
            cs.setString(8, (cliente.getCategoria()));
            cs.execute();
            return null;
        });
    }

    @Override
    public String buscarClienteCategoria(int idCategoria) {
        String sql = "SELECT buscar_categoria_por_id(?)";

        return jdbcTemplate.queryForObject(sql,String.class,idCategoria);
    }

    @Override
    public void atualizarTMBGET(Integer idConta, double tmb, double get) {
        String sql = "CALL atualiza_cliente_get_tmb(?,?,?)";
        jdbcTemplate.update(sql,idConta,tmb,get);
    }

}
