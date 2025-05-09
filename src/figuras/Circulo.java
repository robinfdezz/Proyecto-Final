package figuras;

import java.awt.*;
import java.awt.geom.Ellipse2D; // Importar Ellipse2D para contains

/**
 * Representa una forma de círculo.
 * Puede ser dibujado con un contorno y rellenado con un color separado.
 */
public class Circulo extends Figura {
    // Usamos puntoInicial como centro y puntoFinal como punto en el radio
    // protected Point puntoInicial; // Heredado de Figura, representa el centro
    // protected Point puntoFinal;   // Heredado de Figura, representa un punto en el radio

    /**
     * Constructor de un Círculo con un punto central dado.
     * @param centro El punto central inicial del círculo.
     */
    public Circulo(Point centro) {
        this.puntoInicial = centro; // Usamos puntoInicial como centro
        this.puntoFinal = new Point(centro); // Inicialmente, puntoFinal es el mismo que el centro
    }

    /**
     * Actualiza el punto que determina el radio del círculo mientras se está dibujando.
     * @param puntoActual El punto actual.
     */
    @Override
    public void actualizar(Point puntoActual) {
        this.puntoFinal = puntoActual; // Usamos puntoFinal como punto en el radio
    }

    /**
     * Dibuja el círculo en el contexto gráfico dado.
     * Dibuja un óvalo relleno si 'relleno' es true, y un contorno de óvalo.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int radio = (int) puntoInicial.distance(puntoFinal); // Calcular el radio
        int diametro = radio * 2;

        int x = puntoInicial.x - radio; // Calcular la coordenada 'x' superior izquierda del cuadro delimitador.
        int y = puntoInicial.y - radio; // Calcular la coordenada y superior izquierda del cuadro delimitador.

        if (relleno) { // Verificar si el relleno está habilitado.
            if (colorDeRelleno != null) {
                g2d.setColor(colorDeRelleno); // Establecer el color de relleno si se especifica.
            }
            g2d.fillOval(x, y, diametro, diametro); // Dibujar el óvalo relleno.

            // Dibujar el borde si el color de relleno es diferente al color de borde.
            if (colorDeRelleno != colorDePrimerPlano) { // Usar colorDePrimerLano heredado
                g2d.setColor(colorDePrimerPlano); // Restablecer el color al color de borde.
                g2d.drawOval(x, y, diametro, diametro); // Dibujar el contorno del óvalo.
            }
        } else {
            g2d.setColor(getColorDePrimerLano()); // Usar colorDePrimerLano heredado
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
        data.setPuntoInicial(this.puntoInicial); // Centro (según cómo lo uses)
        data.setPuntoFinal(this.puntoFinal); // Punto en el radio (para recrear el radio)
        data.setCentro(this.puntoInicial); // Opcional: guardar el centro explícitamente en 'centro' de FiguraData también.
        data.setColorDePrimerPlano(this.getColorDePrimerLano()); // Usar colorDePrimerLano heredado
        data.setColorDeRelleno(this.getColorDeRelleno());
        data.setEstaRelleno(this.isRelleno());
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
        if (puntoInicial == null || puntoFinal == null || p == null) {
            return false;
        }
        int radio = (int) puntoInicial.distance(puntoFinal);
        // Verificar si la distancia del punto al centro es menor o igual al radio
        return puntoInicial.distance(p) <= radio;
    }

    /**
     * Obtiene el rectángulo delimitador (bounding box) del círculo.
     * @return Un objeto Rectangle que representa el área delimitador del círculo.
     */
    @Override
    public Rectangle getBounds() {
        if (puntoInicial != null && puntoFinal != null) {
            int radio = (int) puntoInicial.distance(puntoFinal);
            int x = puntoInicial.x - radio;
            int y = puntoInicial.y - radio;
            int diametro = radio * 2;
            return new Rectangle(x, y, diametro, diametro);
        }
        return null;
    }

    /**
     * Traslada la posición del círculo por un desplazamiento dado.
     * Mueve tanto el punto central como el punto que define el radio.
     * @param offset El desplazamiento (dx, dy) a aplicar.
     */
    @Override
    public void translate(Point offset) {
        if (puntoInicial != null) { // Mover el centro
            puntoInicial.translate(offset.x, offset.y);
        }
        if (puntoFinal != null) { // Mover el punto que define el radio
            puntoFinal.translate(offset.x, offset.y);
        }
    }
}