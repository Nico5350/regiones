package modelo;

public class Arista implements Comparable<Arista> {
    int origen;
    int destino;
    double peso;

    public Arista(int origen, int destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public int compareTo(Arista otra) {
        return Double.compare(this.peso, otra.peso);
    }
}