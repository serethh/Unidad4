/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componente1;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ContenedorHospital extends JPanel {

    private final JTable tabla;
    private final DefaultTableModel modelo;
    private final JScrollPane scroll;

    private final Map<String, Color> colores = new HashMap<>();
    private final Map<Integer, String> estadosFilas = new HashMap<>();

    // NUEVO: filas bloqueadas
    private final Map<Integer, String> filasBloqueadas = new HashMap<>();
    private boolean editable = false;
    private Color colorFila = Color.WHITE;
    private Color colorFilaAlterna = new Color(245,245,245);


    public ContenedorHospital() {
        setLayout(new BorderLayout());
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }
        };


        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionBackground(new Color(0,120,215));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(220,220,220));
        tabla.setDefaultRenderer(
                Object.class,
                new RendererColores()
        );


        // NUEVO:
        // Evita selección gráfica de filas bloqueadas
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if(fila >= 0 && filasBloqueadas.containsKey(fila)){
                tabla.clearSelection();
            }});

        scroll = new JScrollPane(tabla);
        add(scroll,BorderLayout.CENTER);

        personalizarEncabezado(
                new Color(30,144,255),
                Color.WHITE,
                new Font("Segoe UI",Font.BOLD,13),
                32
        );
    }

    public void agregarColumna(String nombre){
        modelo.addColumn(nombre);
    }

    public void agregarFila(Object... datos){
        modelo.addRow(datos);
    }



    public void eliminarFila(int fila){
        if(fila>=0 && fila<modelo.getRowCount()){
            modelo.removeRow(fila);
            estadosFilas.remove(fila);
            // NUEVO
            filasBloqueadas.remove(fila);
        }
    }

    public void limpiarTabla(){
        modelo.setRowCount(0);
        estadosFilas.clear();
        // NUEVO
        filasBloqueadas.clear();
    }

    public void setValor(int fila,int columna,Object valor){
        modelo.setValueAt(valor,fila,columna);
    }

    public Object getValor(int fila,int columna){
        return modelo.getValueAt(fila,columna);
    }

    public JTable getTabla(){
        return tabla;
    }

    public DefaultTableModel getModelo(){
        return modelo;
    }

    public int getFilaSeleccionada(){
        return tabla.getSelectedRow();
    }

    public void permitirEdicion(boolean editar){
        editable = editar;
    }

    public void setAlturaFilas(int altura){
        tabla.setRowHeight(altura);
    }

    public void setFuenteTabla(Font fuente){
        tabla.setFont(fuente);
    }

    public void setColorSeleccion(Color fondo, Color texto){
        tabla.setSelectionBackground(fondo);
        tabla.setSelectionForeground(texto);
    }

    public void setColoresAlternados(Color normal, Color alterna){
        colorFila = normal;
        colorFilaAlterna = alterna;
        repaint();
    }

    public void centrarTexto(){
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);

        for(int i=0;i<tabla.getColumnCount();i++){
            tabla.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(r);
        }
    }

    public void personalizarEncabezado(Color fondo,Color texto,Font fuente,int altura){
        JTableHeader h = tabla.getTableHeader();
        h.setBackground(fondo);
        h.setForeground(texto);
        h.setFont(fuente);
        h.setPreferredSize(new Dimension(0,altura));
    }

    public void registrarColor(String nombre, Color color){
        colores.put(nombre.toUpperCase(),color);
    }

    public void colorearFila(int fila,String estado){
        estadosFilas.put(fila,estado.toUpperCase());
        repaint();
    }

    public void quitarColorFila(int fila){
        estadosFilas.remove(fila);
        repaint();
    }

    // ==============================
    // NUEVAS FUNCIONES DE BLOQUEO
    // ==============================

    // Bloquear fila usando una palabra
    public void bloquearFila(int fila,String palabra){
        if(fila >= 0 && fila < modelo.getRowCount()){
            filasBloqueadas.put(
                    fila,
                    palabra.toUpperCase()
            );
            tabla.clearSelection();
            repaint();
        }
    }



    // Habilitar fila usando la palabra
    public void habilitarFila(int fila,String palabra){
        if(fila >= 0 && fila < modelo.getRowCount()){
            String estado = filasBloqueadas.get(fila);
            if(estado != null &&
                    estado.equalsIgnoreCase(palabra)){
                filasBloqueadas.remove(fila);
            }
            repaint();
        }
    }



    // Saber si una fila está bloqueada
    public boolean filaBloqueada(int fila){
        return filasBloqueadas.containsKey(fila);
    }

    private class RendererColores extends DefaultTableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focus,
                int row,
                int column){

            Component c =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            selected,
                            focus,
                            row,
                            column
                    );

            // ==========================
            // FILA BLOQUEADA
            // ==========================

            if(filasBloqueadas.containsKey(row)){
                c.setBackground(Color.LIGHT_GRAY);
                c.setForeground(Color.DARK_GRAY);
                return c;
            }
            if(!selected){
                String estado =
                        estadosFilas.get(row);
                if(estado != null &&
                        colores.containsKey(estado)){
                    c.setBackground(
                            colores.get(estado)
                    );
                }else{
                    c.setBackground(
                            (row%2==0)
                            ? colorFila
                            : colorFilaAlterna
                    );
                }
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }
}
