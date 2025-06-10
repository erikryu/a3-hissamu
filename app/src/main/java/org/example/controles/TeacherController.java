package org.example.controles;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import java.io.IOException;

import org.example.model.entidades.Professor;

public class TeacherController {

    @FXML
    private Label teacherNameLabel;

    @FXML
    private Button logoutButton;

    private Professor professor; // Armazena o professor logado

    public void initialize() {
        teacherNameLabel.setText("Bem-vindo, Professor!");
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
        teacherNameLabel.setText("Bem-vindo, " + professor.getNome() + "!");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginScreen.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela de Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}