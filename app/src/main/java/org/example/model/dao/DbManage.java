package org.example.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbManage {
    private static final String dburl = "jdbc:mysql://localhost:3306/classmng_ex";
    private static final String dbuser = "root";
    private static final String dbpswd = "";
    private static Connection con;
    
    
    public static Connection conectarDb(){
        try{
            con = DriverManager.getConnection(dburl, dbuser, dbpswd);
            System.out.println("Conectado!");        
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return con;
    }
    
    public static void desconectarDb(){
        if (con!=null){
            try {
                con.close();
                System.out.println("Desconectado!");       
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static int initDb() throws SQLException{
        if (con!=null){
            DbManage.criarTabela_Professor();
            DbManage.criarTabela_Disciplina();
            DbManage.criarTabela_Turma();
            DbManage.criarTabela_Aluno();
            DbManage.criarTabela_Nota();
            DbManage.criarTabela_Faltas();
            DbManage.tabelaAlunoTurma();
            DbManage.tabelaProfessorDisciplina();
            DbManage.tabelaLogin();

            return 0;
        }

        return -1;
    }
    //Criação das tabelas das entidades

    public static void criarTabela_Professor() throws SQLException {
        String sqlProfessor = "CREATE TABLE IF NOT EXISTS Professor (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nome VARCHAR (50) NOT NULL, " +
                "pcode VARCHAR (50) NOT NULL UNIQUE" +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(sqlProfessor);
            stmt.executeUpdate();
            stmt.close();
        }

    }

    public static void criarTabela_Disciplina() throws SQLException {
        String sqlDisciplina = "CREATE TABLE IF NOT EXISTS Disciplina (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nome VARCHAR (50) NOT NULL, " +
                "dcode VARCHAR (50) NOT NULL UNIQUE" +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(sqlDisciplina);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public static void criarTabela_Turma() throws SQLException {
        String sqlTurma = "CREATE TABLE IF NOT EXISTS Turma (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nome VARCHAR (50) NOT NULL, " +
                "tcode VARCHAR (50) NOT NULL UNIQUE, " +
                "professor_code VARCHAR(50), " +
                "dcode VARCHAR (50), " +
                "FOREIGN KEY (professor_code) REFERENCES Professor(pcode), " +
                "FOREIGN KEY (dcode) REFERENCES Disciplina(dcode)" +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(sqlTurma);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public static void criarTabela_Aluno() throws SQLException {
        String sqlAluno = "CREATE TABLE IF NOT EXISTS Aluno (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "nome VARCHAR (50) NOT NULL, " +
                "ra VARCHAR (50) NOT NULL UNIQUE" +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(sqlAluno);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public static void criarTabela_Nota() throws SQLException {
        String sqlNota = "CREATE TABLE IF NOT EXISTS Nota (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "tipo VARCHAR (10) NOT NULL, " +
                "valor INT NOT NULL, " +
                "ra VARCHAR (50), " +
                "dcode VARCHAR (50), " +
                "FOREIGN KEY (ra) REFERENCES Aluno(ra), " +
                "FOREIGN KEY (dcode) REFERENCES Disciplina(dcode)" +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(sqlNota);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public static void criarTabela_Faltas() throws SQLException {
        String slqFalta = "CREATE TABLE IF NOT EXISTS Faltas(" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "valor BIT NOT NULL, " +
                "ra VARCHAR (50), " +
                "data DATE NOT NULL UNIQUE, " +
                "tcode VARCHAR (50), " +
                "FOREIGN KEY (ra) REFERENCES Aluno(ra), " +
                "FOREIGN KEY (tcode) REFERENCES Turma(tcode)" +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(slqFalta);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    //Criação das tabelas de relacionamento

    public static void tabelaAlunoTurma() throws SQLException {
        String sqlAlunoTurma = "CREATE TABLE IF NOT EXISTS Aluno_turma (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "ra VARCHAR (50) NOT NULL, " +
                "tcode VARCHAR (50) NOT NULL, " +
                "FOREIGN KEY (ra) REFERENCES Aluno(ra), " +
                "FOREIGN KEY (tcode) REFERENCES Turma(tcode) " +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(sqlAlunoTurma);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public static void tabelaProfessorDisciplina() throws SQLException {
        String sqlProfessorDIsciplina = "CREATE TABLE IF NOT EXISTS Professor_Disciplina(" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "pcode VARCHAR (50), " +
                "dcode VARCHAR (50), " +
                "FOREIGN KEY (pcode) REFERENCES Professor(pcode), " +
                "FOREIGN KEY (dcode) REFERENCES Disciplina(dcode)" +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(sqlProfessorDIsciplina);
            stmt.executeUpdate();
            stmt.close();
        }
    }

    public static void tabelaLogin() throws SQLException{
        String sqlLogin = "CREATE TABLE IF NOT EXISTS Login(" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "ucode VARCHAR (50) NOT NULL UNIQUE, " +
                "senha_h TEXT NOT NULL, " +
                "utipo VARCHAR (50)" +
                ");";

        if (con!=null) {
            PreparedStatement stmt = con.prepareStatement(sqlLogin);
            stmt.executeUpdate();
            stmt.close();
        }
    }
}
