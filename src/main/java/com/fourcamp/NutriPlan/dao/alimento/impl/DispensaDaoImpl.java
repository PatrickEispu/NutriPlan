package com.fourcamp.NutriPlan.dao.alimento.impl;

import com.fourcamp.NutriPlan.dao.alimento.DispensaDao;
import com.fourcamp.NutriPlan.dtos.alimento.DispensaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DispensaDaoImpl implements DispensaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer quantidade) {
        String sql = "SELECT adicionar_alimento_na_dispensa(?,?,?)";
        jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(DispensaDto.class), idDispensa, idAlimento, quantidade);
    }

    public Integer criarDispensa(Integer idConta) {
        String sql = "SELECT criar_dispensa (?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, idConta);

    }

    @Override
    public Integer dispensaExiste(Integer idConta) {
        String sql = ("select contar_dispensas_por_conta(?)");
        return jdbcTemplate.queryForObject(sql, Integer.class, idConta);
    }

    @Override
    public Integer getDispensa(Integer idConta) {
        String sql = "select nr_id_dispensa from buscar_dispensa_por_id_conta(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, idConta);
    }

    @Override
    public Integer getAlimentoDispensaCount(Integer idDispensa) {
        String sql = "SELECT contar_alimento_dispensa(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, idDispensa);
    }

    @Override
    public Integer alimentoExisteNaDispensa(Integer idDispensa, Integer idAlimento) {
        String sql = "SELECT contar_id_alimento(?,?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, idDispensa, idAlimento);
    }

    @Override
    public void atualizarAlimentoNaDispensa(Integer idDispensa, Integer idAlimento, Integer nrQuantidade) {
        String sql = "SELECT atualizar_quantidade_alimento_na_dispensa(?,?,?)";
        jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(DispensaDto.class), idDispensa, idAlimento, nrQuantidade);
    }

    @Override
    public Integer getALimentoQuantidade(Integer idDispensa, Integer idAlimento) {
        String sql = ("SELECT nr_quantidade FROM dispensa_alimento WHERE fk_nr_id_dispensa = ? AND fk_nr_id_alimento = ?");
        return jdbcTemplate.queryForObject(sql, Integer.class, idDispensa, idAlimento);
    }

    @Override
    public List<DispensaDto> getAlimentoList(Integer idDispensa) {
        String sql = "SELECT * FROM dispensa_alimento WHERE fk_nr_id_dispensa = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                {
                    DispensaDto dispensaDto = new DispensaDto();
                    dispensaDto.setFkNrIdAlimento(rs.getInt("fk_nr_id_alimento"));
                    dispensaDto.setNrQuantidade(rs.getInt("nr_quantidade"));
                    return dispensaDto;
                },
                idDispensa);

    }

}
