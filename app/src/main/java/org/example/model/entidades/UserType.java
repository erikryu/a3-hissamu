package org.example.model.entidades;

public class UserType {
    public static U_Tipo utipo = U_Tipo.NULO;

    public enum U_Tipo {
        NULO, ADMIN, PROFESSOR, ALUNO;
    }

    static public Boolean compararNulo(String t) {
        return U_Tipo.NULO.toString().equalsIgnoreCase(t);
    }

    static public Boolean compararAdmin(String t) {
        return U_Tipo.ADMIN.toString().equalsIgnoreCase(t);
    }

    static public Boolean compararProfessor(String t) {
        return U_Tipo.PROFESSOR.toString().equalsIgnoreCase(t);
    }

    static public Boolean compararAluno(String t) {
        return U_Tipo.ALUNO.toString().equalsIgnoreCase(t);
    }
}
