
package com.eclipsedsun.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class StartController {
    @FXML
    private Label lblError;

    @FXML
    private TextField txtPalabra;

    @FXML
    protected void iniciarJuego() {

        String palabra = txtPalabra.getText().trim().toLowerCase();


        if (!palabra.equals("iniciar")) {
            lblError.setText("Debes escribir la palabra clave");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/eclipsedsun/game/game-view.fxml")
            );

            Scene scene = new Scene(loader.load());

            EclipsedSunController controller = loader.getController();
            controller.crearCampos("computador");

            Stage stage = (Stage) txtPalabra.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}