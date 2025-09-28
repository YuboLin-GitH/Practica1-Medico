package org.example.practica1medicoyubo.Controller;

import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class iniciaSesiconController {

    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField passField;


    @FXML
    private Button botonIniciar;


    @FXML
    public void initialize() {

        botonIniciar.setOnAction(event -> validarUsuario());

    }


    private void validarUsuario() {
        String email = usuarioField.getText().trim();
        String password = passField.getText().trim();


     //   boolean usuarioExiste = dataUsuario.validarUsuario(email, password);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado de Validación");
    //   alert.setContentText(usuarioExiste ? "Usuario existe" : "Usuario no existe");
        alert.showAndWait();


        usuarioField.clear();
        passField.clear();
    }
}
