package figuras;

import java.awt.*;
import java.util.ArrayList;

/**
 * Representa una forma de heptágono (polígono de 7 lados).
 * Puede ser dibujado con un contorno y rellenado con un color separado.
 * autor carol
 */
public class Heptagono extends Figura {
    private int numeroLados = 7; // Constante para el número de lados del heptágono.

    /**
     * Constructor de un Heptágono con un punto central dado.
     * @param centro El punto central inicial del heptágono.
     */
    public Heptagono(Point centro) {
        super(centro, new Point(centro));
    }

    /**
     * Actualiza el punto de referencia que determina el tamaño y la orientación del heptágono.
     * @param nuevoPunto El nuevo punto de referencia.
     */
    @Override
    public void actualizar(Point nuevoPunto) {
        setPunto(1, nuevoPunto);
    }

    /**
     * Dibuja el heptágono en el contexto gráfico dado.
     * Calcula los vértices del heptágono basado en el centro y el punto de referencia.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        // Establecer el color del borde
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(colorDePrimerPlano);
        g2.setStroke(new BasicStroke(this.grosor)); // Establecer el grosor

        // Calcular el radio
        int radio = (int) getPunto(0).distance(getPunto(1)); // Calcular el radio.
        int[] puntosX = new int[numeroLados]; // Arreglo para almacenar las coordenadas x de los vértices.
        int[] puntosY = new int[numeroLados]; // Arreglo para almacenar las coordenadas y de los vértices.

        // Calcular coordenadas de los vértices
        for (int i = 0; i < numeroLados; i++) {
            double angulo = Math.toRadians(-90 + i * (360.0 / numeroLados)); // Calcular el ángulo para cada vértice. Comienza a -90 grados (hacia arriba).
            puntosX[i] = getPunto(0).x + (int) (radio * Math.cos(angulo)); // Calcular coordenada x.
            puntosY[i] = getPunto(0).y + (int) (radio * Math.sin(angulo)); // Calcular coordenada y.
        }

        Polygon heptagonoForma = new Polygon(puntosX, puntosY, numeroLados); // Crear un objeto Polygon para el heptágono.

        // Manejar el relleno
        if (relleno) { // Verificar si el relleno está habilitado.
            if (colorDeRelleno != null) {
                g2.setColor(colorDeRelleno); // Establecer el color de relleno.
                g2.fillPolygon(heptagonoForma); // Dibujar el heptágono relleno.
            }

            // Dibujar el borde solo si es diferente del color de relleno.
            if (colorDeRelleno != colorDePrimerPlano) {
                g2.setColor(colorDePrimerPlano); // Restablecer el color al color de borde.
                g2.drawPolygon(heptagonoForma); // Dibujar el contorno.
            }
        } else {
            // Si no hay relleno, solo dibujar el contorno.
            g2.drawPolygon(heptagonoForma);
        }
    }

    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Heptagono");
        data.setPuntoInicial(this.getPunto(0));
        data.setPuntoFinal(this.getPunto(1));
        data.setColorDePrimerPlano(this.colorDePrimerPlano);
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        data.setCentro(this.getPunto(0)); // Guardar el centro explícitamente también por claridad
        data.setGrosor(this.grosor);  // Guardar el grosor
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
            double angle = Math.toRadians(-90 + i * (360.0 / numeroLados));
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
    public Rectangle getBounds() {
        if (puntos.size() < 2) return null;

        int radio = (int) getPunto(0).distance(getPunto(1));
        return new Rectangle(getPunto(0).x - radio, getPunto(0).y - radio, radio * 2, radio * 2);
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