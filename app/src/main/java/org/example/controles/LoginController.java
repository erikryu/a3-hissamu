package org.example.controles;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.example.model.dao.Login;
import org.example.model.entidades.UserType;

public class LoginController {
    private SceneManager sceneManager;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    public void setSceneManager(SceneManager manager) {
        this.sceneManager = manager;
    }

    @FXML
    private void handleLogin(ActionEvent event) throws SQLException, IOException {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        if(Login.login(txtUsuario.getText(), txtSenha.getText())){
            switch (UserType.utipo) {
                case PROFESSOR:
                    TeacherController teacherController = sceneManager.getCOntroller("teacher");
                    sceneManager.switchTo("teacher");
                    break;
            }

        }
        System.out.println(UserType.utipo.toString());

        // Exemplo de validação simples — substitua por sua lógica real depois
        /*if ("admin".equals(usuario) && "123".equals(senha)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TeacherScreen.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Tela do Docente");
                stage.setScene(new Scene(root));
                stage.show();


                // Fechar janela de login atual
                Stage loginStage = (Stage) txtUsuario.getScene().getWindow();
                loginStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Erro ao carregar a próxima tela.");
            }
        } else {
            mostrarAlerta("Usuário ou senha incorretos!");
        }*/
    }



    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro de Login");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
