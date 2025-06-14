package org.example.controles;

import org.example.model.entidades.Aluno;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudentController {
    private SceneManager sceneManager;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label raLabel;

    @FXML
    private Label notaA1Label;

    @FXML
    private Label notaA2Label;

    @FXML
    private Label notaA3Label;

    @FXML
    private Label notaFinalLabel;

    private Aluno aluno;

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
        mostrarDadosDoAluno();
    }

    private void mostrarDadosDoAluno() {
        nomeLabel.setText(aluno.getNome());
        raLabel.setText(aluno.getRa());

    }

    public void setSceneManager(SceneManager manager) {
        this.sceneManager = manager;
    }
}
