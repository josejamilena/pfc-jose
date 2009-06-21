package josejamilena.pfc.servidor.tareas;

import josejamilena.pfc.servidor.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import josejamilena.pfc.servidor.conexion.Comun;
import josejamilena.pfc.servidor.conexion.Crono;
import josejamilena.pfc.servidor.tareas.runner.SqlRunner;
import org.apache.log4j.Logger;

/**
 * This is the simplest task form: a class implementing the {@link Runnable}
 * interface.
 */
public class TaskSQL implements Runnable {

    static Logger logger = Logger.getLogger(TaskSQL.class);
    private String nombreFichero;
    private String nombreTarea = "";
    /**
     * crono.
     */
    private Crono crn = new Crono();
    /** tiempo. */
    private long t;

    private TaskSQL() {
    }

    public TaskSQL(String nombre, String ficheroSQL) {
        this.nombreFichero = ficheroSQL;
        this.nombreTarea = nombre;
    }

    public void run() {
//        ScriptRunner sr = new ScriptRunner("oracle.jdbc.driver.OracleDriver",
//                "jdbc:oracle:thin:@192.168.2.17:1521:XE",
//                "oracle",
//                "oracle",
//                true,
//                false);
//        sr.setLogWriter(null);
        try {

            SqlRunner sr = new SqlRunner("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.2.17:1521:XE", "oracle", "oracle");
            Comun.getComun().getEstadisticas().iniciarEstadisticaDeConsultaActual();
            crn.inicializa();
            sr.runScript(new BufferedReader(new FileReader(this.nombreFichero)));
            t = crn.tiempo();
            Comun.getComun().getEstadisticas().insertarEstadistica(t, this.nombreFichero);
            Comun.getComun().getEstadisticas().insertarEstadisticaUltimasConsultas(t, this.nombreFichero);
        } catch (IOException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        } finally {
            Main.msg.setTextoMsg("Script: " + this.nombreTarea + " lanzado: " + new Date());
            Main.msg.setTextoMsg(Double.toString(t));
        }
    }

    /**
     * obtiene tiempo.
     * @return tiempo en segundos.
     */
    public long obtenerTiempo() {
        return this.t;
    }
}
