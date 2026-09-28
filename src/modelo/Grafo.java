package modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Grafo {
    // Integer es el vértice destino, Double es el peso de la arista.
    private ArrayList<HashMap<Integer, Double>> vecinos;

    public Grafo(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("El grafo debe tener al menos 1 vértice.");
        }
        
        vecinos = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            vecinos.add(new HashMap<Integer, Double>());
        }
    }

    public void agregarArista(int i, int j, double peso) {
        verificarVertice(i);
        verificarVertice(j);
        
        if (i == j) {
            throw new IllegalArgumentException("No se permiten bucles.");
        }

        // Agregamos la arista en ambas direcciones
        vecinos.get(i).put(j, peso);
        vecinos.get(j).put(i, peso);
    }

    public boolean existeArista(int i, int j) {
        verificarVertice(i);
        // Retorna si existe la arista
        return vecinos.get(i).containsKey(j); 
    }
    
    public double obtenerPeso(int i, int j) {
        verificarVertice(i);
        if (!existeArista(i, j)) {
            throw new IllegalArgumentException("La arista no existe.");
        }
        return vecinos.get(i).get(j);
    }

    private void verificarVertice(int i) {
        if (i < 0 || i >= vecinos.size()) {
            throw new IllegalArgumentException("Vértice " + i + " fuera de rango.");
        }
    }
}
