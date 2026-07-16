package dao;

import conexion.Conexion;
import modelo.Egreso;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class EgresoDAO {

    public int guardar(Egreso egreso)
            throws SQLException {

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

            int idEgreso;

            try (
                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
            ) {
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
                    ResultSet resultado =
                            sentencia.executeQuery()
                ) {
                    if (!resultado.next()) {
                        throw new SQLException(
                                "No se generó el ID del egreso."
                        );
                    }

                    idEgreso =
                            resultado.getInt("id_egreso");

                    egreso.setIdEgreso(idEgreso);
                }
            }

            conexion.commit();

            return idEgreso;

        } catch (SQLException e) {

            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            throw new SQLException(
                    "No se registró el egreso. "
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