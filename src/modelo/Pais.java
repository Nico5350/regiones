package modelo;

import java.util.*;

public class Pais {
    private final List<Provincia> provincias;
    private final Grafo grafo;
    private final List<ObservadorPais> observadores;

    public Pais(List<String> nombresProvincias) {
        if (nombresProvincias == null || nombresProvincias.isEmpty()) {
            throw new IllegalArgumentException("El país debe tener al menos una provincia.");
        }
        this.provincias = new ArrayList<>();
        for (int i = 0; i < nombresProvincias.size(); i++) {
            String nombre = nombresProvincias.get(i);
            verificarNombreDuplicado(nombre);
            this.provincias.add(new Provincia(i, nombre));
        }
        this.grafo = new Grafo(provincias.size());
        this.observadores = new ArrayList<>();
    }

    public Pais(List<Provincia> listaProvincias, boolean usarObjetoProvincia) {
        if (listaProvincias == null || listaProvincias.isEmpty()) {
            throw new IllegalArgumentException("El país debe tener al menos una provincia.");
        }
        this.provincias = new ArrayList<>(listaProvincias);
        this.grafo = new Grafo(provincias.size());
        this.observadores = new ArrayList<>();
    }

    private void verificarNombreDuplicado(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la provincia no puede ser vacío.");
        }
        for (Provincia p : provincias) {
            if (p.getNombre().equalsIgnoreCase(nombre.trim())) {
                throw new IllegalArgumentException("Provincia duplicada: " + nombre);
            }
        }
    }

    public void registrarObservador(ObservadorPais observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    private void notificarObservadores() {
        for (ObservadorPais obs : observadores) {
            obs.notificarCambioGrafo();
        }
    }

    public void definirSimilaridad( int idProv1, int idProv2, double similaridad) {
        if (similaridad < 0) {
            throw new IllegalArgumentException("La similaridad no puede ser negativa.");
        }
        grafo.agregarArista(idProv1, idProv2, similaridad);
        notificarObservadores();
    }

    public void eliminarLimitrofe(int idProv1, int idProv2) {
        grafo.eliminarArista(idProv1, idProv2);
        notificarObservadores();
    }

    public boolean sonLimitrofes(int idProv1, int idProv2) {
        return grafo.existeArista(idProv1, idProv2);
    }

    public double obtenerSimilaridad(int idProv1, int idProv2) {
        return grafo.obtenerPeso(idProv1, idProv2);
    }

    public List<Set<Provincia>> disenarRegiones(int k) {
        AlgoritmoRegiones algoritmo = new AlgoritmoRegiones(grafo);
        List<Set<Integer>> regionesPorId = algoritmo.calcularRegiones(k);

        List<Set<Provincia>> resultado = new ArrayList<>();
        for (Set<Integer> regionIds : regionesPorId) {
            Set<Provincia> regionProvincias = new HashSet<>();
            for (int id : regionIds) {
                regionProvincias.add(provincias.get(id));
            }
            resultado.add(regionProvincias);
        }
        return resultado;
    }

    public int cantidadProvincias() {
        return provincias.size();
    }

    public Provincia obtenerProvincia(int id) {
        if (id < 0 || id >= provincias.size()) {
            throw new IllegalArgumentException("ID de provincia fuera de rango: " + id);
        }
        return provincias.get(id);
    }

    public List<Provincia> obtenerProvincias() {
        return Collections.unmodifiableList(provincias);
    }

    public Grafo obtenerGrafo() {
        return grafo;
    }
}