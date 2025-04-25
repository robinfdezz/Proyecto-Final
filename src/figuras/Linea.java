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
 * @author josearielpereyra
 */
public class Linea extends Figura{
    private Point puntoInicial;
    private Point puntoFinal;
    private Color color;


    public Linea(Point puntoInicial, Point puntoFinal) {
        this.puntoInicial = puntoInicial;
        this.puntoFinal = puntoFinal;
        this.color = Color.BLACK;
    }

    public Linea(Point puntoInicial) {
        this(puntoInicial, puntoInicial);
    }
    
    @Override
    public void dibujar(Graphics g) {
        g.setColor(color);
        g.drawLine(puntoInicial.x, puntoInicial.y, puntoFinal.x, puntoFinal.y);
    }

    @Override
    public void actualizar(Point puntoActual) {
        puntoFinal = puntoActual;
    }
}
