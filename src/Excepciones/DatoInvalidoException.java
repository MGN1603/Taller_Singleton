package Excepciones;

//clase de tipo excepcion para lanzar un mensaje correspondiente al error que vaya a ocurrir.
public class DatoInvalidoException extends Exception {

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
