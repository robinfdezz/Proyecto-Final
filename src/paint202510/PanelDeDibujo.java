package paint202510;

import figuras.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

public class PanelDeDibujo extends JPanel {
    private Figura figuraActual;
    private List<Figura> figuras = new ArrayList<>();
    BarraDeHerramientas barraDeHerramientas;
    private Color colorActual = Color.BLACK;
    private boolean rellenoActual = false;

    public PanelDeDibujo(BarraDeHerramientas barraDeHerramientas) {
        this.barraDeHerramientas = barraDeHerramientas;
        configurarEventosRaton();
        setBackground(Color.WHITE);
    }

    private void configurarEventosRaton() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                figuraActual = obtenerFiguraADibujar(e.getPoint());
                figuraActual.setColorDePrimerPlano(colorActual);
                figuraActual.setRelleno(rellenoActual);
                figuras.add(figuraActual);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                figuraActual = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (barraDeHerramientas.btnBorrador.isSelected()) { //detecta si esta activo el borrador
                    figuraActual = new Borrador(e.getPoint());// crea un borador
                    figuras.add(figuraActual); // se agrega a la lista de figuras
                } else if (figuraActual != null) {
                    figuraActual.actualizar(e.getPoint()); // actualiza la figura
                }
                repaint();// redibuja el panel para ver los cambios
            }
        });
    }

    private Figura obtenerFiguraADibujar(Point punto) {
        if (barraDeHerramientas.btnLinea.isSelected()) {
            return new Linea(punto);
        } else if (barraDeHerramientas.btnRectangulo.isSelected()) {
            return new Rectangulo(punto);
        } else if (barraDeHerramientas.btnBorrador.isSelected()) {
            return new Borrador(punto);
        } else if (barraDeHerramientas.btnOvalo.isSelected()) {
            return new Ovalo(punto);
        } else if (barraDeHerramientas.btnCirculo.isSelected()) {
            return new Circulo(punto);
        } else {
            return new DibujoLibre(punto);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Figura figura : figuras) {
            figura.dibujar(g);
        }
    }

    public void limpiarPanel() {
        figuras.clear();
        repaint();
    }

    public void setColorActual(Color color) {
        this.colorActual = color;
    }

    public void setRellenoActual(boolean relleno) {
        this.rellenoActual = relleno;
    }

    public void deshacer() {
        if (!figuras.isEmpty()) {
            figuras.remove(figuras.size() - 1);
            repaint();
        }
    }
}