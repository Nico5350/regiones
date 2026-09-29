package modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GrafoTest {
    
    private Grafo grafo;

    @Before
    public void setUp() {
        // Creamos un grafo con 5 vértices
        grafo = new Grafo(5);
    }

    @Test
    public void agregarAristaTest() {
        //Agregamos arista entre el vértice 0 y el 1 con peso 15.5
        grafo.agregarArista(0, 1, 15.5);

        //Comprobamos arista existe y tiene el peso correcto
        assertTrue(grafo.existeArista(0, 1));
        assertTrue(grafo.existeArista(1, 0));
        assertEquals(15.5, grafo.obtenerPeso(0, 1), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarAristaVerticeInexistenteTest() {
        grafo.agregarArista(0, 10, 5.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void agregarAristaMismoVerticeTest() {
        grafo.agregarArista(2, 2, 1.0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testObtenerPesoAristaInexistente() {
        grafo.obtenerPeso(0, 4); 
    }
}