/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package figuras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;


/**
 *
 * @author Bryan
 */
public class Corazon extends Figura { // Extiende Figura
    private Point puntoInicial;
    private Point puntoFinal;

    public Corazon(Point puntoInicial) {
        this.puntoInicial = puntoInicial;
        this.puntoFinal = puntoInicial;
    }

    @Override
    public void actualizar(Point puntoActual) {
        this.puntoFinal = puntoActual;
    }

    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(colorDePrimerPlano); // Usa el color heredado de Figura

        // Calcular la posición y tamaño del corazón basado en puntoInicial y puntoFinal
        int x = Math.min(puntoInicial.x, puntoFinal.x);
        int y = Math.min(puntoInicial.y, puntoFinal.y);
        int width = Math.abs(puntoFinal.x - puntoInicial.x);
        int height = Math.abs(puntoFinal.y - puntoInicial.y);

        // Calcular el centro y un tamaño base para la escala
        int centroX = x + width / 2;
        int centroY = y + height / 2;
        int tamañoBase = Math.max(width, height);
        double escala = tamañoBase / 100.0; // Ajustar escala

        Path2D corazon = new Path2D.Double();
        // Ajustar los puntos del Path2D para que se centren alrededor de centroX, centroY
        // y escalen según tamañoBase

        corazon.moveTo(centroX, centroY + 20 * escala); // Ajustar punto inicial del path para centrar

        // Curvas superiores del corazón ajustadas al nuevo centro y escala
        corazon.curveTo(centroX - 30 * escala, centroY - 30 * escala + 20 * escala,
                centroX - 50 * escala, centroY + 20 * escala + 20 * escala,
                centroX, centroY + 50 * escala + 20 * escala);
        corazon.curveTo(centroX + 50 * escala, centroY + 20 * escala + 20 * escala,
                centroX + 30 * escala, centroY - 30 * escala + 20 * escala,
                centroX, centroY + 20 * escala);


        if (relleno) { // Usa la variable relleno heredada
            g2.fill(corazon);
        } else {
            g2.draw(corazon);
        }
    }
}