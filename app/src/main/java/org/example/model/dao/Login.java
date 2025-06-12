package org.example.model.dao;

import org.example.model.dao.DbManage;
import org.example.model.entidades.UserType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.model.entidades.User;
import org.example.model.dao.UserDAO;

public class Login {
    public static void registrar(String code, String password) throws SQLException {
        UserDAO user_manage = new UserDAO(DbManage.conectarDb());
        UserType.U_Tipo tipo;
        User user = null;

        String senhaHash = gerarHash(password);

        if (user_manage.verificaAluno(code) == Boolean.TRUE){
            user = new User(code, senhaHash, UserType.U_Tipo.ALUNO);
        } else if (user_manage.verificaProfessor(code) == Boolean.TRUE){
            user = new User(code, senhaHash, UserType.U_Tipo.PROFESSOR);
        }

        user_manage.adicionarUser(user);
    }

    public static Boolean login(String code, String password) throws SQLException {
        UserDAO user_manage = new UserDAO(DbManage.conectarDb());
        User user = null;
        String senhaStored= "";
        String tipo="";
        String senhaHash = gerarHash(password);

        String sqlVerifyLogin = "SELECT senha_h, utipo FROM Login WHERE ucode=?";
        try (Connection con = DbManage.conectarDb()) {
            PreparedStatement stmt = con.prepareStatement(sqlVerifyLogin);

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                senhaStored = rs.getString("senha_h");
                tipo = rs.getString("utipo");
            }
        }

        if (senhaStored.equals("12345") && UserType.compararAdmin(tipo)){
            UserType.utipo = UserType.U_Tipo.ADMIN;
            return true;
        } else if (senhaStored.equals(senhaHash) && UserType.compararAluno(tipo)) {
            UserType.utipo = UserType.U_Tipo.ALUNO;
            return true;
        } else if (senhaStored.equals(senhaHash) && UserType.compararProfessor(tipo)) {
            UserType.utipo = UserType.U_Tipo.PROFESSOR;
            return true;
        }

        return false;
    }

    private static String gerarHash(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro na geração de hash: " + e);
        }
    }
}
