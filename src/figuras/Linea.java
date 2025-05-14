package figuras;

import java.awt.*;

/**
 * Representa un segmento de línea recta.
 * Puede ser dibujado con un color especificado.
 */
public class Linea extends Figura {

    /**
     * Constructor de una Línea con puntos inicial y final dados.
     * @param puntoInicial El punto de inicio de la línea.
     * @param puntoFinal El punto final de la línea.
     */
    public Linea(Point puntoInicial, Point puntoFinal) {
        super(puntoInicial, puntoFinal);
    }

    /**
     * Constructor de una Línea que comienza y termina en el mismo punto.
     * Esto se usa típicamente cuando se presiona el ratón por primera vez.
     * @param puntoInicial El punto inicial de la línea.
     */
    public Linea(Point puntoInicial) {
        this(puntoInicial, puntoInicial);
    }

    /**
     * Dibuja el segmento de línea en el contexto gráfico dado.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        g.setColor(colorDePrimerPlano); // Usar colorDePrimerPlano de la clase base.
        g.drawLine(getPunto(0).x, getPunto(0).y, getPunto(1).x, getPunto(1).y); // Dibujar la línea.
    }

    /**
     * Actualiza el punto final de la línea mientras se está dibujando.
     * @param puntoActual El punto final actual.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual);
    }

    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Linea");
        data.setPuntoInicial(this.getPunto(0));
        data.setPuntoFinal(this.getPunto(1));
        data.setColorDePrimerPlano(this.colorDePrimerPlano);
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
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
    public Rectangle getBounds() {
        if (puntos.size() < 2) return null;
        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);
        return new Rectangle(x, y, width, height);
    }
}