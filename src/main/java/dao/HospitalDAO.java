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

    public int guardarPacienteConIngreso(
            Paciente paciente,
            Ingreso ingreso
    ) throws SQLException {

        String sqlPaciente = """
            INSERT INTO clinica.paciente (
                nombre,
                apellido_paterno,
                apellido_materno,
                genero,
                fecha_nacimiento
            )
            VALUES (?, ?, ?, ?, ?)
            RETURNING id_paciente
            """;

        String sqlIngreso = """
            INSERT INTO clinica.ingreso (
                id_paciente,
                peso,
                fecha_ingreso,
                hora_ingreso
            )
            VALUES (?, ?, ?, ?)
            RETURNING id_ingreso
            """;

        Connection conexion = null;

        try {
            conexion = Conexion.getConexion();

            conexion.setAutoCommit(false);

            conexion.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED
            );

            int idPaciente;

            try (
                    PreparedStatement sentenciaPaciente
                    = conexion.prepareStatement(sqlPaciente)) {
                sentenciaPaciente.setString(
                        1,
                        paciente.getNombre()
                );

                sentenciaPaciente.setString(
                        2,
                        paciente.getApellidoPaterno()
                );

                sentenciaPaciente.setString(
                        3,
                        paciente.getApellidoMaterno()
                );

                sentenciaPaciente.setString(
                        4,
                        paciente.getGenero()
                );

                sentenciaPaciente.setDate(
                        5,
                        Date.valueOf(
                                paciente.getFechaNacimiento()
                        )
                );

                try (
                        ResultSet resultado
                        = sentenciaPaciente.executeQuery()) {
                    if (!resultado.next()) {
                        throw new SQLException(
                                "No se generó el ID del paciente."
                        );
                    }

                    idPaciente
                            = resultado.getInt("id_paciente");

                    paciente.setIdPaciente(idPaciente);
                }
            }

            ingreso.setIdPaciente(idPaciente);

            int idIngreso;

            try (
                    PreparedStatement sentenciaIngreso
                    = conexion.prepareStatement(sqlIngreso)) {
                sentenciaIngreso.setInt(
                        1,
                        idPaciente
                );

                sentenciaIngreso.setBigDecimal(
                        2,
                        ingreso.getPeso()
                );

                sentenciaIngreso.setDate(
                        3,
                        Date.valueOf(
                                ingreso.getFechaIngreso()
                        )
                );

                sentenciaIngreso.setTime(
                        4,
                        Time.valueOf(
                                ingreso.getHoraIngreso()
                        )
                );

                try (
                        ResultSet resultado
                        = sentenciaIngreso.executeQuery()) {
                    if (!resultado.next()) {
                        throw new SQLException(
                                "No se generó el ID del ingreso."
                        );
                    }

                    idIngreso
                            = resultado.getInt("id_ingreso");

                    ingreso.setIdIngreso(idIngreso);
                }
            }

            /*
             * Confirma ambas inserciones.
             */
            conexion.commit();

            return idIngreso;

        } catch (SQLException e) {

            if (conexion != null) {
                try {
                    /*
                     * Revierte paciente e ingreso.
                     */
                    conexion.rollback();

                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            throw new SQLException(
                    "No se guardó el paciente ni el ingreso. "
                    + "La transacción fue revertida. "
                    + e.getMessage(),
                    e
            );

        } finally {

            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println(
                            "No se pudo restaurar autoCommit: "
                            + e.getMessage()
                    );
                }

                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.err.println(
                            "No se pudo cerrar la conexión: "
                            + e.getMessage()
                    );
                }
            }
        }
    }

    public List<Doctor> listarDoctores()
            throws SQLException {

        String sql = """
        SELECT
            id_doctor,
            nombre,
            apellido_paterno,
            apellido_materno,
            especialidad,
            cedula_profesional,
            telefono,
            correo,
            activo
        FROM clinica.doctor
        WHERE activo = TRUE
        ORDER BY apellido_paterno, nombre
        """;

        List<Doctor> doctores
                = new ArrayList<>();

        try (
                Connection conexion
                = Conexion.getConexion(); PreparedStatement sentencia
                = conexion.prepareStatement(sql); ResultSet resultado
                = sentencia.executeQuery()) {

            while (resultado.next()) {

                Doctor doctor = new Doctor();

                doctor.setIdDoctor(
                        resultado.getInt(
                                "id_doctor"
                        )
                );

                doctor.setNombre(
                        resultado.getString(
                                "nombre"
                        )
                );

                doctor.setApellidoPaterno(
                        resultado.getString(
                                "apellido_paterno"
                        )
                );

                doctor.setApellidoMaterno(
                        resultado.getString(
                                "apellido_materno"
                        )
                );

                doctor.setEspecialidad(
                        resultado.getString(
                                "especialidad"
                        )
                );

                doctor.setCedulaProfesional(
                        resultado.getString(
                                "cedula_profesional"
                        )
                );

                doctor.setTelefono(
                        resultado.getString(
                                "telefono"
                        )
                );

                doctor.setCorreo(
                        resultado.getString(
                                "correo"
                        )
                );

                doctor.setActivo(
                        resultado.getBoolean(
                                "activo"
                        )
                );

                doctores.add(doctor);
            }
        }

        return doctores;
    
    }
 
    public List<IngresoPaciente>
            listarPendientesRegistro()
            throws SQLException {

        String sql = """
        SELECT
            i.id_ingreso,
            p.id_paciente,
            p.nombre,
            p.apellido_paterno,
            p.apellido_materno
        FROM clinica.ingreso i
        INNER JOIN clinica.paciente p
            ON p.id_paciente = i.id_paciente
        LEFT JOIN clinica.registro r
            ON r.id_ingreso = i.id_ingreso
        WHERE r.id_registro IS NULL
          AND i.estado = 'INGRESADO'
        ORDER BY
            p.apellido_paterno,
            p.apellido_materno,
            p.nombre
        """;

        List<IngresoPaciente> pacientes
                = new ArrayList<>();

        try (
                Connection conexion
                = Conexion.getConexion(); PreparedStatement sentencia
                = conexion.prepareStatement(sql); ResultSet resultado
                = sentencia.executeQuery()) {

            while (resultado.next()) {

                IngresoPaciente paciente
                        = new IngresoPaciente();

                paciente.setIdIngreso(
                        resultado.getInt(
                                "id_ingreso"
                        )
                );

                paciente.setIdPaciente(
                        resultado.getInt(
                                "id_paciente"
                        )
                );

                paciente.setNombre(
                        resultado.getString(
                                "nombre"
                        )
                );

                paciente.setApellidoPaterno(
                        resultado.getString(
                                "apellido_paterno"
                        )
                );

                paciente.setApellidoMaterno(
                        resultado.getString(
                                "apellido_materno"
                        )
                );

                pacientes.add(paciente);
            }
        }

        return pacientes;
    }
            
         public List<IngresoPaciente> listarPacientes()
        throws SQLException {

    String sql = """
        SELECT
            i.id_ingreso,
            p.id_paciente,
            p.nombre,
            p.apellido_paterno,
            p.apellido_materno
        FROM clinica.ingreso i
        INNER JOIN clinica.paciente p
            ON p.id_paciente = i.id_paciente
        ORDER BY
            p.apellido_paterno,
            p.apellido_materno,
            p.nombre
        """;

    List<IngresoPaciente> pacientes =
            new ArrayList<>();

    try (
        Connection conexion =
                Conexion.getConexion();

        PreparedStatement sentencia =
                conexion.prepareStatement(sql);

        ResultSet resultado =
                sentencia.executeQuery()
    ) {

        while (resultado.next()) {

            IngresoPaciente paciente =
                    new IngresoPaciente();

            paciente.setIdIngreso(
                    resultado.getInt(
                            "id_ingreso"
                    )
            );

            paciente.setIdPaciente(
                    resultado.getInt(
                            "id_paciente"
                    )
            );

            paciente.setNombre(
                    resultado.getString(
                            "nombre"
                    )
            );

            paciente.setApellidoPaterno(
                    resultado.getString(
                            "apellido_paterno"
                    )
            );

            paciente.setApellidoMaterno(
                    resultado.getString(
                            "apellido_materno"
                    )
            );

            pacientes.add(paciente);
        }
    }

    return pacientes;
}

    public int guardarRegistro(
            Registro registro
    ) throws SQLException {

        String sqlRelacion = """
        INSERT INTO clinica.paciente_doctor (
            id_paciente,
            id_doctor
        )
        VALUES (?, ?)
        ON CONFLICT (id_paciente, id_doctor)
        DO UPDATE SET activo = TRUE
        """;

        String sqlRegistro = """
        INSERT INTO clinica.registro (
            id_ingreso,
            id_paciente,
            id_doctor,
            alergias,
            observaciones,
            diagnostico,
            salida
        )
        VALUES (?, ?, ?, ?, ?, ?, ?)
        RETURNING id_registro
        """;

        Connection conexion = null;

        try {

            conexion = Conexion.getConexion();

            conexion.setAutoCommit(false);

            conexion.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED
            );

            try (
                    PreparedStatement relacion
                    = conexion.prepareStatement(
                            sqlRelacion
                    )) {

                        relacion.setInt(
                                1,
                                registro.getIdPaciente()
                        );

                        relacion.setInt(
                                2,
                                registro.getIdDoctor()
                        );

                        relacion.executeUpdate();
                    }

                    int idRegistro;

                    try (
                            PreparedStatement sentencia
                            = conexion.prepareStatement(
                                    sqlRegistro
                            )) {

                                sentencia.setInt(
                                        1,
                                        registro.getIdIngreso()
                                );

                                sentencia.setInt(
                                        2,
                                        registro.getIdPaciente()
                                );

                                sentencia.setInt(
                                        3,
                                        registro.getIdDoctor()
                                );

                                sentencia.setString(
                                        4,
                                        registro.getAlergias()
                                );

                                sentencia.setString(
                                        5,
                                        registro.getObservaciones()
                                );

                                sentencia.setString(
                                        6,
                                        registro.getDiagnostico()
                                );

                                sentencia.setString(
                                        7,
                                        registro.getSalida()
                                );

                                try (
                                        ResultSet resultado
                                        = sentencia.executeQuery()) {

                                    if (!resultado.next()) {

                                        throw new SQLException(
                                                "No se generó el ID del registro."
                                        );
                                    }

                                    idRegistro
                                            = resultado.getInt(
                                                    "id_registro"
                                            );

                                    registro.setIdRegistro(
                                            idRegistro
                                    );
                                }
                            }

                            conexion.commit();

                            return idRegistro;

        } catch (SQLException ex) {

            if (conexion != null) {

                try {

                    conexion.rollback();

                } catch (SQLException rollbackEx) {

                    ex.addSuppressed(
                            rollbackEx
                    );
                }
            }

            throw ex;

        } finally {

            if (conexion != null) {

                try {

                    conexion.setAutoCommit(true);

                } catch (SQLException ex) {

                    System.err.println(
                            ex.getMessage()
                    );
                }

                try {

                    conexion.close();

                } catch (SQLException ex) {

                    System.err.println(
                            ex.getMessage()
                    );
                }
            }
        }
    }

    public int guardarEgreso(
            Egreso egreso
    ) throws SQLException {

        String sql = """
        INSERT INTO clinica.egreso (
            id_ingreso,
            fecha_egreso,
            hora_egreso,
            observaciones
        )
        VALUES (?, ?, ?, ?)
        RETURNING id_egreso
        """;

        Connection conexion = null;

        try {

            conexion = Conexion.getConexion();

            conexion.setAutoCommit(false);

            conexion.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED
            );

            int idEgreso;

            try (
                    PreparedStatement sentencia
                    = conexion.prepareStatement(sql)) {

                sentencia.setInt(
                        1,
                        egreso.getIdIngreso()
                );

                sentencia.setDate(
                        2,
                        Date.valueOf(
                                egreso.getFechaEgreso()
                        )
                );

                sentencia.setTime(
                        3,
                        Time.valueOf(
                                egreso.getHoraEgreso()
                        )
                );

                sentencia.setString(
                        4,
                        egreso.getObservaciones()
                );

                try (
                        ResultSet resultado
                        = sentencia.executeQuery()) {

                    if (!resultado.next()) {

                        throw new SQLException(
                                "No se generó el ID del egreso."
                        );
                    }

                    idEgreso
                            = resultado.getInt(
                                    "id_egreso"
                            );

                    egreso.setIdEgreso(
                            idEgreso
                    );
                }
            }

            conexion.commit();

            return idEgreso;

        } catch (SQLException ex) {

            if (conexion != null) {

                try {

                    conexion.rollback();

                } catch (SQLException rollbackEx) {

                    ex.addSuppressed(
                            rollbackEx
                    );
                }
            }

            throw ex;

        } finally {

            if (conexion != null) {

                try {

                    conexion.setAutoCommit(true);

                } catch (SQLException ex) {

                    System.err.println(
                            ex.getMessage()
                    );
                }

                try {

                    conexion.close();

                } catch (SQLException ex) {

                    System.err.println(
                            ex.getMessage()
                    );
                }
            }
        }
    }
    public List<IngresoPaciente>
        listarPendientesEgreso()
        throws SQLException {

    String sql = """
        SELECT
            i.id_ingreso,
            p.id_paciente,
            p.nombre,
            p.apellido_paterno,
            p.apellido_materno
        FROM clinica.ingreso i
        INNER JOIN clinica.paciente p
            ON p.id_paciente = i.id_paciente
        LEFT JOIN clinica.egreso e
            ON e.id_ingreso = i.id_ingreso
        WHERE e.id_egreso IS NULL
          AND i.estado IN (
              'ALTA'
          )
        ORDER BY
            i.fecha_ingreso,
            i.hora_ingreso
        """;

    List<IngresoPaciente> pacientes =
            new ArrayList<>();

    try (
        Connection conexion =
                Conexion.getConexion();

        PreparedStatement sentencia =
                conexion.prepareStatement(sql);

        ResultSet resultado =
                sentencia.executeQuery()
    ) {

        while (resultado.next()) {

            IngresoPaciente paciente =
                    new IngresoPaciente();

            paciente.setIdIngreso(
                    resultado.getInt(
                            "id_ingreso"
                    )
            );

            paciente.setIdPaciente(
                    resultado.getInt(
                            "id_paciente"
                    )
            );

            paciente.setNombre(
                    resultado.getString(
                            "nombre"
                    )
            );

            paciente.setApellidoPaterno(
                    resultado.getString(
                            "apellido_paterno"
                    )
            );

            paciente.setApellidoMaterno(
                    resultado.getString(
                            "apellido_materno"
                    )
            );

            pacientes.add(paciente);
        }
    }

    return pacientes;
}
        
        public Object[] obtenerDetallePaciente(
        int idIngreso
) throws SQLException {

    String sql = """
        SELECT
            paciente,
            genero,
            fecha_nacimiento,
            edad,
            peso,
            fecha_ingreso,
            hora_ingreso,
            estado,
            doctor,
            especialidad,
            alergias,
            observaciones_registro,
            diagnostico,
            salida,
            fecha_egreso,
            hora_egreso,
            observaciones_egreso
        FROM clinica.vista_pacientes
        WHERE id_ingreso = ?
        """;

    try (
        Connection conexion =
                Conexion.getConexion();

        PreparedStatement sentencia =
                conexion.prepareStatement(sql)
    ) {

        sentencia.setInt(
                1,
                idIngreso
        );

        try (
            ResultSet resultado =
                    sentencia.executeQuery()
        ) {

            if (!resultado.next()) {
                return null;
            }

            return new Object[]{
                resultado.getString("paciente"),
                resultado.getString("genero"),
                resultado.getDate("fecha_nacimiento"),
                resultado.getInt("edad"),
                resultado.getBigDecimal("peso"),
                resultado.getDate("fecha_ingreso"),
                resultado.getTime("hora_ingreso"),
                resultado.getString("estado"),
                resultado.getString("doctor"),
                resultado.getString("especialidad"),
                resultado.getString("alergias"),
                resultado.getString("observaciones_registro"),
                resultado.getString("diagnostico"),
                resultado.getString("salida"),
                resultado.getDate("fecha_egreso"),
                resultado.getTime("hora_egreso"),
                resultado.getString("observaciones_egreso")
            };
        }
    }
}
        public List<Object[]> listarVistaGeneral()
        throws SQLException {

    String sql = """
        SELECT
            id_ingreso,
            paciente,
            genero,
            edad,
            peso,
            fecha_ingreso,
            hora_ingreso,
            doctor,
            diagnostico,
            CASE
                WHEN salida = 'ALTA'
                    OR estado = 'ALTA'
                    THEN 'ALTA'

                WHEN salida = 'HOSPITALIZACION'
                    OR estado = 'HOSPITALIZADO'
                    THEN 'HOSPITALIZADO'

                ELSE COALESCE(estado, 'INGRESADO')
            END AS estado_visual
        FROM clinica.vista_pacientes
        ORDER BY
            fecha_ingreso DESC,
            hora_ingreso DESC,
            paciente
        """;

    List<Object[]> pacientes = new ArrayList<>();

    try (
        Connection conexion =
                Conexion.getConexion();

        PreparedStatement sentencia =
                conexion.prepareStatement(sql);

        ResultSet resultado =
                sentencia.executeQuery()
    ) {

        while (resultado.next()) {

            Object[] fila = {
                resultado.getInt("id_ingreso"),
                resultado.getString("paciente"),
                resultado.getString("genero"),
                resultado.getInt("edad"),
                resultado.getBigDecimal("peso"),
                resultado.getDate("fecha_ingreso"),
                resultado.getTime("hora_ingreso"),
                resultado.getString("doctor"),
                resultado.getString("diagnostico"),
                resultado.getString("estado_visual")
            };

            pacientes.add(fila);
        }
    }

    return pacientes;
}
        
}
