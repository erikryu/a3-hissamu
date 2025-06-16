package org.example.controles;

import jdk.jshell.execution.Util;
import org.example.controles.Utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.example.model.dao.DbManage;
import org.example.model.entidades.UserType;

import org.example.model.dao.ProfessorDAO;
import org.example.model.entidades.Professor;

import org.example.model.dao.AlunoDAO;
import org.example.model.entidades.Aluno;

import org.example.model.dao.TurmaDAO;
import org.example.model.entidades.Turma;

import org.example.model.entidades.Disciplina;
import org.example.model.dao.DisciplinaDAO;

import java.io.IOException;

public class AdminController {
    private SceneManager sceneManager;

    @FXML
    private TextField professorNomeField;

    @FXML
    private TextField codigoDoProfessor;

    @FXML
    private TextField nomeAluno;

    @FXML
    private TextField alunoRa;

    @FXML
    private TextField turmaNomeField;

    @FXML
    private TextField codigoTurma;

    @FXML
    private TextField codigoProfessorTurma;

    @FXML
    private TextField codigoDisciplinaTurma;

    @FXML
    private TextField nomeDisciplina;

    @FXML
    private TextField codigoDisciplina;

    @FXML
    private void handleSalvarProfessor() {
        String nome = professorNomeField.getText();
        String codigo = codigoDoProfessor.getText();

        ProfessorDAO mprofessor = new ProfessorDAO(DbManage.conectarDb());
        mprofessor.adicionarProfessor(new Professor(nome, codigo));

        DbManage.desconectarDb();

        Utils.limparCampos(professorNomeField, codigoDoProfessor);
    }

    @FXML
    private void handleSalvarAluno() {
        String nome = nomeAluno.getText();
        String ra = alunoRa.getText();

        AlunoDAO maluno = new AlunoDAO(DbManage.conectarDb());
        maluno.adicionarAluno(new Aluno(nome, ra));

        DbManage.desconectarDb();

        Utils.limparCampos(nomeAluno, alunoRa);
    }

    @FXML
    private void handleSalvarTurma() {
        String nomeTurma = turmaNomeField.getText();
        String codigo = codigoTurma.getText();
        String pcode = codigoProfessorTurma.getText();
        String dcode = codigoDisciplinaTurma.getText();

        TurmaDAO mturma = new TurmaDAO(DbManage.conectarDb());
        mturma.adicionarTurma(new Turma(nomeTurma, codigo, pcode, dcode));

        DbManage.desconectarDb();

        Utils.limparCampos(turmaNomeField, codigoTurma, codigoProfessorTurma, codigoDisciplinaTurma);
    }

    @FXML
    private void handleSalvarDisciplina() {
        String dnome = nomeDisciplina.getText();
        String codigo = codigoDisciplina.getText();

        DisciplinaDAO mDisciplina = new DisciplinaDAO(DbManage.conectarDb());
        mDisciplina.adicionarDisciplina(new Disciplina(dnome, codigo));

        DbManage.desconectarDb();

        Utils.limparCampos(nomeDisciplina, codigoDisciplina);
    }

    @FXML
    private void adicionarNovoUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddUser.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Novo Usuário");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela principal
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltar(){
        sceneManager.switchTo("login");
        UserType.utipo = UserType.U_Tipo.NULO;
    }

    @FXML
    private void adicionarAluno(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddAlunoTurma.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Adicionar Aluno");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela principal
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adicionarProfessor(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddProfessorTurma.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Adicionar Professor");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela principal
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSceneManager(SceneManager manager){
        this.sceneManager = manager;
    }
}
