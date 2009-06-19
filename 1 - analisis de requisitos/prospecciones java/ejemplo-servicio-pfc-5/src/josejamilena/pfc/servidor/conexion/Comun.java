package josejamilena.pfc.servidor.conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Metodos compartidos.
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class Comun {

    static Logger logger = Logger.getLogger(Comun.class);
    /** */
    static private Comun comun = null;
    /** */
    static private Estadisticas estadisticas = null;
    /** properties de configuracion. */
    private Properties configProperties;

    /** Constructor. */
    private Comun() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.iniciarPropiedades();
            this.estadisticas = new Estadisticas();
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        }
    }

    /** */
    static public Comun getComun() {
        if (comun == null) {
            comun = new Comun();
        }
        return comun;
    }

    /** */
    private void iniciarPropiedades() {
        try {
            this.configProperties = new Properties();
            this.configProperties.load(new FileInputStream("config.properties"));
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
}
