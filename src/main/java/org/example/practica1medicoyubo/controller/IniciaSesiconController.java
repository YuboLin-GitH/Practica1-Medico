package org.example.practica1medicoyubo.controller;

import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.practica1medicoyubo.DAO.UsuarioDAO;


public class IniciaSesiconController {

    @FXML
    private TextField tfPaciente;
    @FXML
    private PasswordField pfPassword;


    @FXML
    private Button btIniciar;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();


    @FXML
  /*  public void initialize() {

        btIniciar.setOnAction(event -> validarUsuario());

    }
*/

    private void validarUsuario() {
        String email = tfPaciente.getText().trim();
        String password = pfPassword.getText().trim();


     //   boolean usuarioExiste = dataUsuario.validarUsuario(email, password);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado de Validación");
        alert.setContentText("Usuario ingresado: " + email + " / " + password);
        alert.showAndWait();


        tfPaciente.clear();
        pfPassword.clear();
    }
}
