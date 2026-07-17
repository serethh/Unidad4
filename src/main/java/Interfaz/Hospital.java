package Interfaz;

import controlador.ControladorHospital;
import modelo.Doctor;
import modelo.Egreso;
import modelo.Ingreso;
import modelo.IngresoPaciente;
import modelo.Paciente;
import modelo.Registro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.time.Period;

public class Hospital extends javax.swing.JFrame {

    private List<Doctor> listaDoctores = new ArrayList<>();

    private List<IngresoPaciente> listaPacientesRegistro
            = new ArrayList<>();

    private List<IngresoPaciente> listaPacientesEgreso
            = new ArrayList<>();
    
    private List<IngresoPaciente> listaPacientesVista
        = new ArrayList<>();
    
    private ControladorHospital controlador;

    public Hospital() {
        initComponents();
        setLocationRelativeTo(null);
         configurarRestricciones();
         configurarCalculoEdad();
         configurarPestanaEgreso();
         configurarTablaVista();
         configurarSeleccionVista();

        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        contenedorHospital1 = new Componente1.ContenedorHospital();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbNombre = new javax.swing.JLabel();
        llApPaterno = new javax.swing.JLabel();
        lbApMa = new javax.swing.JLabel();
        lbNombre3 = new javax.swing.JLabel();
        lbNombre4 = new javax.swing.JLabel();
        lbNombre5 = new javax.swing.JLabel();
        lbNombre6 = new javax.swing.JLabel();
        lbNombre7 = new javax.swing.JLabel();
        lbNombre8 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtPaterno = new javax.swing.JTextField();
        txtMaterno = new javax.swing.JTextField();
        cbGenero = new javax.swing.JComboBox<>();
        fechaN = new com.toedter.calendar.JDateChooser();
        txtEdad = new javax.swing.JTextField();
        txtPeso = new javax.swing.JTextField();
        fechaIngreso = new com.toedter.calendar.JDateChooser();
        txtHoraIngreso = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        reloj1 = new Componente2.Reloj();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbNombre1 = new javax.swing.JLabel();
        lbNombre2 = new javax.swing.JLabel();
        lbNombre9 = new javax.swing.JLabel();
        lbNombre10 = new javax.swing.JLabel();
        lbNombre11 = new javax.swing.JLabel();
        cbBuscar = new javax.swing.JComboBox<>();
        rbAlta = new javax.swing.JRadioButton();
        rbHospitalizacion = new javax.swing.JRadioButton();
        reloj2 = new Componente2.Reloj();
        btnGuardarRegistro = new javax.swing.JButton();
        lbNombre17 = new javax.swing.JLabel();
        cbBuscarDoctor = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlergias = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAlergias1 = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiagnostico = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbNombre12 = new javax.swing.JLabel();
        cbBuscarEgreso = new javax.swing.JComboBox<>();
        lbNombre13 = new javax.swing.JLabel();
        fechaEgreso = new com.toedter.calendar.JDateChooser();
        lbNombre14 = new javax.swing.JLabel();
        txtHoraEgreso = new javax.swing.JTextField();
        lbNombre15 = new javax.swing.JLabel();
        btnGuardarEgreso = new javax.swing.JButton();
        reloj3 = new Componente2.Reloj();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtObservacionesEgreso = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbNombre16 = new javax.swing.JLabel();
        cbBuscarEgreso1 = new javax.swing.JComboBox<>();
        reloj5 = new Componente2.Reloj();
        jPanel6 = new javax.swing.JPanel();
        contenedorHospital2 = new Componente1.ContenedorHospital();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.setBackground(new java.awt.Color(44, 89, 158));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setBackground(new java.awt.Color(21, 101, 192));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(21, 101, 192));
        jLabel1.setText("INGRESO DE PACIENTES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 253;
        gridBagConstraints.ipadx = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 107, 0, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        lbNombre.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 69, 0, 0);
        jPanel2.add(lbNombre, gridBagConstraints);

        llApPaterno.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        llApPaterno.setText("Apellido Paterno:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 25;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 169;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 38, 0, 0);
        jPanel2.add(llApPaterno, gridBagConstraints);

        lbApMa.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbApMa.setText("Apellido Materno:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 69, 0, 0);
        jPanel2.add(lbApMa, gridBagConstraints);

        lbNombre3.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre3.setText("Genero:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 69, 0, 0);
        jPanel2.add(lbNombre3, gridBagConstraints);

        lbNombre4.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre4.setText("Fecha de nacimiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 37;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 60, 0, 0);
        jPanel2.add(lbNombre4, gridBagConstraints);

        lbNombre5.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre5.setText("Edad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 250;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 2, 0, 0);
        jPanel2.add(lbNombre5, gridBagConstraints);

        lbNombre6.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre6.setText("Peso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 69, 0, 0);
        jPanel2.add(lbNombre6, gridBagConstraints);

        lbNombre7.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre7.setText("Fecha de Ingreso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 26, 0, 0);
        jPanel2.add(lbNombre7, gridBagConstraints);

        lbNombre8.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre8.setText("Hora Ingreso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 26, 0, 0);
        jPanel2.add(lbNombre8, gridBagConstraints);

        txtNombre.setBackground(new java.awt.Color(250, 252, 255));
        txtNombre.setFont(new java.awt.Font("Swis721 BT", 0, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(30, 58, 95));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.ipadx = 164;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 69, 0, 0);
        jPanel2.add(txtNombre, gridBagConstraints);

        txtPaterno.setBackground(new java.awt.Color(250, 252, 255));
        txtPaterno.setFont(new java.awt.Font("Swis721 BT", 0, 14)); // NOI18N
        txtPaterno.setForeground(new java.awt.Color(30, 58, 95));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 25;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 312;
        gridBagConstraints.ipadx = 172;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 38, 0, 101);
        jPanel2.add(txtPaterno, gridBagConstraints);

        txtMaterno.setBackground(new java.awt.Color(250, 252, 255));
        txtMaterno.setFont(new java.awt.Font("Swis721 BT", 0, 14)); // NOI18N
        txtMaterno.setForeground(new java.awt.Color(30, 58, 95));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.ipadx = 172;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 69, 0, 0);
        jPanel2.add(txtMaterno, gridBagConstraints);

        cbGenero.setBackground(new java.awt.Color(234, 244, 255));
        cbGenero.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        cbGenero.setForeground(new java.awt.Color(15, 76, 129));
        cbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione uno", "Femenino", "Masculino" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 69, 0, 0);
        jPanel2.add(cbGenero, gridBagConstraints);

        fechaN.setBackground(new java.awt.Color(234, 244, 255));
        fechaN.setForeground(new java.awt.Color(15, 76, 129));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 57;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 58;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 0, 0);
        jPanel2.add(fechaN, gridBagConstraints);

        txtEdad.setBackground(new java.awt.Color(250, 252, 255));
        txtEdad.setFont(new java.awt.Font("Swis721 BT", 0, 14)); // NOI18N
        txtEdad.setForeground(new java.awt.Color(30, 58, 95));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 82;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 170;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 45, 0, 0);
        jPanel2.add(txtEdad, gridBagConstraints);

        txtPeso.setBackground(new java.awt.Color(250, 252, 255));
        txtPeso.setFont(new java.awt.Font("Swis721 BT", 0, 14)); // NOI18N
        txtPeso.setForeground(new java.awt.Color(30, 58, 95));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 42;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 69, 0, 0);
        jPanel2.add(txtPeso, gridBagConstraints);

        fechaIngreso.setBackground(new java.awt.Color(234, 244, 255));
        fechaIngreso.setForeground(new java.awt.Color(15, 76, 129));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 25;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 113;
        gridBagConstraints.ipadx = 58;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 17, 0, 0);
        jPanel2.add(fechaIngreso, gridBagConstraints);

        txtHoraIngreso.setBackground(new java.awt.Color(250, 252, 255));
        txtHoraIngreso.setFont(new java.awt.Font("Swis721 BT", 0, 14)); // NOI18N
        txtHoraIngreso.setForeground(new java.awt.Color(30, 58, 95));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 25;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 58;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 17, 0, 0);
        jPanel2.add(txtHoraIngreso, gridBagConstraints);

        btnGuardar.setBackground(new java.awt.Color(72, 187, 120));
        btnGuardar.setFont(new java.awt.Font("Swis721 BT", 3, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(55, 11, 88, 0);
        jPanel2.add(btnGuardar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 82;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 254;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 3, 0, 0);
        jPanel2.add(reloj1, gridBagConstraints);

        jTabbedPane1.addTab("Ingreso", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setBackground(new java.awt.Color(21, 101, 192));
        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(21, 101, 192));
        jLabel2.setText("REGISTRO DE PACIENTE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 94, 0, 0);
        jPanel3.add(jLabel2, gridBagConstraints);

        lbNombre1.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre1.setText("Paciente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 94, 0, 0);
        jPanel3.add(lbNombre1, gridBagConstraints);

        lbNombre2.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre2.setText("Alergias:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 94, 0, 0);
        jPanel3.add(lbNombre2, gridBagConstraints);

        lbNombre9.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre9.setText("Observaciones:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(94, 94, 0, 0);
        jPanel3.add(lbNombre9, gridBagConstraints);

        lbNombre10.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre10.setText("Diagnostico:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(105, 94, 0, 0);
        jPanel3.add(lbNombre10, gridBagConstraints);

        lbNombre11.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre11.setText("Salida:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 94, 0, 0);
        jPanel3.add(lbNombre11, gridBagConstraints);

        cbBuscar.setBackground(new java.awt.Color(234, 244, 255));
        cbBuscar.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        cbBuscar.setForeground(new java.awt.Color(15, 76, 129));
        cbBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione uno" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 205;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 54, 0, 0);
        jPanel3.add(cbBuscar, gridBagConstraints);

        buttonGroup1.add(rbAlta);
        rbAlta.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        rbAlta.setForeground(new java.awt.Color(76, 175, 80));
        rbAlta.setText("Alta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 82, 0, 0);
        jPanel3.add(rbAlta, gridBagConstraints);

        buttonGroup1.add(rbHospitalizacion);
        rbHospitalizacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rbHospitalizacion.setForeground(new java.awt.Color(229, 57, 53));
        rbHospitalizacion.setText("Hospitalización");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 10, 0, 0);
        jPanel3.add(rbHospitalizacion, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 37, 0, 101);
        jPanel3.add(reloj2, gridBagConstraints);

        btnGuardarRegistro.setBackground(new java.awt.Color(72, 187, 120));
        btnGuardarRegistro.setFont(new java.awt.Font("Swis721 BT", 3, 14)); // NOI18N
        btnGuardarRegistro.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarRegistro.setText("Guardar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 111, 8, 0);
        jPanel3.add(btnGuardarRegistro, gridBagConstraints);

        lbNombre17.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre17.setText("Doctor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 94, 0, 0);
        jPanel3.add(lbNombre17, gridBagConstraints);

        cbBuscarDoctor.setBackground(new java.awt.Color(234, 244, 255));
        cbBuscarDoctor.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        cbBuscarDoctor.setForeground(new java.awt.Color(15, 76, 129));
        cbBuscarDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione uno" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 205;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 54, 0, 0);
        jPanel3.add(cbBuscarDoctor, gridBagConstraints);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        txtAlergias.setBackground(new java.awt.Color(250, 252, 255));
        txtAlergias.setColumns(20);
        txtAlergias.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        txtAlergias.setForeground(new java.awt.Color(30, 58, 95));
        txtAlergias.setRows(5);
        jScrollPane1.setViewportView(txtAlergias);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.ipadx = 312;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 38, 0, 0);
        jPanel3.add(jPanel7, gridBagConstraints);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        txtAlergias1.setBackground(new java.awt.Color(250, 252, 255));
        txtAlergias1.setColumns(20);
        txtAlergias1.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        txtAlergias1.setForeground(new java.awt.Color(30, 58, 95));
        txtAlergias1.setRows(5);
        jScrollPane5.setViewportView(txtAlergias1);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.ipadx = 312;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 38, 0, 0);
        jPanel3.add(jPanel8, gridBagConstraints);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        txtObservaciones.setBackground(new java.awt.Color(250, 252, 255));
        txtObservaciones.setColumns(20);
        txtObservaciones.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        txtObservaciones.setForeground(new java.awt.Color(30, 58, 95));
        txtObservaciones.setRows(5);
        jScrollPane2.setViewportView(txtObservaciones);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.ipadx = 312;
        gridBagConstraints.ipady = 82;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 38, 0, 0);
        jPanel3.add(jPanel9, gridBagConstraints);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        txtDiagnostico.setBackground(new java.awt.Color(250, 252, 255));
        txtDiagnostico.setColumns(20);
        txtDiagnostico.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        txtDiagnostico.setForeground(new java.awt.Color(30, 58, 95));
        txtDiagnostico.setRows(5);
        jScrollPane3.setViewportView(txtDiagnostico);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.ipadx = 312;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 38, 0, 0);
        jPanel3.add(jPanel10, gridBagConstraints);

        jTabbedPane1.addTab("Registro", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel3.setBackground(new java.awt.Color(21, 101, 192));
        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(21, 101, 192));
        jLabel3.setText("EGRESO DE PACIENTE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 143, 0, 0);
        jPanel4.add(jLabel3, gridBagConstraints);

        lbNombre12.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre12.setText("Buscar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(48, 127, 0, 0);
        jPanel4.add(lbNombre12, gridBagConstraints);

        cbBuscarEgreso.setBackground(new java.awt.Color(234, 244, 255));
        cbBuscarEgreso.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        cbBuscarEgreso.setForeground(new java.awt.Color(15, 76, 129));
        cbBuscarEgreso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione uno" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 151;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(47, 25, 0, 0);
        jPanel4.add(cbBuscarEgreso, gridBagConstraints);

        lbNombre13.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre13.setText("Fecha de Egreso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(39, 127, 0, 0);
        jPanel4.add(lbNombre13, gridBagConstraints);

        fechaEgreso.setBackground(new java.awt.Color(234, 244, 255));
        fechaEgreso.setForeground(new java.awt.Color(15, 76, 129));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 84;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 127, 0, 0);
        jPanel4.add(fechaEgreso, gridBagConstraints);

        lbNombre14.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre14.setText("Hora de Egreso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(39, 9, 0, 0);
        jPanel4.add(lbNombre14, gridBagConstraints);

        txtHoraEgreso.setBackground(new java.awt.Color(250, 252, 255));
        txtHoraEgreso.setFont(new java.awt.Font("Swis721 BT", 0, 14)); // NOI18N
        txtHoraEgreso.setForeground(new java.awt.Color(30, 58, 95));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 9, 0, 0);
        jPanel4.add(txtHoraEgreso, gridBagConstraints);

        lbNombre15.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre15.setText("Observaciones:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(34, 127, 0, 0);
        jPanel4.add(lbNombre15, gridBagConstraints);

        btnGuardarEgreso.setBackground(new java.awt.Color(72, 187, 120));
        btnGuardarEgreso.setFont(new java.awt.Font("Swis721 BT", 3, 14)); // NOI18N
        btnGuardarEgreso.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarEgreso.setText("Guardar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(50, 3, 102, 0);
        jPanel4.add(btnGuardarEgreso, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(52, 24, 0, 120);
        jPanel4.add(reloj3, gridBagConstraints);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        txtObservacionesEgreso.setBackground(new java.awt.Color(250, 252, 255));
        txtObservacionesEgreso.setColumns(20);
        txtObservacionesEgreso.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        txtObservacionesEgreso.setForeground(new java.awt.Color(30, 58, 95));
        txtObservacionesEgreso.setRows(5);
        jScrollPane4.setViewportView(txtObservacionesEgreso);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 16;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 131, 0, 120);
        jPanel4.add(jPanel11, gridBagConstraints);

        jTabbedPane1.addTab("Egreso", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel4.setBackground(new java.awt.Color(21, 101, 192));
        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(21, 101, 192));
        jLabel4.setText("LISTA DE PACIENTES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(34, 132, 0, 0);
        jPanel5.add(jLabel4, gridBagConstraints);

        lbNombre16.setFont(new java.awt.Font("Swis721 BT", 2, 14)); // NOI18N
        lbNombre16.setText("Buscar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(47, 109, 0, 0);
        jPanel5.add(lbNombre16, gridBagConstraints);

        cbBuscarEgreso1.setBackground(new java.awt.Color(234, 244, 255));
        cbBuscarEgreso1.setFont(new java.awt.Font("Swis721 BT", 0, 12)); // NOI18N
        cbBuscarEgreso1.setForeground(new java.awt.Color(15, 76, 129));
        cbBuscarEgreso1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione uno" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 177;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(46, 92, 0, 0);
        jPanel5.add(cbBuscarEgreso1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(43, 259, 0, 62);
        jPanel5.add(reloj5, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorHospital2, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(contenedorHospital2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 499;
        gridBagConstraints.ipady = -175;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 50, 141, 0);
        jPanel5.add(jPanel6, gridBagConstraints);

        jTabbedPane1.addTab("Vista", jPanel5);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Hospital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hospital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hospital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hospital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Hospital().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarEgreso;
    private javax.swing.JButton btnGuardarRegistro;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbBuscar;
    private javax.swing.JComboBox<String> cbBuscarDoctor;
    private javax.swing.JComboBox<String> cbBuscarEgreso;
    private javax.swing.JComboBox<String> cbBuscarEgreso1;
    private javax.swing.JComboBox<String> cbGenero;
    private Componente1.ContenedorHospital contenedorHospital1;
    private Componente1.ContenedorHospital contenedorHospital2;
    private com.toedter.calendar.JDateChooser fechaEgreso;
    private com.toedter.calendar.JDateChooser fechaIngreso;
    private com.toedter.calendar.JDateChooser fechaN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbApMa;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbNombre1;
    private javax.swing.JLabel lbNombre10;
    private javax.swing.JLabel lbNombre11;
    private javax.swing.JLabel lbNombre12;
    private javax.swing.JLabel lbNombre13;
    private javax.swing.JLabel lbNombre14;
    private javax.swing.JLabel lbNombre15;
    private javax.swing.JLabel lbNombre16;
    private javax.swing.JLabel lbNombre17;
    private javax.swing.JLabel lbNombre2;
    private javax.swing.JLabel lbNombre3;
    private javax.swing.JLabel lbNombre4;
    private javax.swing.JLabel lbNombre5;
    private javax.swing.JLabel lbNombre6;
    private javax.swing.JLabel lbNombre7;
    private javax.swing.JLabel lbNombre8;
    private javax.swing.JLabel lbNombre9;
    private javax.swing.JLabel llApPaterno;
    private javax.swing.JRadioButton rbAlta;
    private javax.swing.JRadioButton rbHospitalizacion;
    private Componente2.Reloj reloj1;
    private Componente2.Reloj reloj2;
    private Componente2.Reloj reloj3;
    private Componente2.Reloj reloj5;
    private javax.swing.JTextArea txtAlergias;
    private javax.swing.JTextArea txtAlergias1;
    private javax.swing.JTextArea txtDiagnostico;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtHoraEgreso;
    private javax.swing.JTextField txtHoraIngreso;
    private javax.swing.JTextField txtMaterno;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextArea txtObservacionesEgreso;
    private javax.swing.JTextField txtPaterno;
    private javax.swing.JTextField txtPeso;
    // End of variables declaration//GEN-END:variables

   
    public void setControlador(ControladorHospital controlador) {
        
        this.controlador=controlador;

        btnGuardar.addActionListener(
                evento -> controlador.guardarIngreso()
        );

        btnGuardarRegistro.addActionListener(
                evento -> controlador.guardarRegistro()
        );

        btnGuardarEgreso.addActionListener(
                evento -> controlador.guardarEgreso()
        );
    }

    public Paciente obtenerPacienteFormulario() {

        Paciente paciente = new Paciente();

        paciente.setNombre(
                txtNombre.getText().trim()
        );

        paciente.setApellidoPaterno(
                txtPaterno.getText().trim()
        );

        paciente.setApellidoMaterno(
                txtMaterno.getText().trim()
        );

        paciente.setGenero(
                String.valueOf(
                        cbGenero.getSelectedItem()
                )
        );

        if (fechaN.getDate() != null) {

            LocalDate nacimiento
                    = fechaN.getDate()
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            paciente.setFechaNacimiento(nacimiento);

            txtEdad.setText(
                    String.valueOf(
                            paciente.calcularEdad()
                    )
            );
        }

        return paciente;
    }

    public Ingreso obtenerIngresoFormulario() {

        Ingreso ingreso = new Ingreso();

        String pesoTexto
                = txtPeso.getText().trim();

        if (!pesoTexto.isBlank()) {

            try {

                ingreso.setPeso(
                        new BigDecimal(pesoTexto)
                );

            } catch (NumberFormatException ex) {

                throw new IllegalArgumentException(
                        "El peso debe ser numérico."
                );
            }
        }

        if (fechaIngreso.getDate() != null) {

            LocalDate fecha
                    = fechaIngreso.getDate()
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            ingreso.setFechaIngreso(fecha);
        }

        String horaTexto
                = txtHoraIngreso.getText().trim();

        if (!horaTexto.isBlank()) {

            ingreso.setHoraIngreso(
                    convertirHora(
                            horaTexto,
                            "La hora de ingreso"
                    )
            );
        }

        return ingreso;
    }

    public Registro obtenerRegistroFormulario() {

        Registro registro = new Registro();

        registro.setAlergias(
                txtAlergias.getText().trim()
        );

        registro.setObservaciones(
                txtObservaciones.getText().trim()
        );

        registro.setDiagnostico(
                txtDiagnostico.getText().trim()
        );

        if (rbAlta.isSelected()) {

            registro.setSalida("ALTA");

        } else if (rbHospitalizacion.isSelected()) {

            registro.setSalida("HOSPITALIZACION");
        }

        return registro;
    }

    public Egreso obtenerEgresoFormulario() {

        Egreso egreso = new Egreso();

        if (fechaEgreso.getDate() != null) {

            LocalDate fecha
                    = fechaEgreso.getDate()
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            egreso.setFechaEgreso(fecha);
        }

        String horaTexto
                = txtHoraEgreso.getText().trim();

        if (!horaTexto.isBlank()) {

            egreso.setHoraEgreso(
                    convertirHora(
                            horaTexto,
                            "La hora de egreso"
                    )
            );
        }

        egreso.setObservaciones(
                txtObservacionesEgreso
                        .getText()
                        .trim()
        );

        return egreso;
    }

    private LocalTime convertirHora(
            String texto,
            String nombreCampo
    ) {

        try {

            if (texto.length() == 5) {

                return LocalTime.parse(
                        texto + ":00"
                );
            }

            return LocalTime.parse(texto);

        } catch (DateTimeParseException ex) {

            throw new IllegalArgumentException(
                    nombreCampo
                    + " debe tener formato HH:mm o HH:mm:ss."
            );
        }
    }

    public void cargarDoctores(
            List<Doctor> doctores
    ) {

        listaDoctores
                = new ArrayList<>(doctores);

        cbBuscarDoctor.removeAllItems();

        cbBuscarDoctor.addItem(
                "Seleccione uno"
        );

        for (Doctor doctor : listaDoctores) {

            cbBuscarDoctor.addItem(
                    doctor.getNombreCompleto()
            );
        }
    }

    public void cargarPacientesRegistro(
            List<IngresoPaciente> pacientes
    ) {

        listaPacientesRegistro
                = new ArrayList<>(pacientes);

        cbBuscar.removeAllItems();

        cbBuscar.addItem(
                "Seleccione uno"
        );

        for (IngresoPaciente paciente
                : listaPacientesRegistro) {

            cbBuscar.addItem(
                    paciente.getNombreCompleto()
            );
        }
    }

    public void cargarPacientesEgreso(
            List<IngresoPaciente> pacientes
    ) {

        listaPacientesEgreso
                = new ArrayList<>(pacientes);

        cbBuscarEgreso.removeAllItems();

        cbBuscarEgreso.addItem(
                "Seleccione uno"
        );

        for (IngresoPaciente paciente
                : listaPacientesEgreso) {

            cbBuscarEgreso.addItem(
                    paciente.getNombreCompleto()
            );
        }
    }
    
    public void cargarPacientesVista(
        List<IngresoPaciente> pacientes
    ) {

    listaPacientesVista
            = new ArrayList<>(pacientes);

    cbBuscarEgreso1.removeAllItems();

    cbBuscarEgreso1.addItem(
            "Seleccione uno"
    );

    for (IngresoPaciente paciente
            : listaPacientesVista) {

        cbBuscarEgreso1.addItem(
                paciente.getNombreCompleto()
        );
    }
    }
  

    
    public IngresoPaciente
        obtenerPacienteVistaSeleccionado() {

    int indice
            = cbBuscarEgreso1.getSelectedIndex();

    if (indice <= 0
            || indice > listaPacientesVista.size()) {

        return null;
    }

    return listaPacientesVista.get(
            indice - 1
    );
}

    public Doctor obtenerDoctorSeleccionado() {

        int indice
                = cbBuscarDoctor.getSelectedIndex();

        if (indice <= 0
                || indice > listaDoctores.size()) {

            return null;
        }

        return listaDoctores.get(
                indice - 1
        );
    }

    public IngresoPaciente
            obtenerPacienteRegistroSeleccionado() {

        int indice
                = cbBuscar.getSelectedIndex();

        if (indice <= 0
                || indice > listaPacientesRegistro.size()) {

            return null;
        }

        return listaPacientesRegistro.get(
                indice - 1
        );
    }

    public IngresoPaciente
            obtenerPacienteEgresoSeleccionado() {

        int indice
                = cbBuscarEgreso.getSelectedIndex();

        if (indice <= 0
                || indice > listaPacientesEgreso.size()) {

            return null;
        }

        return listaPacientesEgreso.get(
                indice - 1
        );
    }

    public void mostrarMensaje(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Hospital",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void mostrarError(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void limpiarFormularioIngreso() {

        txtNombre.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");

        cbGenero.setSelectedIndex(0);

        fechaN.setDate(null);

        txtEdad.setText("");
        txtPeso.setText("");

        fechaIngreso.setDate(
                new java.util.Date()
        );

        txtHoraIngreso.setText(
                LocalTime.now()
                        .withNano(0)
                        .toString()
        );
    }

    public void limpiarFormularioRegistro() {

        cbBuscarDoctor.setSelectedIndex(0);
        cbBuscar.setSelectedIndex(0);

        txtAlergias.setText("");
        txtObservaciones.setText("");
        txtDiagnostico.setText("");

        buttonGroup1.clearSelection();
    }

    public void limpiarFormularioEgreso() {

        cbBuscarEgreso.setSelectedIndex(0);

        fechaEgreso.setDate(null);

        txtHoraEgreso.setText("");

        txtObservacionesEgreso.setText("");
    }

    
    
      private void configurarRestricciones() {

   
    ((AbstractDocument) txtNombre.getDocument())
            .setDocumentFilter(new FiltroLetras(40));

    ((AbstractDocument) txtPaterno.getDocument())
            .setDocumentFilter(new FiltroLetras(40));

    ((AbstractDocument) txtMaterno.getDocument())
            .setDocumentFilter(new FiltroLetras(40));

    
    ((AbstractDocument) txtPeso.getDocument())
            .setDocumentFilter(new FiltroDecimal(6));
    
      
    ((AbstractDocument) txtAlergias.getDocument())
            .setDocumentFilter(new FiltroTextoGeneral(200));

    ((AbstractDocument) txtObservaciones.getDocument())
            .setDocumentFilter(new FiltroTextoGeneral(500));

    ((AbstractDocument) txtDiagnostico.getDocument())
            .setDocumentFilter(new FiltroTextoGeneral(500));
    
    ((AbstractDocument) txtObservacionesEgreso.getDocument())
            .setDocumentFilter(new FiltroTextoGeneral(500));

}
    
    
    private static class FiltroLetras extends DocumentFilter {

    private final int limite;

    public FiltroLetras(int limite) {
        this.limite = limite;
    }

    @Override
    public void insertString(
            DocumentFilter.FilterBypass fb,
            int offset,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        if (texto == null) {
            return;
        }

        reemplazarTexto(
                fb,
                offset,
                0,
                texto,
                atributos
        );
    }

    @Override
    public void replace(
            DocumentFilter.FilterBypass fb,
            int offset,
            int longitud,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        reemplazarTexto(
                fb,
                offset,
                longitud,
                texto,
                atributos
        );
    }

    private void reemplazarTexto(
            DocumentFilter.FilterBypass fb,
            int offset,
            int longitud,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        String textoActual = fb.getDocument().getText(
                0,
                fb.getDocument().getLength()
        );

        String nuevoTexto
                = textoActual.substring(0, offset)
                + texto
                + textoActual.substring(offset + longitud);

        if (nuevoTexto.length() <= limite
                && nuevoTexto.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñÜü ]*")) {

            fb.replace(
                    offset,
                    longitud,
                    texto,
                    atributos
            );
        }
    }
}
   
    private static class FiltroDecimal extends DocumentFilter {

    private final int limite;

    public FiltroDecimal(int limite) {
        this.limite = limite;
    }

    @Override
    public void insertString(
            DocumentFilter.FilterBypass fb,
            int offset,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        if (texto == null) {
            return;
        }

        reemplazarTexto(
                fb,
                offset,
                0,
                texto,
                atributos
        );
    }

    @Override
    public void replace(
            DocumentFilter.FilterBypass fb,
            int offset,
            int longitud,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        reemplazarTexto(
                fb,
                offset,
                longitud,
                texto,
                atributos
        );
    }

    private void reemplazarTexto(
            DocumentFilter.FilterBypass fb,
            int offset,
            int longitud,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        String textoActual = fb.getDocument().getText(
                0,
                fb.getDocument().getLength()
        );

        String nuevoTexto
                = textoActual.substring(0, offset)
                + texto
                + textoActual.substring(offset + longitud);

        if (nuevoTexto.length() <= limite
                && nuevoTexto.matches("\\d*(\\.\\d{0,2})?")) {

            fb.replace(
                    offset,
                    longitud,
                    texto,
                    atributos
            );
        }
    }
}
    
    private static class FiltroTextoGeneral extends DocumentFilter {

    private final int limite;

    public FiltroTextoGeneral(int limite) {
        this.limite = limite;
    }

    @Override
    public void insertString(
            DocumentFilter.FilterBypass fb,
            int offset,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        if (texto == null) {
            return;
        }

        validarYReemplazar(
                fb,
                offset,
                0,
                texto,
                atributos
        );
    }

    @Override
    public void replace(
            DocumentFilter.FilterBypass fb,
            int offset,
            int longitud,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        if (texto == null) {
            texto = "";
        }

        validarYReemplazar(
                fb,
                offset,
                longitud,
                texto,
                atributos
        );
    }

    private void validarYReemplazar(
            DocumentFilter.FilterBypass fb,
            int offset,
            int longitud,
            String texto,
            AttributeSet atributos
    ) throws BadLocationException {

        String textoActual = fb.getDocument().getText(
                0,
                fb.getDocument().getLength()
        );

        String nuevoTexto
                = textoActual.substring(0, offset)
                + texto
                + textoActual.substring(offset + longitud);

        if (nuevoTexto.length() > limite) {
            return;
        }

        if (nuevoTexto.matches(
                "[a-zA-ZÁÉÍÓÚáéíóúÑñÜü0-9"
                + " .,:;()%º/\\-+\\n\\r]*"
        )) {

            fb.replace(
                    offset,
                    longitud,
                    texto,
                    atributos
            );
        }
    }
}
    
    private void configurarCalculoEdad() {

    txtEdad.setEditable(false);

    fechaN.addPropertyChangeListener(
            "date",
            evento -> calcularEdadAutomaticamente()
    );
}
    private void calcularEdadAutomaticamente() {

    if (fechaN.getDate() == null) {
        txtEdad.setText("");
        return;
    }

    LocalDate fechaNacimiento =
            fechaN.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

    LocalDate fechaActual = LocalDate.now();

    if (fechaNacimiento.isAfter(fechaActual)) {

        txtEdad.setText("");

        mostrarError(
                "La fecha de nacimiento no puede ser futura."
        );

        fechaN.setDate(null);
        return;
    }

    int edad = Period.between(
            fechaNacimiento,
            fechaActual
    ).getYears();

    txtEdad.setText(
            String.valueOf(edad)
    );
}
    
    private void configurarPestanaEgreso() {

    jTabbedPane1.setEnabledAt(
            2,
            false
    );
}
    public void habilitarPestanaEgreso(
        boolean habilitar
) {

    jTabbedPane1.setEnabledAt(
            2,
            habilitar
    );

    if (habilitar) {

        jTabbedPane1.setSelectedIndex(2);
    }
}
  
    private void configurarTablaVista() {

    contenedorHospital2.agregarColumna(
            "Campo"
    );

    contenedorHospital2.agregarColumna(
            "Información"
    );

    contenedorHospital2.permitirEdicion(false);
}
    
    public void mostrarDetallePaciente(
        Object[] datos
) {

    contenedorHospital2.limpiarTabla();

    if (datos == null) {
        return;
    }

    String[] campos = {
        "Paciente",
        "Género",
        "Fecha de nacimiento",
        "Edad",
        "Peso",
        "Fecha de ingreso",
        "Hora de ingreso",
        "Estado",
        "Doctor",
        "Especialidad",
        "Alergias",
        "Observaciones de registro",
        "Diagnóstico",
        "Salida",
        "Fecha de egreso",
        "Hora de egreso",
        "Observaciones de egreso"
    };

    for (int i = 0; i < campos.length; i++) {

        Object valor =
                datos[i] == null
                ? "Sin información"
                : datos[i];

        contenedorHospital2.agregarFila(
                campos[i],
                valor
        );
    }
}

    private void configurarSeleccionVista() {

    cbBuscarEgreso1.addActionListener(
            evento -> {

                IngresoPaciente paciente =
                        obtenerPacienteVistaSeleccionado();

                if (paciente == null) {
                    contenedorHospital2.limpiarTabla();
                    return;
                }

                if (controlador != null) {

                    controlador.mostrarPacienteVista(
                            paciente
                    );
                }
            }
    );
}
}

