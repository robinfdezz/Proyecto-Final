package figuras;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Representa una forma de corazón que puede ser dibujada y rellenada.
 * Extiende la clase abstracta Figura.
 */
public class Corazon extends Figura {

    /**
     * Constructor de un Corazon con un punto inicial.
     * @param puntoInicial El punto donde se inicia el dibujo del corazón.
     */
    public Corazon(Point puntoInicial) {
        super(puntoInicial, new Point(puntoInicial));
    }

    /**
     * Actualiza el punto final que determina el tamaño y la forma del corazón.
     * @param puntoActual El punto actual mientras se arrastra el ratón.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual);
    }

    /**
     * Dibuja la forma del corazón en el contexto gráfico dado.
     * Utiliza Path2D para definir la forma (dos lóbulos superiores curvos y una punta en V).
     * La dibuja o rellena según el estado 'relleno'.
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

        // Asegurar que el tamaño sea positivo para evitar errores de dibujo
        if (width <= 0 || height <= 0) {
            return;
        }

        // Calcular puntos clave basados en el cuadro delimitador
        int hendiduraX = x + width / 2;
        int hendiduraY = y;

        int puntaX = x + width / 2;
        int puntaY = y + height;

        // Puntos de control para los lóbulos superiores
        // Ajusta estos valores para refinar la forma.
        int controlLobeXOffset = width / 2;
        int controlLobeYOffset = height / 3;

        // Puntos donde los lóbulos se encuentran con las líneas de la punta
        int vBaseY = y + height * 2 / 3;
        int leftVBaseX = x + width / 6;
        int rightVBaseX = x + width * 5 / 6;


        Path2D corazon = new Path2D.Double();

        // Mover al punto superior central (la hendidura)
        corazon.moveTo(hendiduraX, hendiduraY);

        // Dibujar el lóbulo izquierdo
        // Curva desde la hendidura hasta la base izquierda de la V
        corazon.curveTo(hendiduraX - controlLobeXOffset * 0.8, hendiduraY - controlLobeYOffset * 0.5,
                x, y + height / 3,
                leftVBaseX, vBaseY);


        // Línea desde la base izquierda de la V hasta la punta inferior
        corazon.lineTo(puntaX, puntaY);

        // Línea desde la punta inferior hasta la base derecha de la V
        corazon.lineTo(rightVBaseX, vBaseY);

        // Dibujar el lóbulo derecho
        // Curva desde la base derecha de la V hasta la hendidura
        corazon.curveTo(x + width, y + height / 3,
                hendiduraX + controlLobeXOffset * 0.8, hendiduraY - controlLobeYOffset * 0.5,
                hendiduraX, hendiduraY);


        // Cerrar la ruta (opcional, pero asegura un polígono cerrado para el relleno)
        corazon.closePath();


        if (relleno) { // Verificar si el relleno está habilitado (propiedad heredada)
            if (colorDeRelleno != null) { // Usar colorDeRelleno (propiedad heredada)
                g2.setColor(colorDeRelleno);
                g2.fill(corazon); // Rellenar la forma del corazón
            }

            // Dibujar el borde si el color de relleno es diferente al color de borde o si no hay color de relleno.
            if (colorDeRelleno != colorDePrimerPlano) { // Usar colorDePrimerLano heredado
                g2.setColor(colorDePrimerPlano); // Usar colorDePrimerLano (propiedad heredada)
                g2.draw(corazon); // Dibujar el contorno del corazón
            }

        } else { // Si no hay relleno, solo dibujar el contorno
            g2.setColor(colorDePrimerPlano); // Usar colorDePrimerLano (propiedad heredada)
            g2.draw(corazon); // Dibujar el contorno del corazón
        }
    }

    /**
     * Obtiene un objeto FiguraData que represente los datos de este corazón.
     * @return Un objeto FiguraData con los datos del corazón.
     */
    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Corazón"); // Tipo correcto
        data.setPuntoInicial(this.getPunto(0)); // Esquina superior izquierda del bounding box
        data.setPuntoFinal(this.getPunto(1)); // Esquina opuesta del bounding box
        data.setColorDePrimerPlano(this.colorDePrimerPlano); // Usar colorDePrimerLano heredado
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        data.setGrosor(this.grosor);  // Guardar el grosor
        // No tiene sentido para Corazón setear centro, puntosTrazo o tamanoBorrador
        return data;
    }

    /**
     * Verifica si un punto dado está contenido dentro del área del corazón.
     * @param p El punto a verificar.
     * @return true si el punto está dentro del corazón, false en caso contrario.
     */
    @Override
    public boolean contains(Point p) {
        if (getPunto(0) == null || getPunto(1) == null || p == null) {
            return false;
        }
        // Para formas complejas como el corazón, usar Path2D.contains() es más preciso.
        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);

        if (width <= 0 || height <= 0) {
            return false;
        }

        int hendiduraX = x + width / 2;
        int hendiduraY = y;

        int puntaX = x + width / 2;
        int puntaY = y + height;

        int controlLobeXOffset = width / 2;
        int controlLobeYOffset = height / 3;

        int vBaseY = y + height * 2 / 3;
        int leftVBaseX = x + width / 6;
        int rightVBaseX = x + width * 5 / 6;

        Path2D corazonForma = new Path2D.Double();
        corazonForma.moveTo(hendiduraX, hendiduraY);
        corazonForma.curveTo(hendiduraX - controlLobeXOffset * 0.8, hendiduraY - controlLobeYOffset * 0.5,
                x, y + height / 3,
                leftVBaseX, vBaseY);
        corazonForma.lineTo(puntaX, puntaY);
        corazonForma.lineTo(rightVBaseX, vBaseY);
        corazonForma.curveTo(x + width, y + height / 3,
                hendiduraX + controlLobeXOffset * 0.8, hendiduraY - controlLobeYOffset * 0.5,
                hendiduraX, hendiduraY);
        corazonForma.closePath();

        return corazonForma.contains(p);
    }

    /**
     * Obtiene el rectángulo delimitador (bounding box) del corazón.
     *
     * @return Un objeto Rectangle que representa el área delimitador del corazón.
     */
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