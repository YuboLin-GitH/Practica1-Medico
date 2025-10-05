package org.example.practica1medicoyubo.controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.practica1medicoyubo.DAO.CitaDAO;
import org.example.practica1medicoyubo.DAO.EspecialidadDAO;
import org.example.practica1medicoyubo.domain.Cita;
import org.example.practica1medicoyubo.domain.Especialidad;
import org.example.practica1medicoyubo.domain.Paciente;
import org.example.practica1medicoyubo.util.AlertUtils;



import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class CitaController {
    @FXML
    public TextField tfTelefono;
    @FXML
    public TextField tfNombre;
    @FXML
    public TextField tfDireccion;
    @FXML
    public TextField tfDNI;

    @FXML
    public Button btVerCita;
    @FXML
    public Button btNuevaCita;
    @FXML
    public Button btBorrarCita;
    @FXML
    public Button btModificarCita;

    @FXML
    public DatePicker dpFechaCita;

    @FXML
    public ComboBox<Especialidad> cbEspecialidad;

    @FXML
    public TableView<Cita> tvCitasPaciente;



    private enum Accion {
        NUEVO, MODIFICAR
    }
    private Accion accion;
    private final CitaDAO citaDAO ;
    private Paciente paciente;

    private Cita citaSeleccionada;





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

    @FXML
    public void initialize() {

        cargarEspecialidades();
        enlazarSelecciónDeTabla();
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
        mostrarDatosPaciente();
    }
    private void mostrarDatosPaciente() {
        tfNombre.setText(paciente.getNombre());
        tfDireccion.setText(paciente.getDireccion());
        tfTelefono.setText(String.valueOf(paciente.getTelefono()));
        tfDNI.setText(paciente.getDni());


        tfNombre.setDisable(true);
        tfDireccion.setDisable(true);
        tfTelefono.setDisable(true);
    }


    private void cargarEspecialidades() {
        EspecialidadDAO especialidadDAO = new EspecialidadDAO();
        try {

            especialidadDAO.conectar();
            List<Especialidad> especialidades = especialidadDAO.obtenerTodas();
            if (especialidades.isEmpty()) {
                AlertUtils.mostrarError("Error al obtener las especialidades");
                return;
            }

            cbEspecialidad.getItems().addAll(especialidades);

        } catch (Exception e) {

            AlertUtils.mostrarError("Error：" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                especialidadDAO.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void enlazarSelecciónDeTabla() {
        tvCitasPaciente.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                citaSeleccionada = newVal; // 赋值选中的预约
                // 加载预约数据到表单（日期+科室）
                dpFechaCita.setValue(newVal.getFechaCita().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                for (Especialidad esp : cbEspecialidad.getItems()) {
                    if (esp.getIdEsp() == newVal.getFkIdEsp()) {
                        cbEspecialidad.setValue(esp);
                        break;
                    }
                }
            }
        });
    }

    @FXML
    public void verCita() {
        if (paciente == null) {
            AlertUtils.mostrarError("introduceze DNI de paciente");
            return;
        }

        try {
            citaDAO.conectar();

            List<Cita> citas = citaDAO.obtenerCitaPorPacienteId(paciente.getIdPaciente());
            if (citas.isEmpty()) {
                tvCitasPaciente.setItems(null);
                AlertUtils.mostrarError("No se encontro la cita");
                return;
            }

            tvCitasPaciente.setItems(FXCollections.observableArrayList(citas));
        } catch (Exception e) {
            AlertUtils.mostrarError("Error：" + e.getMessage());
        } finally {
            try {
                citaDAO.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void nuevaCita() {

        if (paciente == null) {
            AlertUtils.mostrarError("introduceze DNI de paciente");
            return;
        }
        LocalDate fechaSeleccionada = dpFechaCita.getValue();
        Especialidad espSeleccionada = cbEspecialidad.getValue();
        if (fechaSeleccionada == null || espSeleccionada == null) {
            AlertUtils.mostrarError("Elegir fecha de cita");
            return;
        }

        try {
            citaDAO.conectar();
            int nuevoId = citaDAO.obtenerSiguienteIdCita();

            Cita nuevaCita = new Cita();
            nuevaCita.setIdCita(nuevoId);
            nuevaCita.setFechaCita(Date.valueOf(fechaSeleccionada));
            nuevaCita.setFkIdEsp(espSeleccionada.getIdEsp());
            nuevaCita.setFkIdPaciente(paciente.getIdPaciente());


            citaDAO.guardarCita(nuevaCita);
            AlertUtils.mostrarInformacion("Cita creado：" + nuevoId);


            verCita();
            limpiarCajas();
        } catch (Exception e) {
            AlertUtils.mostrarError("Error：" + e.getMessage());
        } finally {
            try {
                citaDAO.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void borrarCita() {

        if (citaSeleccionada == null) {
            AlertUtils.mostrarError("el seleccionado no existe");
            return;
        }
        try {
            citaDAO.conectar();

            citaDAO.eliminarCita(citaSeleccionada);
            AlertUtils.mostrarInformacion("Cita eliminada");


            verCita();
            limpiarCajas();
            citaSeleccionada = null;
        } catch (Exception e) {
            AlertUtils.mostrarError("Error：" + e.getMessage());
        } finally {
            try {
                citaDAO.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void modificarCita() {

        if (citaSeleccionada == null) {
            AlertUtils.mostrarError("El seleccionado no existe");
            return;
        }
        LocalDate fechaModificada = dpFechaCita.getValue();
        Especialidad espModificada = cbEspecialidad.getValue();
        if (fechaModificada == null || espModificada == null) {
            AlertUtils.mostrarError("Eliger bien cita y especificada");
            return;
        }

        try {
            citaDAO.conectar();

            Cita citaModificada = new Cita();
            citaModificada.setIdCita(citaSeleccionada.getIdCita());
            citaModificada.setFechaCita(Date.valueOf(fechaModificada));
            citaModificada.setFkIdEsp(espModificada.getIdEsp());
            citaModificada.setFkIdPaciente(paciente.getIdPaciente());


            citaDAO.modificarCita(citaSeleccionada, citaModificada);
            AlertUtils.mostrarInformacion("Cita actualizada");


            verCita();
            limpiarCajas();
            citaSeleccionada = null;
        } catch (Exception e) {
            AlertUtils.mostrarError("Error：" + e.getMessage());
        } finally {
            try {
                citaDAO.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    private void limpiarCajas() {
        dpFechaCita.setValue(null);
        cbEspecialidad.setValue(null);
    }

}
 
