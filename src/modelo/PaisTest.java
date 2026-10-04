package modelo;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class PaisTest {

    private Pais pais;
    private boolean observadorNotificado;

    @Before
    public void setUp() {
        List<String> nombres = Arrays.asList("Buenos Aires", "Córdoba", "Santa Fe", "Mendoza");
        pais = new Pais(nombres);
        observadorNotificado = false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrearPaisSinProvincias() {
        new Pais(new ArrayList<String>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrearPaisConProvinciasDuplicadas() {
        new Pais(Arrays.asList("Salta", "Jujuy", "salta"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSimilaridadNegativa() {
        pais.definirSimilaridad(0, 1, -5.0);
    }

    @Test(expected = IllegalStateException.class)
    public void testDisenarRegionesGrafoNoConexo() {
        pais.definirSimilaridad(0, 1, 2.0);
        // ls provincias 2 y 3 quedan desconectadas
        pais.disenarRegiones(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisenarRegionesKMayorACantidadProvincias() {
        pais.definirSimilaridad(0, 1, 1.0);
        pais.definirSimilaridad(1, 2, 2.0);
        pais.definirSimilaridad(2, 3, 3.0);
        pais.disenarRegiones(5);
    }

    @Test
    public void testDisenarRegionesCorrectamente() {
        pais.definirSimilaridad(0, 1, 1.0);
        pais.definirSimilaridad(1, 2, 2.0);
        pais.definirSimilaridad(2, 3, 15.0); // arista mas pesada se debe eliminar para k=2

        List<Set<Provincia>> regiones = pais.disenarRegiones(2);
        assertEquals(2, regiones.size());

        Provincia mendoza = pais.obtenerProvincia(3);
        boolean mendozaSeparada = false;
        for (Set<Provincia> region : regiones) {
            if (region.size() == 1 && region.contains(mendoza)) {
                mendozaSeparada = true;
            }
        }
        assertTrue(mendozaSeparada);
    }

    @Test
    public void testNotificacionObservadorAlModificarGrafo() {
        pais.registrarObservador(new ObservadorPais() {
            @Override
            public void notificarCambioGrafo() {
                observadorNotificado = true;
            }
        });

        pais.definirSimilaridad(0, 1, 4.5);
        assertTrue("El observador debería ser notificado al agregar arista", observadorNotificado);

        observadorNotificado = false;
        pais.eliminarLimitrofe(0, 1);
        assertTrue("El observador debería ser notificado al eliminar arista", observadorNotificado);
    }
}