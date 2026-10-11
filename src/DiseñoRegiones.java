import presentador.Presentador;
import vista.PruebaVista;

import java.awt.*;
import java.io.IOException;

public class DiseñoRegiones {
    static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            PruebaVista vista = new PruebaVista();
            Presentador presentador = new Presentador(vista);
            vista.setPresentador(presentador);

            try {
                presentador.iniciarAplicacion();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            vista.setVisible();
        });
    }
}
