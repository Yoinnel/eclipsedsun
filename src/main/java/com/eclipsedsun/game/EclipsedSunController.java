package com.eclipsedsun.game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class EclipsedSunController {
    @FXML
    private ImageView imagenSol;

   // @FXML
    //private ProgressBar progressSol;

    @FXML
    private HBox contenedorLetras;

    @FXML
    private Button btnPista;

    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        crearCampos(palabraSecreta);
        imagenSol.setImage(new Image(
                getClass().getResourceAsStream("/com/eclipsedsun/game/sol0.png")
        ));
    }
    private boolean usandoPista = false;

    @FXML
    protected void onHelloButtonClick() {
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
                break;
        }
    }

    private void revelarLetra() {

        int longitud = palabraSecreta.length();
        int intentos = 0;

        while (intentos < 12) {

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

    private void actualizarEclipse() {

        int nivel = Math.min(errores, 5);

        String ruta = "/com/eclipsedsun/game/sol" + nivel + ".png";

        Image imagen = new Image(getClass().getResourceAsStream(ruta));

        imagenSol.setImage(imagen);
    }

    private String palabraSecreta = "";
    private int errores = 0;
    private int nivelPista = 0;


    private void verificarDerrota() {

        if (errores >= 5) {
            lblMensaje.setText("Perdiste :(");
            contenedorLetras.setDisable(true);
        }
    }

    // This quit the accents in letters after type the secret word to allow that the user don't must use it
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
                    } else {
                        lblMensaje.setText("La letra es incorrecta!");
                        errores++;
                        actualizarEclipse();
                        verificarDerrota();
                    }
                }
            });

            contenedorLetras.getChildren().add(campo);
        }
    }
}
