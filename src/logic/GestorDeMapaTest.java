package logic;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestorDeMapaTest {

    private GestorDeMapa gestor;

    @BeforeEach
    public void setUp() {
        gestor = new GestorDeMapa();
    }

    @Test
    public void testAgregarEstacionValida() {
        gestor.agregarEstacion("A", -34.0, -58.0);
        List<String> nombres = gestor.obtenerNombresEstaciones();
        assertEquals(1, nombres.size());
        assertEquals("A", nombres.get(0));
    }

    @Test
    public void testAgregarEstacionDuplicada() {
        gestor.agregarEstacion("A", -34.0, -58.0);
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.agregarEstacion("A", -35.0, -59.0);
        });
    }

    @Test
    public void testAgregarSenderoValido() {
        gestor.agregarEstacion("A", -34.0, -58.0);
        gestor.agregarEstacion("B", -35.0, -59.0);
        gestor.agregarSendero("A", "B", 3.5);
        List<double[][]> senderos = gestor.obtenerCoordenadasSenderos();
        assertEquals(1, senderos.size());
    }

    @Test
    public void testAgregarSenderoRepetido() {
        gestor.agregarEstacion("A", -34.0, -58.0);
        gestor.agregarEstacion("B", -35.0, -59.0);
        gestor.agregarSendero("A", "B", 4.0);
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.agregarSendero("A", "B", 4.5);
        });
    }

    @Test
    public void testAgregarSenderoConImpactoInvalido() {
        gestor.agregarEstacion("A", -34.0, -58.0);
        gestor.agregarEstacion("B", -35.0, -59.0);
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.agregarSendero("A", "B", 0.5);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.agregarSendero("A", "B", 15);
        });
    }

    @Test
    public void testObtenerAGMVacio() {
        List<double[][]> agmCoords = gestor.obtenerCoordenadasAGM();
        assertTrue(agmCoords.isEmpty());
    }

    @Test
    public void testAGMConTresEstaciones() {
        gestor.agregarEstacion("A", -34.0, -58.0);
        gestor.agregarEstacion("B", -35.0, -59.0);
        gestor.agregarEstacion("C", -36.0, -60.0);
        gestor.agregarSendero("A", "B", 3);
        gestor.agregarSendero("B", "C", 2);
        gestor.agregarSendero("A", "C", 10);

        List<Arista> agm = gestor.generarCaminoMinimoConKruskal();
        assertEquals(2, agm.size());
        assertTrue(agm.stream().anyMatch(a -> a.getImpactoAmbiental() == 2));
        assertTrue(agm.stream().anyMatch(a -> a.getImpactoAmbiental() == 3));
    }

    @Test
    public void testReinicioMapa() {
        gestor.agregarEstacion("A", -34.0, -58.0);
        gestor.agregarEstacion("B", -35.0, -59.0);
        gestor.reinicioMapa();
        assertEquals(0, gestor.obtenerNombresEstaciones().size());
        assertEquals(0, gestor.obtenerCoordenadasSenderos().size());
    }
}
