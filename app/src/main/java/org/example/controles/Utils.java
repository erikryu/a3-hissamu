package org.example.controles;

import javafx.scene.control.TextField;

public class Utils {
    public static void limparCampos(TextField... campos){
        for (TextField campo : campos){
            campo.clear();
        }
    }
}
