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

import java.util.LinkedList;
import java.util.List;

/**
 * Objeto para definir un Grafico.
 * @author Jose Antonio Jamilena Daza
 */
class Grafico {

    /** Titulo para el eje X. */
    private String tituloEjeX;

    /** Titulo para el eje Y. */
    private String tituloEjeY;

    /** lista de consultas. */
    private LinkedList < String > lista;

    /** Constructor. */
    public Grafico() {
    }

    /**
     * Constructor.
     * @param tituloX nombre eje X
     * @param tituloY nombre eje Y
     * @param list lista
     */
    public Grafico(final String tituloX, final String tituloY,
            final List<String> list) {
        this.tituloEjeX = tituloX;
        this.tituloEjeY = tituloY;
        this.lista = new LinkedList < String >(list);
    }

    /**
     * Obtiene la lista.
     * @return lista
     */
    public List<String> getLista() {
        return lista;
    }

    /**
     * Establece la lista.
     * @param list lista
     */
    public void setLista(final List < String > list) {
        this.lista = new LinkedList < String >(list);
    }

    /**
     * Obtiene nombre del eje X.
     * @return nombre
     */
    public String getTituloEjeX() {
        return tituloEjeX;
    }

    /**
     * Define el titulo del eje Y.
     * @param tituloX nombre
     */
    public void setTituloEjeX(final String tituloX) {
        this.tituloEjeX = tituloX;
    }

    /**
     * Obtiene nombre del eje Y.
     * @return nombre
     */
    public String getTituloEjeY() {
        return tituloEjeY;
    }

    /**
     * Define el titulo del eje Y.
     * @param tituloY nombre
     */
    public void setTituloEjeY(final String tituloY) {
        this.tituloEjeY = tituloY;
    }

}
