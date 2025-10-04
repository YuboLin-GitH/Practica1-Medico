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


    public List<Paciente> obtenerUsuarios() throws SQLException {
        List<Paciente> Usuarios = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Paciente usuario = new Paciente();
            usuario.setId(resultado.getInt(1));
            usuario.setDni(resultado.getString(2));
            usuario.setNombre(resultado.getString(3));
            usuario.setPassword(resultado.getString(4));
            usuario.setDireccion(resultado.getString(5));
            usuario.setTelefono(resultado.getInt(6));
            Usuarios.add(usuario);
        }

        return Usuarios;
    }
    public boolean valiadarUsuario(String nombre, String passwordPlano) throws SQLException{
        String sql = "SELECT * FROM paciente WHERE nombre = ? AND password = ?";
        try (    PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            String passwordHash = DigestUtils.sha256Hex(passwordPlano);
            sentencia.setString(1, nombre);
            sentencia.setString(2, passwordHash);
        ResultSet resultado = sentencia.executeQuery();
            return resultado.next();
        }
    }






}
