import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * This is the simplest task form: a class implementing the {@link Runnable}
 * interface.
 */
public class MyTask implements Runnable {

    private String nombreFichero;
    private String nombreTarea = "";

    private MyTask() {
    }

    public MyTask(String ficheroSQL) {
        this.nombreFichero = ficheroSQL;
    }

    public MyTask(String nombre,String ficheroSQL) {
        this.nombreFichero = ficheroSQL;
        this.nombreTarea = nombre;
    }

    public void run() {        
        conex.Comun comun = new conex.Comun();
        try {
            comun.estadisticas = new conex.Estadisticas();
            comun.lcf = new conex.LanzarConsultasDeFichero(comun.conexion, comun.estadisticas, nombreFichero);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("Script: " + this.nombreTarea + " lanzado: " + new Date());
            System.out.println(comun.lcf.mediaDeTiempos());
            Main.msg.setTextoMsg("Script: " + this.nombreTarea + " lanzado: " + new Date());
            Main.msg.setTextoMsg(Double.toString(comun.lcf.mediaDeTiempos()));
        }
    }
}
