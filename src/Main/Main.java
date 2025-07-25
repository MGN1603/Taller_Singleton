
package Main;

import Vista.VentanaMascota;

//Clase Main para ejecutar el programa.
public class Main {
    public static void main(String[] args) {
        VentanaMascota ventanaMascota = new VentanaMascota();
        ventanaMascota.setVisible(true);
        System.out.println("Directorio actual: " + System.getProperty("user.dir"));
    }
}


