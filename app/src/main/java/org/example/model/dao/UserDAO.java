package org.example.model.dao;

import org.example.model.entidades.User;
import org.example.model.entidades.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection){
        this.connection = connection;
    }

    public void adicionarUser(User user){
        String add_sql = "INSERT INTO Login (ucode, senha_h, utipo) VALUES (?, ?, ?);";

        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(add_sql);

                stmt.setString(1, user.getUsr());
                stmt.setString(2, user.getPswd());

                //String tipo = String.format(String.valueOf(user.getTipo()));
                stmt.setString(3, user.getTipo().toString());

                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void removerUser(String ucode) throws SQLException {
        String remove_sql = "DELETE FROM Login WHERE ucode=?";

        if (connection!=null){
            PreparedStatement stmt = connection.prepareStatement(remove_sql);

            stmt.setString(1, ucode);

            stmt.executeUpdate();
            stmt.close();
            }
        }

    public Boolean verificaProfessor(String ucode) throws SQLException {
        String verificaProfessor = "SELECT 1 FROM Professor WHERE pcode=?";

        if (connection!=null) {
            PreparedStatement stmt = connection.prepareStatement(verificaProfessor);

            stmt.setString(1, ucode);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }

        return Boolean.FALSE;
    }

    public Boolean verificaAluno(String ucode) throws SQLException {
        String verificaAluno = "SELECT 1 FROM Aluno WHERE ra=?";

        if (connection!=null) {
            PreparedStatement stmt = connection.prepareStatement(verificaAluno);

            stmt.setString(1, ucode);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }

        return Boolean.FALSE;
    }

    public void modificarSenha(User user, String npasswd){
        String sql = "UPDATE Login SET senha_h=? WHERE ucode=?";
        if (connection!=null){
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, npasswd);
                stmt.setString(2, user.getUsr());

                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
