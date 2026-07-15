package Componente2;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * @author Karen
 */
public class Reloj extends JPanel implements Serializable {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm:ss");
    private JLabel lblReloj;
    private Timer timer; 

    public Reloj() {
        initComponents();
        iniciarReloj();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(new Color(153, 204, 255));

        lblReloj = new JLabel("", SwingConstants.CENTER);
        lblReloj.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        
        add(lblReloj);
    }

    private void iniciarReloj() {
        lblReloj.setText(LocalDateTime.now().format(FORMATO));
       
        timer = new Timer(1000, e -> {
            lblReloj.setText(LocalDateTime.now().format(FORMATO));
        });
        timer.start(); 
    }

    
    public void detener() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    
    public void reiniciar() {
        if (timer != null) {
            lblReloj.setText(LocalDateTime.now().format(FORMATO));
            timer.start();
        }
    }

    public static String getHoraActual() {
        return LocalDateTime.now().format(FORMATO);
    } 
}