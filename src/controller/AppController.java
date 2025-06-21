package controller;

import java.util.List;
import logic.GestorDeMapa;

public class AppController {
	private GestorDeMapa gestor;
	
	public AppController(GestorDeMapa gestor) {
		this.gestor = gestor;
	}
	
	public void agregarEstacion(String nombre, double latitud, double longitud) {
		gestor.agregarEstacion(nombre, latitud, longitud);
	}
	
	public void agregarSendero(String origen, String destino, int impacto) {
		gestor.agregarSendero(origen, destino, impacto); 
	}
	
	public boolean conectarEstacionesPorNombre(String nombre1, String nombre2, double impacto) {
		if(nombre1.equals(nombre2)) {
			return false;
		}
		
		gestor.agregarSendero(nombre1, nombre2, impacto);
		return true;
	}

	public List<double[][]> obtenerCoordenadasSenderos() {
		return gestor.obtenerCoordenadasSenderos();
	}
	
	public List<double[][]> obtenerCoordenadasAGM(){
		return gestor.obtenerCoordenadasAGM();
	}
	
	public double obtenerImpactoTotalAGM() {
		return gestor.obtenerImpactoTotalAGM();
	}
	
	public List<String> obtenerNombresEstaciones(){
		return gestor.obtenerNombresEstaciones();
	}
	
	public void reinicio() {
		gestor.reinicioMapa();
	}
}
