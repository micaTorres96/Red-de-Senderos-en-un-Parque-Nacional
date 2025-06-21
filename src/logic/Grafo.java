package logic;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private List<Vertice> estaciones;
    private List<Arista> senderos;

    public Grafo() {
        this.estaciones = new ArrayList<>();
        this.senderos = new ArrayList<>();
    }

    public void agregarEstacion(Vertice estacion) {
    	estaciones.add(estacion);
    }
    
    public void agregarSendero(Arista sendero) {
    	if(sendero == null) {
          throw new IllegalArgumentException("No se puede agregar un sendero nulo.");
    	}
    	senderos.add(sendero);
    }
    
    public boolean contieneEstacionConNombre(String nombre) {
    	for(Vertice estacion : estaciones) {
    		if(estacion.getNombre().equals(nombre)) {
    			return true;
    		}
    	}
    	return false;
    }
 
    public Vertice obtenerEstacionPorNombre(String nombre) {
        for (Vertice estacion : estaciones) {
            if (estacion.getNombre().equals(nombre)) {
                return estacion;
            }
        }
        throw new RuntimeException("No existe una estación con ese nombre.");
    }

    public boolean existeSenderoEntre(Vertice origen, Vertice destino) {
        for (Arista existente : senderos) {
            boolean mismaConexion =
                (existente.obtenerEstacionDeOrigen().equals(origen) && existente.obtenerEstacionDeDestino().equals(destino)) ||
                (existente.obtenerEstacionDeOrigen().equals(destino) && existente.obtenerEstacionDeDestino().equals(origen));
            if (mismaConexion) {
                return true;
            }
        }
        return false;
    }
    
    public Vertice obtenerEstacionPorId(int identificadorDeEstacion) {
        for (Vertice estacion : estaciones) {
            if (estacion.getIdentificadorDeEstacion() == identificadorDeEstacion) {
                return estacion;
            }
        }
        throw new RuntimeException("No existe la estación con ese identificador.");
    }

    public int obtenerIdEstacionPorNombre(String nombre) {
        for (Vertice estacion : estaciones) {
            if (estacion.getNombre().equals(nombre)) {
                return estacion.getIdentificadorDeEstacion();
            }
        }
        throw new RuntimeException("No existe una estación con ese nombre.");
    }

    public Arista obtenerSenderoEntre(int identificadorDeEstacion1, int identificadorDeEstacion2) {
        for (Arista arista : senderos) {
            int origen = arista.obtenerEstacionDeOrigen().getIdentificadorDeEstacion();
            int destino = arista.obtenerEstacionDeDestino().getIdentificadorDeEstacion();
            if ((origen == identificadorDeEstacion1 && destino == identificadorDeEstacion2)
             || (origen == identificadorDeEstacion2 && destino == identificadorDeEstacion1)) {
                return arista;
            }
        }
        throw new RuntimeException("No existe un sendero entre esas estaciones.");
    }
    
    public List<Arista> obtenerSenderosConectados(Vertice e) {
        List<Arista> conectados = new ArrayList<>();
        for (Arista arista : senderos) {
            if (arista.obtenerEstacionDeOrigen().equals(e) || arista.obtenerEstacionDeDestino().equals(e)) {
                conectados.add(arista);
            }
        }
        return conectados;
    }
    
    public List<Vertice> obtenerEstaciones() {
        return new ArrayList<>(estaciones);
    }

    public List<Arista> obtenerSenderos() {
        return new ArrayList<>(senderos);
    }

    public void reiniciarGrafo() {
    	estaciones.clear();
    	senderos.clear();
    }
} 