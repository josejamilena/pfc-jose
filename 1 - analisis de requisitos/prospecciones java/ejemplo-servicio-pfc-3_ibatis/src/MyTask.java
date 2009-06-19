
import com.ibatis.common.jdbc.ScriptRunner;
import conex.Comun;
import conex.Crono;
import conex.Estadisticas;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

/**
 * This is the simplest task form: a class implementing the {@link Runnable}
 * interface.
 */
public class MyTask implements Runnable {

    private String nombreFichero;
    private String nombreTarea = "";
    /**
     * crono.
     */
    private Crono crn = new Crono();
    /** tiempo. */
    private long t;

    private MyTask() {
    }

    public MyTask(String nombre, String ficheroSQL) {
        this.nombreFichero = ficheroSQL;
        this.nombreTarea = nombre;
    }

    public void run() {
        System.out.println("Antes: " + new Date());
        ScriptRunner sr = new ScriptRunner("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@192.168.2.17:1521:XE",
                "oracle",
                "oracle",
                true,
                false);
        sr.setLogWriter(new PrintWriter(System.err));
        Comun.getComun().getEstadisticas().iniciarEstadisticaDeConsultaActual();
        try {
            crn.inicializa();
            sr.runScript(new BufferedReader(new FileReader(this.nombreFichero)));
            t = crn.tiempo();
            Comun.getComun().getEstadisticas().insertarEstadistica(t, this.nombreFichero);
            Comun.getComun().getEstadisticas().insertarEstadisticaUltimasConsultas(t, this.nombreFichero);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
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
