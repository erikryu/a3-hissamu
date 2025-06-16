package org.example.controles;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.example.model.dao.DbManage;
import org.example.model.dao.DisciplinaDAO;
import org.example.model.dao.NotasDAO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherController {
    private SceneManager sceneManager;

    private AlunoDAO alunoDAO = new AlunoDAO(DbManage.conectarDb());
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO(DbManage.conectarDb());
    private NotasDAO notasDAO = new NotasDAO(DbManage.conectarDb());

    @FXML
    private ComboBox<Disciplina> comboUC;

    @FXML
    private TableView<Aluno> tabelaAlunos;

    @FXML
    private TableColumn<Aluno, String> colNome;

    @FXML
    private TableColumn<Aluno, String> colRa;

    @FXML
    private TableColumn<Aluno, String> colA1;

    @FXML
    private TableColumn<Aluno, String> colA2;

    @FXML
    private TableColumn<Aluno, String> colA3;

    @FXML
    private TableColumn<Nota, String> colNotaFinal;

    @FXML
    private Button btnSalvar;

    private ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();
    private ObservableList<Disciplina> listaDeDisciplinas = FXCollections.observableArrayList();

    //RA do aluno, Map<(A1, A2, OU A3), Nota>
    private Map<String, Map<TipoNotas, Nota>> mapaNotas= new HashMap<>();

    public void initialize() {
        // --- Configuração da Tabela e Colunas ---
        configurarTabela();

        // --- Carregamento Inicial de Disciplinas ---
        // TODO: Substituir o ID do professor por um ID dinâmico do usuário logado
        listaDeDisciplinas.addAll(disciplinaDAO.listarDisciplinasPorProfessor("PROFESSOR002"));
    }

    private void configurarTabela() {
        comboUC.setItems(listaDeDisciplinas);
        comboUC.setConverter(new StringConverter<>() {
            @Override
            public String toString(Disciplina disciplina) {
                return disciplina != null ? disciplina.getNome() : "";
            }
            @Override
            public Disciplina fromString(String string) { return null; }
        });

        comboUC.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, novaDisciplina) -> {
            if (novaDisciplina != null) {
                try {
                    carregarDadosPorDisciplina(novaDisciplina);
                } catch (SQLException e) {
                    showAlert("Erro de Carregamento", "Não foi possível carregar os dados da disciplina: " + e.getMessage());
                }
            }
        });

        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colRa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRa()));

        tabelaAlunos.setEditable(true);

        configurarColunaNota(colA1, TipoNotas.A1);
        configurarColunaNota(colA2, TipoNotas.A2);
        configurarColunaNota(colA3, TipoNotas.A3);

        /*
        colNotaFinal.setCellValueFactory(cellData -> {
            String ra = cellData.getValue().getRa();
            Map<TipoNotas, Nota> notasDoAluno = mapaNotas.get(ra);
            int soma = 0;
            if (notasDoAluno != null) {
                try {
                    soma = notasDAO.somarNotas(ra);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return new SimpleStringProperty(String.valueOf(soma));
        });*/

        tabelaAlunos.setItems(listaAlunos);
        tabelaAlunos.setPlaceholder(new Label("Selecione uma disciplina para exibir os alunos."));
    }

    private void configurarColunaNota(TableColumn<Aluno, String> coluna, TipoNotas tipoNota) {
        // 1. Como exibir o valor na célula
        coluna.setCellValueFactory(cellData -> {
            String ra = cellData.getValue().getRa();
            Map<TipoNotas, Nota> notasDoAluno = mapaNotas.get(ra);
            String valorExibido = "N/L"; // "Não Lançado"
            if (notasDoAluno != null) {
                Nota nota = notasDoAluno.get(tipoNota);
                if (nota != null) {
                    valorExibido = String.valueOf(nota.getValor());
                }
            }
            return new SimpleStringProperty(valorExibido);
        });

        // 2. Como a célula deve se comportar ao ser editada
        coluna.setCellFactory(TextFieldTableCell.forTableColumn());
        coluna.setOnEditCommit(event -> {
            String ra = event.getRowValue().getRa();
            try {
                int novoValor = Integer.parseInt(event.getNewValue());
                Map<TipoNotas, Nota> notasDoAluno = mapaNotas.computeIfAbsent(ra, k -> new HashMap<>());
                Nota nota = notasDoAluno.get(tipoNota);

                if (nota != null) {
                    nota.setValor(novoValor); // Atualiza nota existente
                } else {
                    String dcode = comboUC.getValue().getdCode();
                    Nota novaNota = new Nota(tipoNota, ra, novoValor, dcode); // Cria nova nota
                    notasDoAluno.put(tipoNota, novaNota);
                }
                tabelaAlunos.refresh(); // Atualiza a tabela para refletir mudanças (ex: nota final)
            } catch (NumberFormatException e) {
                showAlert("Formato Inválido", "Por favor, insira um número inteiro válido.");
                tabelaAlunos.refresh(); // Reverte a edição visual
            }
        });
    }

    private void carregarDadosPorDisciplina(Disciplina disciplina) throws SQLException {
        listaAlunos.clear();
        mapaNotas.clear();

        List<Aluno> alunos = alunoDAO.listarAlunosPorDisciplina(disciplina.getdCode());
        listaAlunos.addAll(alunos);

        List<Nota> notasDisciplina = notasDAO.buscaPorDisciplina(disciplina.getdCode());
        for (Nota nota:notasDisciplina) {
            mapaNotas.computeIfAbsent(nota.getRa(), k -> new HashMap<>()).put(nota.getTipoEnum(), nota);
        }

        tabelaAlunos.refresh();
    }

    @FXML
    private void salvar(){
        List<Nota> notasParaSalvar = new ArrayList<>();

        for (Map<TipoNotas, Nota> notasDoAluno : mapaNotas.values()) {
            notasParaSalvar.addAll(notasDoAluno.values());
        }

        if (notasParaSalvar.isEmpty()) {
            showAlert("Aviso", "Nenhuma nota foi modificada ou inserida.");
            return;
        }

        try {
            notasDAO.salvarOuAtualizar(notasParaSalvar);
            showAlert("Sucesso", "Notas salvas com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace(); // Importante para depuração
            showAlert("Erro de Banco de Dados", "Falha ao salvar as notas: " + e.getMessage());
        }
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
