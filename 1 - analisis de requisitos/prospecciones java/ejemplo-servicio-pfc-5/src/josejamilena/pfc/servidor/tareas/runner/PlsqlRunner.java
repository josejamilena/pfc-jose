package josejamilena.pfc.servidor.tareas.runner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Clase conexion.
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class PlsqlRunner {

    static Logger logger = Logger.getLogger(PlsqlRunner.class);
    /** conexion. */
    private Connection conexion = null;
    /** lista de consultas. */
    private List<String> listaDeConsultas = null;

    public PlsqlRunner(Connection connection) {
        this.conexion = connection;
    }

    public PlsqlRunner(String driver, String url, String username, String password) throws IOException, SQLException, ClassNotFoundException {
        Class.forName(driver);
        String cadenaDeConexion = new String(url);
        this.conexion = DriverManager.getConnection(cadenaDeConexion, username, password);
    }

    private void consultaSQL(String s) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        stmt = conexion.createStatement();
        try {
            rs = stmt.executeQuery(s); //lanzador de consulta
        } catch (SQLException ex) {
            logger.error(ex);
        }
        stmt.close();
    }

    public void runScript(Reader reader) throws FileNotFoundException, IOException, SQLException {
        BufferedReader br = new BufferedReader(reader);
        listaDeConsultas = new LinkedList<String>();
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

//    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
//        PropertyConfigurator.configure("log4j.properties");
//        System.err.println((new java.util.Date()));
//        (new PlsqlRunner("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.2.17:1521:XE", "oracle", "oracle")).runScript(new BufferedReader(new FileReader("ejemplo_1.sql")));
//        System.err.println((new java.util.Date()));
//    }
}

