package josejamilena.pfc.servidor;

import josejamilena.pfc.servidor.tareas.CargarTareas;
import josejamilena.pfc.servidor.tareas.TaskPLSQL;
import josejamilena.pfc.servidor.tareas.TaskSQL;
import it.sauronsoftware.cron4j.Scheduler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import josejamilena.pfc.servidor.conexion.Comun;
import josejamilena.pfc.servidor.tareas.autenticacion.TokenConexion;
import org.apache.log4j.Logger;

/**
 * Cargador inicial.
 * @author Jose Antonio Jamilena Daza.
 */
public final class Main {
    /** Logger. */
    private static Logger logger = Logger.getLogger(Main.class);

    /** */
    private Main() {
    }

    /**
     * main.
     * @param args argumentos.
     */
    public static void main(final String[] args) {
        try {
            Comun comun = Comun.getComun();
            CargarTareas t = new CargarTareas(comun.getConfigProperties()
                    .getProperty("josejamilena.pfc.servidor.Main.tareas.txt"));
            Map < String, String > msql = t.getTablaScript();
            Map < String, String > mplsql = t.getTablaPlsql();
            Map < String, TokenConexion > tcsql = t.getTablaConexionScript();
            Map < String, TokenConexion > tcplsql = t.getTablaConexionPlsql();
            for (String sc : msql.keySet()) {
                String pr = msql.get(sc);
                TaskSQL task = new TaskSQL(sc, sc, tcsql.get(sc));
                Scheduler scheduler = new Scheduler();
                scheduler.schedule(pr, task);
                scheduler.start();
            }
            for (String sc : mplsql.keySet()) {
                String pr = mplsql.get(sc);
                TaskPLSQL task = new TaskPLSQL(sc, sc, tcplsql.get(sc));
                Scheduler scheduler = new Scheduler();
                scheduler.schedule(pr, task);
                scheduler.start();
            }
            while (true) {
                ;
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
}
