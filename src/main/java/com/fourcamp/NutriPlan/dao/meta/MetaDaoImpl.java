package com.fourcamp.NutriPlan.dao.meta;

import com.fourcamp.NutriPlan.model.meta.MetaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MetaDaoImpl implements MetaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public MetaEntity criarMeta (MetaEntity meta) {
        String sql = "SELECT criar_meta(?, ?, ?, ?)";
         jdbcTemplate.queryForObject(sql, new Object[]{
                        meta.getPesoDesejado(), meta.getIdConta(), meta.getIdObjetivo(), meta.getIdTempo()},
                Integer.class);
         return meta;
    }

}
