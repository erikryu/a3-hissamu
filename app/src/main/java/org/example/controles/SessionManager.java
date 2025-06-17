package org.example.controles;

import org.example.model.entidades.User;

public class SessionManager {
    private static SessionManager instance = null;

    private String userId;
    private String userPswd;
    private String tipo;

    public static SessionManager getInstance(){
        if (instance == null) {
            instance = new SessionManager();
        }

        return instance;
    }

    public void createSession(User usuario) {
        this.userId = usuario.getUsr();
        this.userPswd= usuario.getPswd();
        this.tipo = usuario.getTipo().toString();
    }

    public void clearSession() {
        this.userId = null;
        this.userPswd = null;
        this.tipo = null;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPswd() {
        return userPswd;
    }

    public String getUserTipo() {
        return tipo;
    }

    public boolean isSessionActive() {
        return this.userId != null;
    }
}
