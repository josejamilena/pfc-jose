package josejamilena.pfc.servidor;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CargarTareas {

    static Logger logger = Logger.getLogger(CargarTareas.class);

    private Map<String, String> tablaScript = null;
    private Map<String, String> tablaPlsql = null;

    private CargarTareas() {
    }

    public CargarTareas(String nombreFichero) throws FileNotFoundException, IOException {
        tablaScript = new HashMap<String, String>();
        tablaPlsql = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(new FileReader(nombreFichero));
        StringTokenizer st = null;
        String scriptSQL, plan, scriptOPlsql;
        String tmp = br.readLine();
        while (tmp != null) {
            st = new StringTokenizer(tmp, ";");
            if (st.countTokens() == 3) {
                scriptSQL = st.nextToken();
                plan = st.nextToken();
                scriptOPlsql = st.nextToken();
                if (scriptOPlsql.equalsIgnoreCase("SQL")) {
                    logger.info("Programación SQL: " + scriptSQL + " " + plan);
                    this.tablaScript.put(scriptSQL, plan);
                } else if (scriptOPlsql.equalsIgnoreCase("PLSQL")) {
                    logger.info("Programación PLSQL: " + scriptSQL + " " + plan);
                    this.tablaPlsql.put(scriptSQL, plan);
                } else {
                    logger.error("Error: TIPO DE SCRIPT DESCONOCIDO. " + scriptSQL + " " + plan);
                }
            }
            tmp = br.readLine();
        }
        br.close();
    }

    /**
     * @return the tablaScript
     */
    public Map<String, String> getTablaScript() {
        return tablaScript;
    }

    /**
     * @return the tablaPlsql
     */
    public Map<String, String> getTablaPlsql() {
        return tablaPlsql;
    }
}
