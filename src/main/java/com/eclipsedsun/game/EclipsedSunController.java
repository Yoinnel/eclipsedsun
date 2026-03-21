/**
 * This class contains the controllers of the "in game" window
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class EclipsedSunController {

    /**
     * These are the resources used on game window
     */
    @FXML
    private ImageView imagenSol;

    @FXML
    private HBox contenedorLetras;

    @FXML
    private Button btnPista;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnReiniciar;

    /**
     * Common variables
     */
    private String palabraSecreta = "";
    private int errores = 0;
    private int nivelPista = 0;
    private boolean usandoPista = false;

    /**
     * This function create the text fields to guess the secret word
     * and initialize the sun image
     */
    @FXML
    public void initialize() {
        crearCampos(palabraSecreta);
        imagenSol.setImage(new Image(
                getClass().getResourceAsStream("/com/eclipsedsun/game/sol0.png")
        ));
    }

    /**
     * This function modifies the letter when secret word is typed;
     * this avoids possible errors when the typed word contains accents "´" or diaeresis "¨"
     * and able the user can guess the word without typing that details of letters
     *
     * @param c is the character typed on the secret word
     * @return the letter without accents "´" or diaeresis "¨"
     */
    private char normalizarLetra(char c) {

        c = Character.toLowerCase(c);

        switch (c) {
            case 'á': return 'a';
            case 'ä': return 'a';
            case 'é': return 'e';
            case 'ë': return 'e';
            case 'í': return 'i';
            case 'ï': return 'i';
            case 'ó': return 'o';
            case 'ö': return 'o';
            case 'ú': return 'u';
            case 'ü': return 'u';
            default: return c;
        }
    }

    /**
     * This function create the text fields by each letter on the secret word
     * @param palabra is the secret word; the variable "palabraSecreta"
     */
    public void crearCampos(String palabra) {

        this.palabraSecreta = palabra;

        contenedorLetras.getChildren().clear();

        for (int i = 0; i < palabra.length(); i++) {

            TextField campo = new TextField();
            campo.setPrefWidth(40);
            campo.setAlignment(Pos.CENTER);

            int index = i;

            campo.textProperty().addListener((obs, oldText, newText) -> {

                if (newText.length() > 1) {
                    campo.setText(newText.substring(0, 1));
                    return;
                }

                if (!newText.isEmpty() && !usandoPista) {

                    char letra = newText.toLowerCase().charAt(0);

                    // Verify that the typed it's a Letter
                    if (!Character.isLetter(letra)) {
                        lblMensaje.setText("Solo puede ingresar letras");
                        campo.setText(""); // Clean the text field
                        return;
                    }

                    // Verify if the letter it's correct or no
                    char letraUsuario = normalizarLetra(letra);
                    char letraSecreta = normalizarLetra(palabraSecreta.charAt(index));

                    if (letraSecreta == letraUsuario) {
                        lblMensaje.setText("La letra es correcta!");
                        campo.setEditable(false);
                        verificarVictoria();
                    } else {
                        lblMensaje.setText("La letra es incorrecta!");
                        campo.setText("");
                        errores++;
                        actualizarEclipse();
                        verificarDerrota();
                    }
                }
            });

            contenedorLetras.getChildren().add(campo);
        }
    }

    /**
     * This function show the remaining uses of the clue button
     * if the user use the clue button thrice it disable
     */
    @FXML
    protected void onPistaButtonClick() {
        usandoPista = true;
        nivelPista++;

        switch (nivelPista) {

            case 1:
                revelarLetra();
                lblMensaje.setText("Usos restantes del botón de pistas 2/3");
                break;

            case 2:
                revelarLetra();
                lblMensaje.setText("Usos restantes del botón de pistaspistas 1/3");
                break;

            case 3:
                revelarLetra();
                lblMensaje.setText("Usos restantes del botón de pistas 0/3");
                break;

            default:
                lblMensaje.setText("No puedes usar más pistas");
                btnPista.setDisable(true);
                break;
        }
        usandoPista = false;
    }

    /**
     * This function show a random letter of the secret word on its text field if the user use the clue button
     */
    private void revelarLetra() {


        int longitud = palabraSecreta.length();
        int intentos = 0;

        // Only 12 tries because the max length of the secret word its 12 characters, this avoids a possible infinite loop
        while (intentos < 13) {

            int i = (int) (Math.random() * longitud);

            TextField campo = (TextField) contenedorLetras.getChildren().get(i);

            if (campo.getText().isEmpty()) {

                campo.setText(String.valueOf(palabraSecreta.charAt(i)));
                campo.setEditable(false);
                break;
            }

            intentos++;
        }
    }

    /**
     * This function update the eclipsed sun images each time that the user typed an incorrect letter
     */
    private void actualizarEclipse() {

        int nivel = Math.min(errores, 5);

        String ruta = "/com/eclipsedsun/game/sol" + nivel + ".png";

        Image imagen = new Image(getClass().getResourceAsStream(ruta));

        imagenSol.setImage(imagen);
    }

    /**
     * This function verify if the user lose; if user typed 5 incorrect letters:
     * show a message of failed
     * disable the text fields
     * disable the clues button
     * show the "Play again" button
     */
    private void verificarDerrota() {

        if (errores >= 5) {
            lblMensaje.setText("Perdiste :(");
            contenedorLetras.setDisable(true);
            btnPista.setDisable(true);
            btnReiniciar.setVisible(true);
        }
    }

    /**
     * This function verify if the win; if user typed the all corrects letters
     * When all text fields are fill and all letters are corrects:
     * show a message of victory
     * disable the text fields
     * disable the clues button
     * show the "Play again" button
     */
    private void verificarVictoria() {

        for (int i = 0; i < palabraSecreta.length(); i++) {

            TextField campo = (TextField) contenedorLetras.getChildren().get(i);
            String texto = campo.getText();

            if (texto.isEmpty()) {
                return;
            }

            // If texto is filled
            char letraUsuario = normalizarLetra(texto.charAt(0));
            char letraSecreta = normalizarLetra(palabraSecreta.charAt(i));

            if (letraUsuario != letraSecreta) {
                return;
            }
        }

        // If all letters are corrects
        lblMensaje.setText("Ganaste! :D");
        contenedorLetras.setDisable(true);
        btnPista.setDisable(true);
        btnReiniciar.setVisible(true);
    }

    /**
     * This function sends the player to the main window when use the "Play again" button.
     */
    @FXML
    protected void volverInicio() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/eclipsedsun/game/start-view.fxml")

            );

            Scene scene = new Scene(loader.load(), 1280, 720);

            Stage stage = (Stage) btnReiniciar.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}