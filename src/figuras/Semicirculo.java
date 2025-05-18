package figuras;

import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Representa una forma de semicírculo que puede ser dibujada y rellenada.
 * Extiende la clase abstracta Figura.
 */
public class Semicirculo extends Figura {

    /**
     * Constructor de un Semicirculo con un punto inicial.
     * 
     * @param puntoInicial El punto donde se inicia el dibujo del semicírculo.
     */
    public Semicirculo(Point puntoInicial) {
        super(puntoInicial, new Point(puntoInicial));
    }

    /**
     * Actualiza el punto final que determina el tamaño y la forma del semicírculo.
     * --- CORRECCIÓN: Ahora actualiza el puntoFinal heredado en lugar de una
     * variable local puntoActual ---
     * 
     * @param puntoActual El punto actual mientras se arrastra el ratón.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual);
    }

    /**
     * Dibuja la forma del semicírculo en el contexto gráfico dado.
     * Utiliza Arc2D para definir la forma.
     * La dibuja o rellena según el estado 'relleno'.
     * 
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(colorDePrimerPlano);
        g2.setStroke(new BasicStroke(this.grosor)); // Establecer el grosor

        // Calcular la posición y tamaño del semicírculo basado en puntoInicial y
        // puntoFinal heredados.
        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);

        // Asegurarse de que haya un tamaño mínimo
        if (width <= 0 || height <= 0) {
            return; // No dibujar si el tamaño es cero o negativo
        }

        // Determinar la dirección del semicírculo basado en la dirección del arrastre
        // vertical
        // Si puntoFinal.y es menor que puntoInicial.y, dibujamos la mitad superior
        // (ángulo de inicio 180)
        // Si puntoFinal.y es mayor o igual que puntoInicial.y, dibujamos la mitad
        // inferior (ángulo de inicio 0)
        int startAngle = (getPunto(1).y < getPunto(0).y) ? 180 : 0;
        int extentAngle = 180; // Extensión del arco en grados (180 para medio círculo)
        int arcType = Arc2D.PIE; // Tipo de arco (PIE para incluir los radios al centro)

        // Crear la forma del arco. El constructor Arc2D.Double(x, y, w, h, start,
        // extent, type)
        // define un arco dentro de un rectángulo delimitador (x, y, w, h).
        Arc2D.Double semicirculoForma = new Arc2D.Double(x, y, width, height, startAngle, extentAngle, arcType);

        if (relleno) { // Si relleno es true (usar getter heredado)
            if (colorDeRelleno != null) { // Usar colorDeRelleno (getter heredado)
                g2.setColor(colorDeRelleno);
                g2.fill(semicirculoForma); // Rellenar la forma del semicírculo
            }

            // Dibujar el borde si el color de relleno es diferente al de borde
            if (colorDeRelleno != colorDePrimerPlano && colorDeRelleno != null) { // Usar getters heredados
                g2.setColor(colorDePrimerPlano); // Restablecer el color al color de borde
                g2.draw(semicirculoForma); // Dibujar el contorno del semicírculo
            }

        } else { // Si no hay relleno, solo dibujar el contorno
            g2.setColor(colorDePrimerPlano); // Usar colorDePrimerLano (getter heredado)
            g2.draw(semicirculoForma); // Dibujar el contorno del semicírculo
        }
    }

    /**
     * Obtiene un objeto FiguraData que represente los datos de este semicírculo.
     * 
     * @return Un objeto FiguraData con los datos del semicírculo.
     */
    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Semicirculo"); // Tipo correcto
        data.setPuntoInicial(this.getPunto(0)); // Esquina superior izquierda del bounding box inicial
        data.setPuntoFinal(this.getPunto(1)); // Esquina opuesta del bounding box
        data.setColorDePrimerPlano(this.colorDePrimerPlano); // Usar colorDePrimerLano heredado
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        data.setGrosor(this.grosor); // Guardar el grosor
        // No tiene sentido para Semicirculo setear centro, puntosTrazo o tamanoBorrador
        return data;
    }

    /**
     * Verifica si un punto dado está contenido dentro del área del semicírculo.
     * 
     * @param p El punto a verificar.
     * @return true si el punto está dentro del semicírculo, false en caso
     *         contrario.
     */
    @Override
    public boolean contains(Point p) {
        // --- CORRECCIÓN: Usar puntoInicial y puntoFinal heredados, y el Path2D/Arc2D
        // para la verificación precisa. ---
        // Reimplementar la lógica de contains usando la forma geométrica (Arc2D o
        // Path2D)
        // similar a cómo se dibuja, para una detección precisa.
        // El código original copiado de Rectangulo NO es correcto para un semicírculo.

        if (getPunto(0) == null || getPunto(1) == null || p == null) {
            return false;
        }

        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);

        if (width <= 0 || height <= 0) {
            return false;
        }

        int startAngle = (getPunto(1).y < getPunto(0).y) ? 180 : 0;
        int extentAngle = 180;
        int arcType = Arc2D.PIE;

        Arc2D.Double semicirculoForma = new Arc2D.Double(x, y, width, height, startAngle, extentAngle, arcType);

        return semicirculoForma.contains(p); // Verificar si el punto está dentro de la forma del semicírculo.
        // --- FIN CORRECCIÓN en contains ---
    }

    /**
     * Obtiene el rectángulo delimitador (bounding box) del semicírculo.
     *
     * @return Un objeto Rectangle que representa el área delimitador del
     *         semicírculo.
     */
    @Override
    public Rectangle getBounds() {
        if (getPunto(0) == null || getPunto(1) == null)
            return null;

        int x = Math.min(getPunto(0).x, getPunto(1).x);
        int y = Math.min(getPunto(0).y, getPunto(1).y);
        int width = Math.abs(getPunto(1).x - getPunto(0).x);
        int height = Math.abs(getPunto(1).y - getPunto(0).y);

        return new Rectangle(x, y, width, height);
    }

    /**
     * Traslada la posición del semicírculo por un desplazamiento dado.
     * Mueve puntoInicial y puntoFinal.
     * 
     * @param offset El desplazamiento (dx, dy) a aplicar.
     */
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