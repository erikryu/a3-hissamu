package org.example.controles;

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
    }

    @FXML
    private void handleSalvarAluno() {
        String nome = nomeAluno.getText();
        String ra = alunoRa.getText();

        AlunoDAO maluno = new AlunoDAO(DbManage.conectarDb());
        maluno.adicionarAluno(new Aluno(nome, ra));

        DbManage.desconectarDb();
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
    }

    @FXML
    private void handleSalvarDisciplina() {
        String dnome = nomeDisciplina.getText();
        String codigo = codigoDisciplina.getText();

        DisciplinaDAO mDisciplina = new DisciplinaDAO(DbManage.conectarDb());
        mDisciplina.adicionarDisciplina(new Disciplina(dnome, codigo));

        DbManage.desconectarDb();
    }

    @FXML
    private void adicionarNovoUsuario() {
        try {
            // Carrega o FXML da nova janela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddUser.fxml"));
            Parent root = loader.load();

            // Configura o Stage (janela)
            Stage stage = new Stage();
            stage.setTitle("Novo Usuário");
            stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela principal
            stage.setScene(new Scene(root));

            // Exibe a janela
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Trate erros adequadamente
        }
    }

    @FXML
    private void handleVoltar(){
        sceneManager.switchTo("login");
        UserType.utipo = UserType.U_Tipo.NULO;
    }

    public void setSceneManager(SceneManager manager){
        this.sceneManager = manager;
    }
}
