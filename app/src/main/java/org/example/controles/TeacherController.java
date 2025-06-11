package org.example.controles;

import javafx.beans.property.Property;
import javafx.scene.control.cell.PropertyValueFactory;

import org.example.model.dao.DbManage;
import org.example.model.dao.DisciplinaDAO;
import org.example.model.entidades.Aluno;
import org.example.model.entidades.Disciplina;
import org.example.model.dao.AlunoDAO;
import org.example.model.entidades.Nota;
import org.example.model.entidades.TipoNotas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.SQLException;

public class TeacherController {
    private SceneManager sceneManager;
    private AlunoDAO alunoDAO = new AlunoDAO(DbManage.conectarDb());
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO(DbManage.conectarDb());

    @FXML
    private ComboBox<Disciplina> comboUC;

    @FXML
    private TableView<Aluno> tableAlunos;

    @FXML
    private TableColumn<Aluno, String> colNome;

    @FXML
    private TableColumn<Aluno, String> colRa;

    @FXML
    private TableColumn<Nota, TipoNotas> colA1;

    @FXML
    private TableColumn<Nota, TipoNotas> colA2;

    @FXML
    private TableColumn<Nota, TipoNotas> colA3;

    @FXML
    private TableColumn<Nota, Integer> colNotaFinal;

    @FXML
    private Button btnSalvar;

    private ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();
    private ObservableList<Disciplina> listaDeDisciplinas = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        // Simulando lista de UCs

        listaDeDisciplinas.addAll(disciplinaDAO.listarDisciplinasPorProfessor("000001"));
        comboUC.setItems(listaDeDisciplinas);
        // Criar função em alunodao para listar alunos por professor
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));

        comboUC.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, novaDisciplina) -> {
            if (novaDisciplina != null) {
                try {
                    carregarAlunosPorDisciplina(novaDisciplina);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Preenchendo a tabela
        if (!listaDeDisciplinas.isEmpty()) {
            comboUC.getSelectionModel().selectFirst();
        }
    }

    private void carregarAlunosPorDisciplina(Disciplina disciplina) throws SQLException {
        listaAlunos.clear();
        listaAlunos.addAll(alunoDAO.listarAlunosPorDisciplina(disciplina.getdCode()));
        tableAlunos.setItems(listaAlunos);
    }
    @FXML
    private void SalvarAluno() {
        // Lógica de salvar aluno (simulação por enquanto)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Salvar");
        alert.setHeaderText(null);
        alert.setContentText("Dados dos alunos salvos com sucesso!");
        alert.showAndWait();
    }

    public void setSceneManager(SceneManager manager) {
        this.sceneManager = manager;
    }
}
