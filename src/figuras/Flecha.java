package figuras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class Flecha extends Figura {
    private Point inicio;
    private Point fin;
    Color colorDeRelleno = null;

    public Flecha(Point inicio) {
        this.inicio = inicio;
        this.fin = inicio; // Inicialmente, el punto final es igual al inicio
    }

    @Override
    public void actualizar(Point nuevoFin) {
        this.fin = nuevoFin; // Actualiza la posición del extremo de la flecha
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(colorDePrimerPlano); // Usa el color definido en la clase Figura

        // Dibuja la línea principal de la flecha
        g.drawLine(inicio.x, inicio.y, fin.x, fin.y);

        // Cálculo de coordenadas para la cabeza de la flecha
        int size = 10; // Tamaño de la cabeza de la flecha
        int dx = fin.x - inicio.x;
        int dy = fin.y - inicio.y;
        double angulo = Math.atan2(dy, dx); // Calcula el ángulo de la flecha

        // Coordenadas para los dos extremos de la cabeza de la flecha
        int x3 = fin.x - (int) (size * Math.cos(angulo - Math.PI / 6));
        int y3 = fin.y - (int) (size * Math.sin(angulo - Math.PI / 6));
        int x4 = fin.x - (int) (size * Math.cos(angulo + Math.PI / 6));
        int y4 = fin.y - (int) (size * Math.sin(angulo + Math.PI / 6));

        // Dibuja la cabeza de la flecha
        Polygon cabeza = new Polygon();
        cabeza.addPoint(fin.x, fin.y);
        cabeza.addPoint(x3, y3);
        cabeza.addPoint(x4, y4);

        if (relleno) {
            g.fillPolygon(cabeza); // Rellena la cabeza de la flecha si se requiere
        } else {
            g.drawPolygon(cabeza); // Dibuja solo el contorno
        }
    }
}