/*
 * EjemploswgApp.java
 */

package ejemploswg;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class EjemploswgApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new EjemploswgView(this));
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
     * @return the instance of EjemploswgApp
     */
    public static EjemploswgApp getApplication() {
        return Application.getInstance(EjemploswgApp.class);
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
    public static void main(String[] args) {
        launch(EjemploswgApp.class, args);
    }
}
