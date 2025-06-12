package org.example.model.entidades;

public class Faltas {
    private int valor;
    private String ra;
    private String tcode;
    private String data;

    public Faltas(int valor, String ra, String tcode, String data) {
        this.valor = valor;
        this.ra = ra;
        this.tcode = tcode;
        this.data = data;
    }

    public String getData(){
        return data;
    }

    public int getValor(){
        return valor;
    }

    public String getAluno(){
        return ra;
    }

    public String getTurma() {
        return tcode;
    }
}
