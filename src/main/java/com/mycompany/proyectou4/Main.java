package com.mycompany.proyectou4;

import Interfaz.Hospital;
import controlador.ControladorHospital;
import dao.HospitalDAO;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            Hospital vista = new Hospital();
            HospitalDAO dao = new HospitalDAO();

            ControladorHospital controlador =
                    new ControladorHospital(vista, dao);

            controlador.iniciar();
        });
    }
}