package dao;

import conexion.Conexion;
import modelo.Registro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroDAO {

    public int insertar(Registro registro)
            throws SQLException {

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

            try (
                PreparedStatement sentenciaRelacion =
                        conexion.prepareStatement(sqlRelacion)
            ) {
                sentenciaRelacion.setInt(1, registro.getIdPaciente());
                sentenciaRelacion.setInt(2, registro.getIdDoctor());
                sentenciaRelacion.executeUpdate();
            }

            int idRegistro;

            try (
                PreparedStatement sentenciaRegistro =
                        conexion.prepareStatement(sqlRegistro)
            ) {
                sentenciaRegistro.setInt(1, registro.getIdIngreso());
                sentenciaRegistro.setInt(2, registro.getIdPaciente());
                sentenciaRegistro.setInt(3, registro.getIdDoctor());
                sentenciaRegistro.setString(4, registro.getAlergias());
                sentenciaRegistro.setString(5, registro.getObservaciones());
                sentenciaRegistro.setString(6, registro.getDiagnostico());
                sentenciaRegistro.setString(7, registro.getSalida());

                try (ResultSet resultado = sentenciaRegistro.executeQuery()) {

                    if (!resultado.next()) {
                        throw new SQLException("No se generó el ID del registro.");
                    }

                    idRegistro = resultado.getInt("id_registro");
                    registro.setIdRegistro(idRegistro);
                }
            }

            conexion.commit();
            return idRegistro;

        } catch (SQLException e) {

            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            throw new SQLException(
                    "No se guardó el registro clínico. "
                    + "La transacción fue revertida. "
                    + e.getMessage(),
                    e
            );

        } finally {

            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public void actualizar(Registro registro)
            throws SQLException {

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
            UPDATE clinica.registro
            SET
                id_doctor = ?,
                alergias = ?,
                observaciones = ?,
                diagnostico = ?,
                salida = ?
            WHERE id_registro = ?
            """;

        Connection conexion = null;

        try {
            conexion = Conexion.getConexion();
            conexion.setAutoCommit(false);

            try (
                PreparedStatement sentenciaRelacion =
                        conexion.prepareStatement(sqlRelacion)
            ) {
                sentenciaRelacion.setInt(1, registro.getIdPaciente());
                sentenciaRelacion.setInt(2, registro.getIdDoctor());
                sentenciaRelacion.executeUpdate();
            }

            try (
                PreparedStatement sentenciaRegistro =
                        conexion.prepareStatement(sqlRegistro)
            ) {
                sentenciaRegistro.setInt(1, registro.getIdDoctor());
                sentenciaRegistro.setString(2, registro.getAlergias());
                sentenciaRegistro.setString(3, registro.getObservaciones());
                sentenciaRegistro.setString(4, registro.getDiagnostico());
                sentenciaRegistro.setString(5, registro.getSalida());
                sentenciaRegistro.setInt(6, registro.getIdRegistro());

                int filasAfectadas = sentenciaRegistro.executeUpdate();

                if (filasAfectadas == 0) {
                    throw new SQLException("No se encontró el registro a actualizar.");
                }
            }

            conexion.commit();

        } catch (SQLException e) {

            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            throw new SQLException(
                    "No se actualizó el registro clínico. "
                    + "La transacción fue revertida. "
                    + e.getMessage(),
                    e
            );

        } finally {

            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Decide automáticamente entre insertar o actualizar
     * según si el registro ya trae un id_registro válido.
     */
    public int guardar(Registro registro)
            throws SQLException {

        if (registro.getIdRegistro() > 0) {
            actualizar(registro);
            return registro.getIdRegistro();
        }

        return insertar(registro);
    }

    public Registro buscarPorIdIngreso(int idIngreso)
            throws SQLException {

        String sql = """
            SELECT
                id_registro,
                id_ingreso,
                id_paciente,
                id_doctor,
                alergias,
                observaciones,
                diagnostico,
                salida
            FROM clinica.registro
            WHERE id_ingreso = ?
            ORDER BY id_registro DESC
            LIMIT 1
            """;

        try (
            Connection conexion = Conexion.getConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(1, idIngreso);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                Registro registro = new Registro();

                registro.setIdRegistro(resultado.getInt("id_registro"));
                registro.setIdIngreso(resultado.getInt("id_ingreso"));
                registro.setIdPaciente(resultado.getInt("id_paciente"));
                registro.setIdDoctor(resultado.getInt("id_doctor"));
                registro.setAlergias(resultado.getString("alergias"));
                registro.setObservaciones(resultado.getString("observaciones"));
                registro.setDiagnostico(resultado.getString("diagnostico"));
                registro.setSalida(resultado.getString("salida"));

                return registro;
            }
        }
    }
}