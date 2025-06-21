package logic;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GrafoTest {

    private Grafo grafo;

    private Vertice crearVertice(String nombre, int id) {
        return new Vertice(nombre, id, 0.0, 0.0); 
    }

    @BeforeEach
    public void setUp() {
        grafo = new Grafo();
    }

    @Test
    public void testAgregarEstacion() {
        Vertice v1 = crearVertice("v1", 0);
        grafo.agregarEstacion(v1);
        List<Vertice> vertices = grafo.obtenerEstaciones();
        assertEquals(1, vertices.size());
        assertEquals("v1", vertices.get(0).getNombre());
    }

    @Test
    public void testAgregarSenderoValido() {
        Vertice v1 = crearVertice("v1", 0);
        Vertice v2 = crearVertice("v2", 1);
        grafo.agregarEstacion(v1);
        grafo.agregarEstacion(v2);
        Arista arista = new Arista(v1, v2, 5.0);
        grafo.agregarSendero(arista);
        List<Arista> aristas = grafo.obtenerSenderos();
        assertEquals(1, aristas.size());
        assertEquals(arista, aristas.get(0));
    }

    @Test
    public void testAgregarSenderoNulo() {
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarSendero(null));
    }

    @Test
    public void testExisteSenderoEntre() {
        Vertice v1 = crearVertice("A", 0);
        Vertice v2 = crearVertice("B", 1);
        grafo.agregarEstacion(v1);
        grafo.agregarEstacion(v2);
        Arista a = new Arista(v1, v2, 7.0);
        grafo.agregarSendero(a);
        assertTrue(grafo.existeSenderoEntre(v1, v2));
        assertTrue(grafo.existeSenderoEntre(v2, v1)); 
    }

    @Test
    public void testObtenerEstacionPorNombre() {
        Vertice v1 = crearVertice("Estacion Central", 0);
        grafo.agregarEstacion(v1);
        Vertice resultado = grafo.obtenerEstacionPorNombre("Estacion Central");
        assertNotNull(resultado);
        assertEquals("Estacion Central", resultado.getNombre());
    }

    @Test
    public void testContieneEstacionConNombre() {
        Vertice v1 = crearVertice("NodoA", 0);
        grafo.agregarEstacion(v1);
        assertTrue(grafo.contieneEstacionConNombre("NodoA"));
        assertFalse(grafo.contieneEstacionConNombre("NodoB"));
    }

    @Test
    public void testReiniciarGrafo() {
        Vertice v1 = crearVertice("X", 0);
        Vertice v2 = crearVertice("Y", 1);
        grafo.agregarEstacion(v1);
        grafo.agregarEstacion(v2);
        Arista a = new Arista(v1, v2, 3.0);
        grafo.agregarSendero(a);

        grafo.reiniciarGrafo();

        assertTrue(grafo.obtenerEstaciones().isEmpty());
        assertTrue(grafo.obtenerSenderos().isEmpty());
    }

    @Test
    public void testObtenerSenderosConectados() {
        Vertice v1 = crearVertice("S1", 0);
        Vertice v2 = crearVertice("S2", 1);
        Vertice v3 = crearVertice("S3", 2);
        grafo.agregarEstacion(v1);
        grafo.agregarEstacion(v2);
        grafo.agregarEstacion(v3);
        Arista a1 = new Arista(v1, v2, 4.0);
        Arista a2 = new Arista(v1, v3, 6.0);
        grafo.agregarSendero(a1);
        grafo.agregarSendero(a2);

        List<Arista> conectados = grafo.obtenerSenderosConectados(v1);
        assertEquals(2, conectados.size());
        assertTrue(conectados.contains(a1));
        assertTrue(conectados.contains(a2));
    }
}
