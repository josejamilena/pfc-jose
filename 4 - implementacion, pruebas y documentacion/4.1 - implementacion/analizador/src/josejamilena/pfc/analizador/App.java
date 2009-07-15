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

package josejamilena.pfc.analizador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class App extends SingleFrameApplication {

    /**
     * Conexión JDBC.
     */
    public static Connection conn = null;
    /**
     * Fichero de BD de estadisticas.
     */
    private static String ficheroEstadisticas = "";

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new View(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of App
     */
    public static App getApplication() {
        return Application.getInstance(App.class);
    }

    /**
     * Get the value of ficheroEstadisticas
     *
     * @return the value of ficheroEstadisticas
     */
    public String getFicheroEstadisticas() {
        return ficheroEstadisticas;
    }

    /**
     * Set the value of ficheroEstadisticas
     *
     * @param ficheroEstadisticas new value of ficheroEstadisticas
     */
    public void setFicheroEstadisticas(String ficheroEsta) {
        ficheroEstadisticas = ficheroEsta;
    }

    /**
     * Iniciar conexión.
     * @return devuelve si se ha podido realizar la conexión
     */
    public static boolean iniciarConexion() {
        if (ficheroEstadisticas.isEmpty()) {
            return false;
        } else {
            try {
                if (conn != null) {
                    conn.close();
                }
                conn = DriverManager.getConnection("jdbc:sqlite:" + ficheroEstadisticas);
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        //conn = DriverManager.getConnection("jdbc:sqlite:.\\estadisticas.db3");
        launch(App.class, args);
    }
}
