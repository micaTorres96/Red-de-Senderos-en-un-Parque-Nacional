package logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    public List<Arista> obtenerArbolGeneradorMinimo(Grafo grafo) {
        List<Arista> arbol = new ArrayList<>();
        List<Arista> arista = grafo.obtenerSenderos().stream().sorted(Comparator.comparingDouble(Arista::getImpactoAmbiental)).toList();
    
        UnionFind uf = new UnionFind(grafo.obtenerEstaciones().size());
    
        for (Arista a : arista) {
            int origen = a.obtenerEstacionDeOrigen().getIdentificadorDeEstacion();
            int destino = a.obtenerEstacionDeDestino().getIdentificadorDeEstacion();
    
            if (uf.find(origen) != uf.find(destino)) {
                uf.union(origen, destino);
                arbol.add(a);
    
                if (arbol.size() == grafo.obtenerEstaciones().size() - 1) {
                    break;
                }
            }
        }
        return arbol;
    }
}






