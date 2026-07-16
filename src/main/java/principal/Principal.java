package principal;

import Interfaz.Hospital;
import conexion.Conexion;
import controlador.ControladorHospital;
import dao.HospitalDAO;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Principal {

    public static void main(String[] args) {

        configurarApariencia();

        SwingUtilities.invokeLater(() -> {

            if (!Conexion.probarConexion()) {

                JOptionPane.showMessageDialog(
                        null,
                        "No fue posible conectarse con "
                        + "la base de datos hospital.",
                        "Error de conexión",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            Hospital vista =
                    new Hospital();

            HospitalDAO dao =
                    new HospitalDAO();

            ControladorHospital controlador =
                    new ControladorHospital(
                            vista,
                            dao
                    );

            controlador.iniciar();
        });
    }

    private static void configurarApariencia() {

        try {

            for (
                UIManager.LookAndFeelInfo info
                : UIManager.getInstalledLookAndFeels()
            ) {

                if ("Nimbus".equals(info.getName())) {

                    UIManager.setLookAndFeel(
                            info.getClassName()
                    );

                    break;
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "No se pudo aplicar Nimbus: "
                    + e.getMessage()
            );
        }
    }
}