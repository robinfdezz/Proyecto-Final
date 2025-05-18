package figuras;

import java.awt.*;

/**
 * Representa una forma de círculo.
 * Puede ser dibujado con un contorno y rellenado con un color separado.
 */
public class Circulo extends Figura {

    //private Color colorDePrimerPlano; // Corregido: colorDePrimerLano -> colorDePrimerPlano

    /**
     * Constructor de un Círculo con un punto central dado.
     * @param centro El punto central inicial del círculo.
     */
    public Circulo(Point centro) {
        super(centro, new Point(centro)); // puntoInicial = centro, puntoFinal = centro
    }

    public Color getColorDePrimerPlano() {
        return colorDePrimerPlano;
    }

    /**
     * Actualiza el punto que determina el radio del círculo mientras se está dibujando.
     * @param puntoActual El punto actual.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual); // Actualizar puntoFinal
    }

    /**
     * Dibuja el círculo en el contexto gráfico dado.
     * Dibuja un óvalo relleno si 'relleno' es true, y un contorno de óvalo.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int radio = (int) getPunto(0).distance(getPunto(1)); // Calcular el radio
        int diametro = radio * 2;

        int x = getPunto(0).x - radio; // Calcular la coordenada 'x' superior izquierda del cuadro delimitador.
        int y = getPunto(0).y - radio; // Calcular la coordenada y superior izquierda del cuadro delimitador.

        g2d.setStroke(new BasicStroke(this.grosor)); // Establecer el grosor

        if (relleno) { // Verificar si el relleno está habilitado.
            if (colorDeRelleno != null) {
                g2d.setColor(colorDeRelleno); // Establecer el color de relleno si se especifica.
            }
            g2d.fillOval(x, y, diametro, diametro); // Dibujar el óvalo relleno.

            // Dibujar el borde si el color de relleno es diferente al color de borde.
            if (colorDeRelleno != colorDePrimerPlano) {
                g2d.setColor(colorDePrimerPlano); // Restablecer el color al color de borde.
                g2d.drawOval(x, y, diametro, diametro); // Dibujar el contorno del óvalo.
            }
        } else {
            g2d.setColor(colorDePrimerPlano); // Usar colorDePrimerLano heredado
            g2d.drawOval(x, y, diametro, diametro); // Si no hay relleno, solo dibujar el contorno del óvalo.
        }
    }

    /**
     * Obtiene un objeto FiguraData que represente los datos de este círculo.
     * @return Un objeto FiguraData con los datos del círculo.
     */
    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Círculo"); // Tipo correcto
        data.setPuntoInicial(this.getPunto(0)); // Centro (según cómo lo uses)
        data.setPuntoFinal(this.getPunto(1)); // Punto en el radio (para recrear el radio)
        data.setCentro(this.getPunto(0)); // Opcional: guardar el centro explícitamente en 'centro' de FiguraData también.
        data.setColorDePrimerPlano(this.colorDePrimerPlano); // Usar colorDePrimerLano heredado
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        data.setGrosor(this.grosor);
        // No tiene sentido para Círculo setear puntosTrazo o tamanoBorrador
        return data;
    }

    /**
     * Verifica si un punto dado está contenido dentro del área del círculo.
     * @param p El punto a verificar.
     * @return true si el punto está dentro del círculo, false en caso contrario.
     */
    @Override
    public boolean contains(Point p) {
        if (getPunto(0) == null || getPunto(1) == null || p == null) {
            return false;
        }
        int radio = (int) getPunto(0).distance(getPunto(1));
        // Verificar si la distancia del punto al centro es menor o igual al radio
        return getPunto(0).distance(p) <= radio;
    }

    /**
     * Obtiene el rectángulo delimitador (bounding box) del círculo.
     *
     * @return Un objeto Rectangle que representa el área delimitador del círculo.
     */
    @Override
    public Rectangle getBounds() {
        if (puntos.size() < 2) return null;

        int radio = (int) getPunto(0).distance(getPunto(1));
        int x = getPunto(0).x - radio;
        int y = getPunto(0).y - radio;
        int diametro = radio * 2;
        return new Rectangle(x, y, diametro, diametro);
    }

    @Override
    public void translate(Point offset) {
        if (getPunto(0) != null) { // Mover el centro
            getPunto(0).translate(offset.x, offset.y);
        }
        if (getPunto(1) != null) { // Mover el punto que define el radio
            getPunto(1).translate(offset.x, offset.y);
        }
    }
}