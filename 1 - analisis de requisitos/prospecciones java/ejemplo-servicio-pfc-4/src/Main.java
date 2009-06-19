
import it.sauronsoftware.cron4j.Scheduler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static VisorMsg msg;

    public static void main(String[] args) {
        try {
            LeerTareas t = new LeerTareas("tareas.txt");
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
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
