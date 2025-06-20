package org.example.model.dao;

import org.example.model.entidades.Aluno;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class AlunoDAO {
    private final Connection connection;
    
    public AlunoDAO(Connection connection){
        this.connection = connection;
    }
    
    public void adicionarAluno(Aluno aluno){
        String add_sql = "INSERT INTO Aluno (nome, ra) VALUES (?, ?);";
        
        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(add_sql);
                
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getRa());
                
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void removerAluno(String ra) {
        String remove_sql = "DELETE FROM Aluno WHERE ra=?";
        
        if (connection!=null){
            try{
                PreparedStatement stmt = connection.prepareStatement(remove_sql);
                
                stmt.setString(1, ra);
                
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public List<Aluno> listarTodos(){
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT ra, nome FROM Aluno";
        
        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()){
                    Aluno aluno = new Aluno(
                    rs.getString("nome"),
                    rs.getString("ra")
                    );
                    alunos.add(aluno);
                }
                
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return alunos;
    }

    public List<Aluno> listarAlunosPorProfessor(String pcode) throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT DISTINCT A.ra, A.nome " +
                "FROM Professor P " +
                "JOIN Turma T ON P.pcode = T.professor_code " +
                "JOIN Aluno_turma AT ON T.tcode = AT.tcode " +
                "JOIN Aluno A ON AT.ra = A.ra " +
                "WHERE P.pcode = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pcode);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno(rs.getString("nome"), rs.getString("ra"));
                    alunos.add(aluno);
                }
            }
        }
        return alunos;
    }

    public List<Aluno> listarAlunosPorDisciplina(String dcode) throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT DISTINCT A.ra, A.nome " +
                "FROM Disciplina D " +
                "JOIN Turma T ON D.dcode = T.dcode " +
                "JOIN Aluno_turma AT ON T.tcode = AT.tcode " +
                "JOIN Aluno A ON AT.ra = A.ra " +
                "WHERE D.dcode = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dcode);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno(rs.getString("nome"), rs.getString("ra"));
                    alunos.add(aluno);
                }
            }
        }
        return alunos;
    }
    
    public Aluno listarPorRa(String ra){
        String sql = "SELECT id, ra, nome FROM Aluno WHERE ra=?";
        Aluno aluno = null;
        
        if(connection!=null) {
            try{
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, ra);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    aluno = new Aluno(
                            rs.getString("nome"),
                            rs.getString("ra")
                    );
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }         
        }
        
        return aluno;
    }
    
    public void atualizarNomeAluno(Aluno aluno, String nome) {
        String sql = "UPDATE Aluno SET nome=? WHERE ra=?";
        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                
                stmt.setString(1, nome);
                stmt.setString(2, aluno.getRa());
                
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
