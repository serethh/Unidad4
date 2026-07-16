package modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Ingreso {

    private int idIngreso;
    private int idPaciente;
    private BigDecimal peso;
    private LocalDate fechaIngreso;
    private LocalTime horaIngreso;
    private String estado;

    public Ingreso() {
    }

    public Ingreso(
            int idPaciente,
            BigDecimal peso,
            LocalDate fechaIngreso,
            LocalTime horaIngreso,
            String estado
    ) {
        this.idPaciente = idPaciente;
        this.peso = peso;
        this.fechaIngreso = fechaIngreso;
        this.horaIngreso = horaIngreso;
        this.estado = estado;
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(
            LocalDate fechaIngreso
    ) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalTime getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(
            LocalTime horaIngreso
    ) {
        this.horaIngreso = horaIngreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}