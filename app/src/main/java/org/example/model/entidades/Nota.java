package org.example.model.entidades;

public class Nota {
    private final String tipo;
    private final String ra;
    private int valor;
    private String dcode;

    public Nota(String tipo, String ra){
        this.tipo = tipo;
        this.ra = ra;
        this.valor =  0;
        this.dcode = null;
    }

    public String getRa(){
        return ra;
    }

    public String getDcode(){
        return dcode;
    }

    public String getTipo(){
        return tipo;
    }

    public int getValor(){
        return valor;
    }

    public void darNota(int n){
        valor = n;
    }

    public void selecionarTurma(String code){
        dcode = code;
    }
}
