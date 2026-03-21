/**
 * This class contains the controllers of the main window of the game
 *
 * @author Estaban Granada Salamanca
 * @author Yoinnel Gabriel Martinez Brito
 *
 * @version 1.0
 * @since 2026
 */

package com.eclipsedsun.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class StartController {

    /**
     * These are the resources used on game main window
     */
    @FXML
    private Label lblError;

    @FXML
    private TextField txtPalabra;

    /**
     * This function starts the game after the user type the secret word
     */
    @FXML
    protected void iniciarJuego() {

        // This made the game recognize all the letters as low cases and erase de blank spaces at start and end of the word
        String palabra = txtPalabra.getText().trim().toLowerCase();

        // Verify that the secret word it's not empty
        if (palabra.isEmpty()) {
            lblError.setText("Ingresa una palabra para que sea la palabra secreta");
            return;
        }

        // Verify that the secret word have no any spaces inside
        if (palabra.contains(" ")) {
            lblError.setText("La palabra no puede tener espacios");
            return;
        }

        // Verify that the secret word have between 6 and 12 letters of length
        if (palabra.length() < 6 || palabra.length() > 12) {
            lblError.setText("La palabra debe tener entre 6 y 12 letras");
            return;
        }

        // Verify that the secret word only have letters from "a" to "z" and "áéíóúñäëïöüÄËÏÖÜ" are exceptions
        if (!palabra.matches("[a-záéíóúñäëïöüÄËÏÖÜ]+")) {
            lblError.setText("La palabra solo puede tener letras");
            return;
        }

        // If everything it's right, starts the game
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/eclipsedsun/game/game-view.fxml")
            );

            Scene scene = new Scene(loader.load());

            // This does that the word typed become the secret word
            EclipsedSunController controller = loader.getController();
            controller.crearCampos(palabra);

            Stage stage = (Stage) txtPalabra.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}