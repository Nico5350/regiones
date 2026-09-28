package modelo;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class AlgoritmoRegiones {
    
    private Grafo grafoOriginal;

    public AlgoritmoRegiones(Grafo grafo) {
        this.grafoOriginal = grafo;
    }

    public List<Set<Integer>> calcularRegiones(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("El número de regiones k debe ser mayor a 0.");
        }
        Grafo arbolMinimo = construirAGM();
        eliminarAristasMasPesadas(arbolMinimo, k - 1);
        return obtenerComponentesConexas(arbolMinimo);
    }

    private Grafo construirAGM() {
        return new Grafo(1);
    }

    private void eliminarAristasMasPesadas(Grafo mst, int cantidadAEliminar) {

    }
    private List<Set<Integer>> obtenerComponentesConexas(Grafo grafo) {
        return new ArrayList<>(); 
    }
}