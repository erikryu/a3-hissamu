package org.example.controles;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jdk.jshell.execution.Util;
import org.example.model.dao.DbManage;
import org.example.model.dao.DisciplinaDAO;

import java.sql.SQLException;

public class AddProfessorTurma {
    @FXML
    private TextField pcode;

    @FXML
    private TextField dcode;

    @FXML
    private void adicionar() throws SQLException {
        DisciplinaDAO mdisciplina = new DisciplinaDAO(DbManage.conectarDb());
        mdisciplina.adicionarProfessor(pcode.getText(), dcode.getText());

        Utils.limparCampos(pcode, dcode);
        DbManage.desconectarDb();
    }
}
