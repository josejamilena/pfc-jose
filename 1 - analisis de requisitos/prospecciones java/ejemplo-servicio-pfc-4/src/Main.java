
import it.sauronsoftware.cron4j.Scheduler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static VisorMsg msg;

    public static void main(String[] args) {
        try {
            LeerTareas t = new LeerTareas("tareas.txt");
            Map<String, String> m = t.getTablaScript();
            msg = new VisorMsg();
            msg.setVisible(true);
            msg.setTitle("Tiempos de respuesta de Oracle");
            for (String sc : m.keySet()) {
                String pr = m.get(sc);
                TaskSQL task = new TaskSQL(sc, sc);
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
