package figuras;

import java.awt.*;

/**
 * Representa una herramienta de borrador.
 * Dibuja círculos blancos para borrar partes del dibujo en el lienzo.
 * Borrador no es una figura seleccionable/copiable/pegable de la misma manera que otras.
 */
public class Borrador extends Figura {
    private int tamano = 20; // El tamaño del pincel del borrador.

    /**
     * Constructor de un Borrador en un punto dado.
     * Establece el color de primer plano a blanco para simular el borrado.
     * @param p El punto inicial del borrador.
     */
    public Borrador(Point p) {
        super(p); // Llama al constructor de Figura con el punto inicial
        this.colorDePrimerPlano = Color.WHITE; // El borrador dibuja en blanco para coincidir con el fondo.
        // Borrador no usa relleno ni color de relleno
        this.relleno = false;
        this.colorDeRelleno = null;
    }

    /**
     * Dibuja el trazo del borrador como un óvalo blanco relleno.
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        // El borrador siempre dibuja en blanco para simular borrado
        g.setColor(Color.WHITE);
        g.fillOval(getPunto(0).x - tamano / 2, getPunto(0).y - tamano / 2, tamano, tamano); // Dibujar un óvalo blanco relleno.
    }

    /**
     * Actualiza la posición del borrador.
     * @param p El nuevo punto central del borrador.
     */
    @Override
    public void actualizar(Point p) {
        setPunto(0, p); // Actualiza el centro del borrador
    }

    /**
     * Establece el tamaño del pincel del borrador.
     * @param tamano El nuevo tamaño del borrador.
     */
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    /**
     * Obtiene el tamaño del pincel del borrador.
     * @return El tamaño del borrador.
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * Obtiene un objeto FiguraData que represente los datos de este borrador.
     * Podríamos guardar su posición y tamaño si quisieramos copiar/pegar borradores.
     * Por ahora, retornaremos null ya que no se suelen copiar/pegar trazos de borrador.
     * @return Un objeto FiguraData con los datos del borrador, o null si no es copiable.
     */
    @Override
    public FiguraData getFiguraData() {
        // Si queremos copiar/pegar borradores, deberíamos crear FiguraData con los puntos y tamaño
        // FiguraData data = new FiguraData("Borrador");
        // data.setPuntoInicial(this.puntoInicial);
        // data.setTamanoBorrador(this.tamano);
        // return data;
        return null; // Por defecto, no se copia el borrador
    }

    /**
     * El borrador no es una figura seleccionable de la misma manera que otras formas.
     * Siempre retorna false para evitar la selección accidental.
     * @param p El punto a verificar.
     * @return Siempre false.
     */
    @Override
    public boolean contains(Point p) {
        return false; // Los trazos de borrador no son seleccionables normalmente.
    }

    /**
     * Obtiene el rectángulo delimitador del borrador (basado en su posición y tamaño).
     *
     * @return Un objeto Rectangle que representa el área del borrador.
     */
    @Override
    public java.awt.Rectangle getBounds() {
        if (getPunto(0) != null) {
            int x = getPunto(0).x - tamano / 2;
            int y = getPunto(0).y - tamano / 2;
            return new java.awt.Rectangle(x, y, tamano, tamano); // javafx.scene.shape.Rectangle
        }
        return null;
    }

    /**
     * Traslada la posición del borrador por un desplazamiento dado.
     * @param offset El desplazamiento (dx, dy) a aplicar.
     */
    @Override
    public void translate(Point offset) {
        if (getPunto(0) != null) {
            getPunto(0).translate(offset.x, offset.y);
        }
        // El borrador solo tiene un punto relevante (el centro).
    }
}