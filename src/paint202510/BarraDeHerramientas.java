package paint202510;

import java.awt.Component;
import javax.swing.ButtonGroup;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;

/**
 * Barra de herramientas para la aplicación de dibujo.
 *
 * @author josearielpereyra
 */
public class BarraDeHerramientas extends JToolBar {

    protected JToggleButton btnBorrador;
    protected JToggleButton btnLapiz;
    protected JToggleButton btnLinea;
    protected JToggleButton btnRectangulo;
    protected JToggleButton btnOvalo;
    protected JToggleButton btnCirculo;
    protected JToggleButton btnCuadrado;
    protected JToggleButton btnTriangulo;
    protected JToggleButton btnGuardar;
    protected JToggleButton btnPentagono;
    protected JToggleButton btnRombo;
    protected JToggleButton btnHeptagono;
    protected JToggleButton btnOctagono;
    protected JToggleButton btnEstrella;
    protected JToggleButton btnFlecha;

    public BarraDeHerramientas() {
    setOrientation(JToolBar.HORIZONTAL);

        // Inicialización de los botones
        btnLapiz = new JToggleButton("Lapiz");
        btnLinea = new JToggleButton("Linea");
        btnRectangulo = new JToggleButton("Rectangulo");
        btnOvalo = new JToggleButton("Óvalo");
        btnCirculo = new JToggleButton("Círculo");
        btnBorrador = new JToggleButton("Borrador");
        btnCuadrado = new JToggleButton("Cuadrado");
        btnTriangulo = new JToggleButton("Triángulo");
        btnGuardar = new JToggleButton("Guardar");
        btnPentagono = new JToggleButton("Pentágono");
        btnRombo = new JToggleButton("Rombo");
        btnHeptagono = new JToggleButton("Heptagono");
        btnOctagono = new JToggleButton("Octagono");
        btnEstrella = new JToggleButton("Estrella");
        btnFlecha = new JToggleButton("Flecha");

        // Agregar botones a la barra de herramientas
        formatearYAgregar(btnGuardar, "guardar.png", "Guardar Imagen");
        formatearYAgregar(btnLapiz, "lapiz.png", "Dibujo Libre");
        formatearYAgregar(btnLinea, "linea.png", "Línea");
        formatearYAgregar(btnOvalo, "ovalo.png", "Óvalo");
        formatearYAgregar(btnCirculo, "circulo.png", "Círculo");
        formatearYAgregar(btnCuadrado, "cuadrado.png", "Cuadrado");
        formatearYAgregar(btnRectangulo, "rectangulo.png", "Rectángulo");
        formatearYAgregar(btnTriangulo, "triangulo.png", "Triángulo");
        formatearYAgregar(btnRombo, "rombo.png", "Rombo");
        formatearYAgregar(btnPentagono, "pentagono.png", "Pentágono");
        formatearYAgregar(btnHeptagono, "heptagono.png", "Heptagono");
        formatearYAgregar(btnOctagono, "octagono.png", "Octagono");
        formatearYAgregar(btnEstrella, "estrella.png", "Estrella");
        formatearYAgregar(btnFlecha, "flecha.png", "Flecha");
        formatearYAgregar(btnBorrador, "borrador.png", "Borrador");

        // Crear un grupo de botones para que solo uno esté seleccionado a la vez
        ButtonGroup grupoBotones = new ButtonGroup();
        for (Component boton : this.getComponents()) {
            if (boton != btnGuardar) {
                grupoBotones.add((JToggleButton) boton);
            }
        }
    }

    private void formatearYAgregar(JToggleButton boton, String nombreIcono, String tooltip) {
        boton.setFocusable(false);
        boton.setToolTipText("Seleccione: " + tooltip);
        boton.setText(null); // No mostrar texto

        // Cargar el ícono desde la carpeta iconos
        java.net.URL ruta = getClass().getResource("/iconos/" + nombreIcono);
        if (ruta != null) {
            boton.setIcon(new javax.swing.ImageIcon(ruta));
        } else {
            System.out.println("Icono no encontrado: " + nombreIcono);
        }

        add(boton);
    }
}