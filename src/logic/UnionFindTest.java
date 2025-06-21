package logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UnionFindTest {

    @Test
    public void testInicialmenteNoConectados() {
        UnionFind uf = new UnionFind(5);
        assertFalse(uf.conectados(0, 1));
        assertFalse(uf.conectados(2, 3));
    }

    @Test
    public void testUnionYFind() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        assertTrue(uf.conectados(0, 1));
        
        uf.union(1, 2);
        assertTrue(uf.conectados(0, 2));
    }

    @Test
    public void testConexionesIndirectas() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(2, 3);

        assertTrue(uf.conectados(0, 3));
        assertFalse(uf.conectados(0, 4));
    }

    @Test
    public void testNoDuplicaUniones() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 0); 
        assertTrue(uf.conectados(0, 1));
    }
}
