package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {

    private static final String URL =
            "jdbc:postgresql://localhost:5433/hospital";

    private static final String USUARIO = "postgres";
    private static final String PASSWORD = "1234";

    private Conexion() {
    }

    public static Connection getConexion()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }

    public static boolean probarConexion() {

        try (Connection conexion = getConexion()) {

            System.out.println(
                    "Conexión exitosa a: "
                    + conexion.getCatalog()
            );

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "ERROR SQL: " + e.getMessage()
            );

            System.err.println(
                    "SQLState: " + e.getSQLState()
            );

            return false;
        }
    }
}