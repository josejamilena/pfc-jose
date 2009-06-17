
import conex.Comun;
import it.sauronsoftware.cron4j.Scheduler;

public class Main {

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

        MyTask task = new MyTask();
        Scheduler scheduler = new Scheduler();
        scheduler.schedule(Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("PLANIFICACION"), task);
        scheduler.start();
        while (true) {
            ;
        }
    }
}
