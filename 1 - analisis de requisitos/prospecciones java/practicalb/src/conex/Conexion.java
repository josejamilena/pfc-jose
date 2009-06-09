package conex;


import java.io.IOException;
import java.sql.*;
/**
 * Clase conexion.
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class Conexion {
    /** conexion. */
    private Connection conexion = null;
    /**
     * crono.
     */
    private Crono crn = new Crono();
    /** tiempo. */
    private long t;
    /**
     * constructor.
     * @throws java.io.IOException excepcion IO.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public Conexion() throws IOException, SQLException {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

        String usuario = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("USUARIO_ORACLE");
        String password = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("PASSWORD_USUARIO");
        String dirOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("SERVIDOR_ORACLE");
        String puertoOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("PUERTO_ORACLE");
        String baseDeDatos = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("BD_ORACLE");
        String cadenaDeConexion = new String("jdbc:oracle:thin:@" + dirOracle + ":" + puertoOracle + ":" + baseDeDatos);

        this.conexion = DriverManager.getConnection(cadenaDeConexion, usuario, password);
    }
    /**
     * ejecuta una consulta SQL.
     * @param s sentencia SQL.
     * @throws java.sql.SQLException excepcion.
     */
    public void consultaSQL(String s) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        stmt = getConexion().createStatement();
        crn.inicializa();
        if (s.endsWith(";")) {
            s.replace(";", " ");
        }
        try {
            rs = stmt.executeQuery(s); //lanzador de consulta
        } catch (SQLException ex) {
            ;
        }
        t = crn.tiempo();
        stmt.close();
    }
    /**
     * obtiene tiempo.
     * @return tiempo en segundos.
     */
    public long obtenerTiempo() {
        return this.t;
    }
    /**
     * establece la conexion a la base de datos.
     * @return conexion.
     */
    public Connection getConexion() {
        return conexion;
    }
    /**
     * obtiene la conexion a la base de datos.
     * @param conexion conexion.
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
