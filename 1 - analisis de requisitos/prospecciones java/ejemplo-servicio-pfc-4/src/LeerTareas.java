
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class LeerTareas {

    private Map<String, String> tablaScript = null;
    private Map<String, String> tablaPlsql = null;

    private LeerTareas() {
    }

    public LeerTareas(String nombreFichero) throws FileNotFoundException, IOException {
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
                // System.err.println(scriptSQL);
                // System.err.println(plan);
                if (scriptOPlsql.equalsIgnoreCase("SQL")) {
                    this.tablaScript.put(scriptSQL, plan);
                } else if (scriptOPlsql.equalsIgnoreCase("PLSQL")) {
                    this.tablaPlsql.put(scriptSQL, plan);
                } else {
                    throw new RuntimeException("Error: TIPO DE SCRIPT DESCONOCIDO.");
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
