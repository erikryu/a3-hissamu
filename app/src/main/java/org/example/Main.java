package org.example;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.model.dao.DbManage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws SQLException, IOException {
        initDb();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
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
