package com.fourcamp.NutriPlan.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class ClienteRepository {

    private Connection connection;

    public ClienteRepository() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/NutriPlan";
        String user = "postgres";
        String password = "password";

        connection = DriverManager.getConnection(url, user, password);
    }

    public void criarCliente(String nome, String email, String genero, Double peso, Double pesoDesejado,Double altura, Date dataNascimento, String senha, String categoria, String tempo_meta) throws SQLException {
        String sql = "{call criar_cliente(?, ?, ?, ?, ?, ?, ?, ? , ? , ?)}";
        try (CallableStatement statement = connection.prepareCall(sql)) {
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, genero);
            statement.setDouble(4, peso);
            statement.setDouble(5, pesoDesejado);
            statement.setDouble(6,altura);
            statement.setDate(7, new java.sql.Date(dataNascimento.getTime()));
            statement.setString(8, senha);
            statement.setString(9,categoria);
            statement.setString(10,tempo_meta);
            statement.execute();
        }
    }

    public void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
