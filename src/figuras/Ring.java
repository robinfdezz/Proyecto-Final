package figuras;

import java.awt.*;

/**
 * Representa una forma de anillo (dos círculos concéntricos).
 * Puede ser dibujado con un contorno y rellenado con un color separado.
 * autor ebenezer (modificado)
 */
public class Ring extends Figura {

    /**
     * Constructor de un Ring con un punto central dado.
     * @param centro El punto central inicial del anillo.
     */
    public Ring(Point centro) {
        super(centro, new Point(centro)); // Usamos puntoInicial como centro
    }

    /**
     * Actualiza el punto que determina el radio exterior del anillo mientras se está dibujando.
     * @param puntoActual El punto actual.
     */
    @Override
    public void actualizar(Point puntoActual) {
        setPunto(1, puntoActual); // Usamos puntoFinal como punto en el radio exterior
    }

    /**
     * Dibuja el anillo en el contexto gráfico dado.
     * Calcula los radios basado en el centro y el punto en el radio exterior.
     * Dibuja dos óvalos (el exterior y el interior).
     * @param g El objeto Graphics sobre el que dibujar.
     */
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(this.grosor)); // Establecer el grosor

        int radioExterior = (int) getPunto(0).distance(getPunto(1)); // Calcular el radio exterior
        int radioInterior = (int) (radioExterior * 0.6); // Calcular el radio interior (ejemplo: 60% del exterior)

        // Calcular las coordenadas de la esquina superior izquierda y dimensiones
        // para los cuadros delimitadores de los óvalos exterior e interior.
        int xExt = getPunto(0).x - radioExterior;
        int yExt = getPunto(0).y - radioExterior;
        int diamExt = radioExterior * 2;

        int xInt = getPunto(0).x - radioInterior;
        int yInt = getPunto(0).y - radioInterior;
        int diamInt = radioInterior * 2;

        // Dibujar la forma
        if (relleno) { // Verificar si el relleno está habilitado.
            if (colorDeRelleno != null) {
                g2d.setColor(colorDeRelleno); // Establecer el color de relleno
            }
            // Dibujar el óvalo exterior relleno
            g2d.fillOval(xExt, yExt, diamExt, diamExt);

            // Para el "agujero", dibujamos un óvalo relleno con el color del fondo
            // o simplemente no dibujamos el óvalo interior si el fondo no es fijo.
            // Si quieres un agujero transparente, necesitarías un enfoque diferente (Shape, Area).
            // Para simplificar, usaremos el color de fondo del panel.
            if (getParent() != null) { // Asegurarse de que tiene un padre para obtener el color de fondo
                Color colorFondoPanel = getParent().getBackground();
                if (colorFondoPanel != null) {
                    g2d.setColor(colorFondoPanel);
                    g2d.fillOval(xInt, yInt, diamInt, diamInt);
                } else {
                    // Si no se puede obtener el color de fondo, usar blanco por defecto
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(xInt, yInt, diamInt, diamInt);
                }
            } else {
                // Si no hay padre, usar blanco por defecto
                g2d.setColor(Color.WHITE);
                g2d.fillOval(xInt, yInt, diamInt, diamInt);
            }

            // Dibujar el contorno exterior si el color de relleno es diferente al de borde
            if (colorDeRelleno != colorDePrimerPlano) {
                g2d.setColor(colorDePrimerPlano); // Restablecer el color al color de borde
                g2d.drawOval(xExt, yExt, diamExt, diamExt);
            }
            // Dibujar el contorno interior (opcional, pero común para anillos)
            g2d.setColor(colorDePrimerPlano); // Usar color de borde para el contorno interior
            g2d.drawOval(xInt, yInt, diamInt, diamInt);

        } else { // Si no hay relleno, solo dibujar los contornos.
            g2d.setColor(colorDePrimerPlano); // Usar el color de borde
            g2d.drawOval(xExt, yExt, diamExt, diamExt); // Contorno exterior
            g2d.drawOval(xInt, yInt, diamInt, diamInt); // Contorno interior
        }
    }

    private Graphics2D getParent() {
        return null;
    }

    /**
     * Obtiene un objeto FiguraData que represente los datos de este anillo.
     * @return Un objeto FiguraData con los datos del anillo.
     */
    @Override
    public FiguraData getFiguraData() {
        FiguraData data = new FiguraData("Ring"); // Tipo correcto
        data.setPuntoInicial(this.getPunto(0)); // Centro
        data.setPuntoFinal(this.getPunto(1));   // Punto en el radio exterior
        data.setColorDePrimerPlano(this.colorDePrimerPlano);
        data.setColorDeRelleno(this.colorDeRelleno);
        data.setEstaRelleno(this.relleno);
        // No tiene sentido para Ring setear puntosTrazo o tamanoBorrador
        data.setCentro(this.getPunto(0)); // Guardar el centro explícitamente también por claridad
        data.setGrosor(this.grosor);
        return data;
    }

    /**
     * Verifica si un punto dado está contenido dentro del área visible del anillo (entre los dos círculos).
     * @param p El punto a verificar.
     * @return true si el punto está dentro del anillo, false en caso contrario.
     */
    @Override
    public boolean contains(Point p) {
        if (getPunto(0) == null || getPunto(1) == null || p == null) {
            return false;
        }

        int radioExterior = (int) getPunto(0).distance(getPunto(1));
        int radioInterior = (int) (radioExterior * 0.6); // Debe coincidir con el cálculo en dibujar

        double distanciaAlCentro = getPunto(0).distance(p);

        // El punto está contenido si está dentro del círculo exterior Y fuera del círculo interior.
        return distanciaAlCentro <= radioExterior && distanciaAlCentro >= radioInterior;
    }

    /**
     * Obtiene el rectángulo delimitador (bounding box) del anillo (basado en el círculo exterior).
     *
     * @return Un objeto Rectangle que representa el área delimitador del anillo.
     */
    @Override
    public Rectangle getBounds() {
        if (puntos.size() < 2) return null;

        int radioExterior = (int) getPunto(0).distance(getPunto(1));
        int x = getPunto(0).x - radioExterior;
        int y = getPunto(0).y - radioExterior;
        int diametro = radioExterior * 2;
        return new Rectangle(x, y, diametro, diametro);
    }

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