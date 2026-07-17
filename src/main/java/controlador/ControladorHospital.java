package controlador;

import Interfaz.Hospital;
import dao.HospitalDAO;
import modelo.Doctor;
import modelo.Egreso;
import modelo.Ingreso;
import modelo.IngresoPaciente;
import modelo.Paciente;
import modelo.Registro;

import java.sql.SQLException;

public class ControladorHospital {

    private final Hospital vista;
    private final HospitalDAO dao;

    public ControladorHospital(
            Hospital vista,
            HospitalDAO dao
    ) {
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

            vista.cargarDoctores(
                    dao.listarDoctores()
            );

            vista.cargarPacientesRegistro(
                    dao.listarPendientesRegistro()
            );

            vista.cargarPacientesEgreso(
                    dao.listarPendientesEgreso()
            );
            
            vista.cargarPacientesVista(
                    dao.listarPacientes());
            
            vista.cargarTablaGeneral(
                dao.listarVistaGeneral()
);

        } catch (SQLException ex) {

            vista.mostrarError(
                    "No se pudieron cargar los datos.\n"
                    + ex.getMessage()
            );
        }
    }

    public void guardarIngreso() {

        try {

            Paciente paciente =
                    vista.obtenerPacienteFormulario();

            Ingreso ingreso =
                    vista.obtenerIngresoFormulario();

            validarPaciente(paciente);
            validarIngreso(ingreso);

            dao.guardarPacienteConIngreso(
                    paciente,
                    ingreso
            );

            vista.mostrarMensaje(
                    "Paciente e ingreso guardados correctamente."
            );

            vista.limpiarFormularioIngreso();

            cargarDatos();

        } catch (IllegalArgumentException ex) {

            vista.mostrarError(
                    ex.getMessage()
            );

        } catch (SQLException ex) {

            vista.mostrarError(
                    "Error al guardar el ingreso.\n"
                    + ex.getMessage()
            );
        }
    }

    public void guardarRegistro() {

        try {

            Doctor doctor =
                    vista.obtenerDoctorSeleccionado();

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

            Registro registro =
                    vista.obtenerRegistroFormulario();

            registro.setIdDoctor(
                    doctor.getIdDoctor()
            );

            registro.setIdPaciente(
                    paciente.getIdPaciente()
            );

            registro.setIdIngreso(
                    paciente.getIdIngreso()
            );

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

            dao.guardarRegistro(registro);

boolean pacienteDadoDeAlta =
        "ALTA".equalsIgnoreCase(
                registro.getSalida()
        );

vista.habilitarPestanaEgreso(
        pacienteDadoDeAlta
);

if (pacienteDadoDeAlta) {

    vista.mostrarMensaje(
            "Registro clínico guardado correctamente."
            + "El paciente fue dado de alta y ya puede registrarse su egreso."
    );

} else {

    vista.mostrarMensaje(
            "Registro clínico guardado correctamente."
            + "El paciente permanece hospitalizado."
    );
}

vista.limpiarFormularioRegistro();

cargarDatos();

        } catch (IllegalArgumentException ex) {

            vista.mostrarError(
                    ex.getMessage()
            );

        } catch (SQLException ex) {

            vista.mostrarError(
                    "Error al guardar el registro.\n"
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

            Egreso egreso =
                    vista.obtenerEgresoFormulario();

            egreso.setIdIngreso(
                    paciente.getIdIngreso()
            );

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

            vista.mostrarError(
                    ex.getMessage()
            );

        } catch (SQLException ex) {

            vista.mostrarError(
                    "Error al guardar el egreso.\n"
                    + ex.getMessage()
            );
        }
    }

    private void validarPaciente(
            Paciente paciente
    ) {

        if (paciente.getNombre() == null
                || paciente.getNombre().isBlank()) {

            throw new IllegalArgumentException(
                    "Escriba el nombre."
            );
        }

        if (paciente.getApellidoPaterno() == null
                || paciente.getApellidoPaterno().isBlank()) {

            throw new IllegalArgumentException(
                    "Escriba el apellido paterno."
            );
        }

        if (paciente.getGenero() == null
                || paciente.getGenero().equals(
                        "Seleccione uno"
                )) {

            throw new IllegalArgumentException(
                    "Seleccione el género."
            );
        }

        if (paciente.getFechaNacimiento() == null) {

            throw new IllegalArgumentException(
                    "Seleccione la fecha de nacimiento."
            );
        }
    }

    private void validarIngreso(
            Ingreso ingreso
    ) {

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
    

}