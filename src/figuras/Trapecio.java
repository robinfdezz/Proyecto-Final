package figuras;

import java.awt.*;

/**
 * Representa una forma de trapecio que puede ser dibujada y rellenada.
 * Extiende la clase abstracta Figura.
 */
public class Trapecio extends Figura {

    /**
     * Constructor de un Trapecio con un punto inicial.
     * @param puntoInicial El punto donde se inicia el dibujo del trapecio.
     */
    public Trapecio(Point puntoInicial) {
        super(puntoInicial, new Point(puntoInicial));
    }

    /**
     * Actualiza el punto actual que determina el tamaño y la forma del trapecio.
     * @param puntoActual El punto actual mientras se arrastra el ratón.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual);
    }

    /**
     * Dibuja la forma del trapecio en el contexto gráfico dado.
     * Calcula los vértices del trapecio basado en puntoInicial y puntoActual
     * y lo dibuja o rellena según el estado 'relleno'.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(this.grosor)); // Establecer el grosor

        // Calcular las coordenadas del cuadro delimitador
        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);

        // Calcular los puntos del trapecio.
        // Este es un ejemplo simple de un trapecio isósceles.
        // Puedes ajustar esta lógica para crear diferentes tipos de trapecios.
        int baseSuperiorAncho = (int) (width * 0.6); // La base superior es el 60% de la inferior
        int diferenciaAncho = (width - baseSuperiorAncho) / 2;

        int[] xPoints = {
                x + diferenciaAncho,           // Esquina superior izquierda
                x + width - diferenciaAncho,   // Esquina superior derecha
                x + width,                     // Esquina inferior derecha
                x                              // Esquina inferior izquierda
        };

        int[] yPoints = {
                y,         // Esquina superior izquierda
                y,         // Esquina superior derecha
                y + height,// Esquina inferior derecha
                y + height // Esquina inferior izquierda
        };

        Polygon trapecioForma = new Polygon(xPoints, yPoints, 4); // Crear un objeto Polygon para el trapecio.

        if (relleno) { // Verificar si el relleno está habilitado (propiedad heredada)
            if (colorDeRelleno != null) { // Usar colorDeRelleno (propiedad heredada)
                g2.setColor(colorDeRelleno);
                g2.fillPolygon(trapecioForma); // Rellenar la forma del trapecio
            }

            // Dibujar el borde si el color de relleno es diferente al color de borde
            if (colorDeRelleno != colorDePrimerPlano && colorDeRelleno != null) {
                g2.setColor(colorDePrimerPlano); // Usar colorDePrimerPlano (propiedad heredada)
                g2.drawPolygon(trapecioForma); // Dibujar el contorno del trapecio
            }

        } else { // Si no hay relleno, solo dibujar el contorno
            g2.setColor(colorDePrimerPlano); // Usar colorDePrimerPlano (propiedad heredada)
            g2.drawPolygon(trapecioForma); // Dibujar el contorno del trapecio
        }
    }

    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Trapecio");
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
        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);
        // Crear un rectángulo Java y verificar si contiene el punto
        return new java.awt.Rectangle(x, y, width, height).contains(p);
    }

    @Override
    public Rectangle getBounds() {
        if (puntos.size() < 2) return null;

        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);
        return new Rectangle(x, y, width, height);
    }
}