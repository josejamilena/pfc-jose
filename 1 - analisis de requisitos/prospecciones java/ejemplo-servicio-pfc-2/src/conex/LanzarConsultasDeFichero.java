package conex;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
/**
 * Clase que lee de un fichero SQL.
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class LanzarConsultasDeFichero {
    /** lista de tiempos de consultas. */
    private List<Double> tiempoConsultas = new LinkedList<Double>();
    /** la clase es un factoria de metodos y no se permite instanciar. */
    private LanzarConsultasDeFichero() {
    }
    /**
     * lanza la consulta.
     * @param con instancia de conexion.
     * @param esta instancia estadistica.
     * @param str consulta SQL.
     * @throws java.io.FileNotFoundException excepcion fichero no encontrado.
     * @throws java.io.IOException excepcion de entrada y salida.
     * @throws java.lang.Exception excecion.
     */
    public LanzarConsultasDeFichero(Conexion con, Estadisticas esta, String str) throws FileNotFoundException, IOException, Exception {
        LeerFicheroSQL lector = new LeerFicheroSQL(str);
        List<String> listaDeConsulta = lector.getListaDeConsultas();
        esta.iniciarEstadisticaDeConsultaActual();
        String tipoConsulta;
        for (String tmp : listaDeConsulta) {
            if (!tmp.isEmpty()) {                
                con.consultaSQL(tmp);
                tiempoConsultas.add((double) con.obtenerTiempo());
                esta.insertarEstadistica(con.obtenerTiempo(), Comun.tipoCosultaString(Comun.tipoConsulta(tmp)));
                esta.insertarEstadisticaUltimasConsultas(con.obtenerTiempo(), Comun.tipoCosultaString(Comun.tipoConsulta(tmp)));
            }
        }
    }
    /**
     * calcula la media de tiempos.
     * @return tiempo en segundos.
     */
    public double mediaDeTiempos() {
        double res = 0.0;
        for (double i : tiempoConsultas) {
            res = res + i;
        }
        res = (double) (res / tiempoConsultas.size());
        return res;
    }
}
