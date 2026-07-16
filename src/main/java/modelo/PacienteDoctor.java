package modelo;

import java.time.LocalDateTime;

public class PacienteDoctor {

    private int idPacienteDoctor;
    private int idPaciente;
    private int idDoctor;
    private LocalDateTime fechaAsignacion;
    private boolean activo;

    public PacienteDoctor() {
    }

    public int getIdPacienteDoctor() {
        return idPacienteDoctor;
    }

    public void setIdPacienteDoctor(
            int idPacienteDoctor
    ) {
        this.idPacienteDoctor =
                idPacienteDoctor;
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

    public void setFechaAsignacion(
            LocalDateTime fechaAsignacion
    ) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}