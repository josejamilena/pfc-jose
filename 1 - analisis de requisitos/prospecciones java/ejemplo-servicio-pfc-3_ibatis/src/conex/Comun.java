package conex;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Metodos compartidos.
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class Comun {

    /** properties. */
    public static Properties PROPIEDADES_DE_CONFIGURACION;

    /** Constructor. */
    public Comun() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /** */
    public static void iniciarPropiedades() {
        try {
            PROPIEDADES_DE_CONFIGURACION = new Properties();
            PROPIEDADES_DE_CONFIGURACION.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
