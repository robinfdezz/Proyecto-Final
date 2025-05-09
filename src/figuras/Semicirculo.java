package figuras;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D; // Asegúrate de importar Path2D si se usa en contains
import java.awt.geom.Rectangle2D; // Importar Rectangle2D para contains

/**
 * Representa una forma de semicírculo que puede ser dibujada y rellenada.
 * Extiende la clase abstracta Figura.
 * @author ebenezer
 */
public class Semicirculo extends Figura {
    // puntoInicial y puntoFinal son heredados de Figura.
    // La clase usaba una variable local puntoActual para el dibujo.
    // private Point puntoInicial; // Heredado de Figura
    // private Point puntoActual; // Variable local usada en el código original

    /**
     * Constructor de un Semicirculo con un punto inicial.
     * @param puntoInicial El punto donde se inicia el dibujo del semicírculo.
     */
    public Semicirculo(Point puntoInicial) {
        this.puntoInicial = puntoInicial; // Inicializa el puntoInicial heredado
        this.puntoFinal = new Point(puntoInicial); // --- CORRECCIÓN: Inicializa el puntoFinal heredado ---
        // Los colores y el estado de relleno se heredarán de Figura.
    }

    /**
     * Actualiza el punto final que determina el tamaño y la forma del semicírculo.
     * --- CORRECCIÓN: Ahora actualiza el puntoFinal heredado en lugar de una variable local puntoActual ---
     * @param puntoActual El punto actual mientras se arrastra el ratón.
     */
    @Override
    public void actualizar(Point puntoActual) {
        this.puntoFinal = puntoActual; // --- CORRECCIÓN: Actualiza el puntoFinal heredado ---
    }

    /**
     * Dibuja la forma del semicírculo en el contexto gráfico dado.
     * Utiliza Arc2D para definir la forma.
     * La dibuja o rellena según el estado 'relleno'.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getColorDePrimerLano()); // Usa el color heredado de Figura

        // Calcular la posición y tamaño del semicírculo basado en puntoInicial y puntoFinal heredados.
        int x = Math.min(puntoInicial.x, puntoFinal.x);
        int y = Math.min(puntoInicial.y, puntoFinal.y);
        int width = Math.abs(puntoFinal.x - puntoInicial.x);
        int height = Math.abs(puntoFinal.y - puntoInicial.y);

        // Asegurarse de que haya un tamaño mínimo
        if (width <= 0 || height <= 0) {
            return; // No dibujar si el tamaño es cero o negativo
        }

        // Determinar la dirección del semicírculo basado en la dirección del arrastre vertical
        // Si puntoFinal.y es menor que puntoInicial.y, dibujamos la mitad superior (ángulo de inicio 180)
        // Si puntoFinal.y es mayor o igual que puntoInicial.y, dibujamos la mitad inferior (ángulo de inicio 0)
        int startAngle = (puntoFinal.y < puntoInicial.y) ? 180 : 0;
        int extentAngle = 180; // Extensión del arco en grados (180 para medio círculo)
        int arcType = Arc2D.PIE; // Tipo de arco (PIE para incluir los radios al centro)


        // Crear la forma del arco. El constructor Arc2D.Double(x, y, w, h, start, extent, type)
        // define un arco dentro de un rectángulo delimitador (x, y, w, h).
        Arc2D.Double semicirculoForma = new Arc2D.Double(x, y, width, height, startAngle, extentAngle, arcType);


        if (isRelleno()) { // Si relleno es true (usar getter heredado)
            if (getColorDeRelleno() != null) { // Usar colorDeRelleno (getter heredado)
                g2.setColor(getColorDeRelleno());
                g2.fill(semicirculoForma); // Rellenar la forma del semicírculo
            }

            // Dibujar el borde si el color de relleno es diferente al de borde
            if (getColorDeRelleno() != getColorDePrimerLano()) { // Usar getters heredados
                g2.setColor(getColorDePrimerLano()); // Restablecer el color al color de borde
                g2.draw(semicirculoForma); // Dibujar el contorno del semicírculo
            }

        } else { // Si no hay relleno, solo dibujar el contorno
            g2.setColor(getColorDePrimerLano()); // Usar colorDePrimerLano (getter heredado)
            g2.draw(semicirculoForma); // Dibujar el contorno del semicírculo
        }
    }

    /**
     * Obtiene un objeto FiguraData que represente los datos de este semicírculo.
     * @return Un objeto FiguraData con los datos del semicírculo.
     */
    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Semicirculo"); // Tipo correcto
        data.setPuntoInicial(this.puntoInicial); // Esquina superior izquierda del bounding box inicial
        data.setPuntoFinal(this.puntoFinal); // Esquina opuesta del bounding box
        data.setColorDePrimerPlano(this.getColorDePrimerLano()); // Usar colorDePrimerLano heredado
        data.setColorDeRelleno(this.getColorDeRelleno());
        data.setEstaRelleno(this.isRelleno());
        // No tiene sentido para Semicirculo setear centro, puntosTrazo o tamanoBorrador
        return data;
    }

    /**
     * Verifica si un punto dado está contenido dentro del área del semicírculo.
     * @param p El punto a verificar.
     * @return true si el punto está dentro del semicírculo, false en caso contrario.
     */
    @Override
    public boolean contains(Point p) {
        // --- CORRECCIÓN: Usar puntoInicial y puntoFinal heredados, y el Path2D/Arc2D para la verificación precisa. ---
        // Reimplementar la lógica de contains usando la forma geométrica (Arc2D o Path2D)
        // similar a cómo se dibuja, para una detección precisa.
        // El código original copiado de Rectangulo NO es correcto para un semicírculo.

        if (puntoInicial == null || puntoFinal == null || p == null) {
            return false;
        }

        int x = Math.min(puntoInicial.x, puntoFinal.x);
        int y = Math.min(puntoInicial.y, puntoFinal.y);
        int width = Math.abs(puntoFinal.x - puntoInicial.x);
        int height = Math.abs(puntoFinal.y - puntoInicial.y);

        if (width <= 0 || height <= 0) {
            return false;
        }

        int startAngle = (puntoFinal.y < puntoInicial.y) ? 180 : 0;
        int extentAngle = 180;
        int arcType = Arc2D.PIE;

        Arc2D.Double semicirculoForma = new Arc2D.Double(x, y, width, height, startAngle, extentAngle, arcType);

        return semicirculoForma.contains(p); // Verificar si el punto está dentro de la forma del semicírculo.
        // --- FIN CORRECCIÓN en contains ---
    }

    /**
     * Obtiene el rectángulo delimitador (bounding box) del semicírculo.
     * @return Un objeto Rectangle que representa el área delimitador del semicírculo.
     */
    @Override
    public Rectangle getBounds() {
        if (puntoInicial != null && puntoFinal != null) {
            int x = Math.min(puntoInicial.x, puntoFinal.x);
            int y = Math.min(puntoInicial.y, puntoFinal.y);
            int width = Math.abs(puntoFinal.x - puntoInicial.x);
            int height = Math.abs(puntoFinal.y - puntoInicial.y);
            return new Rectangle(x, y, width, height);
        }
        return null;
    }

    /**
     * Traslada la posición del semicírculo por un desplazamiento dado.
     * Mueve puntoInicial y puntoFinal.
     * @param offset El desplazamiento (dx, dy) a aplicar.
     */
    @Override
    public void translate(Point offset) {
        if (puntoInicial != null) {
            puntoInicial.translate(offset.x, offset.y);
        }
        if (puntoFinal != null) {
            puntoFinal.translate(offset.x, offset.y);
        }
    }
}