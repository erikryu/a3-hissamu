package org.example.controles;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.model.dao.DbManage;
import org.example.model.dao.TurmaDAO;

import java.sql.SQLException;

public class AddAlunoTurma {

    @FXML
    private TextField tcode;

    @FXML
    private TextField ra;

    @FXML
    private void Adicionar() throws SQLException {
        TurmaDAO mturma = new TurmaDAO(DbManage.conectarDb());
        mturma.adicionarAluno(ra.getText(), tcode.getText());

        Utils.limparCampos(tcode, ra);
        DbManage.desconectarDb();
    }

}
