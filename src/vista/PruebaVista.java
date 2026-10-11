package vista;

import modelo.Arista;
import modelo.Provincia;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import presentador.Presentador;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PruebaVista {
    private static final Color FONDO = new Color(245, 247, 250);
    private static final Color SIDEBAR = new Color(30, 41, 59);
    private static final Color ACENTO = new Color(37, 99, 235);

    private final JMapViewer mapa;
    private final JSpinner cantidadRegiones;
    private final JLabel estado;
    private Presentador presentador;
    private JPanel frame;

    public PruebaVista() {
        frame = new JPanel(new BorderLayout());
        frame.setPreferredSize(new Dimension(1200, 760));
        frame.setBackground(FONDO);

        mapa = new JMapViewer();
        mapa.setZoomControlsVisible(true);
        mapa.setDisplayPosition(new Coordinate(-39.84228602074339, -66.62109375), 4);

        cantidadRegiones = new JSpinner(new SpinnerNumberModel(3, 1, 24, 1));
        estado = new JLabel("Listo para diseñar regiones");

        frame.add(crearSidebar(), BorderLayout.WEST);
        frame.add(crearPanelMapa(), BorderLayout.CENTER);
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PruebaVista().setVisible());
    }

    public void setPresentador(Presentador presentador) {
        this.presentador = presentador;
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout(0, 24));
        sidebar.setPreferredSize(new Dimension(290, 760));
        sidebar.setBackground(SIDEBAR);
        sidebar.setBorder(BorderFactory.createEmptyBorder(28, 22, 24, 22));

        JPanel encabezado = new JPanel(new GridLayout(0, 1, 0, 7));
        encabezado.setOpaque(false);
        JLabel titulo = new JLabel("Diseño de regiones");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        JLabel subtitulo = new JLabel("Explorá provincias y conexiones");
        subtitulo.setForeground(new Color(203, 213, 225));
        encabezado.add(titulo);
        encabezado.add(subtitulo);

        JPanel opciones = new JPanel(new GridLayout(0, 1, 0, 12));
        opciones.setOpaque(false);
        JLabel etiquetaK = new JLabel("Cantidad de regiones");
        etiquetaK.setForeground(Color.WHITE);
        opciones.add(etiquetaK);
        opciones.add(cantidadRegiones);

        JButton disenar = crearBoton("Diseñar regiones", ACENTO, Color.WHITE);
        disenar.addActionListener(e -> {
            if (presentador == null) {
                estado.setText("La aplicación no está inicializada");
                return;
            }

            int k = (Integer) cantidadRegiones.getValue();
            try {
                presentador.generarRegiones(k);
                estado.setText("Se diseñaron " + k + " regiones");
            } catch (IllegalArgumentException | IllegalStateException ex) {
                estado.setText(ex.getMessage());
            }
        });
        opciones.add(disenar);

        opciones.add(crearBotonLimpiar());

        JPanel pie = new JPanel(new BorderLayout(0, 8));
        pie.setOpaque(false);
        JLabel estadoTitulo = new JLabel("ESTADO");
        estadoTitulo.setForeground(new Color(148, 163, 184));
        estadoTitulo.setFont(new Font("SansSerif", Font.BOLD, 11));
        estado.setForeground(Color.WHITE);
        pie.add(estadoTitulo, BorderLayout.NORTH);
        pie.add(estado, BorderLayout.CENTER);

        sidebar.add(encabezado, BorderLayout.NORTH);
        sidebar.add(opciones, BorderLayout.CENTER);
        sidebar.add(pie, BorderLayout.SOUTH);
        return sidebar;
    }

    private JPanel crearPanelMapa() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel tituloMapa = new JLabel("Mapa de provincias", SwingConstants.LEFT);
        tituloMapa.setFont(new Font("SansSerif", Font.BOLD, 20));
        tituloMapa.setForeground(new Color(30, 41, 59));
        panel.add(tituloMapa, BorderLayout.NORTH);

        mapa.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        panel.add(mapa, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBotonLimpiar() {
        JButton limpiar = crearBoton("Limpiar mapa", new Color(71, 85, 105), Color.WHITE);
        limpiar.addActionListener(e -> {
            mapa.removeAllMapMarkers();
            mapa.removeAllMapPolygons();
            presentador.limpiarMapa();
            estado.setText("Mapa limpio");
        });
        return limpiar;
    }

    private JButton crearBoton(String texto, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(11, 12, 11, 12));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        return boton;
    }

    public JPanel getPanel() {
        return frame;
    }

    public void setVisible() {
        JFrame ventana = new JFrame("Diseño de regiones");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setContentPane(frame);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setMinimumSize(new Dimension(900, 600));
        ventana.setVisible(true);
    }

    public void mostrarProvincias(List<Provincia> provincias) {
        mapa.removeAllMapMarkers();
        for (Provincia provincia : provincias) {
            Coordinate coordenada = new Coordinate(provincia.getLatitud(), provincia.getLongitud());
            mapa.addMapMarker(new MapMarkerDot(provincia.getNombre(), coordenada));
        }
    }

    public void mostrarProvinciasLimitrofes(List<Provincia> provincias, List<Arista> aristas) {
        Map<Integer, Provincia> provinciasPorId = new HashMap<>();
        for (Provincia provincia : provincias) {
            provinciasPorId.put(provincia.getId(), provincia);
        }

        for (Arista arista : aristas) {
            Provincia origen = provinciasPorId.get(arista.getOrigen() + 1);
            Provincia destino = provinciasPorId.get(arista.getDestino() + 1);

            if (origen == null || destino == null) {
                continue;
            }

            Coordinate punto1 = new Coordinate(origen.getLatitud(), origen.getLongitud());
            Coordinate punto2 = new Coordinate(destino.getLatitud(), destino.getLongitud());
            List<Coordinate> listaCoordenadas = new ArrayList<Coordinate>();
            listaCoordenadas.add(punto1);
            listaCoordenadas.add(punto2);
            listaCoordenadas.add(punto1);

            MapPolygon linea = new MapPolygonImpl(listaCoordenadas);
            mapa.addMapPolygon(linea);
        }
    }

    public void dibujarRegiones(List<Set<Provincia>> regiones) {
        mapa.removeAllMapMarkers();

        for (int i = 0; i < regiones.size(); i++) {
            Color colorRegion = colorDeRegion(i);

            for (Provincia provincia : regiones.get(i)) {
                Coordinate coordenada = new Coordinate(provincia.getLatitud(), provincia.getLongitud());
                MapMarkerDot marcador = new MapMarkerDot(provincia.getNombre(), coordenada);
                marcador.getStyle().setBackColor(colorRegion);
                marcador.getStyle().setColor(Color.DARK_GRAY);
                mapa.addMapMarker(marcador);
            }
        }
    }

    private Color colorDeRegion(int indice) {
        // El avance áureo distribuye los tonos para diferenciar regiones con distintos valores de k.
        float tono = (float) ((indice * 0.618033988749895) % 1.0);
        return Color.getHSBColor(tono, 0.68f, 0.95f);
    }
}
