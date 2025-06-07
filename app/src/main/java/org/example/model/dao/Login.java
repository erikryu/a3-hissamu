package org.example.model.dao;

import org.example.model.dao.DbManage;
import org.example.model.entidades.UserType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.example.model.entidades.User;
import org.example.model.dao.UserDAO;

public class Login {
    public static void registrar(String code, String password) throws SQLException {
        UserDAO user_manage = new UserDAO(DbManage.conectarDb());
        UserType.U_Tipo tipo;
        User user = null;

        if (user_manage.verificaAluno(code) == Boolean.TRUE){
            user = new User(code, password, UserType.U_Tipo.ALUNO);
        } else if (user_manage.verificaProfessor(code) == Boolean.TRUE){
            user = new User(code, password, UserType.U_Tipo.PROFESSOR);
        }

        user_manage.adicionarUser(user);
    }

    public static Boolean login(String code, String password) throws SQLException {
        UserDAO user_manage = new UserDAO(DbManage.conectarDb());
        User user = null;

        if (user_manage.verificaAluno(code) == Boolean.TRUE){
            UserType.utipo = UserType.U_Tipo.ALUNO;
            System.out.println("Aluno logado.");

            return Boolean.TRUE;

        } else if (user_manage.verificaProfessor(code) == Boolean.TRUE){
            UserType.utipo = UserType.U_Tipo.PROFESSOR;
            System.out.println("Professor logado");

            return Boolean.TRUE;

        }

        return Boolean.FALSE;
    }
}
