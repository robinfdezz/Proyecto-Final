package figuras;

import java.awt.*;
//import javafx.scene.shape.Rectangle;
import java.awt.geom.Path2D;

/**
 * Representa una forma de flecha.
 * Puede ser dibujado con una línea y una punta de flecha.
 */
public class Flecha extends Figura {

    private static final double ARROW_ANGLE = Math.PI / 6; // Ángulo de la punta de flecha (30 grados)
    private static final int ARROW_SIZE = 10; // Tamaño de la punta de flecha

    /**
     * Constructor de una Flecha que comienza en un punto dado.
     * @param inicio El punto de inicio inicial de la flecha.
     */
    public Flecha(Point inicio) {
        super(inicio, new Point(inicio));
    }

    /**
     * Actualiza el punto final de la flecha mientras se está dibujando.
     * @param nuevoFin El nuevo punto final.
     */
    @Override
    public void actualizar(Point nuevoFin) {
        setPunto(1, nuevoFin);
    }

    /**
     * Dibuja la flecha (línea y punta de flecha) en el contexto gráfico dado.
     * La punta de la flecha se dibuja como un polígono relleno.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(colorDePrimerPlano);
        g2d.setStroke(new BasicStroke(this.grosor)); // Establecer el grosor

        double dx = getPunto(1).x - getPunto(0).x;
        double dy = getPunto(1).y - getPunto(0).y;
        double angle = Math.atan2(dy, dx);

        // Dibujar la línea
        g2d.drawLine(getPunto(0).x, getPunto(0).y, getPunto(1).x, getPunto(1).y);

        // Calcular los puntos de la punta de la flecha
        Path2D.Double arrowHead = new Path2D.Double();
        arrowHead.moveTo(getPunto(1).x, getPunto(1).y);
        arrowHead.lineTo(getPunto(1).x - ARROW_SIZE * Math.cos(angle - ARROW_ANGLE), getPunto(1).y - ARROW_SIZE * Math.sin(angle - ARROW_ANGLE));
        arrowHead.lineTo(getPunto(1).x - ARROW_SIZE * Math.cos(angle + ARROW_ANGLE), getPunto(1).y - ARROW_SIZE * Math.sin(angle + ARROW_ANGLE));
        arrowHead.closePath();

        // Rellenar la punta de la flecha
        if (relleno && colorDeRelleno != null) {
            g2d.setColor(colorDeRelleno);
            g2d.fill(arrowHead);
            g2d.setColor(colorDePrimerPlano); // Restaurar el color para el contorno
        }
        g2d.draw(arrowHead); // Dibujar el contorno de la punta
    }

    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Flecha");
        data.setPuntoInicial(this.getPunto(0));
        data.setPuntoFinal(this.getPunto(1));
        data.setColorDePrimerPlano(this.colorDePrimerPlano);
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        data.setGrosor(this.grosor);  // Guardar el grosor
        return data;
    }

    @Override
    public boolean contains(Point p) {
        // Implementación básica: verifica si el punto está cerca de alguno de los extremos
        final int TOLERANCIA = 5;
        if (getPunto(0) != null && p.distance(getPunto(0)) <= TOLERANCIA) {
            return true;
        }
        if (getPunto(1) != null && p.distance(getPunto(1)) <= TOLERANCIA) {
            return true;
        }
        return false;
    }

    @Override
    public java.awt.Rectangle getBounds() {
        if (puntos.size() < 2) return null;
        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);
        return new java.awt.Rectangle(x, y, width, height);
    }
}