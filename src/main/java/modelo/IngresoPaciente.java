package modelo;

public class IngresoPaciente {

    private int idIngreso;
    private int idPaciente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public IngresoPaciente() {
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(
            int idIngreso
    ) {
        this.idIngreso = idIngreso;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(
            int idPaciente
    ) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(
            String nombre
    ) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(
            String apellidoPaterno
    ) {
        this.apellidoPaterno =
                apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(
            String apellidoMaterno
    ) {
        this.apellidoMaterno =
                apellidoMaterno;
    }

    public String getNombreCompleto() {

        String completo =
                nombre + " " + apellidoPaterno;

        if (apellidoMaterno != null
                && !apellidoMaterno.isBlank()) {

            completo +=
                    " " + apellidoMaterno;
        }

        return completo;
    }

    @Override
    public String toString() {
        return getNombreCompleto();
    }
}