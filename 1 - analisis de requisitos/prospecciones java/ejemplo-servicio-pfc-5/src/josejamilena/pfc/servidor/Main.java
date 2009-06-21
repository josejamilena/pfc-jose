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

public class Main {

    static Logger logger = Logger.getLogger(Main.class);
    public static VisorMsg msg;

    public static void main(String[] args) {
        try {
            Comun comun = Comun.getComun();
            CargarTareas t = new CargarTareas(comun.getConfigProperties().getProperty("josejamilena.pfc.servidor.Main.tareas.txt"));
            Map<String, String> msql = t.getTablaScript();
            Map<String, String> mplsql = t.getTablaPlsql();
            msg = new VisorMsg();
            msg.setVisible(true);
            msg.setTitle(comun.getConfigProperties().getProperty("josejamilena.pfc.servidor.Main.msgTitle"));
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
