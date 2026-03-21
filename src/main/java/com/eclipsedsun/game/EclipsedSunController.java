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
    private Button btnAyuda;

    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        crearCampos("computador");
        imagenSol.setImage(new Image(
                getClass().getResourceAsStream("/com/eclipsedsun/game/sol0.png")
        ));
    }
    private boolean usandoAyuda = false;

    @FXML
    protected void onHelloButtonClick() {
        usandoAyuda = true;
        nivelAyuda++;

        switch (nivelAyuda) {

            case 1:
                lblMensaje.setText("construido por primera vez entre 1943 y 1945 en Estados Unidos,");
                break;

            case 2:
                lblMensaje.setText("Es un objeto tecnológico");
                break;

            case 3:
                revelarLetra();
                lblMensaje.setText("Te revelamos una letra");
                break;

            default:
                lblMensaje.setText("No hay más ayudas, sorry");
                break;
        }
    }

    private void revelarLetra() {

        for (int i = 0; i < palabraSecreta.length(); i++) {

            TextField campo = (TextField) contenedorLetras.getChildren().get(i);

            if (campo.getText().isEmpty()) {

                campo.setText(String.valueOf(palabraSecreta.charAt(i)));
                campo.setEditable(false);
                break;
            }
        }
    }

    private void actualizarEclipse() {

        int nivel = Math.min(errores, 5);

        String ruta = "/com/eclipsedsun/game/sol" + nivel + ".png";

        Image imagen = new Image(getClass().getResourceAsStream(ruta));

        imagenSol.setImage(imagen);
    }

    private String palabraSecreta = "computador";
    private int errores = 0;
    private int nivelAyuda = 0;


    private void verificarDerrota() {

        if (errores >= 5) {
            lblMensaje.setText("Perdistes :(");
            contenedorLetras.setDisable(true);
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

                if (!newText.isEmpty() && !usandoAyuda) {

                    char letra = newText.toLowerCase().charAt(0);
                    if (palabraSecreta.charAt(index) == letra) {
                        lblMensaje.setText("Correcto");
                        campo.setEditable(false); //
                    } else {
                        lblMensaje.setText("Incorrecto");
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
