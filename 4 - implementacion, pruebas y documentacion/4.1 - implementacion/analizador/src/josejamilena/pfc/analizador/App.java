/*
 * App.java
 */

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

    public static Connection conn;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new View(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of App
     */
    public static App getApplication() {
        return Application.getInstance(App.class);
    }

    /**
     * Fichero de BD de estadisticas.
     */
    private String ficheroEstadisticas = "";

    /**
     * Get the value of ficheroEstadisticas
     *
     * @return the value of ficheroEstadisticas
     */
    public String getFicheroEstadisticas() {
        return this.ficheroEstadisticas;
    }

    /**
     * Set the value of ficheroEstadisticas
     *
     * @param ficheroEstadisticas new value of ficheroEstadisticas
     */
    public void setFicheroEstadisticas(String ficheroEstadisticas) {
        this.ficheroEstadisticas = ficheroEstadisticas;
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:.\\estadisticas.db3");
        launch(App.class, args);
    }
}
