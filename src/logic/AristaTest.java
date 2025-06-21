package logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AristaTest {

    @Test
    public void testCrearAristaValida() {
        Vertice v1 = new Vertice("v1", 0, 0, 0);
        Vertice v2 = new Vertice("v2", 1, 1, 1);
        Arista arista = new Arista(v1, v2, 2.5);

        assertEquals(v1, arista.obtenerEstacionDeOrigen());
        assertEquals(v2, arista.obtenerEstacionDeDestino());
        assertEquals(2.5, arista.getImpactoAmbiental());
    }

    @Test
    public void testCrearAristaConOrigenNulo() {
        Vertice v2 = new Vertice("v2", 1, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Arista(null, v2, 2);
        });
    }

    @Test
    public void testCrearAristaConDestinoNulo() {
        Vertice v1 = new Vertice("v1", 0, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            new Arista(v1, null, 2);
        });
    }
}

