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

package josejamilena.pfc.servidor;

import it.sauronsoftware.cron4j.Scheduler;
import josejamilena.pfc.servidor.tareas.CargarTareas;
import josejamilena.pfc.servidor.tareas.TaskPLSQL;
import josejamilena.pfc.servidor.tareas.TaskSQL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import josejamilena.pfc.servidor.conexion.Comun;
import josejamilena.pfc.servidor.tareas.autenticacion.TokenConexion;
import josejamilena.pfc.servidor.tcp.FileSender;
import org.apache.log4j.Logger;

/**
 * Cargador inicial.
 * @author Jose Antonio Jamilena Daza.
 */
public final class Main {
    /** Logger. */
    private static Logger logger = Logger.getLogger(Main.class);

    /** */
    private Main() {
    }

    /**
     * main.
     * @param args argumentos.
     */
    public static void main(final String[] args) {
        try {
            Comun comun = Comun.getComun();
            CargarTareas t = new CargarTareas(comun.getConfigProperties()
                    .getProperty("josejamilena.pfc.servidor.Main.tareas.txt"));
            Map < String, String > msql = t.getTablaScript();
            Map < String, String > mplsql = t.getTablaPlsql();
            Map < String, TokenConexion > tcsql = t.getTablaConexionScript();
            Map < String, TokenConexion > tcplsql = t.getTablaConexionPlsql();
            for (String sc : msql.keySet()) {
                String pr = msql.get(sc);
                TaskSQL task = new TaskSQL(sc, sc, tcsql.get(sc));
                Scheduler scheduler = new Scheduler();
                scheduler.schedule(pr, task);
                scheduler.start();
            }
            for (String sc : mplsql.keySet()) {
                String pr = mplsql.get(sc);
                TaskPLSQL task = new TaskPLSQL(sc, sc, tcplsql.get(sc));
                Scheduler scheduler = new Scheduler();
                scheduler.schedule(pr, task);
                scheduler.start();
            }
            // Servidor de fichero de estadisticas     
            new FileSender(
                Comun.getComun().getConfigProperties().getProperty(
                "josejamilena.pfc.servidor.tcp.ServidorTCP.ficherosqlite"),
                Integer.parseInt(Comun.getComun().getConfigProperties().getProperty(
                "josejamilena.pfc.servidor.tcp.ServidorTCP.TCPPort"))
                ).runServer();
            while (true) {
                ;
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
}
