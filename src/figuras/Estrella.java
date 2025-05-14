package figuras;

//import javafx.scene.shape.Rectangle;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 * Representa una forma de estrella.
 * Puede ser dibujado con un contorno y rellenado con un color separado.
 * autor carol
 */
public class Estrella extends Figura {
    private int puntas = 5; // Número de puntas de la estrella.

    /**
     * Constructor de una Estrella con un punto central dado.
     * @param centro El punto central inicial de la estrella.
     */
    public Estrella(Point centro) {
        super(centro, new Point(centro));
    }

    /**
     * Actualiza el punto de referencia que determina el tamaño y la orientación de la estrella.
     * @param nuevoPunto El nuevo punto de referencia.
     */
    @Override
    public void actualizar(Point nuevoPunto) {
        setPunto(1, nuevoPunto);
    }

    /**
     * Dibuja la estrella en el contexto gráfico dado.
     * Calcula los vértices de la estrella basado en el centro y el punto de referencia.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        // Establecer el color del borde
        g.setColor(colorDePrimerPlano); // Establecer el color para el contorno.

        // Calcular el radio
        int radio = (int) getPunto(0).distance(getPunto(1)); // Calcular el radio exterior.
        int[] puntosX = new int[puntas * 2]; // Arreglo para almacenar las coordenadas x de los vértices.
        int[] puntosY = new int[puntas * 2]; // Arreglo para almacenar las coordenadas y de los vértices.

        // Calcular coordenadas de los vértices
        for (int i = 0; i < puntas * 2; i++) {
            double angulo = Math.toRadians(-90 + i * (360.0 / (puntas * 2))); // Calcular el ángulo para cada vértice. Comienza a -90 grados (hacia arriba).
            int radioActual = (i % 2 == 0) ? radio : (int) (radio * 0.5); // Alternar entre radio exterior e interior.
            puntosX[i] = getPunto(0).x + (int) (radioActual * Math.cos(angulo)); // Calcular coordenada x.
            puntosY[i] = getPunto(0).y + (int) (radioActual * Math.sin(angulo)); // Calcular coordenada y.
        }

        Polygon estrellaForma = new Polygon(puntosX, puntosY, puntas * 2); // Crear un objeto Polygon para la estrella.

        // Manejar el relleno
        if (relleno) { // Verificar si el relleno está habilitado.
            if (colorDeRelleno != null) {
                g.setColor(colorDeRelleno); // Establecer el color de relleno.
                g.fillPolygon(estrellaForma); // Dibujar la estrella rellena.
            }

            // Dibujar el borde solo si es diferente del color de relleno.
            if (colorDeRelleno != colorDePrimerPlano) {
                g.setColor(colorDePrimerPlano); // Restablecer el color al color de borde.
                g.drawPolygon(estrellaForma); // Dibujar el contorno.
            }
        } else {
            // Si no hay relleno, solo dibujar el contorno.
            g.drawPolygon(estrellaForma);
        }
    }

    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Estrella");
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
        for (int i = 0; i < puntas * 2; i++) {
            double angle = Math.toRadians(-90 + i * (360.0 / (puntas * 2)));
            int radioActual = (i % 2 == 0) ? radio : (int) (radio * 0.5);
            vertices.add(new Point(getPunto(0).x + (int) (radioActual * Math.cos(angle)), getPunto(0).y + (int) (radioActual * Math.sin(angle))));
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