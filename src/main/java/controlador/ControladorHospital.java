package controlador;

import Interfaz.GestionPDF;
import Interfaz.Hospital;
import dao.HospitalDAO;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import modelo.DetalleReceta;
import modelo.Doctor;
import modelo.Egreso;
import modelo.Ingreso;
import modelo.IngresoPaciente;
import modelo.Paciente;
import modelo.Receta;
import modelo.Registro;

public class ControladorHospital {

    private final Hospital vista;
    private final HospitalDAO dao;

    public ControladorHospital(Hospital vista, HospitalDAO dao) {
        this.vista = vista;
        this.dao = dao;
    }

    public void iniciar() {
        vista.setControlador(this);
        cargarDatos();
        vista.setVisible(true);
    }

    public void cargarDatos() {
        try {
            vista.cargarDoctores(dao.listarDoctores());
            vista.cargarPacientesRegistro(dao.listarPendientesRegistro());

            List<IngresoPaciente> pacientesEgreso =
                    dao.listarPendientesEgreso();

            vista.cargarPacientesEgreso(pacientesEgreso);
            vista.habilitarPestanaEgreso(!pacientesEgreso.isEmpty());

            vista.cargarPacientesVista(dao.listarPacientes());
            vista.cargarTablaGeneral(dao.listarVistaGeneral());

        } catch (SQLException ex) {
            vista.mostrarError(
                    "No se pudieron cargar los datos.\n"
                    + ex.getMessage()
            );
        }
    }

    public void guardarIngreso() {
        try {
            Paciente paciente = vista.obtenerPacienteFormulario();
            Ingreso ingreso = vista.obtenerIngresoFormulario();

            validarPaciente(paciente);
            validarIngreso(ingreso);

            dao.guardarPacienteConIngreso(paciente, ingreso);

            vista.mostrarMensaje(
                    "Paciente e ingreso guardados correctamente."
            );

            vista.limpiarFormularioIngreso();
            cargarDatos();

        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());

        } catch (SQLException ex) {
            vista.mostrarError(
                    "Error al guardar el ingreso.\n"
                    + ex.getMessage()
            );
        }
    }

    public void guardarRegistro() {
        try {
            Doctor doctor = vista.obtenerDoctorSeleccionado();
            IngresoPaciente paciente =
                    vista.obtenerPacienteRegistroSeleccionado();

            if (doctor == null) {
                throw new IllegalArgumentException(
                        "Seleccione un doctor."
                );
            }

            if (paciente == null) {
                throw new IllegalArgumentException(
                        "Seleccione un paciente."
                );
            }

            Registro registro = vista.obtenerRegistroFormulario();

            registro.setIdDoctor(doctor.getIdDoctor());
            registro.setIdPaciente(paciente.getIdPaciente());
            registro.setIdIngreso(paciente.getIdIngreso());

            if (registro.getDiagnostico() == null
                    || registro.getDiagnostico().isBlank()) {
                throw new IllegalArgumentException(
                        "Escriba el diagnóstico."
                );
            }

            if (registro.getSalida() == null) {
                throw new IllegalArgumentException(
                        "Seleccione Alta u Hospitalización."
                );
            }

            Receta receta = vista.solicitarReceta();

            dao.guardarRegistroConReceta(registro, receta);

            if ("ALTA".equalsIgnoreCase(registro.getSalida())) {
                vista.habilitarPestanaEgreso(true);
            }

            if (receta != null) {
                generarPdfReceta(paciente, doctor, registro, receta);
            }

            vista.mostrarMensaje(
                    "Registro clínico y receta guardados correctamente."
            );

            vista.limpiarFormularioRegistro();
            cargarDatos();

        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());

        } catch (SQLException ex) {
            vista.mostrarError(
                    "Error al guardar el registro y la receta.\n"
                    + ex.getMessage()
            );

        } catch (Exception ex) {
            vista.mostrarError(
                    "El registro se guardó, pero no se pudo generar el PDF.\n"
                    + ex.getMessage()
            );
        }
    }

    public void guardarEgreso() {
        try {
            IngresoPaciente paciente =
                    vista.obtenerPacienteEgresoSeleccionado();

            if (paciente == null) {
                throw new IllegalArgumentException(
                        "Seleccione un paciente."
                );
            }

            Egreso egreso = vista.obtenerEgresoFormulario();
            egreso.setIdIngreso(paciente.getIdIngreso());

            if (egreso.getFechaEgreso() == null) {
                throw new IllegalArgumentException(
                        "Seleccione la fecha de egreso."
                );
            }

            if (egreso.getHoraEgreso() == null) {
                throw new IllegalArgumentException(
                        "Escriba la hora de egreso."
                );
            }

            dao.guardarEgreso(egreso);

            vista.mostrarMensaje(
                    "Egreso guardado correctamente."
            );

            vista.limpiarFormularioEgreso();
            cargarDatos();

        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());

        } catch (SQLException ex) {
            vista.mostrarError(
                    "Error al guardar el egreso.\n"
                    + ex.getMessage()
            );
        }
    }

    private void validarPaciente(Paciente paciente) {
        if (paciente.getNombre() == null
                || paciente.getNombre().isBlank()) {
            throw new IllegalArgumentException("Escriba el nombre.");
        }

        if (paciente.getApellidoPaterno() == null
                || paciente.getApellidoPaterno().isBlank()) {
            throw new IllegalArgumentException(
                    "Escriba el apellido paterno."
            );
        }

        if (paciente.getGenero() == null
                || paciente.getGenero().equals("Seleccione uno")) {
            throw new IllegalArgumentException("Seleccione el género.");
        }

        if (paciente.getFechaNacimiento() == null) {
            throw new IllegalArgumentException(
                    "Seleccione la fecha de nacimiento."
            );
        }
    }

    private void validarIngreso(Ingreso ingreso) {
        if (ingreso.getPeso() == null
                || ingreso.getPeso().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Escriba un peso válido."
            );
        }

        if (ingreso.getFechaIngreso() == null) {
            throw new IllegalArgumentException(
                    "Seleccione la fecha de ingreso."
            );
        }

        if (ingreso.getHoraIngreso() == null) {
            throw new IllegalArgumentException(
                    "Escriba la hora de ingreso."
            );
        }
    }

    private void generarPdfReceta(
            IngresoPaciente paciente,
            Doctor doctor,
            Registro registro,
            Receta receta
    ) throws Exception {

        if (receta.getDetalles() == null
                || receta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException(
                    "La receta no contiene medicamentos."
            );
        }

        DetalleReceta detalle = receta.getDetalles().get(0);

        Path rutaPDF = GestionPDF.generarReceta(
                paciente.getNombreCompleto(),
                doctor.getNombreCompleto(),
                registro.getAlergias(),
                registro.getDiagnostico(),
                detalle.getMedicamento(),
                detalle.getDosis(),
                detalle.getFrecuencia(),
                detalle.getDuracion(),
                detalle.getViaAdministracion(),
                detalle.getIndicaciones(),
                receta.getIndicacionesGenerales()
        );

        System.out.println(
                "PDF GENERADO EN: " + rutaPDF.toAbsolutePath()
        );

        vista.mostrarMensaje(
                "PDF generado en:\n" + rutaPDF.toAbsolutePath()
        );

        GestionPDF.abrirPDF(rutaPDF);
    }
}