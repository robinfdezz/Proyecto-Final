/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package figuras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;


/**
 *
 * @author Bryan
 */
public class Corazon  {
    private int x, y; // Posición del centro del corazón
    private int tamaño;
    private Color color;

    public Corazon(int x, int y, int tamaño, Color color) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
    }

    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);

        Path2D corazon = new Path2D.Double();
        double escala = tamaño / 100.0;

        corazon.moveTo(x, y);

        // Curvas superiores del corazón
        corazon.curveTo(x - 30 * escala, y - 30 * escala, x - 50 * escala, y + 20 * escala, x, y + 50 * escala);
        corazon.curveTo(x + 50 * escala, y + 20 * escala, x + 30 * escala, y - 30 * escala, x, y);

        g2.fill(corazon);
    }

    // Setters y otros métodos si se necesitan
    public void setColor(Color color) {
        this.color = color;
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }
    
}
