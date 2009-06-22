package josejamilena.pfc.servidor.tareas.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Clase conexion.
 * @author Jose Antonio Jamilena Daza.
 */
public class PlsqlRunner {

    /** Logger. */
    private static Logger logger = Logger.getLogger(PlsqlRunner.class);
    /** conexion. */
    private Connection conexion = null;
    /** lista de consultas. */
    private List < String > listaDeConsultas = null;

    /**
     * Lanzador SQL.
     * @param connection coneexión JDBC.
     */
    public PlsqlRunner(final Connection connection) {
        this.conexion = connection;
    }

    /**
     * Lanzador SQL.
     * @param driver driver.
     * @param url cadena de cconexión.
     * @param username usuario.
     * @param password contraseña.
     * @throws java.io.IOException fallo de lectura.
     * @throws java.sql.SQLException fallo en JDBC.
     * @throws java.lang.ClassNotFoundException driver no encontrado.
     */
    public PlsqlRunner(final String driver, final String url,
            final String username, final String password)
            throws IOException, SQLException, ClassNotFoundException {
        Class.forName(driver);
        this.conexion = DriverManager.getConnection(url, username, password);
    }

    /**
     * lanza la consulta indicada.
     * @param s consulta SQL.
     * @throws java.sql.SQLException fallo.
     */
    private void consultaSQL(final String s) throws SQLException {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conexion.createStatement();
            rs = stmt.executeQuery(s); //lanzador de consulta
            stmt.close();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    /**
     * leer script SQL.
     * @param reader reader.
     * @throws java.io.IOException error de lectura.
     * @throws java.sql.SQLException fallo JDBC.
     */
    public final void runScript(final Reader reader)
            throws IOException, SQLException {
        BufferedReader br = new BufferedReader(reader);
        listaDeConsultas = new LinkedList < String > ();
        String bloque = "";
        String tmp = br.readLine();
        while (tmp != null) {
            // System.err.println(bloque);
            // System.err.println(tmp);
            if (tmp.startsWith("//") || tmp.startsWith("--")) {
                tmp = br.readLine();
            } else {
                if (!tmp.equalsIgnoreCase("/")) {
                    bloque = bloque + " " + tmp;
                } else {
                    listaDeConsultas.add(bloque);
                    bloque = "";
                }
                tmp = br.readLine();
            }
        }
        br.close();
        for (String s : listaDeConsultas) {
            // System.err.println(s);
            this.consultaSQL(s);
        }
    }

//    public static void main(String[] args)
//        throws IOException, SQLException, ClassNotFoundException {
//        PropertyConfigurator.configure("log4j.properties");
//        System.err.println((new java.util.Date()));
//        (new PlsqlRunner("oracle.jdbc.driver.OracleDriver",
//        "jdbc:oracle:thin:@192.168.2.17:1521:XE", "oracle", "oracle")
//        ).runScript(new BufferedReader(new FileReader("ejemplo_1.sql")));
//        System.err.println((new java.util.Date()));
//    }
}

