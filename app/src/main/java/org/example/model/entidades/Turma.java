package org.example.model.entidades;

public class Turma {
    private final String nome;
    private final String tcode;
    private final String pcode;
    private final String dcode;
    
    public Turma(String nome, String tcode, String pcode, String dcode)
    {
        this.nome = nome;
        this.tcode = tcode;
        this.pcode = pcode;
        this.dcode = dcode;
    }
    
    public String getDisciplina(){
        return dcode;
    }
    
    public String getProfessor(){
        return pcode;
    }
    
    public String getCode(){
        return tcode;
    }

    public String getNome(){
        return nome;
    }
}
