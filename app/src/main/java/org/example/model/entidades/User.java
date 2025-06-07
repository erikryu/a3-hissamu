package org.example.model.entidades;

public class User {
    private String username;
    private String password;
    private UserType.U_Tipo tipo;

    public User(String username, String password, UserType.U_Tipo tipo)
    {
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public String getUsr(){
        return username;
    }

    public String getPswd(){
        return password;
    }

    public UserType.U_Tipo getTipo(){
        return tipo;
    }
}
