/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package figuras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon; // Usaremos Polygon para simplificar
import java.awt.geom.Path2D;


/**
 *
 * @author Bryan
 */
public class Trapecio extends Figura { // Extiende Figura
    private Point puntoInicial;
    private Point puntoFinal;

    public Trapecio(Point puntoInicial) {
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
        g2.setColor(colorDePrimerPlano); // Usa el color heredado

        int x1 = puntoInicial.x;
        int y1 = puntoInicial.y;
        int x2 = puntoFinal.x;
        int y2 = puntoFinal.y;

        // Implementación básica de un trapecio:
        // La base inferior se define por puntoInicial y puntoFinal.x, puntoInicial.y
        // La base superior es paralela a la inferior y centrada, con un ancho reducido.
        int baseInferiorAncho = Math.abs(x2 - x1);
        int altura = Math.abs(y2 - y1);
        int baseSuperiorAncho = (int) (baseInferiorAncho * 0.6); // Base superior más pequeña
        int desplazamientoSuperiorX = (baseInferiorAncho - baseSuperiorAncho) / 2;

        int[] xPoints;
        int[] yPoints;

        if (y2 > y1) { // Si arrastramos hacia abajo
            xPoints = new int[]{
                    x1, // Esquina inferior izquierda
                    x2, // Esquina inferior derecha
                    x2 - desplazamientoSuperiorX, // Esquina superior derecha
                    x1 + desplazamientoSuperiorX // Esquina superior izquierda
            };
            yPoints = new int[]{
                    y1, // Esquina inferior izquierda
                    y1, // Esquina inferior derecha
                    y2, // Esquina superior derecha
                    y2  // Esquina superior izquierda
            };
        } else { // Si arrastramos hacia arriba
            xPoints = new int[]{
                    x1, // Esquina superior izquierda
                    x2, // Esquina superior derecha
                    x2 - desplazamientoSuperiorX, // Esquina inferior derecha
                    x1 + desplazamientoSuperiorX // Esquina inferior izquierda
            };
            yPoints = new int[]{
                    y1, // Esquina superior izquierda
                    y1, // Esquina superior derecha
                    y2, // Esquina inferior derecha
                    y2  // Esquina inferior izquierda
            };
        }


        Polygon trapecioForma = new Polygon(xPoints, yPoints, 4);

        if (relleno) { // Usa la variable relleno heredada
            g2.fill(trapecioForma);
        } else {
            g2.draw(trapecioForma);
        }
    }
}