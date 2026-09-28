package modelo;

public class UnionFind {
    private int[] padre;
    private int[] tamano; 

    public UnionFind(int n) {
        padre = new int[n];
        tamano = new int[n];
        for (int i = 0; i < n; i++) {
            padre[i] = i;
            tamano[i] = 1;
        }
    }

 
    private int root(int i) {
        while (padre[i] != i) {
            i = padre[i];
        }
        return i;
    }
    // Para determinar si dos vértices están en la misma componente conexa
    public boolean find(int i, int j) {
        return root(i) == root(j);
    }

    // La única operación que modifica la estructura es la unión
    public void union(int i, int j) {
        int rootI = root(i);
        int rootJ = root(j);

        if (rootI == rootJ) {
            return; // Ya están en la misma componente
        }

        // Hacemos que la raíz del menor árbol apunte a la raíz del mayor
        if (tamano[rootI] < tamano[rootJ]) {
            padre[rootI] = rootJ;
            tamano[rootJ] += tamano[rootI];
        } else {
            padre[rootJ] = rootI;
            tamano[rootI] += tamano[rootJ];
        }
    }
}