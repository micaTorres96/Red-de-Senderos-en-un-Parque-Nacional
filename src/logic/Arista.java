package logic;

import java.util.Objects;

public class Arista {
    private Vertice estacionOrigen;
    private Vertice estacionDestino;
    private double impactoAmbiental;

    public Arista(Vertice estacionOrigen, Vertice estacionDestino, double impactoAmbiental) {
        if (estacionOrigen == null || estacionDestino == null) {
            throw new IllegalArgumentException("Las estaciones no pueden ser nulas.");
        }
        if (impactoAmbiental < 0) {
            throw new IllegalArgumentException("El impacto ambiental no puede ser negativo.");
        }
        this.estacionOrigen = estacionOrigen;
        this.estacionDestino = estacionDestino;
        this.impactoAmbiental = impactoAmbiental;
    }

    public Vertice obtenerEstacionDeOrigen() {
        return estacionOrigen;
    }

    public Vertice obtenerEstacionDeDestino() {
        return estacionDestino;
    }
    
    public double getImpactoAmbiental() {
        return impactoAmbiental;
    }

    public void setImpactoAmbiental(double impactoAmbiental) {
        this.impactoAmbiental = impactoAmbiental;
    }

//Solo creados para test
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Arista arista = (Arista) obj;
        return Double.compare(arista.impactoAmbiental, impactoAmbiental) == 0 &&
               estacionOrigen.equals(arista.estacionOrigen) &&
               estacionDestino.equals(arista.estacionDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estacionOrigen, estacionDestino, impactoAmbiental);
    }
}

