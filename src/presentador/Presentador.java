package presentador;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import modelo.Arista;
import modelo.Pais;
import modelo.Provincia;
import persistencia.LectorProvincias;
import vista.PruebaVista;
import vista.VistaPrincipal;

public class Presentador {
	private PruebaVista mainView;
	private Pais pais;
	private LectorProvincias lector;

	public Presentador(PruebaVista mainView) {
		this.mainView = mainView;
		this.lector = new LectorProvincias();
	}

	public void iniciarAplicacion() throws IOException {
		List<Provincia> listaProvincias = lector.cargarProvincias();
		List<Arista> listaPaisesLimitrofes = lector.cargarAristas();
		pais = new Pais(listaProvincias, true);
		
		for (Arista arista : listaPaisesLimitrofes) {

		    int origen = arista.getOrigen() - 1;
		    int destino = arista.getDestino() - 1;

		    pais.obtenerGrafo().agregarArista(
		        origen,
		        destino,
		        arista.getPeso()
		    );
		}

		limpiarMapa();
	}

	public void generarRegiones(int k) {
		List<Set<Provincia>> regiones = pais.disenarRegiones(k);
		mainView.dibujarRegiones(regiones);
	}

	public void limpiarMapa() {
		mainView.mostrarProvincias(pais.obtenerProvincias());
		mainView.mostrarProvinciasLimitrofes(pais.obtenerProvincias(), pais.obtenerAristas());
	}
}
