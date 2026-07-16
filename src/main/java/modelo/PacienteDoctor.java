package modelo;

import java.time.LocalDateTime;

public class PacienteDoctor {

    private int idPaciente;
    private int idDoctor;
    private LocalDateTime fechaAsignacion;

    public PacienteDoctor() {
    }

    public PacienteDoctor(int idPaciente, int idDoctor) {
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}