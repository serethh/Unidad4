package dao;

import conexion.Conexion;
import modelo.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public List<Doctor> listarActivos()
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

        List<Doctor> doctores = new ArrayList<>();

        try (
                Connection conexion
                = Conexion.getConexion(); PreparedStatement sentencia
                = conexion.prepareStatement(sql); ResultSet resultado
                = sentencia.executeQuery()) {
            while (resultado.next()) {

                Doctor doctor = new Doctor();

                doctor.setIdDoctor(
                        resultado.getInt("id_doctor")
                );

                doctor.setNombre(
                        resultado.getString("nombre")
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
                        resultado.getString("telefono")
                );

                doctor.setCorreo(
                        resultado.getString("correo")
                );

                doctor.setActivo(
                        resultado.getBoolean("activo")
                );

                doctores.add(doctor);
            }
        }

        return doctores;
    }
}
