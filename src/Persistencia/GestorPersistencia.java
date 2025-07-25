package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//clase importante donde se gestiona la persistencia de los datos por medio de una sola instancia "Singleton" para un archivo.
public class GestorPersistencia {

    private static GestorPersistencia instancia;

    private GestorPersistencia() {
    }

    public static synchronized GestorPersistencia getInstance() {
        if (instancia == null) {
            instancia = new GestorPersistencia();
        }
        return instancia;
    }

    public <T extends Serializable> void guardarLista(String ruta, List<T> lista) {
        try {
            File archivo = new File(ruta);

            //Asegura que la carpeta exista
            File carpeta = archivo.getParentFile();
            if (carpeta != null && !carpeta.exists()) {
                carpeta.mkdirs(); // Crea "Data/" si no existe
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                oos.writeObject(lista);
            }

        } catch (IOException e) {
            System.err.println("❌ Error al guardar datos: " + e.getMessage());
        }
    }
    
    public <T extends Serializable> List<T> cargarLista(String ruta) {
    File archivo = new File(ruta);
    if (!archivo.exists()) {
        return new ArrayList<>(); // Retorna lista vacía si el archivo no existe aún
    }

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
        return (List<T>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("❌ Error al leer datos: " + e.getMessage());
    }
    return null;
}

}
