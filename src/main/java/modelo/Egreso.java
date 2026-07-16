package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Egreso {

    private int idEgreso;
    private int idIngreso;
    private LocalDate fechaEgreso;
    private LocalTime horaEgreso;
    private String observaciones;

    public Egreso() {
    }

    public int getIdEgreso() {
        return idEgreso;
    }

    public void setIdEgreso(int idEgreso) {
        this.idEgreso = idEgreso;
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(
            LocalDate fechaEgreso
    ) {
        this.fechaEgreso = fechaEgreso;
    }

    public LocalTime getHoraEgreso() {
        return horaEgreso;
    }

    public void setHoraEgreso(
            LocalTime horaEgreso
    ) {
        this.horaEgreso = horaEgreso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(
            String observaciones
    ) {
        this.observaciones = observaciones;
    }
}