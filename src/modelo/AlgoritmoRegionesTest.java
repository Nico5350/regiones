package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Set;

public class AlgoritmoRegionesTest {

    private Grafo grafo;
    private AlgoritmoRegiones algoritmo;

    @Before
    public void setUp() {
        //Grafo de 4 provincias 
        grafo = new Grafo(4);
        
        grafo.agregarArista(0, 1, 1.0);
        grafo.agregarArista(1, 2, 2.0);
        grafo.agregarArista(2, 3, 10.0);
        
        algoritmo = new AlgoritmoRegiones(grafo);
    }

    @Test
    public void testDividirEnDosRegiones() {
        List<Set<Integer>> regiones = algoritmo.calcularRegiones(2);
        
        //Deberíamos obtener 2 regiones
        assertEquals(2, regiones.size());
        boolean contieneRegionAislada = false;
        for (Set<Integer> region : regiones) {
            if (region.size() == 1 && region.contains(3)) {
                contieneRegionAislada = true;
            }
        }
        assertTrue("El vértice 3 debería haber quedado en una región separada", contieneRegionAislada);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testDividirEnRegionesInvalidas() {
        algoritmo.calcularRegiones(0); 
    }

    @Test
    public void testDividirEnUnaSolaRegion() {
        // Testeamos un caso de borde
        List<Set<Integer>> regiones = algoritmo.calcularRegiones(1);
        assertEquals(1, regiones.size());
        assertEquals(4, regiones.get(0).size());
    }
}