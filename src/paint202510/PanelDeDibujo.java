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

public class PanelDeDibujo extends JPanel implements HerramientaSeleccionadaListener {
    private Figura figuraActual;
    private List<Figura> figuras = new ArrayList<>();
    private Stack<Figura> figurasDeshechas = new Stack<>();

    PanelDeColores panelDeColores; // Referencia al panel de colores para obtener la configuración actual de color/relleno.
    BarraDeHerramientas barraDeHerramientas; // Referencia a la barra de herramientas para obtener la herramienta seleccionada.

    private BufferedImage imagenDeFondo; // Imagen de fondo para el lienzo.
    private Figura figuraSeleccionada = null;
    private Cursor cursorDibujoGeneral;
    private Cursor cursorBorrador;
    private Cursor cursorLataPintura;
    private Cursor cursorSeleccionar;
    private Cursor cursorEliminar;
    private Point puntoInicialArrastre = null;
    private int grosor = 1;

    /**
     * Constructor del panel de dibujo.
     * 
     * @param barraDeHerramientas La instancia de BarraDeHerramientas.
     * @param panelDeColores      La instancia de PanelDeColores.
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
            BufferedImage lataPinturaImage = ImageIO.read(getClass().getResource("/iconos/rellenar_pintura.png"));
            BufferedImage seleccionarImage = ImageIO.read(getClass().getResource("/iconos/seleccion4.png"));

            cursorDibujoGeneral = Toolkit.getDefaultToolkit().createCustomCursor(dibujoGeneralImage, new Point(16, 16),
                    "cursorDibujoGeneral");
            cursorBorrador = Toolkit.getDefaultToolkit().createCustomCursor(borradorImage, new Point(8, 18),
                    "cursorBorrador");
            cursorLataPintura = Toolkit.getDefaultToolkit().createCustomCursor(lataPinturaImage, new Point(0, 24),
                    "cursorLataPintura");
            cursorSeleccionar = Toolkit.getDefaultToolkit().createCustomCursor(seleccionarImage, new Point(16, 16),
                    "cursorSeleccionar");
            cursorEliminar = Toolkit.getDefaultToolkit().createCustomCursor(seleccionarImage, new Point(16, 16),
                    "cursorSeleccionar");

        } catch (IOException e) {
            System.err.println("Error al cargar las imágenes de los cursores: " + e.getMessage());
            cursorDibujoGeneral = Cursor.getDefaultCursor();
            cursorBorrador = Cursor.getDefaultCursor();
            cursorLataPintura = Cursor.getDefaultCursor();
            cursorSeleccionar = Cursor.getDefaultCursor();
            cursorEliminar = Cursor.getDefaultCursor();
        }
        barraDeHerramientas.setHerramientaSeleccionadaListener(this); 
    }

    // --- Implementación del listener para cursores---
    @Override
    public void herramientaSeleccionadaCambio(String nuevaHerramienta) {
        actualizarCursor(nuevaHerramienta);
    }

    private void actualizarCursor(String herramienta) {
        switch (herramienta) {
            case "Color de relleno":
                setCursor(cursorLataPintura);
                break;
            case "Seleccionar Figura":
                setCursor(cursorSeleccionar);
                break;
            case "Eliminar Figura":
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
             * Inicia el dibujo de una nueva figura, inicia una acción basada en la
             * herramienta seleccionada,
             * o selecciona una figura existente.
             * 
             * @param e El MouseEvent.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                // Obtener la herramienta seleccionada de la barra de herramientas
                String herramienta = barraDeHerramientas.getHerramientaSeleccionada();

                // --- Logica de la Color de relleno ---
                if ("Color de relleno".equals(herramienta)) {
                    Figura figuraClickeada = getFiguraEnPunto(e.getPoint());
                    if (figuraClickeada != null) {
                        figuraClickeada.setColorDeRelleno(panelDeColores.getColorRellenoActual());
                        figuraClickeada.setRelleno(panelDeColores.isRellenar());
                        repaint();
                    }
                    return;
                }

                // --- Lógica de Selección ---
                if ("Seleccionar Figura".equals(herramienta)) {
                    Figura figuraClickeada = getFiguraEnPunto(e.getPoint());
                    if (figuraClickeada != null) {
                        figuraSeleccionada = figuraClickeada;
                        puntoInicialArrastre = new Point(e.getPoint().x - figuraSeleccionada.getBounds().x,
                                e.getPoint().y - figuraSeleccionada.getBounds().y);
                        System.out.println("Figura seleccionada: " + figuraSeleccionada.getClass().getSimpleName());
                    } else {
                        figuraSeleccionada = null;
                        System.out.println("Ninguna figura seleccionada.");
                    }
                    figuraActual = null;
                    repaint();
                    return;
                }
                // --- Fin Lógica de Selección ---


            if ("Eliminar Figura".equals(herramienta)) {
                Figura figuraClickeada = getFiguraEnPunto(e.getPoint());
                if (figuraClickeada != null) {
                    figuras.remove(figuraClickeada);
                    figurasDeshechas.clear();
                    repaint();
                }
                return;
            }

                if ("Borrador".equals(herramienta)) {
                    figuraSeleccionada = null;
                    figurasDeshechas.clear();
                    return;
                }

                // --- Lógica de Dibujo Normal para otras herramientas (Línea, Rectángulo, etc.)
                // ---
                // Si no estamos en modo selección o borrador, proceder con el dibujo normal.
                // Deseleccionar cualquier figura si empezamos a dibujar una nueva
                figuraSeleccionada = null;

                // Obtener el tipo de figura según la herramienta seleccionada y crear una nueva
                // instancia.
                figuraActual = obtenerFiguraADibujar(e.getPoint());

                if (figuraActual != null) {
                    figuraActual.setColorDePrimerPlano(panelDeColores.getColorBordeActual());
                    figuraActual.setColorDeRelleno(panelDeColores.getColorRellenoActual());
                    figuraActual.setRelleno(panelDeColores.isRellenar());
                    figuraActual.setGrosor(grosor);
                    figuras.add(figuraActual);
                    figurasDeshechas.clear();
                    repaint();
                }
            }

            /**
             * Maneja los eventos de liberación del ratón.
             * Finaliza la figura actual.
             * 
             * @param e El MouseEvent.
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                String herramienta = barraDeHerramientas.getHerramientaSeleccionada();
                if (!"Borrador".equals(herramienta) && !"Seleccionar Figura".equals(herramienta)) {
                    figuraActual = null;
                }
                puntoInicialArrastre = null;
            }

        });

        addMouseMotionListener(new MouseAdapter() {
            /**
             * Maneja los eventos de arrastre del ratón.
             * Actualiza la figura actual mientras se arrastra el ratón.
             * 
             * @param e El MouseEvent.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                String herramienta = barraDeHerramientas.getHerramientaSeleccionada();

                if ("Eliminar Figura".equals(herramienta)) {
             Figura figuraClickeada = getFiguraEnPunto(e.getPoint());
             if (figuraClickeada != null) {
                 figuras.remove(figuraClickeada);
                 figurasDeshechas.clear();
                 repaint();
             }
             return;
         }
                if ("Seleccionar Figura".equals(herramienta) && figuraSeleccionada != null) {
                    // Calcular la nueva posición de la figura
                    int nuevaX = e.getPoint().x - puntoInicialArrastre.x;
                    int nuevaY = e.getPoint().y - puntoInicialArrastre.y;

                    // Crear un Point para el desplazamiento
                    Point nuevoPunto = new Point(nuevaX, nuevaY);

                    // Mover la figura
                    figuraSeleccionada.translate(new Point(nuevaX - figuraSeleccionada.getBounds().x,
                            nuevaY - figuraSeleccionada.getBounds().y));
                    repaint();
                } else if (figuraActual != null && !"Seleccionar Figura".equals(herramienta)) {
                    figuraActual.actualizar(e.getPoint());
                }

                if ("Borrador".equals(herramienta)) {
                    figuraActual = new Borrador(e.getPoint());
                    figuras.add(figuraActual);
                } else if (figuraActual != null && !"Seleccionar Figura".equals(herramienta)) {
                    figuraActual.actualizar(e.getPoint());
                }
                repaint();
            }
        });
    }

    /**
     * Busca qué figura se encuentra en un punto dado, iterando desde la última
     * figura dibujada.
     * 
     * @param p El punto a verificar.
     * @return La figura en la que se hizo clic, o null si no se hizo clic en
     *         ninguna figura.
     */
    private Figura getFiguraEnPunto(Point p) {
        // Iterar la lista de figuras en orden inverso para seleccionar la figura
        // superior
        List<Figura> figurasInvertidas = new ArrayList<>(figuras);
        Collections.reverse(figurasInvertidas);

        for (Figura figura : figurasInvertidas) {
            // Usar el método contains de cada figura para verificar si el punto está dentro
            // de su área
            if (figura.contains(p)) {
                return figura; // Devolver la primera figura encontrada (la superior)
            }
        }
        return null;
    }

    /**
     * Determina y crea un nuevo objeto Figura basado en la herramienta seleccionada
     * actualmente.
     * Este método SOLO debe ser llamado cuando se está en un modo de dibujo.
     * 
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
                return new Borrador(punto);
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
            case "Ring":
                return new Ring(punto);
            case "Dibujo Libre":
            default:
                return new DibujoLibre(punto);
        }
    }

    /**
     * Pinta el componente. Este método es llamado por el framework Swing
     * cuando el componente necesita ser dibujado.
     * 
     * @param g El objeto Graphics a usar para pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagenDeFondo != null) {
            g.drawImage(imagenDeFondo, 0, 0, this);
        }

        Graphics2D g2d = (Graphics2D) g; // Obtener Graphics2D para el grosor g2d.setStroke(new BasicStroke(grosor)); // Establecer el grosor del trazo

        for (Figura figura : figuras) {
            figura.dibujar(g2d); // Usar g2d para dibujar
            // Dibujar un indicador visual si la figura está seleccionada
            if (figura == figuraSeleccionada) {
                g2d.setColor(Color.BLUE); // Color del indicador de selección - ¡Usar g2d!
                Stroke originalStroke = g2d.getStroke(); // Guardar el stroke original
                g2d.setStroke(new BasicStroke(2)); // Configurar un trazo más grueso para el indicador

                Rectangle bounds = figura.getBounds(); // Obtener los límites de la figura
                if (bounds != null) {
                    g2d.drawRect(bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4);                                                                              // más grande
                }

                g2d.setStroke(originalStroke); // Restaurar el stroke original
            }
        }
        g2d.setStroke(new BasicStroke(1));
    }

    /**
     * Establece una imagen de fondo para el panel de dibujo.
     * 
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
            repaint();
        }
    }

    /**
     * Rehace la última acción de dibujo deshecha moviendo una figura de la pila de
     * rehacer de vuelta a la lista principal de figuras.
     */
    public void redo() {
        if (!figurasDeshechas.isEmpty()) {
            Figura figuraRehecha = figurasDeshechas.pop(); // Sacar la figura de la pila de rehacer.
            figuras.add(figuraRehecha); // Añadir la figura de vuelta a la lista principal.
            repaint(); // Repintar el panel.
        }
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

    // Método para añadir una figura
    public void addFigura(Figura figura) {
        figuras.add(figura);
        figurasDeshechas.clear();
        repaint();
    }

    // Método para deseleccionar la figura actual
    public void deseleccionarFigura() {
        this.figuraSeleccionada = null;
    }

    /**
     * Establece el grosor de la línea para dibujar.
     * 
     * @param grosor El grosor de la línea.
     */
    public void setGrosor(int grosor) {
        this.grosor = grosor;
    }
}