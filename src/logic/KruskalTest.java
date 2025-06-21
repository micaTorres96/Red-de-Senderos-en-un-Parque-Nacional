package logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KruskalTest {

    private Grafo grafo;
    private Vertice vertice1, vertice2, vertice3, vertice4;

    private Vertice crearVertice(String nombre, int id) {
        return new Vertice(nombre, id, 0.0, 0.0);
    }

    @BeforeEach
    public void setUp() {
        grafo = new Grafo();

        grafo.agregarEstacion(new Vertice("Espía 1", 0, 40.7128, -74.0060)); 
        grafo.agregarEstacion(new Vertice("Espía 2", 1, 34.0522, -118.2437));
        grafo.agregarEstacion(new Vertice("Espía 3", 2, 41.8781, -87.6298));
        grafo.agregarEstacion(new Vertice("Espía 4", 3, 51.5074, -0.1278));
        grafo.agregarEstacion(new Vertice("Espía 5", 4, 48.8566, 2.3522));

        List<Vertice> vertices = grafo.obtenerEstaciones();
        vertice1 = vertices.get(0);
        vertice2 = vertices.get(1);
        vertice3 = vertices.get(2);
        vertice4 = vertices.get(3);

        grafo.agregarSendero(new Arista(vertice1, vertice2, 5.0));
        grafo.agregarSendero(new Arista(vertice2, vertice3, 2.0));
        grafo.agregarSendero(new Arista(vertice1, vertice3, 8.0));
        grafo.agregarSendero(new Arista(vertice3, vertice4, 6.0));
        grafo.agregarSendero(new Arista(vertice2, vertice4, 7.0));
    }

    @Test
    public void testArbolGeneradorMinimo() {
        Kruskal kruskal = new Kruskal();
        List<Arista> arbolMinimo = kruskal.obtenerArbolGeneradorMinimo(grafo);

        assertEquals(3, arbolMinimo.size());
        assertTrue(arbolMinimo.contains(new Arista(vertice2, vertice3, 2.0)));
        assertTrue(arbolMinimo.contains(new Arista(vertice1, vertice2, 5.0)));
        assertTrue(arbolMinimo.contains(new Arista(vertice3, vertice4, 6.0)));
    }

    @Test
    public void testSinCiclos() {
        Kruskal kruskal = new Kruskal();
        List<Arista> arbolMinimo = kruskal.obtenerArbolGeneradorMinimo(grafo);

        UnionFind uf = new UnionFind(grafo.obtenerEstaciones().size());
        for (Arista arista : arbolMinimo) {
            Vertice origen = arista.obtenerEstacionDeOrigen();
            Vertice destino = arista.obtenerEstacionDeDestino();
            assertNotEquals(uf.find(origen.getIdentificadorDeEstacion()), uf.find(destino.getIdentificadorDeEstacion()));
            uf.union(origen.getIdentificadorDeEstacion(), destino.getIdentificadorDeEstacion());
        }
    }

    @Test
    public void testGrafoConCiclos() {
        Grafo grafoConCiclos = new Grafo();
        Vertice a = crearVertice("A", 0);
        Vertice b = crearVertice("B", 1);
        Vertice c = crearVertice("C", 2);

        grafoConCiclos.agregarEstacion(a);
        grafoConCiclos.agregarEstacion(b);
        grafoConCiclos.agregarEstacion(c);

        List<Vertice> vs = grafoConCiclos.obtenerEstaciones();
        a = vs.get(0);
        b = vs.get(1);
        c = vs.get(2);

        grafoConCiclos.agregarSendero(new Arista(a, b, 4.0));
        grafoConCiclos.agregarSendero(new Arista(b, c, 6.0));
        grafoConCiclos.agregarSendero(new Arista(a, c, 5.0));

        Kruskal kruskal = new Kruskal();
        List<Arista> mst = kruskal.obtenerArbolGeneradorMinimo(grafoConCiclos);

        assertEquals(2, mst.size());
        assertTrue(mst.contains(new Arista(a, b, 4.0)));
        assertTrue(mst.contains(new Arista(a, c, 5.0)));
        assertFalse(mst.contains(new Arista(b, c, 6.0)));
    }

    @Test
    public void testGrafoGrande() {
        int numVertices = 100;
        Grafo grafoGrande = new Grafo();

        for (int i = 0; i < numVertices; i++) {
            Vertice estacion = new Vertice("V" + i, i, Math.random() * 90, Math.random() * 180);
            grafoGrande.agregarEstacion(estacion);
        }

        List<Vertice> vertices = grafoGrande.obtenerEstaciones();

        Random rand = new Random();
        for (int i = 0; i < numVertices - 1; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                double peso = 1 + rand.nextDouble() * 9;
                grafoGrande.agregarSendero(new Arista(
                    vertices.get(i),
                    vertices.get(j),
                    peso
                ));
            }
        }

        int aristasEsperadas = (numVertices * (numVertices - 1)) / 2;
        assertEquals(aristasEsperadas, grafoGrande.obtenerSenderos().size());
    }
}

