package org.example.controles;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import javafx.util.StringConverter;
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

import java.sql.SQLException;
import java.util.List;

public class TeacherController {
    private SceneManager sceneManager;
    private AlunoDAO alunoDAO = new AlunoDAO(DbManage.conectarDb());
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO(DbManage.conectarDb());

    @FXML
    private ComboBox<Disciplina> comboUC;

    @FXML
    private TableView<Aluno> tabelaAlunos;

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

    public void initialize() throws SQLException {
        listaDeDisciplinas.addAll(disciplinaDAO.listarDisciplinasPorProfessor("PROFESSOR002"));

        // Configurar como exibir as disciplinas no ComboBox
        comboUC.setCellFactory(lv -> new ListCell<Disciplina>() {
            @Override
            protected void updateItem(Disciplina disciplina, boolean empty) {
                super.updateItem(disciplina, empty);
                setText(empty || disciplina == null ? "" : disciplina.getNome());
            }
        });

        // Configurar como exibir a disciplina selecionada
        comboUC.setConverter(new StringConverter<Disciplina>() {
            @Override
            public String toString(Disciplina disciplina) {
                return disciplina != null ? disciplina.getNome() : "";
            }

            @Override
            public Disciplina fromString(String string) {
                return null; // Não necessário para exibição
            }
        });

        comboUC.setItems(listaDeDisciplinas);

        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colRa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRa()));

        tabelaAlunos.setItems(listaAlunos);

        comboUC.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, novaDisciplina) -> {
            if (novaDisciplina != null) {
                try {
                    carregarAlunosPorDisciplina(novaDisciplina);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void carregarAlunosPorDisciplina(Disciplina disciplina) throws SQLException {
        listaAlunos.clear();
        List<Aluno> alunos = alunoDAO.listarAlunosPorDisciplina(disciplina.getdCode());
        listaAlunos.addAll(alunos);
    }
    @FXML
    private void atualizar() throws SQLException {
        Disciplina disciplinaSelecionada = comboUC.getValue();

        if (disciplinaSelecionada == null) {
            showAlert("Nenhuma Disciplina Selecionada", "Por favor, selecione uma disciplina primeiro.");
            return;
        }

        try {
            List<Aluno> alunos = alunoDAO.listarAlunosPorDisciplina(disciplinaSelecionada.getdCode());
            listaAlunos.setAll(alunos);

            if (alunos.isEmpty()) {
                tabelaAlunos.setPlaceholder(new Label("Nenhum aluno encontrado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            tabelaAlunos.setPlaceholder(new Label("Erro ao carregar dados"));
            showAlert("Erro de Banco de Dados", "Falha ao carregar alunos: " + e.getMessage());
        }
    }

    public void setSceneManager(SceneManager manager) {
        this.sceneManager = manager;
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
