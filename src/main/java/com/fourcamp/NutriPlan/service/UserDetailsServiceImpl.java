package com.fourcamp.NutriPlan.service;

import com.fourcamp.NutriPlan.model.Cliente;
import com.fourcamp.NutriPlan.dao.JdbcTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplateDao jdbcTemplateDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = jdbcTemplateDao.buscarClientePorEmail(email);

        if (cliente == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }

        return UserDetailsImpl.build(cliente);
    }
}
