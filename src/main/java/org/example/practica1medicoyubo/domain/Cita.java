package org.example.practica1medicoyubo.domain;

import java.util.Date;

/**
 * ClassName: Cita
 * Package: org.example.practica1medicoyubo.domain
 * Description:
 *
 * @Author Yubo
 * @Create 28/09/2025 16:19
 * @Version 1.0
 */
public class Cita  {
    private int idCita;
    private Date fechaCita;

    public Cita() {
    }


    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }
}
