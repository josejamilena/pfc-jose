//   Copyright 2009 Jose Antonio Jamilena Daza
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

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
