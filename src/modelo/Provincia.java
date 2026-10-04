package modelo;

import java.util.Objects;

public class Provincia {
    private final int id;
    private final String nombre;
    private final double latitud;
    private final double longitud;

    public Provincia(int id, String nombre, double latitud, double longitud) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID de la provincia no puede ser negativo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la provincia no puede estar vacío.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Provincia(int id, String nombre) {
        this(id, nombre, 0.0, 0.0);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Provincia otra = (Provincia) obj;
        return id == otra.id && Objects.equals(nombre, otra.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}