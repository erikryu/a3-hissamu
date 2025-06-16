package org.example.model.dao;

import org.example.model.entidades.Aluno;
import org.example.model.entidades.Disciplina;
import org.example.model.entidades.Turma;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class TurmaDAO {
    private final Connection connection;

    public TurmaDAO(Connection connection){
        this.connection = connection;
    }

    public void adicionarTurma(Turma turma){
        String add_sql = "INSERT INTO Turma (nome, tcode, professor_code, dcode) VALUES (?, ?, ?, ?);";

        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(add_sql);

                stmt.setString(1, turma.getNome());
                stmt.setString(2, turma.getCode());
                stmt.setString(3, turma.getProfessor());
                stmt.setString(4, turma.getDisciplina());

                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void removerTurma(String tcode) {
        String remove_sql = "DELETE FROM Turma WHERE tcode=?";

        if (connection!=null){
            try{
                PreparedStatement stmt = connection.prepareStatement(remove_sql);

                stmt.setString(1, tcode);

                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Turma> listarTodos(){
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT nome, tcode, professor_code, dcode FROM Turma";

        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    Turma turma = new Turma(
                            rs.getString("nome"),
                            rs.getString("tcode"),
                            rs.getString("professor_code"),
                            rs.getString("dcode"));

                    turmas.add(turma);
                }

            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return turmas;
    }

    public Turma listarPorCodigo(String tcode){
        String sql = "SELECT nome, tcode, professor_code, dcode FROM Turma WHERE tcode=?";
        Turma turma = null;

        if(connection!=null) {
            try{
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, tcode);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    turma = new Turma(
                            rs.getString("nome"),
                            rs.getString("tcode"),
                            rs.getString("professor_code"),
                            rs.getString("dcode"));
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return turma;
    }

    public void atualizarTurma(Turma turma, String tcode){
        String sql = "UPDATE Turma SET nome=?, professor_code=?, dcode=? WHERE tcode=?";
        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, turma.getNome());
                stmt.setString(2, turma.getProfessor());
                stmt.setString(3, turma.getDisciplina());
                stmt.setString(4, tcode);

                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void adicionarAluno(String ra, String tcode) throws SQLException{
        String sql = "INSERT INTO Aluno_turma (ra, tcode) VALUES (?, ?);";
        AlunoDAO maluno = new AlunoDAO(connection);
        TurmaDAO mturma = new TurmaDAO(connection);

        if (connection!=null) {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, maluno.listarPorRa(ra).getRa());
            stmt.setString(2, mturma.listarPorCodigo(tcode).getCode());

            stmt.executeUpdate();
            stmt.close();
        }
    }
}
