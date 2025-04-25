package paint202510;


import java.awt.Component;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josearielpereyra
 */
public class BarraDeHerramientas extends JToolBar{
  
    protected JToggleButton btnBorrador;
    protected JToggleButton btnLapiz;
    protected JToggleButton btnLinea;
    protected JToggleButton btnRectangulo;
    protected JToggleButton btnOvalo;
    protected JToggleButton btnCirculo;

    public BarraDeHerramientas() {
        setOrientation(JToolBar.VERTICAL); // Esto orienta la barra en vertical
        btnLapiz = new JToggleButton("Lapiz");
        btnLinea = new JToggleButton("Linea");
        btnRectangulo = new JToggleButton("Rectangulo");
        btnOvalo = new JToggleButton("Óvalo");
        btnCirculo = new JToggleButton("Círculo");
        btnBorrador = new JToggleButton("Borrador");

        //formatear y agregar a la barra de herramientas los componentes
        formatearYAgregar(btnLapiz, "Dibujo Libre");
        formatearYAgregar(btnLinea, "Linea");
        formatearYAgregar(btnRectangulo, "Rectangulo");
        formatearYAgregar(btnOvalo, "Óvalo");
        formatearYAgregar(btnCirculo, "Círculo");
        formatearYAgregar(btnBorrador, "Borrador");

        ButtonGroup grupoBotones = new ButtonGroup();
        for(Component boton : this.getComponents() ){
            grupoBotones.add( (JToggleButton)boton);
        }
    }

    private void formatearYAgregar(JToggleButton boton, String texto) {
        boton.setFocusable(false);
        boton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setToolTipText(String.format("<html><strong>%s</strong><p>Selecione para dibujar %s</p></html>", texto, texto));
        add(boton);
    }

}
