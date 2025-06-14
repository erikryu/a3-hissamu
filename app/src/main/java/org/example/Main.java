package org.example;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.example.controles.*;
import org.example.model.dao.DbManage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws SQLException, IOException {
        SceneManager sceneManager = new SceneManager(stage);

        FXMLLoader loginL = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        Parent loginRoot = loginL.load();
        LoginController lControl = loginL.getController();
        lControl.setSceneManager(sceneManager);

        FXMLLoader teacherL = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/TeacherScreen.fxml")));
        Parent teacherRoot = teacherL.load();
        TeacherController tControl = teacherL.getController();
        tControl.setSceneManager(sceneManager);

        FXMLLoader adminL = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/AdminScreen.fxml")));
        Parent adminRoot = adminL.load();
        AdminController adminController = adminL.getController();
        adminController.setSceneManager(sceneManager);

        FXMLLoader alunoL = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/StudentScreen.fxml")));
        Parent alunoRoot = alunoL.load();
        StudentController alunoController = alunoL.getController();
        alunoController.setSceneManager(sceneManager);

        sceneManager.addScene("login", new Scene(loginRoot, 600, 400), lControl);
        sceneManager.addScene("teacher", new Scene(teacherRoot, 800, 600), tControl);
        sceneManager.addScene("admin", new Scene(adminRoot, 400, 600), adminController);
        sceneManager.addScene("aluno", new Scene(alunoRoot, 800, 600), alunoController);

        sceneManager.switchTo("login");
        stage.setTitle("Login");
        stage.show();

        /*
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
         */
    }

    public void initDb() throws SQLException {
        DbManage.conectarDb();
        if(DbManage.initDb()==0){
            System.out.println("Tudo certo e rodando");
        } else {
            System.out.println("Algo deu errado");
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
