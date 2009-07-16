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

package josejamilena.pfc.servidor.tareas;

import josejamilena.pfc.servidor.tareas.runner.PlsqlRunner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import josejamilena.pfc.servidor.conexion.Comun;
import josejamilena.pfc.servidor.conexion.Crono;
import josejamilena.pfc.servidor.tareas.autenticacion.TokenConexion;
import org.apache.log4j.Logger;

/**
 * Define una tareas PL/SQL.
 * @link java.lang.Runnable implementa esta interfaz.
 * @author Jose Antonio Jamilena Daza.
 */
public class TaskPLSQL implements Runnable {
    /** Logger. */
    private static Logger logger = Logger.getLogger(TaskPLSQL.class);
    /** Nombre del fichero. */
    private String nombreFichero = "";
    /** Nombre de la tarea. */
    private String nombreTarea = "";
    /** Autenticacion. */
    private TokenConexion token = null;
    /**
     * crono.
     */
    private Crono crn = new Crono();
    /** tiempo. */
    private long t;

    /**
     * No se permite el constructor sin parametros.
     */
    private TaskPLSQL() {
    }

    /**
     * Constructor.
     * @param nombre nombre de la tarea.
     * @param ficheroPLSQL fichero de la tarea.
     * @param tc autenticacion
     */
    public TaskPLSQL(final String nombre, final String ficheroPLSQL,
            final TokenConexion tc) {
        this.nombreFichero = ficheroPLSQL;
        this.nombreTarea = nombre;
        this.token = tc;
    }

    /**
     * Sobrecarga el metodo.
     */
    public final void run() {
        try {
            PlsqlRunner sr = new PlsqlRunner(this.token.getDriver(),
                    this.token.getCadenaConexion(), this.token.getUsuario(),
                    this.token.getPassword());
            //Comun.getComun().getEstadisticas()
            //                    .iniciarEstadisticaDeConsultaActual();
            crn.inicializa();
            sr.runScript(
                    new BufferedReader(new FileReader(this.nombreFichero)));
            t = crn.tiempo();
            Comun.getComun().getEstadisticas()
                    .insertarEstadistica(t, this.nombreFichero,
                    this.token.getHostname(), Comun.getComun().getHostname());
            //Comun.getComun().getEstadisticas()
            //        .insertarEstadisticaUltimasConsultas(t, this.nombreFichero,
            //        this.token.getHostname(), Comun.getComun().getHostname());
        } catch (IOException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        } finally {
            logger.debug("Script: " + this.nombreTarea + " lanzado: "
                    + new Date());
            logger.debug(Double.toString(t));
        }
    }

    /**
     * obtiene tiempo.
     * @return tiempo en segundos.
     */
    public final long obtenerTiempo() {
        return this.t;
    }
}
