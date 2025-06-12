package org.example.model.dao;

import org.example.model.entidades.Faltas;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class FaltasDAO {
    private final Connection conexao;

    public FaltasDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void darFalta(Faltas falta) throws SQLException{
        String darFaltaSql = "INSERT INTO Faltas (valor, ra, tcode, data) VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = conexao.prepareStatement(darFaltaSql);

        stmt.setInt(1, falta.getValor());
        stmt.setString(2, falta.getAluno());
        stmt.setString(3, falta.getTurma());
        stmt.setDate(4, Date.valueOf(falta.getData()));

        stmt.executeUpdate();
        stmt.close();
    }

    public void removerFalta(Faltas falta) throws SQLException {
        String darFaltaSql = "REMOVE FROM Faltas WHERE data=? AND ra=? AND tcode=?";

        PreparedStatement stmt = conexao.prepareStatement(darFaltaSql);

        stmt.setDate(1, Date.valueOf(falta.getData()));
        stmt.setString(2, falta.getAluno());
        stmt.setString(3, falta.getTurma());

        stmt.executeUpdate();
        stmt.close();
    }

    public int somarFaltas(String ra, String tcode) throws SQLException {
        String listarFaltasSql = "SELECT SUM(valor) AS tfaltas FROM Faltas WHERE ra=? AND tcode=?";
        int tfaltas = 0;

        PreparedStatement stmt = conexao.prepareStatement(listarFaltasSql);
        stmt.setString(1, ra);
        stmt.setString(2, tcode);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            tfaltas = rs.getInt("tfaltas");
        }

        return tfaltas;
    }
}
