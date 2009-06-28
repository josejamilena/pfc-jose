package josejamilena.pfc.servidor.chartserver;

import java.util.List;

/**
 * Objeto formado por una lista de consultas, y un nombre para dicho grupo.
 * @author Jose Antonio Jamilena Daza
 */
public class GrupoConsulta {

    /** Nombre del grupo. */
    private String titulo;

    /** Lista de consultas. */
    private List < String > lista;

    /**
     * Constructor.
     * @param titu nombre del grupo.
     * @param list lista de consultas.
     */
    public GrupoConsulta(final String titu, final List < String > list) {
        this.titulo = titu;
        this.lista = list;
    }

    /**
     * @return the titulo
     */
    public final String getTitulo() {
        return titulo;
    }

    /**
     * @param titu the titulo to set
     */
    public final void setTitulo(final String titu) {
        this.titulo = titu;
    }

    /**
     * @return the lista
     */
    public final List < String > getLista() {
        return lista;
    }

    /**
     * @param list the lista to set
     */
    public final void setLista(final List < String > list) {
        this.lista = list;
    }
}
