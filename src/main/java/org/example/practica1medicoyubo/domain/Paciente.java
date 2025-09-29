package org.example.practica1medicoyubo.domain;

/**
 * ClassName: Usuario
 * Package: org.example.practica1medicoyubo.domain
 * Description:
 *
 * @Author Yubo
 * @Create 28/09/2025 16:14
 * @Version 1.0
 */
public class Paciente {
    private int idPaciente;
    private String dni;
    private String nombre;
    private String password;
    private String direccion;
    private int telefono;


    public Paciente() {
    }

    public Paciente(int id, String dni, String nombre, String password, String direccion, int telefono) {
        this.idPaciente = id;
        this.dni = dni;
        this.nombre = nombre;
        this.password = password;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() {
        return idPaciente;
    }

    public void setId(int id) {
        this.idPaciente = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
