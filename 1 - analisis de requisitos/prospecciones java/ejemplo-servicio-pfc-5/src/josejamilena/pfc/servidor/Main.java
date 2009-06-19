package josejamilena.pfc.servidor;


import it.sauronsoftware.cron4j.Scheduler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {

    static Logger logger = Logger.getLogger(Main.class);

    public static VisorMsg msg;

    public static void main(String[] args) {
        try {
            PropertyConfigurator.configure("log4j.properties");
            CargarTareas t = new CargarTareas("tareas.txt");
            Map<String, String> msql = t.getTablaScript();
            Map<String, String> mplsql = t.getTablaPlsql();
            msg = new VisorMsg();
            msg.setVisible(true);
            msg.setTitle("Tiempos de respuesta de Oracle");
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
