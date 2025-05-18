package figuras;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Figura implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Color colorDePrimerPlano = Color.BLACK;
    protected boolean relleno = false;
    public Color colorDeRelleno;
    protected List<Point> puntos = new ArrayList<>();  // Usar una lista de puntos
    protected int grosor;


    // Constructores
    public Figura() {
        this.grosor = 1;
    } // Constructor por defecto

    public Figura(Point... puntosIniciales) {
        this.grosor = 1;
        puntos.addAll(Arrays.asList(puntosIniciales));
    }

    // Métodos para acceder y modificar puntos
    public Point getPunto(int index) {
        if (index >= 0 && index < puntos.size()) {
            return puntos.get(index);
        }
        return null; // O lanzar una excepción, dependiendo de cómo quieras manejar errores
    }

    public void setPunto(int index, Point p) {
        if (index >= 0 && index < puntos.size()) {
            puntos.set(index, p);
        }
        // Puedes agregar un 'else' aquí para manejar índices fuera de rango
    }

    // Métodos abstractos
    public abstract void dibujar(Graphics g);

    public abstract void actualizar(Point puntoActual);

    // Métodos para establecer propiedades (color, relleno) - Sin cambios

    // Métodos getter para color y relleno - Sin cambios

    // Métodos getBounds() y translate() - A MODIFICAR
    public Rectangle getBounds() {
        if (puntos.isEmpty()) {
            return null;
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point p : puntos) {
            minX = Math.min(minX, p.x);
            minY = Math.min(minY, p.y);
            maxX = Math.max(maxX, p.x);
            maxY = Math.max(maxY, p.y);
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public void translate(Point offset) {
        for (Point p : puntos) {
            p.translate(offset.x, offset.y);
        }
    }

    public abstract FiguraData getFiguraData();

    public boolean contains(Point p) {
        // Implementación por defecto, puede necesitar ser sobrescrita
        if (puntos.isEmpty()) {
            return false;
        }

        final int TOLERANCIA = 5;
        for (Point punto : puntos) {
            if (punto != null && p.distance(punto) <= TOLERANCIA) {
                return true;
            }
        }
        return false;
    }

public void setColorDePrimerPlano(Color colorBordeActual) {
    this.colorDePrimerPlano = colorBordeActual;
}

    // public boolean isRelleno() {
    //     return false;
    // }

    public boolean isRelleno() {
    return this.relleno;
}

public void setColorDeRelleno(Color colorRellenoActual) {
    this.colorDeRelleno = colorRellenoActual;
}

public void setRelleno(boolean relleno) {
    this.relleno = relleno;
}

    public void setGrosor(int grosor) {
        this.grosor = grosor;
    }

    public int getGrosor() {
        return this.grosor;
    }

}