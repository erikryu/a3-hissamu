package org.example.model.dao;

import org.example.model.entidades.Aluno;
import org.example.model.entidades.Disciplina;
import org.example.model.entidades.Nota;
import org.example.model.entidades.TipoNotas;

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

    public int somarNotas(String ra) throws SQLException {
        String sql = "SELECT SUM(valor) as total FROM Nota WHERE ra = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, ra);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("total");
        }

        return 0;
    }

    public void salvarOuAtualizar(List<Nota> notas) throws SQLException {
        // SQL para verificar se uma nota específica já existe
        String checkSql = "SELECT COUNT(*) FROM Nota WHERE ra = ? AND tipo = ? AND dcode = ?";

        String insertSql = "INSERT INTO Nota (ra, tipo, valor, dcode) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE Nota SET valor = ? WHERE ra = ? AND tipo = ? AND dcode = ?";

        try (PreparedStatement checkStmt = conexao.prepareStatement(checkSql);
             PreparedStatement insertStmt = conexao.prepareStatement(insertSql);
             PreparedStatement updateStmt = conexao.prepareStatement(updateSql)) {

            for (Nota nota : notas) {
                // 1. Verifica se a nota existe
                checkStmt.setString(1, nota.getRa());
                checkStmt.setString(2, nota.getTipo()); // getTipo() retorna a String do Enum
                checkStmt.setString(3, nota.getDcode());
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                boolean existe = rs.getInt(1) > 0;

                // 2. Executa INSERT ou UPDATE
                if (existe) {
                    // É uma nota existente, então UPDATE
                    updateStmt.setInt(1, nota.getValor());
                    updateStmt.setString(2, nota.getRa());
                    updateStmt.setString(3, nota.getTipo());
                    updateStmt.setString(4, nota.getDcode());
                    updateStmt.addBatch(); // Adiciona ao lote de execução
                } else {
                    // É uma nota nova, então INSERT
                    insertStmt.setString(1, nota.getRa());
                    insertStmt.setString(2, nota.getTipo());
                    insertStmt.setInt(3, nota.getValor());
                    insertStmt.setString(4, nota.getDcode());
                    insertStmt.addBatch();
                }
            }

            // 3. Executa as operações em lote
            insertStmt.executeBatch();
            updateStmt.executeBatch();
        }
    }

    public List<Nota> buscaPorDisciplina(String dcode) throws SQLException {
        List<Nota> notas = new ArrayList<>();

        String sql = "SELECT tipo, ra, valor, dcode FROM Nota WHERE dcode=?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, dcode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TipoNotas tipo = TipoNotas.valueOf(rs.getString("tipo"));
                String ra = rs.getString("ra");
                int valor = rs.getInt("valor");

                notas.add(new Nota(tipo, ra, valor, dcode));
            }
        }
        return notas;
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
