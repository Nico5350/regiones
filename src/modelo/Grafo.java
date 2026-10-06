package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grafo {
    // Integer es el vértice destino, Double es el peso de la arista
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
        verificarVertice(j);
        return vecinos.get(i).containsKey(j); 
    }
    
    public double obtenerPeso(int i, int j) {
        verificarVertice(i);
        if (!existeArista(i, j)) {
            throw new IllegalArgumentException("La arista no existe entre " + i + " y " + j);
        }
        return vecinos.get(i).get(j);
    }

    public int cantidadVertices() {
        return vecinos.size();
    }

    public Set<Integer> obtenerVecinos(int i) {
        verificarVertice(i);
        return java.util.Collections.unmodifiableSet(vecinos.get(i).keySet());
    }

    public void eliminarArista(int i, int j) {
        verificarVertice(i);
        verificarVertice(j);

        // Se elimina la conexión en ambas direcciones
        vecinos.get(i).remove(j);
        vecinos.get(j).remove(i);
    }

    private void verificarVertice(int i) {
        if (i < 0 || i >= vecinos.size()) {
            throw new IllegalArgumentException("Vértice " + i + " fuera de rango.");
        }
    }
    
    public List<Arista> obtenerAristas() {
        List<Arista> aristas = new ArrayList<>();

        for (int i = 0; i < vecinos.size(); i++) {
            for (Map.Entry<Integer, Double> vecino : vecinos.get(i).entrySet()) {
                int j = vecino.getKey();

                // Evita agregar dos veces la misma arista
                if (i < j) {
                    aristas.add(new Arista(i, j, vecino.getValue()));
                }
            }
        }

        return aristas;
    }
}
