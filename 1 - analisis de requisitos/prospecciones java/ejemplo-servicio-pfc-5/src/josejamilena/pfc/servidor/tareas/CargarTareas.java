package josejamilena.pfc.servidor.tareas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import josejamilena.pfc.servidor.conexion.Comun;
import org.apache.log4j.Logger;

/**
 * Cargador de tareas.
 * @author Jose Antonio Jamilena Daza.
 */
public class CargarTareas {
    /** Logger. */
    private static Logger logger = Logger.getLogger(CargarTareas.class);
    /** Tareas SQL. */
    private Map < String, String > tablaScript = null;
    /** Tareas PLSQL. */
    private Map < String, String > tablaPlsql = null;
    /** Número de tokens. */
    private final int numeroTokens = 3;

    /**
     * Contructor.
     */
    private CargarTareas() {
    }

    /**
     * Carga las tareas del fichero.
     * @param nombreFichero fichero.
     * @throws java.io.IOException error de lectura.
     */
    public CargarTareas(final String nombreFichero)
            throws IOException {
        tablaScript = new HashMap < String, String > ();
        tablaPlsql = new HashMap < String, String > ();
        BufferedReader br = new BufferedReader(new FileReader(nombreFichero));
        StringTokenizer st = null;
        String scriptSQL, plan, scriptOPlsql;
        String tmp = br.readLine();
        while (tmp != null) {
            st = new StringTokenizer(tmp, Comun.getComun().getConfigProperties()
               .getProperty(
               "josejamilena.pfc.servidor.tareas.CargarTareas.separador"));
            if (st.countTokens() == this.numeroTokens) {
                scriptSQL = st.nextToken();
                plan = st.nextToken();
                scriptOPlsql = st.nextToken();
                if (scriptOPlsql.equalsIgnoreCase(Comun.getComun()
                        .getConfigProperties().getProperty(
                        "josejamilena.pfc.servidor.tareas.CargarTareas.SQL"))) {
                    logger.info("Programación SQL: " + scriptSQL + " " + plan);
                    this.tablaScript.put(scriptSQL, plan);
                } else if (scriptOPlsql.equalsIgnoreCase(Comun.getComun()
                        .getConfigProperties().getProperty(
                        "josejamilena.pfc.servidor.tareas.CargarTareas.PLSQL"))
                        ) {
                    logger.info("Programación PLSQL: " + scriptSQL + " "
                            + plan);
                    this.tablaPlsql.put(scriptSQL, plan);
                } else {
                    logger.error("Error: TIPO DE SCRIPT DESCONOCIDO. "
                            + scriptSQL + " " + plan);
                }
            }
            tmp = br.readLine();
        }
        br.close();
    }

    /**
     * @return the tablaScript
     */
    public final Map < String, String > getTablaScript() {
        return tablaScript;
    }

    /**
     * @return the tablaPlsql
     */
    public final Map < String, String > getTablaPlsql() {
        return tablaPlsql;
    }
}
