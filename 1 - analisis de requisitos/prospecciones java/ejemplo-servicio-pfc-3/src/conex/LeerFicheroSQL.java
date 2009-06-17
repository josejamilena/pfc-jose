package conex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Lee el fichero SQL.
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class LeerFicheroSQL {

    /** lista de consultas. */
    private List<String> listaDeConsultas = null;

    /** la clase es una factoria de metodos, no se permite instanciarla. */
    private LeerFicheroSQL() {
    }

    /**
     * lee el fichero SQL.
     * @param str nombre del fichero.
     * @throws java.io.FileNotFoundException excepcion de fichero no encontrado.
     * @throws java.io.IOException excepcion  de entrada / salida.
     */
    public LeerFicheroSQL(String str) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(str));
        listaDeConsultas = new LinkedList<String>();
        String tmp = br.readLine();
        while (tmp != null) {
            if (!tmp.isEmpty()) {
                listaDeConsultas.add(tmp);
            }
            tmp = br.readLine();
        }
        br.close();
    }

    /**
     * obtiene la lista de consultas.
     * @return lista de consultas.
     */
    public List<String> getListaDeConsultas() {
        return listaDeConsultas;
    }

    /**
     * establece la lista de consultas.
     * @param listaDeConsultas lista de consultas.
     */
    public void setListaDeConsultas(List<String> listaDeConsultas) {
        this.listaDeConsultas = listaDeConsultas;
    }
}
