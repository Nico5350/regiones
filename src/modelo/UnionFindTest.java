package modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class UnionFindTest {

    private UnionFind uf;

    @Before
    public void setUp() {
        // Inicializamos la estructura para 5 vértices (0 al 4)
        uf = new UnionFind(5);
    }

    @Test
    public void testInicializacion() {
        // Inicialmente cada vértice está en su propia componente conexa
        assertFalse(uf.find(0, 1));
        assertFalse(uf.find(2, 3));
    }

    @Test
    public void testUnionYFind() {

        uf.union(0, 1);
        uf.union(2, 3);
        assertTrue(uf.find(0, 1));
        assertTrue(uf.find(2, 3));
        
        assertFalse(uf.find(1, 2));
    }

    @Test
    public void testUnionTransitiva() {
        uf.union(0, 1);
        uf.union(1, 2);
        
        assertTrue(uf.find(0, 2));
    }
}
