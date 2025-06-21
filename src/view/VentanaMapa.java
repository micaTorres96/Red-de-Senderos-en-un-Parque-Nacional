package view;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.List;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import controller.AppController;

public class VentanaMapa 
{
	public JFrame frame;
	private JPanel panelMapa;
	private JPanel panelControles;
	private JMapViewer mapa;
    private JLabel estacionDestino;
    private JLabel estacionOrigen;
    private JButton conectarEstaciones;
    private JButton generarAGM;
    private JButton reinicioMapa;
	private JComboBox<String> comboEstacionOrigen;
	private JComboBox<String> comboEstacionDestino;
    private AppController controller;

	public VentanaMapa(AppController controller) 
	{
		this.controller = controller;
		initialize();
	}
	
	private void initialize() 
	{
		frame = new JFrame("Mapa de parque nacional 'Campos del Tuyú");
		frame.setBounds(100, 100, 725, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panelMapa = new JPanel();
		panelMapa.setBounds(10, 11, 437, 446);
		frame.getContentPane().add(panelMapa);
		
		panelControles = new JPanel();
		panelControles.setBounds(457, 11, 242, 446);
		frame.getContentPane().add(panelControles);		
		panelControles.setLayout(null);
		
		mapa = new JMapViewer();
		mapa.setDisplayPosition(new Coordinate(-36.35, -56.866666666667), 10);
		panelMapa.add(mapa);
		
		estacionOrigen = new JLabel("Estacion origen");
		estacionOrigen.setBounds(10,45,200,23);
		panelControles.add(estacionOrigen);
		
		comboEstacionOrigen = new JComboBox<>();
		comboEstacionOrigen.setBounds(10, 70, 200, 23);
		panelControles.add(comboEstacionOrigen);

        estacionDestino = new JLabel("Estación destino:");
        estacionDestino.setBounds(10, 100, 200, 23);
        panelControles.add(estacionDestino);

        comboEstacionDestino = new JComboBox<>();
        comboEstacionDestino.setBounds(10, 125, 200, 23);
        panelControles.add(comboEstacionDestino);
        
        conectarEstaciones = new JButton("Formar Sendero");
        conectarEstaciones.setBounds(10,160,200,23);
        panelControles.add(conectarEstaciones);
        
		JButton botonAgregar = new JButton("Agregar Estacion");
		botonAgregar.setBounds(10,10,200,23);
		panelControles.add(botonAgregar);
		
		botonAgregar.addActionListener(e -> {
			mapa.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						Coordinate  coordenadas = (Coordinate) mapa.getPosition(e.getPoint());
						String nombre = JOptionPane.showInputDialog("Nombre nueva Estacion: ");
						controller.agregarEstacion(nombre, coordenadas.getLat(), coordenadas.getLon());
						mapa.addMapMarker(new MapMarkerDot(nombre, coordenadas));
						actualizarComboBoxes();
					}
					mapa.removeMouseListener(this);
				}
			});
		});

		conectarEstaciones.addActionListener(e -> {
                String nombre1 = (String) comboEstacionOrigen.getSelectedItem();
                String nombre2 = (String) comboEstacionDestino.getSelectedItem();
                String input = JOptionPane.showInputDialog("Impacto ambiental:");
                
                if (input == null || nombre1 == null || nombre2 == null) return;
                
                if (input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Debe ingresar un valor numérico para el impacto ambiental.");
                    return;
                }
                
                double impacto = Double.parseDouble(input);
                if(impacto < 1 || impacto > 10 ) {
                    JOptionPane.showMessageDialog(frame, "El impacto debe estar entre 1 y 10.");
                    return; }
                
                boolean ok = controller.conectarEstacionesPorNombre(nombre1, nombre2, impacto);
                if(ok) {
                	agregarSenderoAlMapa(controller.obtenerCoordenadasSenderos());
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Error. No se pueden conectar estaciones iguales o senderos ya existentes.");
                }
		});
        
		generarAGM = new JButton("Menor impacto ambiental");
		generarAGM.setBounds(10, 200, 220, 23);
		panelControles.add(generarAGM);	
		
		generarAGM.addActionListener(e -> {
		    List<double[][]> coordenadas = controller.obtenerCoordenadasAGM();

		    if (coordenadas.isEmpty()) {
		        JOptionPane.showMessageDialog(frame, "No hay suficientes senderos para formar el camino.");
		        return;
		    }

		    for (double[][] par : coordenadas) {
		        Coordinate c1 = new Coordinate(par[0][0], par[0][1]);
		        Coordinate c2 = new Coordinate(par[1][0], par[1][1]);
		        MapPolygon sendero = new MapPolygonImpl(c1, c2, c2);
		        sendero.getStyle().setColor(Color.RED);
		        mapa.addMapPolygon(sendero);
		    }

		    double impacto = controller.obtenerImpactoTotalAGM();
		    JOptionPane.showMessageDialog(frame, "Impacto ambiental total del camino: " + impacto);
		});
		
		reinicioMapa = new JButton("Reiniciar");
		reinicioMapa.setBounds(10,260,220,23);
		panelControles.add(reinicioMapa);
		
		reinicioMapa.addActionListener(e -> {
			controller.reinicio();
			mapa.removeAllMapPolygons();
			mapa.removeAllMapMarkers();
			actualizarComboBoxes();
			mapa.repaint();
		});
	}
	
        private void actualizarComboBoxes() {
            comboEstacionOrigen.removeAllItems();
            comboEstacionDestino.removeAllItems();

            List<String> nombres = controller.obtenerNombresEstaciones();
            for (String nombreEstacion : controller.obtenerNombresEstaciones()) {
                comboEstacionOrigen.addItem(nombreEstacion);
                comboEstacionDestino.addItem(nombreEstacion);
            }
            conectarEstaciones.setEnabled(nombres.size() >= 2);
        }
        
        private void agregarSenderoAlMapa(List<double[][]> coordenadas) {
        for (double[][] par : coordenadas) {
            double lat1 = par[0][0];
            double lon1 = par[0][1];
            double lat2 = par[1][0];
            double lon2 = par[1][1];

            MapPolygon sendero = new MapPolygonImpl(
                new Coordinate(lat1, lon1),
                new Coordinate(lat2, lon2),
                new Coordinate(lat2, lon2) 
            );
            mapa.addMapPolygon(sendero);
        	}
        }
}