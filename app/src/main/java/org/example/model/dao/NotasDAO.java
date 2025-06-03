package org.example.model.dao;

import org.example.model.entidades.Aluno;
import org.example.model.entidades.Nota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class NotasDAO {
    private final Connection conexao;

    public NotasDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirNota(Nota nota) throws SQLException {
        String sql = "INSERT INTO Nota (tipo, ra, valor, dcode) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, nota.getTipo());
        stmt.setString(2, nota.getRa());
        stmt.setInt(3, nota.getValor());
        stmt.setString(4, nota.getDcode());

        stmt.executeUpdate();
        stmt.close();
    }

    public int somarNotas(Aluno aluno) throws SQLException {
        String sql = "SELECT SUM(valor) as total FROM Nota WHERE ra = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, aluno.getRa());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("total");
        }

        return 0;
    }

    public List<Integer> listarTodasAsNotas(Aluno aluno) throws SQLException {
        List<Integer> notas_do_aluno = new ArrayList<>();

        String sql = "SELECT valor FROM Nota WHERE ra = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, aluno.getRa());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            notas_do_aluno.add(rs.getInt("valor"));
        }

        return notas_do_aluno;
    }
}
