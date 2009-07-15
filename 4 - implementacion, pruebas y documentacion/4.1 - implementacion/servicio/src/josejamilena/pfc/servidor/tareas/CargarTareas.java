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
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import josejamilena.pfc.servidor.conexion.Comun;
import josejamilena.pfc.servidor.tareas.autenticacion.TokenConexion;
import org.apache.log4j.Logger;

/**
 * Cargador de tareas.
 * @author Jose Antonio Jamilena Daza.
 */
public class CargarTareas {

    /** Logger. */
    private static Logger logger = Logger.getLogger(CargarTareas.class);
    /** Tareas SQL. */
    private Map<String, String> tablaScript = null;
    /** Conexión SQL. */
    private Map<String, TokenConexion> tablaConexionScript = null;
    /** Tareas PLSQL. */
    private Map<String, String> tablaPlsql = null;
    /** Conexión PLSQL. */
    private Map<String, TokenConexion> tablaConexionPlsql = null;
    /** Número de tokens que espera de cada linea del fichero de tareas. */
    private final int numeroTokens = 8;

    /**
     * Contructor.
     */
    private CargarTareas() {
    }

    /**
     * Carga las tareas del fichero.
     * @param nombreFichero fichero.
     * @throws java.io.IOException error de lectura.
     */
    public CargarTareas(final String nombreFichero)
            throws IOException {
        this.tablaScript = new HashMap<String, String>();
        this.tablaPlsql = new HashMap<String, String>();
        this.tablaConexionScript = new HashMap<String, TokenConexion>();
        this.tablaConexionPlsql = new HashMap<String, TokenConexion>();
        BufferedReader br = new BufferedReader(new FileReader(nombreFichero));
        StringTokenizer st = null;
        String nombreScript, plan, scriptOPlsql, driver, url, usuario, password,
                hostname;
        String tmp = br.readLine();
        int numLinea = 1;
        while (tmp != null) {
            if (tmp.startsWith("#")) {
                // es un comentario
                tmp = br.readLine();
                numLinea++;
            } else {
                st = new StringTokenizer(tmp, Comun.getComun()
                        .getConfigProperties().getProperty(
                        "josejamilena.pfc.servidor.tareas.CargarTareas.separador"
                        ));
                if (st.countTokens() == this.numeroTokens) {
                    nombreScript = st.nextToken();
                    plan = st.nextToken();
                    scriptOPlsql = st.nextToken();
                    driver = st.nextToken();
                    hostname = st.nextToken();
                    url = st.nextToken();
                    usuario = st.nextToken();
                    password = st.nextToken();
                    if (scriptOPlsql.equalsIgnoreCase(Comun.getComun()
                            .getConfigProperties().getProperty(
                            "josejamilena.pfc.servidor.tareas.CargarTareas.SQL"
                            ))) {
                        logger.info("Programación SQL: " + nombreScript + " "
                                + plan);
                        this.tablaScript.put(nombreScript, plan);
                        this.tablaConexionScript.put(nombreScript,
                                new TokenConexion(hostname, driver, url,
                                usuario, password));
                    } else if (scriptOPlsql.equalsIgnoreCase(Comun.getComun()
                            .getConfigProperties().getProperty(
                            "josejamilena.pfc.servidor.tareas.CargarTareas.PLSQL"
                            ))) {
                        logger.info("Programación PLSQL: " + nombreScript + " "
                                + plan);
                        this.tablaPlsql.put(nombreScript, plan);
                        this.tablaConexionPlsql.put(nombreScript,
                                new TokenConexion(hostname, driver, url,
                                usuario, password));
                    } else {
                        logger.error("Error: TIPO DE SCRIPT DESCONOCIDO. "
                                + nombreScript + " " + plan);
                    }
                } else {
                    // No contamos en esto las lineas vacias.
                    if (st.countTokens() != 0) {
                        logger.error("Número de tokens incorrectos en " +
                                Comun.getComun().getConfigProperties()
                                .getProperty(
                                "josejamilena.pfc.servidor.Main.tareas.txt"
                                ) + " en la linea " + numLinea
                                );
                    }
                }
                tmp = br.readLine();
                numLinea++;
            }
        }
        br.close();
    }

    /**
     * @return the tablaScript
     */
    public final Map<String, String> getTablaScript() {
        return tablaScript;
    }

    /**
     * @return the tablaPlsql
     */
    public final Map<String, String> getTablaPlsql() {
        return tablaPlsql;
    }

    /**
     * @return the tablaConexionScript
     */
    public Map<String, TokenConexion> getTablaConexionScript() {
        return tablaConexionScript;
    }

    /**
     * @return the tablaConexionPlsql
     */
    public Map<String, TokenConexion> getTablaConexionPlsql() {
        return tablaConexionPlsql;
    }
}
