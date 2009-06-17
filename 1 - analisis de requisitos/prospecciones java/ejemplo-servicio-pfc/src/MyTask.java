import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * This is the simplest task form: a class implementing the {@link Runnable}
 * interface.
 */
public class MyTask implements Runnable {

    public void run() {
        String nombreFichero;
        conex.Comun comun = new conex.Comun();
        try {
            comun.estadisticas = new conex.Estadisticas();
            nombreFichero = comun.getFicheroConsultas();
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
            System.out.println("Si ves esto, pueda que vaya. " + new Date());
            System.out.println(comun.lcf.mediaDeTiempos());
        }
    }
}
