package logic;

import java.util.Objects;

public class Vertice {
    private String nombre;
    private double latitud;
    private double longitud;
    private int id;
    
    public Vertice(String nombre, int identificadorDeEstacion, double latitud, double longitud){
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del vértice no puede ser nulo o vacío.");
        }
        if (identificadorDeEstacion < 0) {
            throw new IllegalArgumentException("El índice del vértice no puede ser negativo.");
        }
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = identificadorDeEstacion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getIdentificadorDeEstacion() {
        return id;
    }
    
    public double getLatitud() {
    	return latitud;
    }
    
    public double getLongitud() {
    	return longitud;
    }

//Solo creados para test
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertice vertice = (Vertice) obj;
        return id == vertice.getIdentificadorDeEstacion() &&
               nombre.equals(vertice.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, id);
    }
}


