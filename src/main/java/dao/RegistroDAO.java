package dao;

import conexion.Conexion;
import modelo.Registro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroDAO {

    public int guardar(Registro registro)
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
                sentenciaRelacion.setInt(
                        1,
                        registro.getIdPaciente()
                );

                sentenciaRelacion.setInt(
                        2,
                        registro.getIdDoctor()
                );

                sentenciaRelacion.executeUpdate();
            }

            int idRegistro;

            try (
                PreparedStatement sentenciaRegistro =
                        conexion.prepareStatement(sqlRegistro)
            ) {
                sentenciaRegistro.setInt(
                        1,
                        registro.getIdIngreso()
                );

                sentenciaRegistro.setInt(
                        2,
                        registro.getIdPaciente()
                );

                sentenciaRegistro.setInt(
                        3,
                        registro.getIdDoctor()
                );

                sentenciaRegistro.setString(
                        4,
                        registro.getAlergias()
                );

                sentenciaRegistro.setString(
                        5,
                        registro.getObservaciones()
                );

                sentenciaRegistro.setString(
                        6,
                        registro.getDiagnostico()
                );

                sentenciaRegistro.setString(
                        7,
                        registro.getSalida()
                );

                try (
                    ResultSet resultado =
                            sentenciaRegistro.executeQuery()
                ) {
                    if (!resultado.next()) {
                        throw new SQLException(
                                "No se generó el ID del registro."
                        );
                    }

                    idRegistro =
                            resultado.getInt("id_registro");

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
}