package josejamilena.pfc.servidor;

import josejamilena.pfc.servidor.tareas.CargarTareas;
import josejamilena.pfc.servidor.tareas.TaskPLSQL;
import josejamilena.pfc.servidor.tareas.TaskSQL;
import it.sauronsoftware.cron4j.Scheduler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import josejamilena.pfc.servidor.conexion.Comun;
import org.apache.log4j.Logger;

/**
 * Cargador inicial.
 * @author Jose Antonio Jamilena Daza.
 */
public final class Main {
    /** Logger. */
    private static Logger logger = Logger.getLogger(Main.class);
    /** ventana. */
    private static VisorMsg msg;

    /** */
    private Main() {
    }

    /**
     * @return the msg
     */
    public static VisorMsg getMsg() {
        return msg;
    }

    /**
     * @param visorMsg msg.
     */
    private static void setMsg(final VisorMsg visorMsg) {
        Main.msg = visorMsg;
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
            if (comun.getConfigProperties()
                    .getProperty("josejamilena.pfc.servidor.Main.msg.activado")
                    .equalsIgnoreCase("ON")) {
                setMsg(new VisorMsg());
                getMsg().setVisible(true);
                getMsg().setTitle(comun.getConfigProperties()
                        .getProperty("josejamilena.pfc.servidor.Main.msg.Title")
                        );
            }
            for (String sc : msql.keySet()) {
                String pr = msql.get(sc);
                TaskSQL task = new TaskSQL(sc, sc);
                Scheduler scheduler = new Scheduler();
                scheduler.schedule(pr, task);
                scheduler.start();
            }
            for (String sc : mplsql.keySet()) {
                String pr = mplsql.get(sc);
                TaskPLSQL task = new TaskPLSQL(sc, sc);
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
