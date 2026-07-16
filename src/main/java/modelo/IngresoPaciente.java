package modelo;

public class IngresoPaciente {

    private int idIngreso;
    private int idPaciente;
    private String nombreCompleto;

    public IngresoPaciente() {
    }

    public IngresoPaciente(int idIngreso, int idPaciente,
            String nombreCompleto) {
        this.idIngreso = idIngreso;
        this.idPaciente = idPaciente;
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public String toString() {
        return nombreCompleto;
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}