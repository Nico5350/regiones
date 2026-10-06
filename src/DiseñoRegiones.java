import java.awt.EventQueue;
import java.io.IOException;

import presentador.Presentador;
import vista.VistaPrincipal;

public class DiseñoRegiones {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {

			VistaPrincipal view = new VistaPrincipal();
			Presentador presenter = new Presentador(view);

			try {
				presenter.iniciarAplicacion();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			view.setVisible();
		});
	}
}
