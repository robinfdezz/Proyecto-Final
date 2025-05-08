/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package figuras;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author ebenezer
 */
public class Semicirculo extends Figura {
     private Point centro;
    private Point puntoActual;

    public Semicirculo(Point centro) {
        this.centro = centro;
        this.puntoActual = centro;
    }

    @Override
    public void actualizar(Point puntoActual) {
        this.puntoActual = puntoActual;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(colorDePrimerPlano);

        int radio = (int) centro.distance(puntoActual);
        int x = centro.x - radio;
        int y = centro.y - radio;
        int diametro = radio * 2;

        // Dibuja un semicírculo (parte superior)
        if (relleno) {
            g.fillArc(x, y, diametro, diametro, 0, 180);
        } else {
            g.drawArc(x, y, diametro, diametro, 0, 180);
        }
    }
}
