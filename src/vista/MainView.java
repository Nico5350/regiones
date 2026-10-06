package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import javax.swing.JPanel;

public class MainView {

	private JFrame frame;
	private JPanel panelMapa;
	private JMapViewer mapa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
	}
	
	private void initializeMap() {
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 500, 500);
		mapa.setZoomControlsVisible(false);
		
		Coordinate coordinate = new Coordinate(-34.521, -58.719);
		mapa.setDisplayPosition(coordinate, 12);
		
		MapMarker markerRed = new MapMarkerDot("Aqui", coordinate);
		markerRed.getStyle().setBackColor(Color.red);
		markerRed.getStyle().setColor(Color.red);
		mapa.addMapMarker(markerRed);
		
		Coordinate coordinate2 = new Coordinate(-34.521, -58.706);
		MapMarker markerYellow = new MapMarkerDot("Aqui", coordinate2);
		markerYellow.getStyle().setBackColor(Color.yellow);
		markerYellow.getStyle().setColor(Color.yellow);
		mapa.addMapMarker(markerYellow);
		
		ArrayList<Coordinate> coordenadas = new ArrayList<Coordinate>();
		coordenadas.add(new Coordinate(-34.521, -58.7008));
		coordenadas.add(new Coordinate(-34.546, -58.719));
		coordenadas.add(new Coordinate(-34.521, -58.7008));
		coordenadas.add(new Coordinate(-34.559, -58.721));
		coordenadas.add(new Coordinate(-34.569, -58.725));
		coordenadas.add(new Coordinate(-34.532, -58.738));
		
		MapPolygon poligono = new MapPolygonImpl (coordenadas);
		mapa.addMapPolygon(poligono);
	}
}
