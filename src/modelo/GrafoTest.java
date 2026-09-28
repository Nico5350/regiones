package modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GrafoTest {
    
    private Grafo grafo;

    @Before
    public void setUp() {
        // Se ejecuta antes de cada test para inicializar el fixture
        // Creamos un grafo con 5 vértices
        grafo = new Grafo(5);
    }

    @Test
    public void agregarAristaTest() {
        //Agregamos una arista entre el vértice 0 y el 1 con peso 15.5
        grafo.agregarArista(0, 1, 15.5);

        // Verificación: Comprobamos que la arista existe y tiene el peso correcto
        assertTrue(grafo.existeArista(0, 1));
        assertTrue(grafo.existeArista(1, 0));
        assertEquals(15.5, grafo.obtenerPeso(0, 1), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarAristaVerticeInexistenteTest() {
        // Falla si el método no lanza la excepción
        grafo.agregarArista(0, 10, 5.0); // El vértice 10 está fuera de los límites
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void agregarAristaMismoVerticeTest() {
        grafo.agregarArista(2, 2, 1.0);
    }
}