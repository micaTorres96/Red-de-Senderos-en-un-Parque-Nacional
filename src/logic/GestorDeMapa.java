package logic;

import java.util.ArrayList;
import java.util.List;

public class GestorDeMapa {
	private Grafo grafo;
	private int contadorId;
	
	public GestorDeMapa() {
		this.grafo = new Grafo();
		this.contadorId = 0;
	}
	
	public void agregarEstacion(String nombre, double latitud, double longitud) {
		if(grafo.contieneEstacionConNombre(nombre)) {
          throw new IllegalArgumentException("Ya existe una estación con ese nombre");
		}
		Vertice nuevaEstacion = new Vertice(nombre, contadorId++, latitud, longitud);
		grafo.agregarEstacion(nuevaEstacion);
	}
	
	public void agregarSendero(String nombreOrigen, String nombreDestino, double impactoAmbiental) {
		if(nombreOrigen.equals(nombreDestino)) {
	          throw new IllegalArgumentException("No se puede conectar una estación consigo misma.");
		}
		if(impactoAmbiental < 1 || impactoAmbiental > 10) {
	          throw new IllegalArgumentException("El impacto ambiental debe estar entre 1 y 10.");
		}
		Vertice origen = grafo.obtenerEstacionPorNombre(nombreOrigen);
		Vertice destino = grafo.obtenerEstacionPorNombre(nombreDestino);
		
		if(grafo.existeSenderoEntre(origen, destino)) {
	          throw new IllegalArgumentException("Ya existe un sendero entre esas dos estaciones.");
		}
		Arista nuevo = new Arista(origen, destino, impactoAmbiental);
		grafo.agregarSendero(nuevo);
	}
	
	public List<double[][]> obtenerCoordenadasAGM() {
	    List<Arista> agm = generarCaminoMinimoConKruskal();
	    List<double[][]> coordenadas = new ArrayList<>();

	    for (Arista a : agm) {
	        double[][] par = {
	            { a.obtenerEstacionDeOrigen().getLatitud(), a.obtenerEstacionDeOrigen().getLongitud() },
	            { a.obtenerEstacionDeDestino().getLatitud(), a.obtenerEstacionDeDestino().getLongitud() }
	        };
	        coordenadas.add(par);
	    }
	    return coordenadas;
	}

	public double obtenerImpactoTotalAGM() {
		double suma = 0;
		List<Arista> agm = generarCaminoMinimoConKruskal();
		for(Arista a : agm) {
			suma +=a.getImpactoAmbiental();
		}
		return suma;
	}

	public List<Arista> generarCaminoMinimoConKruskal() {
		Kruskal kruskal = new Kruskal();
		return kruskal.obtenerArbolGeneradorMinimo(grafo);
	}

	public List<String> obtenerNombresEstaciones(){
		List<String> nombres = new ArrayList<>();
		for(Vertice v : grafo.obtenerEstaciones()) {
			nombres.add(v.getNombre());
		}
		return nombres;
	}
	
	public List<double[][]> obtenerCoordenadasSenderos() {
	    List<Arista> senderos = grafo.obtenerSenderos();
	    List<double[][]> coordenadasSenderos = new ArrayList<>();

	    for (Arista a : senderos) {
	        Vertice origen = a.obtenerEstacionDeOrigen();
	        Vertice destino = a.obtenerEstacionDeDestino();

	        coordenadasSenderos.add(new double[][] {
	            { origen.getLatitud(), origen.getLongitud() },
	            { destino.getLatitud(), destino.getLongitud() }
	        });
	    }

	    return coordenadasSenderos;
	}
	
	public void reinicioMapa() {
		grafo.reiniciarGrafo();
		contadorId = 0;
	}
}