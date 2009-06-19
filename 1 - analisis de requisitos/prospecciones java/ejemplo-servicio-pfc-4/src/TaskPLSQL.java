
import com.ibatis.common.jdbc.ScriptRunner;
import conex.Comun;
import conex.Crono;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the simplest task form: a class implementing the {@link Runnable}
 * interface.
 */
public class TaskPLSQL implements Runnable {

    private String nombreFichero;
    private String nombreTarea = "";
    /**
     * crono.
     */
    private Crono crn = new Crono();
    /** tiempo. */
    private long t;

    private TaskPLSQL() {
    }

    public TaskPLSQL(String nombre, String ficheroPLSQL) {
        this.nombreFichero = ficheroPLSQL;
        this.nombreTarea = nombre;
    }

    public void run() {
        try {
            System.out.println("Antes: " + new Date());
            PlsqlRunner sr = new PlsqlRunner("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.2.17:1521:XE", "oracle", "oracle");
            Comun.getComun().getEstadisticas().iniciarEstadisticaDeConsultaActual();
            crn.inicializa();
            sr.runScript(new BufferedReader(new FileReader(this.nombreFichero)));
            t = crn.tiempo();
            Comun.getComun().getEstadisticas().insertarEstadistica(t, this.nombreFichero);
            Comun.getComun().getEstadisticas().insertarEstadisticaUltimasConsultas(t, this.nombreFichero);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
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
