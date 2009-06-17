
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
        Comun.iniciarPropiedades();
        String usuario = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("USUARIO_ORACLE");
        String dirOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("SERVIDOR_ORACLE");
        String puertoOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("PUERTO_ORACLE");
        String baseDeDatos = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("BD_ORACLE");
        usuario = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("USUARIO_ESTADISTICAS");
        dirOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("SERVIDOR_ESTADISTICAS");
        puertoOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("PUERTO_ESTADISTICAS");
        baseDeDatos = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("BD_ESTADISTICAS");
//        msg =  new VisorMsg();
//        msg.setVisible(true);
//        msg.setTitle("Tiempos de respuesta de Oracle");
//
//        MyTask task = new MyTask("ejemplo.sql");
//        Scheduler scheduler = new Scheduler();
//        scheduler.schedule("* * * * *", task);
//        scheduler.start();
//        while (true) {
//            ;
//        }
        try {
            LeerTareas t = new LeerTareas("tareas.txt");
            Map<String, String> m = t.getTablaTareas();
//            System.err.println(m.size());
//            for (String s : m.keySet()) {
//                System.err.println(s);
//            }
//            for (String s : m.values()) {
//                System.err.println(s);
//            }
            msg = new VisorMsg();
            msg.setVisible(true);
            msg.setTitle("Tiempos de respuesta de Oracle");
            for (String sc : m.keySet()) {
                String pr = m.get(sc);
                System.err.println(sc + " " + pr);
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
