package josejamilena.pfc.servidor.tcp;

/**
 * Excepción.
 * @author Jose Antonio Jamilena Daza
 */
public class TCPException extends Exception {

    /**
     * Creates a new instance of <code>TCPException</code> without detail message.
     */
    public TCPException() {
    }


    /**
     * Constructs an instance of <code>TCPException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TCPException(String msg) {
        super(msg);
    }
}
