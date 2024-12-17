package com.fourcamp.NutriPlan.dao.meta.impl;

import com.fourcamp.NutriPlan.dao.meta.MetaDao;
import com.fourcamp.NutriPlan.dtos.diario.MacrosDto;
import com.fourcamp.NutriPlan.model.meta.MetaEntity;
import com.fourcamp.NutriPlan.model.meta.ObjetivoEntity;
import com.fourcamp.NutriPlan.model.meta.TempoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MetaDaoImpl implements MetaDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public MetaEntity criarMeta(MetaEntity meta) {
        String sql = "SELECT criar_meta(?, ?, ?, ?)";
        jdbcTemplate.queryForObject(sql, new Object[]{
                        meta.getPesoDesejado(), meta.getIdConta(), meta.getIdObjetivo(), meta.getIdTempo()},
                Integer.class);
        return meta;
    }

    @Override
    public void salvarMetaDiaria(Integer idConta, MacrosDto macroDiario) {
        String sql = "CALL criar_diario_meta (?,?,?,?,?)";

        double kcal = macroDiario.getKcalTotais();
        double carboidrato = macroDiario.getCarboidrato();
        double proteina = macroDiario.getProteina();
        double gordura = macroDiario.getGordura();

        jdbcTemplate.update(sql, idConta, kcal, carboidrato, proteina, gordura);

    }

    @Override
    public void atualizarMetaDiaria(Integer idConta, MacrosDto macroDiario) {
        String sql = "CALL atualizar_diario_meta(?,?,?,?,?)";
        double kcal = macroDiario.getKcalTotais();
        double carboidrato = macroDiario.getCarboidrato();
        double proteina = macroDiario.getProteina();
        double gordura = macroDiario.getGordura();

        jdbcTemplate.update(sql, idConta, kcal, carboidrato, proteina, gordura);

    }

    @Override
    public MetaEntity getMeta(Integer idConta) {
        String sql = "SELECT * FROM buscar_meta_por_id (?)";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
        {
            MetaEntity meta = new MetaEntity();
            meta.setIdConta(idConta);
            meta.setIdTempo(rs.getInt("fk_nr_id_tempo"));
            meta.setIdObjetivo(rs.getInt("fk_nr_id_objetivo"));

            return meta;
        }, idConta);
    }

    @Override
    public TempoEntity buscarTempoPorId(Integer idTempo) {
        String sqlTempoStr = ("SELECT ds_tempo FROM buscar_tempo_por_id (?)");
        String tempoStr = jdbcTemplate.queryForObject(sqlTempoStr, String.class, idTempo);

        TempoEntity tempo = new TempoEntity();
        tempo.setIdTempo(idTempo);
        tempo.setDescricaoTempo(tempoStr);

        return tempo;
    }

    @Override
    public ObjetivoEntity buscarObjetivoPorId(Integer idObjetivo) {
        String objetivoStr = ("SELECT ds_objetivo FROM buscar_objetivo_por_id (?)");
        String string = jdbcTemplate.queryForObject(objetivoStr, String.class, idObjetivo);

        ObjetivoEntity objetivo = new ObjetivoEntity();
        objetivo.setDsDescricaoObjetivo(string);
        objetivo.setNrIdObjetivo(idObjetivo);
        return objetivo;

    }

    @Override
    public Integer metaExiste(Integer idConta) {
        String sql = "SELECT COUNT(*) FROM meta WHERE fk_nr_id_conta = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idConta);
    }

    @Override
    public MacrosDto getMetaDiaria(Integer idConta) {
        String sql = "SELECT * FROM diario_meta WHERE fk_nr_id_conta = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) ->
                {
                    MacrosDto macrosDto = new MacrosDto();
                    macrosDto.setKcalTotais(rs.getDouble("nr_kcal"));
                    macrosDto.setProteina(rs.getDouble("nr_proteina"));
                    macrosDto.setCarboidrato(rs.getDouble("nr_carboidrato"));
                    macrosDto.setGordura(rs.getDouble("nr_gordura"));

                    return macrosDto;
                }, idConta);

    }

    @Override
    public Integer metaDiariaExiste(Integer idConta) {
        String sql = "SELECT COUNT(*) FROM diario_meta WHERE fk_nr_id_conta = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idConta);
    }


}
