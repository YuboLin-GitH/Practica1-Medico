package org.example.practica1medicoyubo.DAO;

import org.example.practica1medicoyubo.domain.Cita;
import org.example.practica1medicoyubo.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CitaDAO {
    private Connection conexion;

    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        Properties configuration = new Properties();
        configuration.load(R.getProperties("database.properties"));
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                username, password);
    }


    public void eliminarCita(Cita cita) throws SQLException {
        String sql = "DELETE FROM cita WHERE idCita = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, cita.getIdCita());
        sentencia.executeUpdate();
    }

    public void modificarCoche(Cita citaAntiguo, Cita citaNuevo) throws SQLException {
        String sql = "UPDATE cita SET idCita = ?, fechaCita = ? WHERE idCita = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, citaNuevo.getIdCita());
        sentencia.setDate(2, (Date) citaNuevo.getFechaCita());
        sentencia.setInt(5, citaAntiguo.getIdCita());
        sentencia.executeUpdate();
    }

    public List<Cita> obtenerCita() throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM cita";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Cita cita = new Cita();
            cita.setIdCita(resultado.getInt(1));
            cita.setFechaCita(resultado.getDate(2));


            citas.add(cita);
        }

        return citas;
    }

}
