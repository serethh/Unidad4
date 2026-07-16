package modelo;

public class Doctor {

    private int idDoctor;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String especialidad;
    private String cedulaProfesional;
    private String telefono;
    private String correo;
    private boolean activo;

    public Doctor() {
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(
            String especialidad
    ) {
        this.especialidad = especialidad;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(
            String cedulaProfesional
    ) {
        this.cedulaProfesional =
                cedulaProfesional;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombreCompleto() {

        String completo =
                nombre + " " + apellidoPaterno;

        if (
            apellidoMaterno != null
            && !apellidoMaterno.isBlank()
        ) {
            completo += " " + apellidoMaterno;
        }

        return completo;
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }
}