package launch;

import controller.AppController;
import logic.GestorDeMapa;
import view.VentanaMapa;

public class Main {

	public static void main(String[] args) {
		GestorDeMapa gestor = new GestorDeMapa();
		AppController controller = new AppController(gestor);
		VentanaMapa window = new VentanaMapa(controller);
		window.frame.setVisible(true);
	}

}
