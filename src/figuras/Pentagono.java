package figuras;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 * Representa una forma de pentágono (polígono de 5 lados).
 * Puede ser dibujado con un contorno y rellenado con un color separado.
 */
public class Pentagono extends Figura {
    private int numeroLados = 5; // Constante para el número de lados del pentágono.

    /**
     * Constructor de un Pentágono con un punto central dado.
     * @param centro El punto central inicial del pentágono.
     */
    public Pentagono(Point centro) {
        super(centro, new Point(centro));
    }

    /**
     * Actualiza el punto que determina el tamaño y la orientación del pentágono.
     * @param puntoActual El punto actual.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual);
    }

    /**
     * Dibuja el pentágono en el contexto gráfico dado.
     * Calcula los vértices del pentágono basado en el centro y el punto actual.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        g.setColor(colorDePrimerPlano); // Establecer el color para el contorno.

        if (relleno) { // Verificar si el relleno está habilitado.
            if (colorDeRelleno != null) {
                g.setColor(colorDeRelleno); // Establecer el color de relleno.
            }
            int radio = (int) getPunto(0).distance(getPunto(1)); // Calcular el radio.
            int[] xPoints = new int[numeroLados]; // Arreglo para almacenar las coordenadas x de los vértices.
            int[] yPoints = new int[numeroLados]; // Arreglo para almacenar las coordenadas y de los vértices.

            // Calcular coordenadas de los vértices.
            for (int i = 0; i < numeroLados; i++) {
                double angle = Math.toRadians(-90 + i * 72); // Calcular el ángulo para cada vértice (72 grados por lado para un pentágono). Comienza apuntando hacia arriba (-90 grados).
                xPoints[i] = getPunto(0).x + (int) (radio * Math.cos(angle)); // Calcular coordenada x.
                yPoints[i] = getPunto(0).y + (int) (radio * Math.sin(angle)); // Calcular coordenada y.
            }

            Polygon pentagono = new Polygon(xPoints, yPoints, 5); // Crear un objeto Polygon para el pentágono.
            g.fillPolygon(pentagono); // Dibujar el pentágono relleno.

            // Dibujar el borde si es diferente del color de relleno.
            if (colorDeRelleno != colorDePrimerPlano) {
                g.setColor(colorDePrimerPlano); // Restablecer el color al color de borde.
                g.drawPolygon(pentagono); // Dibujar el contorno.
            }
        } else {
            // Si no hay relleno, solo dibujar el contorno.
            int radio = (int) getPunto(0).distance(getPunto(1));
            int[] xPoints = new int[5];
            int[] yPoints = new int[5];

            for (int i = 0; i < 5; i++) {
                double angle = Math.toRadians(-90 + i * 72);
                xPoints[i] = getPunto(0).x + (int) (radio * Math.cos(angle));
                yPoints[i] = getPunto(0).y + (int) (radio * Math.sin(angle));
            }

            Polygon pentagono = new Polygon(xPoints, yPoints, 5);
            g.drawPolygon(pentagono); // Solo dibujar el contorno.
        }
    }

    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Pentagono");
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

        int radio = (int) getPunto(0).distance(getPunto(1));
        ArrayList<Point> vertices = new ArrayList<>();
        for (int i = 0; i < numeroLados; i++) {
            double angle = Math.toRadians(-90 + i * 72);
            vertices.add(new Point(getPunto(0).x + (int) (radio * Math.cos(angle)), getPunto(0).y + (int) (radio * Math.sin(angle))));
        }

        // Ray casting algorithm to check if the point is inside the polygon
        boolean inside = false;
        for (int i = 0, j = vertices.size() - 1; i < vertices.size(); j = i++) {
            if ((vertices.get(i).y > p.y) != (vertices.get(j).y > p.y) &&
                    (p.x < (vertices.get(j).x - vertices.get(i).x) * (p.y - vertices.get(i).y) / (vertices.get(j).y - vertices.get(i).y) + vertices.get(i).x)) {
                inside = !inside;
            }
        }
        return inside;
    }

    @Override
    public java.awt.Rectangle getBounds() {
        if (puntos.size() < 2) return null;

        int radio = (int) getPunto(0).distance(getPunto(1));
        return new java.awt.Rectangle(getPunto(0).x - radio, getPunto(0).y - radio, radio * 2, radio * 2);
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