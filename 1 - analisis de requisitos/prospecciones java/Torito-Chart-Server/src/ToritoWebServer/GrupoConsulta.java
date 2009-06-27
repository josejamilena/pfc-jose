package ToritoWebServer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class GrupoConsulta {

    private String titulo;
    private List<String> lista;

    public GrupoConsulta(String titulo, List<String> lista) {
        this.titulo = titulo;
        HashSet<String> ss = new HashSet<String>();
        ss.addAll(lista);
        this.lista = new LinkedList<String>(ss);
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the lista
     */
    public List<String> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<String> lista) {
        this.lista = lista;
    }
}
