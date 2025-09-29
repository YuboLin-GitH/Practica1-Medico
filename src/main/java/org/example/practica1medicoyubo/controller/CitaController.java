package org.example.practica1medicoyubo.controller;

import org.example.practica1medicoyubo.DAO.CitaDAO;
import org.example.practica1medicoyubo.util.AlertUtils;


import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class CitaController {
        public TextField tfTelefono;


    private final CitaDAO citaDAO;


    public CitaController() {
        citaDAO = new CitaDAO();
        try {
            citaDAO.conectar();
        } catch (SQLException sqle) {
            AlertUtils.mostrarError("Error al conectar con la base de datos");
        } catch (ClassNotFoundException cnfe) {
            AlertUtils.mostrarError("Error al iniciar la aplicación");
        } catch (IOException ioe) {
            AlertUtils.mostrarError("Error al cargar la configuración");
        }

        System.out.println(System.getProperty("user.home"));
    }

}
 
