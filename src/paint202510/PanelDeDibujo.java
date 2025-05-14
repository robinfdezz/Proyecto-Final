package paint202510;

import figuras.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections; // Importar Collections para invertir la lista
import java.util.List;
import java.util.Stack;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Clase PanelDeDibujo - Representa el lienzo de dibujo donde se dibujan las figuras.
 * Maneja los eventos del ratón para crear y modificar figuras y gestiona la lista de figuras.
 * Incluye funcionalidad para Undo, Redo, y Clear. Ahora soporta las figuras Corazon y Trapecio.
 * Se ha añadido lógica básica para seleccionar figuras.
 */
public class PanelDeDibujo extends JPanel implements HerramientaSeleccionadaListener{
    private Figura figuraActual; // La figura que se está dibujando o modificando actualmente.
    private List<Figura> figuras = new ArrayList<>(); // Lista para almacenar todas las figuras dibujadas.
    private Stack<Figura> figurasDeshechas = new Stack<>(); // Pila para almacenar figuras deshechas para la funcionalidad de rehacer.
    PanelDeColores panelDeColores; // Referencia al panel de colores para obtener la configuración actual de color/relleno.
    BarraDeHerramientas barraDeHerramientas; // Referencia a la barra de herramientas para obtener la herramienta seleccionada.
    private BufferedImage imagenDeFondo; // Imagen de fondo para el lienzo.

    private Figura figuraSeleccionada = null; // Variable para mantener la figura seleccionada1

    private Cursor cursorDibujoGeneral;
    private Cursor cursorBorrador;
    private Cursor cursorLataPintura;
    private Cursor cursorSeleccionar;

    /**
     * Constructor del panel de dibujo.
     * @param barraDeHerramientas La instancia de BarraDeHerramientas.
     * @param panelDeColores La instancia de PanelDeColores.
     */
    public PanelDeDibujo(BarraDeHerramientas barraDeHerramientas, PanelDeColores panelDeColores) {
        this.barraDeHerramientas = barraDeHerramientas;
        this.panelDeColores = panelDeColores;
        configurarEventosRaton(); // Configurar los listeners de eventos del ratón.
        setBackground(Color.WHITE); // Establecer el color de fondo del panel.

        // Inicializar los cursores
        try {
            BufferedImage dibujoGeneralImage = ImageIO.read(getClass().getResource("/iconos/cruz.png"));
            BufferedImage borradorImage = ImageIO.read(getClass().getResource("/iconos/borrador2.png"));
            BufferedImage lataPinturaImage = ImageIO.read(getClass().getResource("/iconos/lata_pintura2.png"));
            BufferedImage seleccionarImage = ImageIO.read(getClass().getResource("/iconos/seleccion2.png"));

            cursorDibujoGeneral = Toolkit.getDefaultToolkit().createCustomCursor(dibujoGeneralImage, new Point(16, 16), "cursorDibujoGeneral");
            cursorBorrador = Toolkit.getDefaultToolkit().createCustomCursor(borradorImage, new Point(8, 8), "cursorBorrador");
            cursorLataPintura = Toolkit.getDefaultToolkit().createCustomCursor(lataPinturaImage, new Point(0, 24), "cursorLataPintura");
            cursorSeleccionar = Toolkit.getDefaultToolkit().createCustomCursor(seleccionarImage, new Point(0, 0), "cursorSeleccionar");

        } catch (IOException e) {
            System.err.println("Error al cargar las imágenes de los cursores: " + e.getMessage());
            // Cursores por defecto como respaldo
            cursorDibujoGeneral = Cursor.getDefaultCursor();
            cursorBorrador = Cursor.getDefaultCursor();
            cursorLataPintura = Cursor.getDefaultCursor();
            cursorSeleccionar = Cursor.getDefaultCursor();
        }

        barraDeHerramientas.setHerramientaSeleccionadaListener(this); // Registrarse como listener - NUEVO

    }

        // --- Implementación del listener para cursores---
    @Override
    public void herramientaSeleccionadaCambio(String nuevaHerramienta) { // NUEVO
        actualizarCursor(nuevaHerramienta); 
    }

    private void actualizarCursor(String herramienta) {
        switch (herramienta) {
            case "Lata de Pintura":
                setCursor(cursorLataPintura);
                break;
            case "Seleccionar Figura":
                setCursor(cursorSeleccionar);
                break;
            case "Borrador":
                setCursor(cursorBorrador);
                break;
            case "Línea":
            case "Rectángulo":
            case "Óvalo":
            case "Círculo":
            case "Cuadrado":
            case "Triángulo":
            case "Pentágono":
            case "Rombo":
            case "Heptagono":
            case "Octagono":
            case "Estrella":
            case "Flecha":
            case "Corazón":
            case "Trapecio":
            case "Semicirculo":
            case "Ring":
            case "Lapiz":
            case "Dibujo Libre":
                setCursor(cursorDibujoGeneral);
                break;
            default:
                setCursor(Cursor.getDefaultCursor());
                break;
        }
    }

    /**
     * Configura los listeners de eventos del ratón para el dibujo y la selección.
     */
    private void configurarEventosRaton() {
        addMouseListener(new MouseAdapter() {
            /**
             * Maneja los eventos de presión del ratón.
             * Inicia el dibujo de una nueva figura, inicia una acción basada en la herramienta seleccionada,
             * o selecciona una figura existente.
             * @param e El MouseEvent.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                // Obtener la herramienta seleccionada de la barra de herramientas
                String herramienta = barraDeHerramientas.getHerramientaSeleccionada();

                // --- Lógica de la Lata de Pintura ---
                if ("Lata de Pintura".equals(herramienta)) {
                    Figura figuraClickeada = getFiguraEnPunto(e.getPoint());
                    if (figuraClickeada != null) {
                        figuraClickeada.setColorDeRelleno(panelDeColores.getColorRellenoActual());
                        figuraClickeada.setRelleno(panelDeColores.isRellenar()); // Usar el estado de la casilla
                        repaint();
                    }
                    return; // ¡MUY IMPORTANTE! No crear una nueva figura
                }

                // --- Lógica de Selección ---
                if ("Seleccionar Figura".equals(herramienta)) {
                    Figura figuraClickeada = getFiguraEnPunto(e.getPoint());

                    if (figuraClickeada != null) {
                        // Si se hizo clic en una figura, seleccionarla
                        figuraSeleccionada = figuraClickeada;
                        System.out.println("Figura seleccionada: " + figuraSeleccionada.getClass().getSimpleName()); // Para depuración
                    } else {
                        // Si se hizo clic fuera de cualquier figura, deseleccionar
                        figuraSeleccionada = null;
                        System.out.println("Ninguna figura seleccionada."); // Para depuración
                    }
                    figuraActual = null; // No estamos dibujando una nueva figura al seleccionar
                    repaint(); // Repintar para mostrar la selección (o la falta de ella)
                    return; // Salir del método ya que la acción fue seleccionar
                }
                // --- Fin Lógica de Selección ---


                // Si la herramienta es el Borrador, creamos y añadimos una instancia inmediatamente
                if ("Borrador".equals(herramienta)) {
                    // Deseleccionar cualquier figura si empezamos a borrar
                    figuraSeleccionada = null;
                    // La instancia de Borrador se crea y añade en mouseDragged para un borrado continuo.
                    // Simplemente nos aseguramos de que el modo de borrado está activo y salimos,
                    // el mouseDragged se encargará de añadir las "marcas" del borrador.
                    figurasDeshechas.clear(); // Limpiar rehacer al iniciar un trazo de borrador
                    // No creamos figuraActual = new Borrador(e.getPoint()); aquí en mousePressed,
                    // ya que mouseDragged lo hará repetidamente.
                    return; // Salir, mouseDragged manejará el resto
                }


                // --- Lógica de Dibujo Normal para otras herramientas (Línea, Rectángulo, etc.) ---
                // Si no estamos en modo selección o borrador, proceder con el dibujo normal.
                // Deseleccionar cualquier figura si empezamos a dibujar una nueva
                figuraSeleccionada = null;

                // Obtener el tipo de figura según la herramienta seleccionada y crear una nueva instancia.
                figuraActual = obtenerFiguraADibujar(e.getPoint());

                // Si la figura actual no es nula (es decir, es una herramienta de dibujo)
                if (figuraActual != null) {
                    // Usamos los colores y estado de relleno DEL PANEL DE COLORES
                   figuraActual.setColorDePrimerPlano(panelDeColores.getColorBordeActual());
        figuraActual.setColorDeRelleno(panelDeColores.getColorRellenoActual());
        figuraActual.setRelleno(panelDeColores.isRellenar()); // ¡Asegúrate de esto!

    System.out.println("Rellenar: " + panelDeColores.isRellenar() + ", Color Relleno: " + panelDeColores.getColorRellenoActual()); // *** AÑADIR ESTO ***

                    figuras.add(figuraActual); // Añadir la nueva figura a la lista.
                    figurasDeshechas.clear(); // Limpiar la pila de rehacer cuando se dibuja una nueva figura.
                    repaint(); // Repintar el panel para mostrar la nueva figura.
                }
                // --- Fin Lógica de Dibujo Normal ---
            }

            /**
             * Maneja los eventos de liberación del ratón.
             * Finaliza la figura actual.
             * @param e El MouseEvent.
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                String herramienta = barraDeHerramientas.getHerramientaSeleccionada();
                if (!"Borrador".equals(herramienta) && !"Seleccionar Figura".equals(herramienta)) {
                    figuraActual = null; // Restablecer la figura actual después de completar el dibujo (solo para figuras de dibujo).
                }
            }

        });


        addMouseMotionListener(new MouseAdapter() {
            /**
             * Maneja los eventos de arrastre del ratón.
             * Actualiza la figura actual mientras se arrastra el ratón.
             * @param e El MouseEvent.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                String herramienta = barraDeHerramientas.getHerramientaSeleccionada();

                if ("Borrador".equals(herramienta)) {
                    // Para el borrador, creamos y añadimos una nueva instancia en cada arrastre
                    figuraActual = new Borrador(e.getPoint());
                    // El tamaño del borrador podría controlarse aquí si tu PanelDeColores tuviera esa opción
                    // ((Borrador) figuraActual).setTamano(tamañoSeleccionado);
                    figuras.add(figuraActual);
                    // No limpiar figurasDeshechas aquí, solo en mousePressed cuando se inicia un nuevo trazo de borrador,
                    // o cuando se inicia una nueva figura no borrador.
                    // figurasDeshechas.clear(); // Limpiar la pila de rehacer al usar el borrador - se hace en mousePressed ahora.

                } else if (figuraActual != null && !"Seleccionar Figura".equals(herramienta)) {
                    // Si estamos dibujando una figura normal y NO estamos en modo selección, actualizarla.
                    figuraActual.actualizar(e.getPoint());
                }
                repaint(); // Repintar el panel para mostrar la figura actualizada.
            }
        });
    }

    /**
     * Busca qué figura se encuentra en un punto dado, iterando desde la última figura dibujada.
     * @param p El punto a verificar.
     * @return La figura en la que se hizo clic, o null si no se hizo clic en ninguna figura.
     */
    private Figura getFiguraEnPunto(Point p) {
        // Iterar la lista de figuras en orden inverso para seleccionar la figura superior
        List<Figura> figurasInvertidas = new ArrayList<>(figuras);
        Collections.reverse(figurasInvertidas);

        for (Figura figura : figurasInvertidas) {
            // Usar el método contains de cada figura para verificar si el punto está dentro de su área
            if (figura.contains(p)) {
                return figura; // Devolver la primera figura encontrada (la superior)
            }
        }
        return null; // No se encontró ninguna figura en el punto dado
    }


    /**
     * Determina y crea un nuevo objeto Figura basado en la herramienta seleccionada actualmente.
     * Este método SOLO debe ser llamado cuando se está en un modo de dibujo.
     * @param punto El punto de inicio para la figura.
     * @return Un nuevo objeto Figura del tipo seleccionado.
     */
    private Figura obtenerFiguraADibujar(Point punto) {
        String herramienta = barraDeHerramientas.getHerramientaSeleccionada(); // Obtener el nombre de la herramienta seleccionada.

        // Crear una nueva instancia de figura basada en la herramienta seleccionada.
        switch (herramienta) {
            case "Línea":
                return new Linea(punto);
            case "Rectángulo":
                return new Rectangulo(punto);
            case "Borrador":
                // La instancia de Borrador se crea y añade en mouseDragged para un borrado continuo
                return new Borrador(punto); // Aunque ya lo manejamos antes, lo mantenemos aquí por consistencia si se llama directamente.
            case "Óvalo":
                return new Ovalo(punto);
            case "Círculo":
                return new Circulo(punto);
            case "Cuadrado":
                return new Cuadrado(punto);
            case "Triángulo":
                return new Triangulo(punto);
            case "Pentágono":
                return new Pentagono(punto);
            case "Rombo":
                return new Rombo(punto);
            case "Heptagono":
                return new Heptagono(punto);
            case "Octagono":
                return new Octagono(punto);
            case "Estrella":
                return new Estrella(punto);
            case "Flecha":
                return new Flecha(punto);
            case "Corazón":
                return new Corazon(punto);
            case "Trapecio":
                return new Trapecio(punto);
            case "Semicirculo":
                return new Semicirculo(punto);
            case "Ring": // Corregido para instanciar Ring
                return new Ring(punto);
            case "Dibujo Libre":
            default:
                return new DibujoLibre(punto); // Por defecto, dibujo libre (Lapiz)
        }
    }

    /**
     * Pinta el componente. Este método es llamado por el framework Swing
     * cuando el componente necesita ser dibujado.
     * @param g El objeto Graphics a usar para pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagenDeFondo != null) {
            g.drawImage(imagenDeFondo, 0, 0, this);
        }

        for (Figura figura : figuras) {
            figura.dibujar(g);
            // Dibujar un indicador visual si la figura está seleccionada
            if (figura == figuraSeleccionada) {
                g.setColor(Color.BLUE); // Color del indicador de selección
                Graphics2D g2d = (Graphics2D) g; // Usar Graphics2D para configuraciones avanzadas
                Stroke originalStroke = g2d.getStroke(); // Guardar el stroke original
                g2d.setStroke(new BasicStroke(2)); // Configurar un trazo más grueso para el indicador

                Rectangle bounds = figura.getBounds(); // Obtener los límites de la figura
                if (bounds != null) {
                    g2d.drawRect(bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4); // Dibujar el rectángulo de selección un poco más grande
                }
                // Nota: Para DibujoLibre/Lapiz, necesitarías acceder a la lista de puntos y calcular el bounding box en su getBounds().

                g2d.setStroke(originalStroke); // Restaurar el stroke original
            }
        }
    }

    /**
     * Establece una imagen de fondo para el panel de dibujo.
     * @param imagen El BufferedImage a establecer como fondo.
     */
    public void setImagenDeFondo(BufferedImage imagen) {
        this.imagenDeFondo = imagen;
        repaint();
    }

    /**
     * Deshace la última acción de dibujo eliminando la última figura de la lista.
     * Mueve la figura deshecha a la pila de rehacer.
     */
    public void undo() {
        if (!figuras.isEmpty()) {
            // Si la figura deshecha es la figura seleccionada, deseleccionarla.
            if (figuras.get(figuras.size() - 1) == figuraSeleccionada) {
                figuraSeleccionada = null;
            }
            Figura figuraDeshecha = figuras.remove(figuras.size() - 1); // Eliminar la última figura.
            figurasDeshechas.push(figuraDeshecha); // Empujar la figura deshecha a la pila de rehacer.
            repaint(); // Repintar el panel.
        }
    }

    /**
     * Rehace la última acción de dibujo deshecha moviendo una figura de la pila de rehacer
     * de vuelta a la lista principal de figuras.
     */
    public void redo() {
        if (!figurasDeshechas.isEmpty()) {
            Figura figuraRehecha = figurasDeshechas.pop(); // Sacar la figura de la pila de rehacer.
            figuras.add(figuraRehecha); // Añadir la figura de vuelta a la lista principal.
            repaint(); // Repintar el panel.
        }
        // Nota: Si se añade una nueva figura después de rehacer, la pila figurasDeshechas se limpia
        // en mousePressed o mouseDragged (para el borrador).
    }

    /**
     * Limpia completamente el lienzo de dibujo eliminando todas las figuras.
     * También limpia la pila de rehacer y deselecciona cualquier figura.
     */
    public void clearCanvas() {
        figuras.clear(); // Limpiar la lista principal de figuras.
        figurasDeshechas.clear(); // Limpiar la pila de rehacer.
        imagenDeFondo = null; // También limpiar la imagen de fondo si hay alguna.
        figuraSeleccionada = null; // Deseleccionar cualquier figura
        repaint(); // Repintar el panel vacío.
    }

    // Método para obtener la figura seleccionada (útil para copiar/pegar)
    public Figura getFiguraSeleccionada() {
        return figuraSeleccionada;
    }

    // Método para añadir una figura (útil para pegar)
    public void addFigura(Figura figura) {
        figuras.add(figura);
        figurasDeshechas.clear(); // Limpiar rehacer al pegar
        // No seleccionar la figura pegada por defecto, pero podrías hacerlo si quisieras:
        // this.figuraSeleccionada = figura;
        repaint();
    }

    // Método para deseleccionar la figura actual
    public void deseleccionarFigura() {
        this.figuraSeleccionada = null;
        // No repintamos aquí, se espera que la llamada que usa este método lo haga.
    }
}