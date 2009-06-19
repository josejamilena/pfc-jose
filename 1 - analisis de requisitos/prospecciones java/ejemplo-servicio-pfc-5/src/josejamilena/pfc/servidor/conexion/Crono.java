package josejamilena.pfc.servidor.conexion;


/**
 * Crono se encarga de medir el tiempo.
 * 
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class Crono {
    /** tiempo inicial */
    private java.util.Date t0;
    /** tiempo final */
    private java.util.Date t1;

    /**
     * Constructor.
     */
    public Crono() {
        super();
    }

    /**
     * Inicia la cuenta.
     */
    public void inicializa() {
        t0 = new java.util.Date();
    }

    /**
     * Devuelve el tiempo desde el inicio de cuenta en segundos.
     * 
     * @return tiempo desde el inicio.
     */
    public long tiempo() {
        t1 = new java.util.Date();
        return (t1.getTime() - t0.getTime());
    }
}

