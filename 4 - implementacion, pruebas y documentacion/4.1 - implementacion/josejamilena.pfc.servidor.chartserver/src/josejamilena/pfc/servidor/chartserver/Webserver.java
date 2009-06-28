package josejamilena.pfc.servidor.chartserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor web para mostras gráficas con Google Charts.
 * @author Jose Antonio Jamilena Daza
 */
public final class Webserver {

    /** Socket servidor. */
    private static ServerSocket serverSocket;

    /** Puerto HTTP por defecto, el 80. */
    private static final int DEFAULT_HTTP_PORT = 80;

    /** Puerto HTTP empleado. */
    private static int httpPort;

    /** No se permiten instancias de la clase. */
    private Webserver() {
    }

    /**
     * Método principal.
     * @param args puerto HTTP que abrirá, si se indica, usa el 80.
     * @throws java.io.IOException Error.
     */
    public static void main(final String[] args) throws IOException {
        if (args.length < 1) {
            // Por defecto, inicia en el puerto 80
            httpPort = DEFAULT_HTTP_PORT;
            serverSocket = new ServerSocket(httpPort);
        } else {
            // Se inicia en en puerto pasado por argumento
            try {
                httpPort = Integer.parseInt(args[0]);
                serverSocket = new ServerSocket(httpPort);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            try {
                Socket s = serverSocket.accept();
                new ClientHandler(s);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

