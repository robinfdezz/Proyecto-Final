package figuras;

import java.awt.*;
import java.util.ArrayList;

/**
 * Representa una herramienta de dibujo a mano alzada.
 * Captura una serie de puntos mientras se arrastra el ratón y dibuja líneas entre ellos.
 */
public class DibujoLibre extends Figura {
    // ArrayList<Point> puntos = new ArrayList<>(); // Lista para almacenar los puntos del dibujo a mano alzada.

    /**
     * Constructor de un DibujoLibre (Dibujo Libre) que comienza en un punto dado.
     * @param puntoInicial El punto inicial del dibujo a mano alzada.
     */
    public DibujoLibre(Point puntoInicial) {
        super(puntoInicial); // Llama al constructor de Figura con el punto inicial
    }

    /**
     * Dibuja el trazo a mano alzada conectando los puntos en la lista con líneas.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        if (puntos.size() < 2) {
            // Si solo hay un punto o ninguno, no hay línea que dibujar
            if (puntos.size() == 1 && getPunto(0) != null) {
                // Dibujar un punto si solo hay un punto
                g.setColor(colorDePrimerPlano);
                g.fillRect(getPunto(0).x, getPunto(0).y, 1, 1);
            }
            return;
        }

        g.setColor(colorDePrimerPlano); // Establecer el color para el dibujo.

        // Dibujar líneas entre puntos consecutivos.
        for (int i = 1; i < puntos.size(); i++) {
            Point p1 = getPunto(i - 1); // Obtener el punto anterior.
            Point p2 = getPunto(i); // Obtener el punto actual.
            if (p1 != null && p2 != null) {
                g.drawLine(p1.x, p1.y, p2.x, p2.y); // Dibujar un segmento de línea entre los dos puntos.
            }
        }
    }

    /**
     * Añade un nuevo punto al dibujo a mano alzada.
     * @param puntoActual El punto actual a añadir.
     */
    @Override
    public void actualizar(Point puntoActual) {
        puntos.add(puntoActual); // Añadir el punto actual a la lista de puntos.
    }

    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("DibujoLibre");
        data.setPuntoInicial(this.getPunto(0));
        data.setPuntoFinal(this.getPunto(puntos.isEmpty() ? 0 : puntos.size() - 1));
        data.setColorDePrimerPlano(this.colorDePrimerPlano);
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        data.setPuntosTrazo(new ArrayList<>(this.puntos)); // Clonar la lista para evitar modificaciones externas
        return data;
    }

    @Override
    public boolean contains(Point p) {
        if (puntos == null || puntos.isEmpty()) {
            return false;
        }

        final int TOLERANCIA = 5; // Define la cercanía para considerar que el punto "toca" la línea

        // Verificar si el punto está cerca de algún punto del trazo
        for (Point punto : puntos) {
            if (punto != null && punto.distance(p) <= TOLERANCIA) {
                return true;
            }
        }

        // Verificar si el punto está cerca de algún segmento de línea
        for (int i = 1; i < puntos.size(); i++) {
            Point p1 = getPunto(i - 1);
            Point p2 = getPunto(i);
            if (p1 != null && p2 != null && distanciaPuntoALinea(p, p1, p2) <= TOLERANCIA) {
                return true;
            }
        }

        return false;
    }

    // Método auxiliar para calcular la distancia de un punto a un segmento de línea
    private double distanciaPuntoALinea(Point p, Point p1, Point p2) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;

        if (dx == 0 && dy == 0) {
            // Es un punto, no un segmento
            return p1.distance(p);
        }

        double t = ((p.x - p1.x) * dx + (p.y - p1.y) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t)); // Limitar t al rango [0, 1]

        Point closestPoint = new Point((int) (p1.x + t * dx), (int) (p1.y + t * dy));
        return p.distance(closestPoint);
    }

    @Override
    public Rectangle getBounds() {
        if (puntos == null || puntos.isEmpty()) {
            return null;
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point p : puntos) {
            if (p != null) {
                minX = Math.min(minX, p.x);
                minY = Math.min(minY, p.y);
                maxX = Math.max(maxX, p.x);
                maxY = Math.max(maxY, p.y);
            }
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public void translate(Point offset) {
        if (puntos != null) {
            for (Point p : puntos) {
                if (p != null) {
                    p.translate(offset.x, offset.y);
                }
            }
        }
    }
}