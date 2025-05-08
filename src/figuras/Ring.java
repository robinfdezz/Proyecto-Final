/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package figuras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;



/**
 *
 * @author ebenezer
 */
    public class Ring extends Figura {
    private Point centro;
    private Point puntoActual;

    public Ring(Point centro) {
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

        int radioExterior = (int) centro.distance(puntoActual);
        int radioInterior = (int) (radioExterior * 0.6); 

        int xExt = centro.x - radioExterior;
        int yExt = centro.y - radioExterior;
        int diamExt = radioExterior * 2;

        int xInt = centro.x - radioInterior;
        int yInt = centro.y - radioInterior;
        int diamInt = radioInterior * 2;

        if (relleno) {
            g.fillOval(xExt, yExt, diamExt, diamExt);
            //g.setColor(colordefondo); // Borrar el centro con color de fondo
            g.fillOval(xInt, yInt, diamInt, diamInt);
        } else {
            g.drawOval(xExt, yExt, diamExt, diamExt);
            g.drawOval(xInt, yInt, diamInt, diamInt);
        }
    }
}

   
