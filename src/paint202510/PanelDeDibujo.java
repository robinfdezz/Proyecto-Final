/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint202510;

import figuras.DibujoLibre;
import figuras.Linea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import figuras.Figura;
import java.awt.Point;

/**
 *
 * @author josearielpereyra
 */
public class PanelDeDibujo extends JPanel {
    ArrayList<Figura> figuras = new ArrayList<>();
    Figura figuraActual;
    BarraDeHerramientas barraDeHerramientas = new BarraDeHerramientas();

    public PanelDeDibujo() {
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                figuraActual = obtenerFiguraADibujar(e.getPoint());
                figuras.add(figuraActual);
                repaint();
            }
        });
        addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                figuraActual.actualizar(e.getPoint());
                repaint();
            }
        });
    }

    private Figura obtenerFiguraADibujar(Point punto) {
        if(barraDeHerramientas.btnLinea.isSelected()) {
            return new Linea(punto);
        }
//        else if(barraDeHerramientas.btnRectangulo.isSelected()){
//            //agregar codigo para el rectangulo
//            return new Rectangulo(punto);
//        }
//        else if(barraDeHerramientas.btnBorrador.isSelected()){
//            //agregar codigo para el borrador
//            return new Borrador(punto);
//        }
        else {
            return new DibujoLibre(punto);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        for(Figura figura : figuras) {
            figura.dibujar(g);
        }
    }
    
}
