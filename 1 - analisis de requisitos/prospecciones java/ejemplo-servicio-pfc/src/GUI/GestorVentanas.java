package GUI;

import javax.swing.JFrame;

/**
 * Clase encarga de gestionar las distintas ventanas de la aplicaci�n
 * 
 * @author Jose Jamilena Daza, Miguel A. Moreno Leiva y Pablo Land�n Romero
 */
public class GestorVentanas extends JFrame {

    private JFrame ventSeleccion = null;
    private JFrame ventMuestra = null;
    //private JFrame ventBusqueda = null;
    private JFrame ventDatosConex = null;
    private JFrame ventSalida = null;
    private Salida s;

    /**
     * Constructor
     */
    public GestorVentanas() {
    }

    public void crearVentanaDatosConex() {
        DatosConexion a = new DatosConexion(this);
        ventDatosConex = new JFrame("Identificacion");
        getVentanaDatosConex().setContentPane(a);
        getVentanaDatosConex().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getVentanaDatosConex().pack();
        getVentanaDatosConex().setLocation(250, 100);
        getVentanaDatosConex().setVisible(true);
    }

    /*public void crearVentanaBusqueda() {
    Busqueda b = new Busqueda(this);
    ventBusqueda = new JFrame("Busqueda de fichero");
    getVentanaBusqueda().setContentPane(b);
    getVentanaBusqueda().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getVentanaBusqueda().pack();
    getVentanaBusqueda().setLocation(250, 100);
    getVentanaBusqueda().setVisible(true);
    }*/
    private void crearVentanaSalida(conex.Comun comun) {
        s = new Salida(this, comun);
        ventSalida = new JFrame("Salida de resultados");
        getVentanaSalida().setContentPane(s);
        getVentanaSalida().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getVentanaSalida().setSize(500, 700);
        getVentanaSalida().setLocation(250, 60);
        getVentanaSalida().setVisible(true);
    }

    /**
     * Crea la ventana de selecci�n
     */
    public void crearVentanaSeleccion() {
        Seleccion s = new Seleccion(this);
        ventSeleccion = new JFrame("Test");
        getVentanaSeleccion().setContentPane(s);
        getVentanaSeleccion().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getVentanaSeleccion().setSize(600, 400);
        getVentanaSeleccion().setLocation(250, 100);
        getVentanaSeleccion().setVisible(true);
    }

    public void pasarADatosConex() {
        if (getVentanaDatosConex() == null) {
            crearVentanaDatosConex();
        } else {
            getVentanaDatosConex().setVisible(true);
        }
    }

    /**
     * Obtiene la ventana principal
     * @return instancia de la ventana

    public JFrame getVentanaBusqueda() {
    return ventBusqueda;
    }*/
    /**
     * Obtiene la ventana de selecci�n
     * @return instancia de la ventana
     */
    public JFrame getVentanaSeleccion() {
        return ventSeleccion;
    }

    public JFrame getVentanaDatosConex() {
        return ventDatosConex;
    }

    /**
     * Obtiene la ventana de muestra
     * @return instancia de la ventana
     */
    public JFrame getVentanaMuestra() {
        return ventMuestra;
    }

    /* void pasarABusqueda() {
    if (getVentanaBusqueda() == null) {
    crearVentanaBusqueda();
    } else {
    getVentanaBusqueda().setVisible(true);
    }
    }*/
    void pasarASalida(conex.Comun comun) {
        getVentanaSeleccion().setVisible(false);
        if (getVentanaSalida() == null) {
            crearVentanaSalida(comun);
        } else {
            s.cargarDatos();
            getVentanaSalida().setVisible(true);
        }
    }

    void pasarASeleccion() {
        getVentanaDatosConex().setVisible(false);
        if (getVentanaSalida() != null) {
            getVentanaSalida().setVisible(false);
        }
        if (getVentanaSeleccion() == null) {
            crearVentanaSeleccion();
        } else {
            getVentanaSeleccion().setVisible(true);
        }
    }

    private JFrame getVentanaSalida() {
        return ventSalida;
    }
}
