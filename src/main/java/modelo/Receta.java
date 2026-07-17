package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Receta {

    private int idReceta;
    private int idRegistro;
    private LocalDate fechaReceta;
    private String indicacionesGenerales;
    private List<DetalleReceta> detalles;

    public Receta() {
        this.fechaReceta = LocalDate.now();
        this.detalles = new ArrayList<>();
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public LocalDate getFechaReceta() {
        return fechaReceta;
    }

    public void setFechaReceta(LocalDate fechaReceta) {
        this.fechaReceta = fechaReceta;
    }

    public String getIndicacionesGenerales() {
        return indicacionesGenerales;
    }

    public void setIndicacionesGenerales(
            String indicacionesGenerales
    ) {
        this.indicacionesGenerales =
                indicacionesGenerales;
    }

    public List<DetalleReceta> getDetalles() {
        return detalles;
    }

    public void setDetalles(
            List<DetalleReceta> detalles
    ) {
        this.detalles = detalles;
    }

    public void agregarDetalle(
            DetalleReceta detalle
    ) {
        this.detalles.add(detalle);
    }
}