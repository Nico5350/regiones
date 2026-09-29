package modelo;

import java.util.*;

public class AlgoritmoRegiones {
    
    private Grafo grafoOriginal;

    public AlgoritmoRegiones(Grafo grafo) {
        this.grafoOriginal = grafo;
    }

    public List<Set<Integer>> calcularRegiones(int k) {
        if (k <= 0) throw new IllegalArgumentException("k debe ser mayor a 0.");

        // Construir el Árbol Generador Mínimo
        Grafo arbolMinimo = construirAGM();

        // Elimina las k-1 aristas de mayor peso
        eliminarAristasMasPesadas(arbolMinimo, k - 1);

        // Obtener las componentes conexas (Regiones)
        return obtenerComponentesConexas(arbolMinimo);
    }

    private Grafo construirAGM() {
        int n = grafoOriginal.cantidadVertices(); 
        Grafo mst = new Grafo(n);
        UnionFind uf = new UnionFind(n);
        
        List<Arista> todasLasAristas = obtenerTodasLasAristas(grafoOriginal);
        Collections.sort(todasLasAristas); 
        
        // Algoritmo de Kruskal
        for (Arista e : todasLasAristas) {
            if (!uf.find(e.origen, e.destino)) {
                uf.union(e.origen, e.destino);
                mst.agregarArista(e.origen, e.destino, e.peso);
            }
        }
        return mst;
    }

    private void eliminarAristasMasPesadas(Grafo mst, int cantidadAEliminar) {
        if (cantidadAEliminar <= 0) return;
        
        List<Arista> aristasMst = obtenerTodasLasAristas(mst);
        //sacar las más pesadas primero
        aristasMst.sort(Collections.reverseOrder()); 
        
        for (int i = 0; i < cantidadAEliminar && i < aristasMst.size(); i++) {
            Arista aEliminar = aristasMst.get(i);
            mst.eliminarArista(aEliminar.origen, aEliminar.destino);
        }
    }

    private List<Set<Integer>> obtenerComponentesConexas(Grafo grafo) {
        int n = grafo.cantidadVertices();
        boolean[] marcados = new boolean[n];
        List<Set<Integer>> regiones = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!marcados[i]) {
                Set<Integer> nuevaRegion = recorrerBFS(grafo, i, marcados);
                regiones.add(nuevaRegion);
            }
        }
        return regiones;
    }

    //BFS usando una Cola
    private Set<Integer> recorrerBFS(Grafo grafo, int inicio, boolean[] marcados) {
        Set<Integer> region = new HashSet<>();
        Queue<Integer> cola = new LinkedList<>();
        
        cola.add(inicio);
        marcados[inicio] = true; //inicio

        while (!cola.isEmpty()) {
            int actual = cola.poll(); // Sacar de L (L := L\{i})
            region.add(actual);

            // Agregar todos los vecinos no marcados
            for (int vecino : grafo.obtenerVecinos(actual)) { 
                if (!marcados[vecino]) {
                    marcados[vecino] = true;
                    cola.add(vecino);
                }
            }
        }
        return region;
    }

    // Método auxiliar 
    private List<Arista> obtenerTodasLasAristas(Grafo g) {
        List<Arista> lista = new ArrayList<>();
        int n = g.cantidadVertices();
        for (int i = 0; i < n; i++) {
            for (int vecino : g.obtenerVecinos(i)) {
                if (i < vecino) { 
                    lista.add(new Arista(i, vecino, g.obtenerPeso(i, vecino)));
                }
            }
        }
        return lista;
    }
}