package conex;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
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
    /** fichero de consultas */
    private static String FicheroConsultas = "ejemplo.sql";
    /** */
    public conex.LanzarConsultasDeFichero lcf;
    /** */
    public conex.Estadisticas estadisticas;
    /** */
    public conex.Conexion conexion;

    /** Constructor. */
    public Comun() throws ClassNotFoundException {
        try {
            conexion = new conex.Conexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** */
    public static void iniciarPropiedades() {
        try {
            PROPIEDADES_DE_CONFIGURACION = new Properties();
            PROPIEDADES_DE_CONFIGURACION.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(Comun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Devuelve el tipo de consulta, a partir de una sentencia SQL.
     * @param str sentencia SQL.
     * @return devuelve un objeto de TipoConsulta.
     */
    public static TipoConsulta tipoConsulta(String str) {
        TipoConsulta res = TipoConsulta.NO_DEFINIDO;
        if (str.contains("INSERT") || str.contains("DELETE") || str.contains("UPDATE")) {
            res = TipoConsulta.MANIPULACION_DE_DATOS;
        } else if (str.contains("CREATE") || str.contains("DROP")) {
            res = TipoConsulta.MANIPULACION_DE_TABLAS;
        } else if (str.contains("SELECT")) {
            res = TipoConsulta.SELECION;
        }
        return res;
    }

    /**
     * Convierte el TipoConsulta a cadena de caracteres.
     * @param tc objeto TipoConsulta.
     * @return convierte el TipoConsulta a cadena de caracteres.
     */
    public static String tipoCosultaString(TipoConsulta tc) {
        String res = "";
        if (tc == TipoConsulta.MANIPULACION_DE_DATOS) {
            res = "MANIPULACION_DE_DATOS";
        } else if (tc == TipoConsulta.MANIPULACION_DE_TABLAS) {
            res = "MANIPULACION_DE_TABLAS";
        } else if (tc == TipoConsulta.NO_DEFINIDO) {
            res = "NO_DEFINIDO";
        } else if (tc == TipoConsulta.SELECION) {
            res = "SELECION";
        }
        return res;
    }

    /**
     * obtiene el nombre del fichero SQL
     * @return fichero SQL
     */
    public static String getFicheroConsultas() {
        return FicheroConsultas;
    }
}
