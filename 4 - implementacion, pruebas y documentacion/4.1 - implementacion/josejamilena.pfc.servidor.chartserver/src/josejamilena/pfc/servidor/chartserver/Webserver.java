package josejamilena.pfc.servidor.chartserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servidor web para mostras gráficas con Google Charts.
 * @author Jose Antonio Jamilena Daza
 */
public final class Webserver {

    /** properties de configuracion. */
    private static Properties configProperties;

    /** Logger. */
    private static Logger logger = Logger.getLogger(Webserver.class);

    /** Socket servidor. */
    private static ServerSocket serverSocket;

    /** Puerto HTTP empleado. */
    private static int httpPort;

    /** No se permiten instancias de la clase. */
    private Webserver() {
    }

    /**
     * Método principal.
     * @param args puerto HTTP que abrirá, si se indica, usa el 80.
     * @throws java.io.IOException Error.
     * @throws ClassNotFoundException no encuentra el driver JDBC
     */
    public static void main(final String[] args)
            throws IOException, ClassNotFoundException {
        PropertyConfigurator.configure("log4j.properties");
        Properties prop = new Properties();
        prop.load(new FileInputStream("config.properties"));
        Webserver.setConfigProperties(prop);
        httpPort = Integer.parseInt(getConfigProperties().getProperty(
                "josejamilena.pfc.servidor.chartserver.TCPPort"));
        serverSocket = new ServerSocket(httpPort);
        logger.info("josejamilena.pfc.servidor.chartserver");
        logger.info("Iniciado en puerto: " + httpPort);
        logger.info(getConfigProperties().getProperty(
                    "josejamilena.pfc.servidor.chartserver.driver"));
        Class.forName(getConfigProperties().getProperty(
                    "josejamilena.pfc.servidor.chartserver.driver"));
        while (true) {
            try {
                Socket s = serverSocket.accept();
                new ClientHandler(s);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @return the configProperties
     */
    public static Properties getConfigProperties() {
        return configProperties;
    }

    /**
     * @param aConfigProperties the configProperties to set
     */
    public static void setConfigProperties(final Properties aConfigProperties) {
        configProperties = aConfigProperties;
    }

}

