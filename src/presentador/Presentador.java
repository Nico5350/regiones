package presentador;

import java.io.IOException;
import java.util.List;

import modelo.Pais;
import modelo.Provincia;
import persistencia.LectorProvincias;
import vista.VistaPrincipal;

public class Presentador {
	private VistaPrincipal mainView;
	private Pais pais;

	public Presentador(VistaPrincipal mainView) {
		this.mainView = mainView;
	}

	public void iniciarAplicacion() throws IOException {
		List<Provincia> listaProvincias = new LectorProvincias().cargarArchivo();
		pais = new Pais(listaProvincias, true);

		mainView.mostrarProvincias(pais.obtenerProvincias());
	}
}
