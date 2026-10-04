package modelo;

public class Arista implements Comparable<Arista> {
    private final int origen;
    private final int destino;
    private final double peso;

    public Arista(int origen, int destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public int getOrigen() { return origen; }
    public int getDestino() { return destino; }
    public double getPeso() { return peso; }

    @Override
    public int compareTo(Arista otra) {
        return Double.compare(this.peso, otra.peso);
    }
}