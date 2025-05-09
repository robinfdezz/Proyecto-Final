package figuras;

import java.awt.*;
import java.io.Serializable; // Asegurar que Figura implemente Serializable

/**
 * Clase base abstracta para todas las figuras dibujables.
 * Define propiedades comunes como color, estado de relleno y métodos abstractos para dibujar y actualizar.
 * Implementa Serializable para permitir la copia y el pegado.
 */
public abstract class Figura implements Serializable {
    private static final long serialVersionUID = 1L; // Añadir serialVersionUID

    protected Color colorDePrimerPlano = Color.BLACK; // El color del contorno o trazo de la figura.
    protected boolean relleno = false; // Indica si la figura debe ser rellenada.
    public Color colorDeRelleno; // El color usado para rellenar la figura.
    protected Point puntoInicial; // El punto de inicio, puede usarse como centro o esquina.
    protected Point puntoFinal;   // El punto final, puede usarse como esquina o punto de referencia para tamaño.

    /**
     * Método abstracto para dibujar la figura en el contexto gráfico dado.
     * Este método debe ser implementado por todas las subclases de figura concretas.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    public abstract void dibujar(Graphics g);

    /**
     * Método abstracto para actualizar la forma o posición de la figura basado en un nuevo punto.
     * Esto se usa típicamente durante el dibujo mientras se arrastra el ratón.
     * Este método debe ser implementado por todas las subclases de figura concretas.
     * @param puntoActual El punto actual para actualizar la figura.
     */
    public abstract void actualizar(Point puntoActual);

    // Métodos para establecer propiedades
    /**
     * Establece el color de primer plano (color del contorno o trazo) de la figura.
     * @param color El Color a establecer como color de primer plano.
     */
    public void setColorDePrimerPlano(Color color) {
        this.colorDePrimerPlano = color;
    }

    /**
     * Establece si la figura debe ser rellenada.
     * @param relleno true para habilitar el relleno, false para deshabilitar.
     */
    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }

    /**
     * Establece el color de relleno de la figura.
     * @param colorDeRelleno El Color a establecer como color de relleno.
     */
    public void setColorDeRelleno(Color colorDeRelleno) {
        this.colorDeRelleno = colorDeRelleno;
    }

    // Métodos getter
    /**
     * Obtiene el color de primer plano actual de la figura.
     * @return El Color de primer plano actual.
     */
    public Color getColorDePrimerLano() {
        return colorDePrimerPlano;
    }

    /**
     * Verifica si la figura está configurada para ser rellenada.
     * @return true si el relleno está habilitado, false en caso contrario.
     */
    public boolean isRelleno() {
        return relleno;
    }

    /**
     * Obtiene el color de relleno actual de la figura.
     * @return El Color de relleno actual.
     */
    public Color getColorDeRelleno() {
        return colorDeRelleno;
    }

    // --- INICIO: Métodos getter públicos añadidos para los puntos ---
    /**
     * Obtiene el punto inicial de la figura.
     * @return El punto inicial.
     */
    public Point getPuntoInicial() {
        return puntoInicial;
    }

    /**
     * Obtiene el punto final de la figura.
     * @return El punto final.
     */
    public Point getPuntoFinal() {
        return puntoFinal;
    }
    // --- FIN: Métodos getter públicos añadidos ---


    /**
     * Método abstracto para obtener un objeto FiguraData que represente los datos de esta figura.
     * Debe ser implementado por cada subclase de figura concreta.
     * @return Un objeto FiguraData con los datos de la figura.
     */
    public abstract FiguraData getFiguraData();

    /**
     * Método para verificar si un punto dado está contenido dentro del área de la figura.
     * Es útil para la selección de figuras.
     * Las subclases deben sobrescribir este método para proporcionar una implementación precisa.
     * @param p El punto a verificar.
     * @return true si el punto está dentro de la figura, false en caso contrario.
     */
    public boolean contains(Point p) {
        // Implementación por defecto: verificar si el punto está cerca de algún punto de control.
        final int TOLERANCIA = 5; // Pixeles de tolerancia para la selección por defecto

        if (puntoInicial != null && p.distance(puntoInicial) <= TOLERANCIA) {
            return true;
        }
        if (puntoFinal != null && p.distance(puntoFinal) <= TOLERANCIA) {
            return true;
        }
        // Nota: Figuras con muchos puntos (como DibujoLibre) o basadas en centro/radio
        // deberán sobrescribir esto para una verificación más precisa.
        return false;
    }

    /**
     * Obtiene el rectángulo delimitador (bounding box) de la figura.
     * Usado principalmente para la selección visual.
     * Las subclases deben sobrescribir este método para una implementación precisa.
     * @return Un objeto Rectangle que representa el área delimitador de la figura, o null si no aplica.
     */
    public Rectangle getBounds() {
        // Implementación por defecto: crear un rectángulo a partir de puntoInicial y puntoFinal
        if (puntoInicial != null && puntoFinal != null) {
            int x = Math.min(puntoInicial.x, puntoFinal.x);
            int y = Math.min(puntoInicial.y, puntoFinal.y);
            int width = Math.abs(puntoFinal.x - puntoInicial.x);
            int height = Math.abs(puntoFinal.y - puntoInicial.y);
            return new Rectangle(x, y, width, height);
        }
        return null; // Retornar null si los puntos no están definidos
    }

    /**
     * Traslada la posición de la figura por un desplazamiento dado.
     * Las subclases con estructuras de puntos más complejas (como listas de puntos)
     * deberían sobrescribir este método para mover todos los puntos relevantes.
     * La implementación por defecto traslada puntoInicial y puntoFinal.
     * @param offset El desplazamiento (dx, dy) a aplicar.
     */
    public void translate(Point offset) {
        if (puntoInicial != null) {
            puntoInicial.translate(offset.x, offset.y);
        }
        if (puntoFinal != null) {
            puntoFinal.translate(offset.x, offset.y);
        }
        // NOTA IMPORTANTE: Las subclases que usan otros puntos (como 'centro' o una lista de 'puntos')
        // DEBEN sobrescribir este método e incluir la lógica para trasladar ESOS puntos también.
    }
}