package com.fourcamp.NutriPlan.service.conta;

import com.fourcamp.NutriPlan.dao.conta.ContaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {
    @Autowired
    ContaDao contaDao;

    public Integer getIdContaPorEmail(String email) {
        return contaDao.getIdContaPorEmail(email);

    }
}
