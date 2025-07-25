package DAO;

import DTOs.MascotaDTO;
import Persistencia.GestorPersistencia;
import java.util.ArrayList;
import java.util.List;

//DAO que gestiona los metodos crud para el guardado el eliminar y el listar de el archivo correspondiente.
public class MascotaDao {

    private final String RUTA = "Data/mascotas.dat";
    private final GestorPersistencia gestor = GestorPersistencia.getInstance();

    public void guardar(MascotaDTO mascota) {
        List<MascotaDTO> lista = listar();
        lista.add(mascota);
        gestor.guardarLista(RUTA, lista);
    }

    public List<MascotaDTO> listar() {
        List<MascotaDTO> lista = gestor.cargarLista(RUTA);
        return lista != null ? lista : new ArrayList<>();  
    }

    public void eliminar(int indice) {
        List<MascotaDTO> lista = listar();
        if (indice >= 0 && indice < lista.size()) {
            lista.remove(indice);
            gestor.guardarLista(RUTA, lista);
        }
    }

    public void actualizar(int indice, MascotaDTO mascotaActualizada) {
        List<MascotaDTO> lista = listar();
        if (indice >= 0 && indice < lista.size()) {
            lista.set(indice, mascotaActualizada);
            gestor.guardarLista(RUTA, lista);
        }
    }
}
