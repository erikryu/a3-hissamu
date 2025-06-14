package org.example.controles;

import org.example.model.dao.Login;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddUserController {
    @FXML
    private TextField ucode;

    @FXML
    private TextField usenha;

    @FXML
    private void salvarUsuario() throws SQLException {
        String codigo = ucode.getText();
        String senha = usenha.getText();

        Login.registrar(codigo, senha);

        Utils.limparCampos(ucode, usenha);
    }
}
