package vista;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import modelo.Arista;
import modelo.Provincia;

public class VistaPrincipal {

	private JFrame frame;
	private JPanel panelMapa;
	private JMapViewer mapa;

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

	}

	private void initializeMap() {
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 500, 500);
		mapa.setZoomControlsVisible(false);

		Coordinate coordinate = new Coordinate(-39.84228602074339, -66.62109375);
		mapa.setDisplayPosition(coordinate, 4);
	}

	public void mostrarProvincias(List<Provincia> provincias) {
		for (Provincia provincia : provincias) {

			Coordinate coordenada = new Coordinate(provincia.getLatitud(), provincia.getLongitud());

			MapMarker marker = new MapMarkerDot(provincia.getNombre(), coordenada);
			marker.getStyle().setBackColor(Color.YELLOW);
			marker.getStyle().setColor(Color.BLACK);

			mapa.addMapMarker(marker);
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
}
