package figuras;

import java.awt.Graphics;
import java.awt.Point;

public class Triangulo extends Figura {
    private Point puntoInicial;
    private Point puntoFinal;

    public Triangulo(Point puntoInicial) {
        this.puntoInicial = puntoInicial;
        this.puntoFinal = puntoInicial;
    }

    @Override
    public void actualizar(Point puntoActual) {
        this.puntoFinal = puntoActual;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(colorDePrimerPlano);

        int[] xPoints;
        int[] yPoints;

        // Determinar el tipo de triángulo basado en la posición del puntoFinal
        if (puntoFinal.x > puntoInicial.x) {
            xPoints = new int[]{puntoInicial.x, puntoFinal.x, puntoInicial.x};
            yPoints = new int[]{puntoInicial.y, puntoFinal.y, puntoFinal.y};
        } else {
            xPoints = new int[]{puntoInicial.x, puntoFinal.x, puntoInicial.x};
            yPoints = new int[]{puntoInicial.y, puntoFinal.y, puntoInicial.y};
        }

        if (relleno) {
            g.fillPolygon(xPoints, yPoints, 3);
        } else {
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }
}