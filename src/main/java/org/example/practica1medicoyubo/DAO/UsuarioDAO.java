package org.example.practica1medicoyubo.DAO;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.practica1medicoyubo.domain.Paciente;
import org.example.practica1medicoyubo.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * ClassName: UsuarioDAO
 * Package: org.example.practica1medicoyubo.DAO
 * Description:
 *
 * @Author Yubo
 * @Create 28/09/2025 22:07
 * @Version 1.0
 */
public class UsuarioDAO {

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

    public void desconectar() throws SQLException {
        if (conexion != null) conexion.close();
    }


    public List<Paciente> obtenerPacientes() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            Paciente p = new Paciente();
            p.setIdPaciente(resultado.getInt("idPaciente"));
            p.setDni(resultado.getString("dni"));
            p.setNombre(resultado.getString("nombre"));
            p.setPassword(resultado.getString("password"));
            p.setDireccion(resultado.getString("direccion"));
            p.setTelefono(resultado.getInt("telefono"));
            pacientes.add(p);
        }

        return pacientes;
    }


    public Paciente buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE dni = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, dni);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(resultado.getInt("idPaciente"));
                p.setDni(resultado.getString("dni"));
                p.setNombre(resultado.getString("nombre"));
                p.setPassword(resultado.getString("password"));
                p.setDireccion(resultado.getString("direccion"));
                p.setTelefono(resultado.getInt("telefono"));
                return p;
            }
        }
        return null;
    }


    public List<Paciente> obtenerUsuarios() throws SQLException {

        List<Paciente> Usuarios = new ArrayList<>();
        String sql = "SELECT * FROM paciente";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            Paciente usuario = new Paciente();
            usuario.setIdPaciente(resultado.getInt(1));
            usuario.setDni(resultado.getString(2));
            usuario.setNombre(resultado.getString(3));
            usuario.setPassword(resultado.getString(4));
            usuario.setDireccion(resultado.getString(5));
            usuario.setTelefono(resultado.getInt(6));
            Usuarios.add(usuario);
        }

        return Usuarios;
    }
    public Paciente valiadarUsuario(String nombre, String passwordPlano) throws SQLException{
        String sql = "SELECT * FROM paciente WHERE nombre = ? AND password = ?";
        String passwordHash = DigestUtils.sha256Hex(passwordPlano);

        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, nombre);
            sentencia.setString(2, passwordHash);
            ResultSet resultado = sentencia.executeQuery();

        if (resultado.next()) {
            Paciente usuario = new Paciente();
            usuario.setIdPaciente(resultado.getInt("idPaciente"));
            usuario.setDni(resultado.getString("dni"));
            usuario.setNombre(resultado.getString("nombre"));
            usuario.setPassword(resultado.getString("password"));
            usuario.setDireccion(resultado.getString("direccion"));
            usuario.setTelefono(resultado.getInt("telefono"));
            return usuario;
        }
        return null;
    }

  }





}
