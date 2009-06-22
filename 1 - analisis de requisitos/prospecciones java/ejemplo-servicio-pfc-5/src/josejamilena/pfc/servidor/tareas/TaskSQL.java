package josejamilena.pfc.servidor.tareas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import josejamilena.pfc.servidor.Main;
import josejamilena.pfc.servidor.conexion.Comun;
import josejamilena.pfc.servidor.conexion.Crono;
import josejamilena.pfc.servidor.tareas.runner.SqlRunner;
import org.apache.log4j.Logger;

/**
 * Tareas SQL.
 * @link java.lang.Runnable implementa esta interfaz.
 * @author Jose Antonio Jamilena Daza.
 */
public class TaskSQL implements Runnable {
    /** Logger. */
    private static Logger logger = Logger.getLogger(TaskSQL.class);
    /** Nombre del fichero. */
    private String nombreFichero = "";
    /** Nombre de la tarea. */
    private String nombreTarea = "";
    /**
     * crono.
     */
    private Crono crn = new Crono();
    /** tiempo. */
    private long t;

    /**
     * No se permite contructor sin parámetros.
     */
    private TaskSQL() {
    }

    /**
     * Constructor.
     * @param nombre nombre de la tarea.
     * @param ficheroSQL fichero scripts.
     */
    public TaskSQL(final String nombre, final String ficheroSQL) {
        this.nombreFichero = ficheroSQL;
        this.nombreTarea = nombre;
    }

    /**
     * Método que sobreescribe.
     */
    public final void run() {
//        ScriptRunner sr = new ScriptRunner("oracle.jdbc.driver.OracleDriver",
//                "jdbc:oracle:thin:@192.168.2.17:1521:XE",
//                "oracle",
//                "oracle",
//                true,
//                false);
//        sr.setLogWriter(null);
        try {

            SqlRunner sr = new SqlRunner("oracle.jdbc.driver.OracleDriver",
                    "jdbc:oracle:thin:@192.168.2.17:1521:XE", "oracle", "oracle"
                    );
            Comun.getComun().getEstadisticas()
                    .iniciarEstadisticaDeConsultaActual();
            crn.inicializa();
            sr.runScript(
                    new BufferedReader(new FileReader(this.nombreFichero)));
            t = crn.tiempo();
            Comun.getComun().getEstadisticas()
                    .insertarEstadistica(t, this.nombreFichero);
            Comun.getComun().getEstadisticas()
                    .insertarEstadisticaUltimasConsultas(t, this.nombreFichero);
        } catch (IOException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        } finally {
            Main.getMsg().setTextoMsg("Script: " + this.nombreTarea
                    + " lanzado: " + new Date());
            Main.getMsg().setTextoMsg(Double.toString(t));
        }
    }

    /**
     * obtiene tiempo.
     * @return tiempo en segundos.
     */
    public final long obtenerTiempo() {
        return this.t;
    }
}
