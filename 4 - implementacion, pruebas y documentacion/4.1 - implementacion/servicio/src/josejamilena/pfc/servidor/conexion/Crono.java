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

package josejamilena.pfc.servidor.conexion;

/**
 * Crono se encarga de medir el tiempo.
 * @author Jose Antonio Jamilena Daza.
 */
public class Crono {

    /** tiempo inicial. */
    private java.util.Date t0;
    /** tiempo final. */
    private java.util.Date t1;

    /**
     * Constructor.
     */
    public Crono() {
        super();
    }

    /**
     * Inicia la cuenta.
     */
    public final void inicializa() {
        t0 = new java.util.Date();
    }

    /**
     * Devuelve el tiempo desde el inicio de cuenta en segundos.
     * @return tiempo desde el inicio.
     */
    public final long tiempo() {
        t1 = new java.util.Date();
        return (t1.getTime() - t0.getTime());
    }
}

