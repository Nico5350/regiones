package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import modelo.Datos;
import modelo.Grafo;
import modelo.Provincia;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaPrincipal {

	private JFrame frame;
	private JPanel panelMapa;
	private JMapViewer mapa;
	private boolean seleccionandoProvincia;

	public VistaPrincipal() {
		initialize();
	}

	public void setVisible() {
		this.frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 650);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("JMapViewer");

		panelMapa = new JPanel();
		panelMapa.setBounds(48, 49, 500, 500);

		frame.getContentPane().add(panelMapa);

		initializeMap();
		panelMapa.setLayout(null);
		panelMapa.add(mapa);

		JButton btnNewButton = new JButton("Cargar provincia");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccionandoProvincia = true;
				JOptionPane.showMessageDialog(frame, "Hacé clic en el mapa donde querés ubicar la provincia.");
			}
		});
		btnNewButton.setBounds(568, 49, 154, 34);
		frame.getContentPane().add(btnNewButton);

		mapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!seleccionandoProvincia)
					return;

				ICoordinate coordenadaSeleccionada = mapa.getPosition(e.getPoint());

				String nombre = JOptionPane.showInputDialog(frame, "Nombre de la provincia:");

				if (nombre == null || nombre.trim().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Nombre invalido, intentelo denuevo");
					return;
				}

				MapMarker marker = new MapMarkerDot(nombre,
						new Coordinate(coordenadaSeleccionada.getLat(), coordenadaSeleccionada.getLon()));
				marker.getStyle().setBackColor(Color.yellow);
				marker.getStyle().setColor(Color.black);

				mapa.addMapMarker(marker);

				seleccionandoProvincia = false;
			}
		});
	}

	private void initializeMap() {
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 500, 500);
		mapa.setZoomControlsVisible(false);

		Coordinate coordinate = new Coordinate(-39.84228602074339, -66.62109375);
		mapa.setDisplayPosition(coordinate, 4);

		Grafo grafo = Datos.crearGrafo();
		List<Provincia> provincias = Datos.crearProvincias();

		/*
		 * for (int i = 0; i < provincias.size(); i++) { Provincia provincia =
		 * provincias.get(i); Coordinate coordProvincia = new
		 * Coordinate(provincia.getLatitud(), provincia.getLongitud());
		 * 
		 * MapMarker provMarker = new MapMarkerDot("", coordProvincia);
		 * provMarker.getStyle().setBackColor(Color.yellow);
		 * provMarker.getStyle().setColor(Color.black);
		 * 
		 * mapa.addMapMarker(provMarker); }
		 * 
		 * 
		 * 
		 * for (int i = 0; i < grafo.cantidadVertices(); i++) {
		 * 
		 * Provincia origen = provincias.get(i);
		 * 
		 * for (int vecino : grafo.obtenerVecinos(i)) {
		 * 
		 * // Evita dibujar dos veces la misma arista if (i < vecino) {
		 * 
		 * Provincia destino = provincias.get(vecino);
		 * 
		 * Coordinate coordOrigen = new Coordinate( origen.getLatitud(),
		 * origen.getLongitud() );
		 * 
		 * Coordinate coordDestino = new Coordinate( destino.getLatitud(),
		 * destino.getLongitud() );
		 * 
		 * ArrayList<Coordinate> puntos = new ArrayList<>();
		 * 
		 * puntos.add(coordOrigen); puntos.add(coordDestino); puntos.add(coordOrigen);
		 * 
		 * MapPolygon arista = new MapPolygonImpl(puntos);
		 * 
		 * mapa.addMapPolygon(arista); } } }
		 */

	}

	public void mostrarProvincias(List<Provincia> provincias) {
		for (Provincia provincia : provincias) {
			System.out.println(provincia.getNombre());

			Coordinate coordenada = new Coordinate(provincia.getLatitud(), provincia.getLongitud());

			MapMarker marker = new MapMarkerDot(provincia.getNombre(), coordenada);
			marker.getStyle().setBackColor(Color.YELLOW);
			marker.getStyle().setColor(Color.BLACK);

			mapa.addMapMarker(marker);
		}

	}
}
