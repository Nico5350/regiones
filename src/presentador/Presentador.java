package presentador;

import java.io.IOException;
import java.util.List;

import modelo.Arista;
import modelo.Pais;
import modelo.Provincia;
import persistencia.LectorProvincias;
import vista.VistaPrincipal;

public class Presentador {
	private VistaPrincipal mainView;
	private Pais pais;
	private LectorProvincias lector;

	public Presentador(VistaPrincipal mainView) {
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

		mainView.mostrarProvincias(pais.obtenerProvincias());
		mainView.mostrarProvinciasLimitrofes(listaProvincias, pais.obtenerAristas());
	}
}
