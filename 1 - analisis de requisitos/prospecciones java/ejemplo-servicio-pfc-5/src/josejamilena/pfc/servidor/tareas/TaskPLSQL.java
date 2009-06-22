package josejamilena.pfc.servidor.tareas;

import josejamilena.pfc.servidor.tareas.runner.PlsqlRunner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import josejamilena.pfc.servidor.Main;
import josejamilena.pfc.servidor.conexion.Comun;
import josejamilena.pfc.servidor.conexion.Crono;
import org.apache.log4j.Logger;

/**
 * Tareas PLSQL.
 * @link java.lang.Runnable implementa esta interfaz.
 * @author Jose Antonio Jamilena Daza.
 */
public class TaskPLSQL implements Runnable {
    /** Logger. */
    private static Logger logger = Logger.getLogger(TaskPLSQL.class);
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
     * No se permite el constructor sin parametros.
     */
    private TaskPLSQL() {
    }

    /**
     * Constructor.
     * @param nombre nombre de la tarea.
     * @param ficheroPLSQL fichero de la tarea.
     */
    public TaskPLSQL(final String nombre, final String ficheroPLSQL) {
        this.nombreFichero = ficheroPLSQL;
        this.nombreTarea = nombre;
    }

    /**
     * Sobrecarga el metodo.
     */
    public final void run() {
        try {
            PlsqlRunner sr = new PlsqlRunner("oracle.jdbc.driver.OracleDriver",
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
            Main.getMsg().setTextoMsg("Script: " + this.nombreTarea + " lanzado: "
                    + new Date());
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
