package org.example.model.entidades;

public class Nota {
    private final TipoNotas tipo;
    private final String ra;
    private int valor;
    private String dcode;

    public Nota(TipoNotas tipo, String ra, int valor, String dcode){
        this.tipo = tipo;
        this.ra = ra;
        this.valor = valor;
        this.dcode = dcode;
    }

    public String getRa(){
        return ra;
    }

    public String getDcode(){
        return dcode;
    }

    public String getTipo(){
        return tipo.toString();
    }

    public int getValor(){
        return valor;
    }

}
