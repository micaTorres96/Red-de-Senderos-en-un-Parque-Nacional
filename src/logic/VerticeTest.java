package logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VerticeTest {

    @Test
    public void testCrearVerticeValido() {
        Vertice vertice = new Vertice("v1", 0, 0.0, 0.0);
        assertEquals("v1", vertice.getNombre());
        assertEquals(0, vertice.getIdentificadorDeEstacion());
    }

    @Test
    public void testCrearVerticeConNombreNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice(null, 0, 0.0, 0.0);
        });
    }

    @Test
    public void testCrearVerticeConNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice("", 0, 0.0, 0.0);
        });
    }

    @Test
    public void testCrearVerticeConIndiceNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Vertice("v1", -1, 0.0, 0.0);
        });
    }

    @Test
    public void testEqualsYHashCode() {
        Vertice v1 = new Vertice("v1", 0, 0.0, 0.0);
        Vertice v2 = new Vertice("v1", 0, 0.0, 0.0);
        Vertice v3 = new Vertice("v2", 1, 0.0, 0.0);
        
        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
        assertEquals(v1.hashCode(), v2.hashCode());
    }

}
