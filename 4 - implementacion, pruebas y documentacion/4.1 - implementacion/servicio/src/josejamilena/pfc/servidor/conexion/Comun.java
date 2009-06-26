package josejamilena.pfc.servidor.conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Metodos compartidos.
 * @author Jose Antonio Jamilena Daza.
 */
public final class Comun {

    /** Logger. */
    private static Logger logger = Logger.getLogger(Comun.class);
    /** Instancia Singleton. */
    private static Comun comun = null;
    /** Instancia de Estadisticas. */
    private static Estadisticas estadisticas = null;
    /** Hostname. */
    private static String hostname = "";
    /** properties de configuracion. */
    private Properties configProperties;

    /** Constructor. */
    private Comun() {
        try {
            this.iniciarPropiedades();
            this.estadisticas = new Estadisticas(
                    this.configProperties.getProperty(
                    "josejamilena.pfc.servidor.conexion.Estadisticas.driver"),
                    this.configProperties.getProperty(
                    "josejamilena.pfc.servidor.conexion.Estadisticas.url"),
                    this.configProperties.getProperty(
                    "josejamilena.pfc.servidor.conexion.Estadisticas."
                    + "estadisticas.properties"));
            this.hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Obtiene instancia de Comun.
     * @return instancia de Comun.
     */
    public static Comun getComun() {
        if (comun == null) {
            comun = new Comun();
        }
        return comun;
    }

    /**
     * Iniciar properties.
     */
    private void iniciarPropiedades() {
        try {
            this.configProperties = new Properties();
            this.configProperties.load(new FileInputStream(
                    "config.properties"));
            PropertyConfigurator.configure(this.configProperties.getProperty(
                    "josejamilena.pfc.servidor.conexion.Comun.log4j.properties"));
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    /**
     * @return the ConfigProperties
     */
    public Properties getConfigProperties() {
        return configProperties;
    }

    /**
     * @return the estadisticas
     */
    public static Estadisticas getEstadisticas() {
        return estadisticas;
    }

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }
}
