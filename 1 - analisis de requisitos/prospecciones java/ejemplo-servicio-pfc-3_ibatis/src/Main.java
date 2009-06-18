
import conex.Comun;
import it.sauronsoftware.cron4j.Scheduler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

    public static VisorMsg msg;

    public static void main(String[] args) {
        try {
            LeerTareas t = new LeerTareas("tareas.txt");
            Map<String, String> m = t.getTablaTareas();
            msg = new VisorMsg();
            msg.setVisible(true);
            msg.setTitle("Tiempos de respuesta de Oracle");
            for (String sc : m.keySet()) {
                String pr = m.get(sc);
                MyTask task = new MyTask(sc,sc);
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
