package figuras;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Representa una forma de rombo.
 * Puede ser dibujado con un contorno y rellenado con un color separado.
 */
public class Rombo extends Figura {

    /**
     * Constructor de un Rombo con un punto central dado.
     * @param centro El punto central inicial del rombo.
     */
    public Rombo(Point centro) {
        super(centro, new Point(centro));
    }

    /**
     * Actualiza el punto que determina el tamaño y la forma del rombo.
     * @param puntoActual El punto actual.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual);
    }

    /**
     * Dibuja el rombo en el contexto gráfico dado.
     * Calcula los vértices del rombo basado en el centro y el punto actual.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        g.setColor(colorDePrimerPlano); // Establecer el color para el contorno.

        if (relleno) { // Verificar si el relleno está habilitado.
            if (colorDeRelleno != null) {
                g.setColor(colorDeRelleno); // Establecer el color de relleno.
            }
            int dx = getPunto(1).x - getPunto(0).x; // Calcular la distancia horizontal desde el centro.
            int dy = getPunto(1).y - getPunto(0).y; // Calcular la distancia vertical desde el centro.

            int[] xPoints = { // Coordenadas x de los vértices del rombo.
                    getPunto(0).x,
                    getPunto(0).x + dx,
                    getPunto(0).x,
                    getPunto(0).x - dx
            };

            int[] yPoints = { // Coordenadas y de los vértices del rombo.
                    getPunto(0).y - dy,
                    getPunto(0).y,
                    getPunto(0).y + dy,
                    getPunto(0).y
            };

            Polygon rombo = new Polygon(xPoints, yPoints, 4); // Crear un objeto Polygon para el rombo.
            g.fillPolygon(rombo); // Dibujar el rombo relleno.

            // Dibujar el borde si es diferente del color de relleno.
            if (colorDeRelleno != colorDePrimerPlano) {
                g.setColor(colorDePrimerPlano); // Restablecer el color al color de borde.
                g.drawPolygon(rombo); // Dibujar el contorno.
            }
        } else {
            // Si no hay relleno, solo dibujar el contorno.
            int dx = getPunto(1).x - getPunto(0).x;
            int dy = getPunto(1).y - getPunto(0).y;

            int[] xPoints = {
                    getPunto(0).x,
                    getPunto(0).x + dx,
                    getPunto(0).x,
                    getPunto(0).x - dx
            };

            int[] yPoints = {
                    getPunto(0).y - dy,
                    getPunto(0).y,
                    getPunto(0).y + dy,
                    getPunto(0).y
            };

            Polygon rombo = new Polygon(xPoints, yPoints, 4);
            g.drawPolygon(rombo); // Solo dibujar el contorno.
        }
    }

    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Rombo");
        data.setPuntoInicial(this.getPunto(0));
        data.setPuntoFinal(this.getPunto(1));
        data.setColorDePrimerPlano(this.colorDePrimerPlano);
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        data.setCentro(this.getPunto(0)); // Guardar el centro explícitamente también por claridad
        return data;
    }

    @Override
    public boolean contains(Point p) {
        if (getPunto(0) == null || getPunto(1) == null) {
            return false;
        }

        // Calcular los vértices del rombo basado en el centro y el puntoActual.
        int dx = getPunto(1).x - getPunto(0).x;
        int dy = getPunto(1).y - getPunto(0).y;

        Point[] vertices = new Point[]{
                getPunto(0),
                new Point(getPunto(0).x + dx, getPunto(0).y),
                new Point(getPunto(0).x, getPunto(0).y + dy),
                new Point(getPunto(0).x - dx, getPunto(0).y)
        };

        // Ray casting algorithm to check if the point is inside the polygon
        boolean inside = false;
        for (int i = 0, j = vertices.length - 1; i < vertices.length; j = i++) {
            if ((vertices[i].y > p.y) != (vertices[j].y > p.y) &&
                    (p.x < (vertices[j].x - vertices[i].x) * (p.y - vertices[i].y) / (vertices[j].y - vertices[i].y) + vertices[i].x)) {
                inside = !inside;
            }
        }
        return inside;
    }

    @Override
    public java.awt.Rectangle getBounds() {
        if (puntos.size() < 2) return null;

        int dx = Math.abs(getPunto(1).x - getPunto(0).x);
        int dy = Math.abs(getPunto(1).y - getPunto(0).y);
        return new java.awt.Rectangle(getPunto(0).x - dx, getPunto(0).y - dy, dx * 2, dy * 2);
    }

    @Override
    public void translate(Point offset) {
        if (getPunto(0) != null) {
            getPunto(0).translate(offset.x, offset.y);
        }
        if (getPunto(1) != null) {
            getPunto(1).translate(offset.x, offset.y);
        }
    }
}