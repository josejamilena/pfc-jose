
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class LeerTareas {

    private Map<String, String> tablaTareas = null;

    private LeerTareas() {
    }

    public LeerTareas(String nombreFichero) throws FileNotFoundException, IOException {
        tablaTareas = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(new FileReader(nombreFichero));
        StringTokenizer st = null;
        String scriptSQL, plan;
        String tmp = br.readLine();
        while (tmp != null) {
            st = new StringTokenizer(tmp, ";");
            if (st.countTokens() == 2) {
                scriptSQL = st.nextToken();
                plan = st.nextToken();
                // System.err.println(scriptSQL);
                // System.err.println(plan);
                tablaTareas.put(scriptSQL, plan);
            }
            tmp = br.readLine();
        }
        br.close();
    }

    /**
     * @return the tablaTareas
     */
    public Map<String, String> getTablaTareas() {
        return tablaTareas;
    }
}
