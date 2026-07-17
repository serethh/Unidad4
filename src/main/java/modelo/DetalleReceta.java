package modelo;

public class DetalleReceta {

    private int idDetalleReceta;
    private int idReceta;
    private String medicamento;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private String viaAdministracion;
    private String indicaciones;

    public DetalleReceta() {
    }

    public int getIdDetalleReceta() {
        return idDetalleReceta;
    }

    public void setIdDetalleReceta(
            int idDetalleReceta
    ) {
        this.idDetalleReceta =
                idDetalleReceta;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(
            String medicamento
    ) {
        this.medicamento = medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(
            String frecuencia
    ) {
        this.frecuencia = frecuencia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(
            String duracion
    ) {
        this.duracion = duracion;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(
            String viaAdministracion
    ) {
        this.viaAdministracion =
                viaAdministracion;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(
            String indicaciones
    ) {
        this.indicaciones = indicaciones;
    }
}