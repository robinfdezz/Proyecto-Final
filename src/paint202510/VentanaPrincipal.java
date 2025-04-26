package paint202510;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class VentanaPrincipal extends javax.swing.JFrame {
    private PanelDeDibujo lienzo;
    private BarraDeHerramientas barraDeHerramientas;

    public VentanaPrincipal() {
        initComponents();
        setSize(1000, 500);
        setLocationRelativeTo(null);

        // Inicializar primero la barra de herramientas
        barraDeHerramientas = new BarraDeHerramientas();

        // Luego crear el panel de dibujo pasando la barra de herramientas
        lienzo = new PanelDeDibujo(barraDeHerramientas);

        // Configurar el layout y añadir componentes
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(barraDeHerramientas, BorderLayout.EAST);
        getContentPane().add(lienzo, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}