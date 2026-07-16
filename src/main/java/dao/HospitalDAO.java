package dao;

import conexion.Conexion;
import modelo.Doctor;
import modelo.Egreso;
import modelo.Ingreso;
import modelo.IngresoPaciente;
import modelo.Paciente;
import modelo.Registro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class HospitalDAO {

    public void guardarPacienteConIngreso(
            Paciente paciente, Ingreso ingreso) throws SQLException {

        String sqlPaciente =
                "INSERT INTO clinica.paciente "
                + "(nombre, apellido_paterno, apellido_materno, "
                + "genero, fecha_nacimiento) "
                + "VALUES (?, ?, ?, ?, ?) "
                + "RETURNING id_paciente";

        String sqlIngreso =
                "INSERT INTO clinica.ingreso "
                + "(id_paciente, peso, fecha_ingreso, hora_ingreso) "
                + "VALUES (?, ?, ?, ?) "
                + "RETURNING id_ingreso";

        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement psPaciente =
                             conn.prepareStatement(sqlPaciente)) {

                    psPaciente.setString(1, paciente.getNombre());
                    psPaciente.setString(
                            2, paciente.getApellidoPaterno());
                    psPaciente.setString(
                            3, paciente.getApellidoMaterno());
                    psPaciente.setString(4, paciente.getGenero());
                    psPaciente.setDate(
                            5,
                            Date.valueOf(
                                    paciente.getFechaNacimiento()));

                    try (ResultSet rs = psPaciente.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "No se obtuvo el ID del paciente.");
                        }

                        paciente.setIdPaciente(
                                rs.getInt("id_paciente"));
                    }
                }

                ingreso.setIdPaciente(paciente.getIdPaciente());

                try (PreparedStatement psIngreso =
                             conn.prepareStatement(sqlIngreso)) {

                    psIngreso.setInt(
                            1, ingreso.getIdPaciente());
                    psIngreso.setBigDecimal(
                            2, ingreso.getPeso());
                    psIngreso.setDate(
                            3,
                            Date.valueOf(
                                    ingreso.getFechaIngreso()));
                    psIngreso.setTime(
                            4,
                            Time.valueOf(
                                    ingreso.getHoraIngreso()));

                    try (ResultSet rs = psIngreso.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "No se obtuvo el ID del ingreso.");
                        }

                        ingreso.setIdIngreso(
                                rs.getInt("id_ingreso"));
                    }
                }

                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;

            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<Doctor> listarDoctores() throws SQLException {
        String sql =
                "SELECT id_doctor, nombre, apellido_paterno, "
                + "apellido_materno, especialidad, "
                + "cedula_profesional, activo "
                + "FROM clinica.doctor "
                + "WHERE activo = TRUE "
                + "ORDER BY apellido_paterno, nombre";

        List<Doctor> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor();

                doctor.setIdDoctor(
                        rs.getInt("id_doctor"));
                doctor.setNombre(
                        rs.getString("nombre"));
                doctor.setApellidoPaterno(
                        rs.getString("apellido_paterno"));
                doctor.setApellidoMaterno(
                        rs.getString("apellido_materno"));
                doctor.setEspecialidad(
                        rs.getString("especialidad"));
                doctor.setCedulaProfesional(
                        rs.getString("cedula_profesional"));
                doctor.setActivo(
                        rs.getBoolean("activo"));

                lista.add(doctor);
            }
        }

        return lista;
    }

    public List<IngresoPaciente> listarPendientesRegistro()
            throws SQLException {

        String sql =
                "SELECT i.id_ingreso, p.id_paciente, "
                + "CONCAT_WS(' ', p.nombre, "
                + "p.apellido_paterno, p.apellido_materno) "
                + "AS paciente "
                + "FROM clinica.ingreso i "
                + "INNER JOIN clinica.paciente p "
                + "ON p.id_paciente = i.id_paciente "
                + "LEFT JOIN clinica.registro r "
                + "ON r.id_ingreso = i.id_ingreso "
                + "WHERE r.id_registro IS NULL "
                + "AND i.estado = 'INGRESADO' "
                + "ORDER BY p.apellido_paterno, p.nombre";

        return ejecutarConsultaIngresos(sql);
    }

    public List<IngresoPaciente> listarPendientesEgreso()
            throws SQLException {

        String sql =
                "SELECT i.id_ingreso, p.id_paciente, "
                + "CONCAT_WS(' ', p.nombre, "
                + "p.apellido_paterno, p.apellido_materno) "
                + "AS paciente "
                + "FROM clinica.ingreso i "
                + "INNER JOIN clinica.paciente p "
                + "ON p.id_paciente = i.id_paciente "
                + "LEFT JOIN clinica.egreso e "
                + "ON e.id_ingreso = i.id_ingreso "
                + "WHERE e.id_egreso IS NULL "
                + "ORDER BY i.fecha_ingreso DESC, "
                + "i.hora_ingreso DESC";

        return ejecutarConsultaIngresos(sql);
    }

    private List<IngresoPaciente> ejecutarConsultaIngresos(
            String sql) throws SQLException {

        List<IngresoPaciente> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                IngresoPaciente ingresoPaciente =
                        new IngresoPaciente();

                ingresoPaciente.setIdIngreso(
                        rs.getInt("id_ingreso"));
                ingresoPaciente.setIdPaciente(
                        rs.getInt("id_paciente"));
                ingresoPaciente.setNombreCompleto(
                        rs.getString("paciente"));

                lista.add(ingresoPaciente);
            }
        }

        return lista;
    }

    public void guardarRegistro(Registro registro)
            throws SQLException {

        String sqlRelacion =
                "INSERT INTO clinica.paciente_doctor "
                + "(id_paciente, id_doctor) "
                + "VALUES (?, ?) "
                + "ON CONFLICT (id_paciente, id_doctor) "
                + "DO NOTHING";

        String sqlRegistro =
                "INSERT INTO clinica.registro "
                + "(id_ingreso, id_paciente, id_doctor, "
                + "alergias, observaciones, diagnostico, salida) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) "
                + "RETURNING id_registro";

        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement psRelacion =
                             conn.prepareStatement(sqlRelacion)) {

                    psRelacion.setInt(
                            1, registro.getIdPaciente());
                    psRelacion.setInt(
                            2, registro.getIdDoctor());
                    psRelacion.executeUpdate();
                }

                try (PreparedStatement psRegistro =
                             conn.prepareStatement(sqlRegistro)) {

                    psRegistro.setInt(
                            1, registro.getIdIngreso());
                    psRegistro.setInt(
                            2, registro.getIdPaciente());
                    psRegistro.setInt(
                            3, registro.getIdDoctor());
                    psRegistro.setString(
                            4, registro.getAlergias());
                    psRegistro.setString(
                            5, registro.getObservaciones());
                    psRegistro.setString(
                            6, registro.getDiagnostico());
                    psRegistro.setString(
                            7, registro.getSalida());

                    try (ResultSet rs =
                                 psRegistro.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                    "No se obtuvo el ID del registro.");
                        }

                        registro.setIdRegistro(
                                rs.getInt("id_registro"));
                    }
                }

                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;

            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void guardarEgreso(Egreso egreso)
            throws SQLException {

        String sql =
                "INSERT INTO clinica.egreso "
                + "(id_ingreso, fecha_egreso, "
                + "hora_egreso, observaciones) "
                + "VALUES (?, ?, ?, ?) "
                + "RETURNING id_egreso";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, egreso.getIdIngreso());
            ps.setDate(
                    2,
                    Date.valueOf(
                            egreso.getFechaEgreso()));
            ps.setTime(
                    3,
                    Time.valueOf(
                            egreso.getHoraEgreso()));
            ps.setString(
                    4, egreso.getObservaciones());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException(
                            "No se obtuvo el ID del egreso.");
                }

                egreso.setIdEgreso(
                        rs.getInt("id_egreso"));
            }
        }
    }
}