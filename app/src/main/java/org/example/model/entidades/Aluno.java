package org.example.model.entidades;

import org.example.model.entidades.Turma;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private final String ra;
    private final String nome;
    private boolean falta;
    private final List<Turma> turmas;
    
    public Aluno(String nome, String ra){
        this.ra = ra;
        this.nome = nome;
        this.falta = false;
        this.turmas = new ArrayList<>();
    }
    
    //Retorna RA do aluno
    public String getRa() {
        return ra;
    }
    
    //Retorna nome do aluno
    public String getNome() {
        return nome;
    }

    public boolean getFaltas(){
        return falta;
    }

    public void escolherTurma(Turma turma){
        if (turmas.size()<3 && !turmas.contains(turma))
        {
            turmas.add(turma);
        }
    }
    
    public void sairDaTurma(Turma turma){
        if (turmas.size() - 1 > 1){
            turmas.remove(turma);
        }
    }
}
