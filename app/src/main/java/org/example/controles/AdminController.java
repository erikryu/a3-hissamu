package org.example.controles;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdminController {

    @FXML
    private TextField professorNomeField;

    @FXML
    private TextField professorDisciplinaField;

    @FXML
    private TextField professorUCField;

    @FXML
    private TextField professorEmailField;

    @FXML
    private TextField alunoRAField;

    @FXML
    private TextField alunoNomeField;

    @FXML
    private TextField turmaNomeField;

    @FXML
    private TextField disciplinaNomeField;

    @FXML
    private void handleSalvarProfessor() {
        String nome = professorNomeField.getText();
        String disciplina = professorDisciplinaField.getText();
        String uc = professorUCField.getText();
        String email = professorEmailField.getText();

        System.out.println("Professor salvo: " + nome + ", " + disciplina + ", " + uc + ", " + email);


    }

    @FXML
    private void handleSalvarAluno() {
        String ra = alunoRAField.getText();
        String nome = alunoNomeField.getText();

        System.out.println("Aluno salvo: RA = " + ra + ", Nome = " + nome);
    }

    @FXML
    private void handleSalvarTurma() {
        String nomeTurma = turmaNomeField.getText();
        System.out.println("Turma salva: " + nomeTurma);
    }

    @FXML
    private void handleSalvarDisciplina() {
        String nomeDisciplina = disciplinaNomeField.getText();
        System.out.println("Disciplina salva: " + nomeDisciplina);
    }
}
