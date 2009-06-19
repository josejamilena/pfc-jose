package conex;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;

/**
 * Estadisticas.
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class Estadisticas {

    /** conexion. */
    private Connection conexion = null;

    /** properties. */
    private Properties configProperties;

    /** Constructor. */
    public Estadisticas() {
        try {
            this.iniciarPropiedades();

            Class.forName("org.sqlite.JDBC");
            this.conexion = DriverManager.getConnection("jdbc:sqlite:.\\estadisticas.db3");

            iniciarEstructuraEnBD(conexion);
            borrarEstructuraTmpEnBD(conexion);
            iniciarEstructuraTmpEnBD(conexion);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /** */
    private void iniciarPropiedades() {
        try {
            this.configProperties = new Properties();
            this.configProperties.load(new FileInputStream("estadisticas.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Insertar estadisticas en la base de datos.
     * @param tiempo tiempo.
     * @param tipoConsultaStr tipo de consulta como cadena de texto.
     * @throws java.sql.SQLException excepcion de SQL.
     */
    public void insertarEstadistica(long tiempo, String tipoConsultaStr) throws SQLException {
        String query;
        PreparedStatement stmt;
        stmt = conexion.prepareStatement("INSERT INTO ESTADISTICAS VALUES (?,?,?)");
        stmt.setLong(1, tiempo);
        stmt.setLong(2, Calendar.getInstance().getTimeInMillis());
        stmt.setString(3, tipoConsultaStr);
        int retorno = stmt.executeUpdate();
    }

    /**
     * Insertar estadisticas en la base de datos.
     * @param tiempo tiempo.
     * @param tipoConsultaStr tipo de consulta como cadena de texto.
     * @throws java.sql.SQLException excepcion de SQL.
     */
    public void insertarEstadisticaUltimasConsultas(long tiempo, String tipoConsultaStr) throws SQLException {
        String query;
        PreparedStatement stmt;
        stmt = conexion.prepareStatement("INSERT INTO ESTADISTICAS_TMP VALUES (?,?,?)");
        stmt.setLong(1, tiempo);
        stmt.setLong(2, Calendar.getInstance().getTimeInMillis());
        stmt.setString(3, tipoConsultaStr);
        int retorno = stmt.executeUpdate();
    }

    /**
     * borra la tabla en la que se guardan las estadisticas por ejecucion.
     * @param conn conexion a la base de datos.
     */
    private void borrarEstructuraTmpEnBD(Connection conn) {
        Statement stmt;
        ResultSet rs;
        String query;
        query = "DROP TABLE  ESTADISTICAS_TMP";
        try {
            stmt = conn.createStatement();
            int retorno = stmt.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * inicializa la tabla en la que se guardan las estadisticas.
     * @param conn conexion a la base de datos.
     */
    private void iniciarEstructuraEnBD(Connection conn) {
        Statement stmt;
        ResultSet rs;
        String query;
        query = "CREATE TABLE ESTADISTICAS ( TIEMPO NUMBER NOT NULL, FECHA NUMBER NOT NULL, TIPO VARCHAR2(30), CONSTRAINT ESTADISTICAS_PK PRIMARY KEY (FECHA, TIEMPO) )";
        try {
            stmt = conn.createStatement();
            int retorno = stmt.executeUpdate(query);
        } catch (SQLException ex) {
            ;
        }
    }

    /**
     * inicializa la tabla intermedia en la que se guardan las estadisticas.
     * @param conn conexion a la base de datos.
     */
    private void iniciarEstructuraTmpEnBD(Connection conn) {
        Statement stmt;
        ResultSet rs;
        String query;
        query = "CREATE TABLE ESTADISTICAS_TMP  ( TIEMPO NUMBER NOT NULL ,FECHA NUMBER NOT NULL ,TIPO VARCHAR2(30), CONSTRAINT ESTADISTICAS_TMP_PK PRIMARY KEY (FECHA, TIEMPO) )";
        try {
            stmt = conn.createStatement();
            int retorno = stmt.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Lanza la consulta SQL.
     * @param s consulta SQL.
     * @return resultado.
     */
    private String consultaSQL(String s) {
        String res = null;
        try {
            Statement stmt = null;
            ResultSet rs = null;
            ResultSetMetaData rsmd;
            stmt = this.conexion.createStatement();
            rs = stmt.executeQuery(s); //lanzador de consulta
            while (rs.next()) {
                res = rs.getString(1);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return res;
        }
    }

    /**
     * Media de tiempos del fichero.
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public String mostrarMedia() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO FROM ESTADISTICAS");
    }

    /**
     * Media de tiempos en consultas de selecion (SELECT).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public String mostrarMediaSeleccion() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO_SELECT FROM ESTADISTICAS WHERE TIPO = 'SELECION'");
    }

    /**
     * Media de tiempos en manipulacion de datos (INSERT / UPDATE /DELETE).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public String mostrarMediaManipulacionDatos() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO_MD FROM ESTADISTICAS WHERE TIPO = 'MANIPULACION_DE_DATOS'");
    }

    /**
     * Media de tiempos en manipulacion de tablas (DROP / CREATE).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public String mostrarMediaManipulacionTablas() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO_MT FROM ESTADISTICAS WHERE TIPO = 'MANIPULACION_DE_TABLAS'");
    }

    /**
     * Media de tiempos de otras operaciones (?)
     * @return tiempo de la media en segundos
     * @throws java.sql.SQLException excepcion SQL
     */
    public String mostrarMediaOtrasOperaciones() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO_OTRAS FROM ESTADISTICAS WHERE TIPO = 'NO_DEFINIDO'");
    }
    // de ultima tabla
    /**
     * Media de tiempos del fichero de la ultima prueba.
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public String mostrarMediaUltimaPrueba() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO FROM ESTADISTICAS_TMP");
    }

    /**
     * Media de tiempos en consultas de selecion de la ultima prueba (SELECT).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public String mostrarMediaSeleccionUltimaPrueba() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO_SELECT FROM ESTADISTICAS_TMP WHERE TIPO = 'SELECION'");
    }

    /**
     * Media de tiempos en manipulacion de datos de la ultima prueba (INSERT / UPDATE /DELETE).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public String mostrarMediaManipulacionDatosUltimaPrueba() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO_MD FROM ESTADISTICAS_TMP WHERE TIPO = 'MANIPULACION_DE_DATOS'");
    }

    /**
     * Media de tiempos en manipulacion de tablas  de la ultima prueba (DROP / CREATE).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public String mostrarMediaManipulacionTablasUltimaPrueba() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO_MT FROM ESTADISTICAS_TMP WHERE TIPO = 'MANIPULACION_DE_TABLAS'");
    }

    /**
     * Media de tiempos de otras operaciones  de la ultima prueba (?)
     * @return tiempo de la media en segundos
     * @throws java.sql.SQLException excepcion SQL
     */
    public String mostrarMediaOtrasOperacionesPrueba() throws SQLException {
        return this.consultaSQL("SELECT SUM(TIEMPO)/COUNT(TIEMPO) AS MEDIA_TIEMPO_MT FROM ESTADISTICAS_TMP WHERE TIPO = 'NO_DEFINIDO'");
    }

    /**
     * 
     */
    public void iniciarEstadisticaDeConsultaActual() {
        borrarEstructuraTmpEnBD(conexion);
        iniciarEstructuraTmpEnBD(conexion);
    }
}
