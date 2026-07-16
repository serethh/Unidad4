package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Paciente {

    private int idPaciente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String genero;
    private LocalDate fechaNacimiento;
    private boolean activo;
    private LocalDateTime fechaRegistro;

    public Paciente() {
    }

    public Paciente(
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String genero,
            LocalDate fechaNacimiento
    ) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = true;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(
            String apellidoPaterno
    ) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(
            String apellidoMaterno
    ) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(
            LocalDate fechaNacimiento
    ) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(
            LocalDateTime fechaRegistro
    ) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreCompleto() {

        String nombreCompleto =
                nombre + " " + apellidoPaterno;

        if (
            apellidoMaterno != null
            && !apellidoMaterno.isBlank()
        ) {
            nombreCompleto +=
                    " " + apellidoMaterno;
        }

        return nombreCompleto;
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }
    public int calcularEdad() {

    if (fechaNacimiento == null) {
        return 0;
    }

    return Period.between(
            fechaNacimiento,
            LocalDate.now()
    ).getYears();
}
}