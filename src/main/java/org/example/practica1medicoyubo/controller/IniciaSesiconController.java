package org.example.practica1medicoyubo.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.example.practica1medicoyubo.DAO.UsuarioDAO;
import org.example.practica1medicoyubo.domain.Paciente;
import org.example.practica1medicoyubo.util.R;


public class IniciaSesiconController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private TextField tfPaciente;
    @FXML
    private PasswordField pfPassword;


    @FXML
    private Button btIniciar;



    @FXML
    private void validarUsuario() {
        String nombre = tfPaciente.getText().trim();
        String password = pfPassword.getText().trim();

        if (nombre.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor, rellene todos los campos.", Alert.AlertType.ERROR);
            return;
        }

        try {
            usuarioDAO.conectar();

            Paciente paciente = usuarioDAO.valiadarUsuario(nombre, password);

            if (paciente != null) {
                mostrarAlerta("Éxito", "Inicio de sesión correcto ", Alert.AlertType.INFORMATION);
                abrirVentanaCita(paciente);
                Stage stage = (Stage) btIniciar.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Error", "Usuario o contraseña incorrectos ", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al conectar con la base de datos", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        tfPaciente.clear();
        pfPassword.clear();
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    private void abrirVentanaCita(Paciente paciente) {
        try {
            FXMLLoader loader = new FXMLLoader(R.getUI("citas.fxml"));
            Scene scene = new Scene(loader.load());
            CitaController citaController = loader.getController();
            citaController.setPaciente(paciente);
            Stage stage = new Stage();
            stage.setTitle("Panel cita");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana principal", Alert.AlertType.ERROR);
        }
    }



}


