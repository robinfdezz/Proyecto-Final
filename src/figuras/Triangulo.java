package figuras;

import java.awt.*;
import java.awt.geom.Path2D;


/**
 * Representa una forma de triángulo.
 * Puede ser dibujado con un contorno y rellenado con un color separado.
 * Nota: La implementación actual dibuja tipos específicos de triángulos rectángulos.
 */
public class Triangulo extends Figura {

    /**
     * Constructor de un Triángulo con un punto inicial dado.
     * @param puntoInicial El punto inicial del triángulo.
     */
    public Triangulo(Point puntoInicial) {
        super(puntoInicial, new Point(puntoInicial));
    }

    /**
     * Actualiza el punto final que determina la forma del triángulo.
     * @param puntoActual El punto actual.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual);
    }

    /**
     * Dibuja el triángulo en el contexto gráfico dado.
     * Calcula los vértices basado en los puntos inicial y final.
     * Dibuja un polígono relleno si 'relleno' es true, y un contorno de polígono.
     * @param g El objeto Graphics sobre el que dibujar.
     */
@Override
public void dibujar(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(colorDePrimerPlano);
    g2.setStroke(new BasicStroke(this.grosor));

    Point p0 = getPunto(0); // Punto base A
    Point p1 = getPunto(1); // Punto base B

    double dx = p1.x - p0.x;
    double dy = p1.y - p0.y;

    // Longitud del lado
    double length = Math.sqrt(dx * dx + dy * dy);

    // Ángulo de la base (en radianes)
    double angle = Math.atan2(dy, dx);

    // Ángulo para rotar 60 grados (en radianes)
    double angle60 = Math.toRadians(60);

    // Punto C (rotación 60 grados desde A hacia B)
    double x3 = p0.x + length * Math.cos(angle + angle60);
    double y3 = p0.y + length * Math.sin(angle + angle60);

    int[] xPoints = {(int) p0.x, (int) p1.x, (int) x3};
    int[] yPoints = {(int) p0.y, (int) p1.y, (int) y3};

    if (relleno) {
        if (colorDeRelleno != null) {
            g2.setColor(colorDeRelleno);
        }
        g2.fillPolygon(xPoints, yPoints, 3);

        if (colorDeRelleno != colorDePrimerPlano && colorDeRelleno != null) {
            g2.setColor(colorDePrimerPlano);
            g2.drawPolygon(xPoints, yPoints, 3);
        }
    } else {
        g2.drawPolygon(xPoints, yPoints, 3);
    }
}


    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Triangulo");
        data.setPuntoInicial(this.getPunto(0));
        data.setPuntoFinal(this.getPunto(1)); // Para rectángulos, puntoInicial y puntoFinal definen el tamaño/posición
        data.setColorDePrimerPlano(this.colorDePrimerPlano);
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        data.setGrosor(this.grosor);  // Guardar el grosor
        // No tiene sentido para Rectangulo setear centro, puntosTrazo o tamanoBorrador
        return data;
    }

    // Implementación de contains para Rectángulo (más precisa)
   @Override
public boolean contains(Point p) {
    if (puntos.size() < 2 || getPunto(0) == null || getPunto(1) == null) return false;

    Point p0 = getPunto(0);
    Point p1 = getPunto(1);

    double dx = p1.x - p0.x;
    double dy = p1.y - p0.y;
    double side = Math.sqrt(dx * dx + dy * dy);
    double angle = Math.atan2(dy, dx);
    double angle60 = Math.toRadians(60);

    int x3 = (int) (p0.x + side * Math.cos(angle + angle60));
    int y3 = (int) (p0.y + side * Math.sin(angle + angle60));

    Path2D.Double path = new Path2D.Double();
    path.moveTo(p0.x, p0.y);
    path.lineTo(p1.x, p1.y);
    path.lineTo(x3, y3);
    path.closePath();

    return path.contains(p.x, p.y);
}

@Override
public Rectangle getBounds() {
    if (puntos.size() < 2 || getPunto(0) == null || getPunto(1) == null) return null;

    Point p0 = getPunto(0);
    Point p1 = getPunto(1);

    double dx = p1.x - p0.x;
    double dy = p1.y - p0.y;
    double side = Math.sqrt(dx * dx + dy * dy);  // Calculate side once
    double angle = Math.atan2(dy, dx);
    double angle60 = Math.toRadians(60);

    // Calculate x3 and y3 only once
    int x3 = (int) (p0.x + side * Math.cos(angle + angle60));
    int y3 = (int) (p0.y + side * Math.sin(angle + angle60));

    // Calculate min and max values efficiently
    int minX = Math.min(p0.x, Math.min(p1.x, x3));
    int minY = Math.min(p0.y, Math.min(p1.y, y3));
    int maxX = Math.max(p0.x, Math.max(p1.x, x3));
    int maxY = Math.max(p0.y, Math.max(p1.y, y3));

    return new Rectangle(minX, minY, maxX - minX, maxY - minY);
}
}