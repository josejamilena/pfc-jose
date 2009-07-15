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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Date;
import josejamilena.pfc.servidor.conexion.Comun;
import josejamilena.pfc.servidor.conexion.Crono;
import josejamilena.pfc.servidor.tareas.autenticacion.TokenConexion;
import josejamilena.pfc.servidor.tareas.runner.SqlRunner;
import org.apache.log4j.Logger;

/**
 * Tareas SQL.
 * @link java.lang.Runnable implementa esta interfaz.
 * @author Jose Antonio Jamilena Daza.
 */
public class TaskSQL implements Runnable {
    /** Logger. */
    private static Logger logger = Logger.getLogger(TaskSQL.class);
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
     * No se permite contructor sin parámetros.
     */
    private TaskSQL() {
    }

    /**
     * Constructor.
     * @param nombre nombre de la tarea.
     * @param ficheroSQL fichero scripts.
     * @param tc autenticacion
     */
    public TaskSQL(final String nombre, final String ficheroSQL,
            final TokenConexion tc) {
        this.nombreFichero = ficheroSQL;
        this.nombreTarea = nombre;
        this.token = tc;
    }

    /**
     * Método que sobreescribe.
     */
    public final void run() {
//        ScriptRunner sr = new ScriptRunner("oracle.jdbc.driver.OracleDriver",
//                "jdbc:oracle:thin:@192.168.2.17:1521:XE",
//                "oracle",
//                "oracle",
//                true,
//                false);
//        sr.setLogWriter(null);
        try {
            SqlRunner sr = new SqlRunner(this.token.getDriver(),
                    this.token.getCadenaConexion(), this.token.getUsuario(),
                    this.token.getPassword());
            // Comun.getComun().getEstadisticas()
            //         .iniciarEstadisticaDeConsultaActual();
            crn.inicializa();
            sr.runScript(
                    new BufferedReader(new FileReader(this.nombreFichero)));
            t = crn.tiempo();
            Comun.getComun().getEstadisticas()
                    .insertarEstadistica(t, this.nombreFichero,
                    this.token.getHostname(), Comun.getComun().getHostname());
            // Comun.getComun().getEstadisticas()
            //         .insertarEstadisticaUltimasConsultas(t, this.nombreFichero,
            //         this.token.getHostname(), Comun.getComun().getHostname());
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
