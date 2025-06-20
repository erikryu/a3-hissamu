package org.example.model.dao;

import org.example.model.entidades.Disciplina;
import org.example.model.entidades.Professor;
import org.example.model.dao.ProfessorDAO;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DisciplinaDAO {
    private final Connection connection;
    
    public DisciplinaDAO(Connection connection){
        this.connection = connection;
    }
    
    public void adicionarDisciplina(Disciplina disciplina){
        String add_sql = "INSERT INTO Disciplina (nome, dcode) VALUES (?, ?);";
        
        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(add_sql);
                
                stmt.setString(1, disciplina.getNome());
                stmt.setString(2, disciplina.getdCode());
                
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void removerDisciplina(String dcode) {
        String remove_sql = "DELETE FROM Disciplina WHERE dcode=?";
        
        if (connection!=null){
            try{
                PreparedStatement stmt = connection.prepareStatement(remove_sql);
                
                stmt.setString(1, dcode);
                
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public List<Disciplina> listarTodos(){
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT id, nome, dcode FROM Disciplina";
        
        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()){
                        Disciplina disciplina = new Disciplina(rs.getString("nome"), 
                        rs.getString("dcode"));
                    
                    disciplinas.add(disciplina);
                }
                
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return disciplinas;
    }
    
    public Disciplina listarPorCodigo(String dcode){
        String sql = "SELECT id, nome, dcode FROM Disciplina WHERE dcode=?";
        Disciplina disciplina = null;
        
        if(connection!=null) {
            try{
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, dcode);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    disciplina = new Disciplina(rs.getString("nome"), rs.getString("dcode"));
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }         
        }
        
        return disciplina;
    }
    
    public void atualizarDisciplina(Disciplina disciplina){
        String sql = "UPDATE Disciplina SET nome=? WHERE dcode=?";
        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                
                stmt.setString(1, disciplina.getNome());
                stmt.setString(2, disciplina.getdCode());
                
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Disciplina> listarDisciplinasPorProfessor(String pcode) {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT d.nome, d.dcode " +
                "FROM Professor p " +
                "INNER JOIN Professor_Disciplina pd ON p.pcode = pd.pcode " +
                "INNER JOIN Disciplina d ON pd.dcode = d.dcode " +
                "WHERE p.pcode =?;";

        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, pcode);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    String dnome = rs.getString("nome");
                    String dcode = rs.getString("dcode");

                    disciplinas.add(new Disciplina(dnome, dcode));
                }

                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return disciplinas;
    }

    public List<Disciplina> listarDisciplinasPorAluno(String ra) throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT DISTINCT D.dcode, D.nome " +
                "FROM Aluno A " +
                "JOIN Aluno_turma AT ON A.ra = AT.ra " +
                "JOIN Turma T ON AT.tcode = T.tcode " +
                "JOIN Disciplina D ON T.dcode = D.dcode " +
                "WHERE A.ra = ?";

        try (Connection conn = DbManage.conectarDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ra);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Disciplina disciplina = new Disciplina(rs.getString("nome"), rs.getString("nome"));
                disciplinas.add(disciplina);
            }
        }
        return disciplinas;
    }

    public void adicionarProfessor(String pcode, String dcode) throws SQLException{
        String sql = "INSERT INTO Professor_Disciplina (pcode, dcode) VALUES (?, ?);";
        ProfessorDAO mprofessor = new ProfessorDAO(connection);
        DisciplinaDAO mdisciplina = new DisciplinaDAO(connection);

        if (connection!=null) {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, mprofessor.listarPorCodigo(pcode).getPcode());
            stmt.setString(2, mdisciplina.listarPorCodigo(dcode).getdCode());

            stmt.executeUpdate();
            stmt.close();
        }
    }
    
}
