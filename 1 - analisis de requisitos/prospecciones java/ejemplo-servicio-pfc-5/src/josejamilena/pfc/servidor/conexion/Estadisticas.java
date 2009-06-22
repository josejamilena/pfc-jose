package josejamilena.pfc.servidor.conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Estadisticas.
 * @author Jose Antonio Jamilena Daza.
 */
public class Estadisticas {
    /** Logger. */
    private static Logger logger = Logger.getLogger(Estadisticas.class);
    /** 1. */
    private final int uno = 1;
    /** 2. */
    private final int dos = 2;
    /** 3. */
    private final int tres = 3;

    /** conexion. */
    private Connection conexion = null;
    /** properties. */
    private Properties configProperties;

    /**
     * Constructor.
     * @param driver driver JDBC.
     * @param url enlace.
     * @param properties fichero de properties para Estadisticas.
     */
    public Estadisticas(final String driver, final String url,
            final String properties) {
        try {
            this.iniciarPropiedades(properties);
            Class.forName(driver);
            logger.info(driver);
            this.conexion = DriverManager.getConnection(url);
            logger.info(url);
            iniciarEstructuraEnBD(conexion);
            borrarEstructuraTmpEnBD(conexion);
            iniciarEstructuraTmpEnBD(conexion);
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    /**
     * Iniciar propiedades.
     * @param prop nombre del fichero.
     */
    private void iniciarPropiedades(final String prop) {
        try {
            this.configProperties = new Properties();
            this.configProperties.load(new FileInputStream(prop));
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    /**
     * Insertar estadisticas en la base de datos.
     * @param tiempo tiempo.
     * @param tipoConsultaStr tipo de consulta como cadena de texto.
     */
    public final void insertarEstadistica(final long tiempo,
            final String tipoConsultaStr) {
        try {
            PreparedStatement stmt;
            stmt = conexion.prepareStatement(this.configProperties
                    .getProperty("insertarEstadistica"));
            stmt.setLong(this.uno, tiempo);
            stmt.setLong(this.dos, Calendar.getInstance().getTimeInMillis());
            stmt.setString(this.tres, tipoConsultaStr);
            int retorno = stmt.executeUpdate();
            logger.info(retorno);
        } catch (SQLException ex) {
            logger.warn(ex);
        }
    }

    /**
     * Insertar estadisticas en la base de datos.
     * @param tiempo tiempo.
     * @param tipoConsultaStr tipo de consulta como cadena de texto.
     */
    public final void insertarEstadisticaUltimasConsultas(final long tiempo,
            final String tipoConsultaStr) {
        try {
            PreparedStatement stmt;
            stmt = conexion.prepareStatement(this.configProperties
                    .getProperty("insertarEstadisticaUltimasConsultas"));
            stmt.setLong(this.uno, tiempo);
            stmt.setLong(this.dos, Calendar.getInstance().getTimeInMillis());
            stmt.setString(this.tres, tipoConsultaStr);
            int retorno = stmt.executeUpdate();
            logger.info(retorno);
        } catch (SQLException ex) {
            logger.warn(ex);
        }
    }

    /**
     * borra la tabla en la que se guardan las estadisticas por ejecucion.
     * @param conn conexion a la base de datos.
     */
    private void borrarEstructuraTmpEnBD(final Connection conn) {
        Statement stmt;
        String query;
        query = this.configProperties.getProperty("borrarEstructuraTmpEnBD");
        try {
            stmt = conn.createStatement();
            int retorno = stmt.executeUpdate(query);
            logger.info(retorno);
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    /**
     * inicializa la tabla en la que se guardan las estadisticas.
     * @param conn conexion a la base de datos.
     */
    private void iniciarEstructuraEnBD(final Connection conn) {
        Statement stmt;
        String query;
        query = this.configProperties.getProperty("iniciarEstructuraEnBD");
        try {
            stmt = conn.createStatement();
            int retorno = stmt.executeUpdate(query);
            logger.info(retorno);
        } catch (SQLException ex) {
            logger.warn("La tabla donde se guardan las estadisticas, ya existe."
                    );
        }
    }

    /**
     * inicializa la tabla intermedia en la que se guardan las estadisticas.
     * @param conn conexion a la base de datos.
     */
    private void iniciarEstructuraTmpEnBD(final Connection conn) {
        Statement stmt;
        String query;
        query = this.configProperties.getProperty("iniciarEstructuraTmpEnBD");
        try {
            stmt = conn.createStatement();
            int retorno = stmt.executeUpdate(query);
            logger.info(retorno);
        } catch (SQLException ex) {
            logger.warn("La tabla donde se guardan las estadisticas, ya existe."
                    );
        }
    }

    /**
     * Lanza la consulta SQL.
     * @param s consulta SQL.
     * @return resultado.
     */
    private String consultaSQL(final String s) {
        String res = null;
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = this.conexion.createStatement();
            rs = stmt.executeQuery(s); //lanzador de consulta
            while (rs.next()) {
                res = rs.getString(1);
            }
            stmt.close();
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            return res;
        }
    }

    /**
     * Media de tiempos del fichero.
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMedia() throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMedia"));
    }

    /**
     * Media de tiempos en consultas de selecion (SELECT).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaSeleccion() throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaSeleccion"));
    }

    /**
     * Media de tiempos en manipulacion de datos (INSERT / UPDATE /DELETE).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaManipulacionDatos() throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaManipulacionDatos"));
    }

    /**
     * Media de tiempos en manipulacion de tablas (DROP / CREATE).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaManipulacionTablas() throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaManipulacionTablas"));
    }

    /**
     * Media de tiempos de otras operaciones (?).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaOtrasOperaciones() throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaOtrasOperaciones"));
    }
    // de ultima tabla

    /**
     * Media de tiempos del fichero de la ultima prueba.
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaUltimaPrueba() throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaUltimaPrueba"));
    }

    /**
     * Media de tiempos en consultas de selecion de la ultima prueba (SELECT).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaSeleccionUltimaPrueba()
            throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaSeleccionUltimaPrueba"));
    }

    /**
     * Media de tiempos en manipulacion de datos de la ultima prueba
     * (INSERT / UPDATE /DELETE).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaManipulacionDatosUltimaPrueba()
            throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaManipulacionDatosUltimaPrueba"));
    }

    /**
     * Media de tiempos en manipulacion de tablas  de la ultima prueba
     * (DROP / CREATE).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaManipulacionTablasUltimaPrueba()
            throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaManipulacionTablasUltimaPrueba"));
    }

    /**
     * Media de tiempos de otras operaciones  de la ultima prueba (?).
     * @return tiempo de la media en segundos.
     * @throws java.sql.SQLException excepcion SQL.
     */
    public final String mostrarMediaOtrasOperacionesPrueba()
            throws SQLException {
        return this.consultaSQL(this.configProperties
                .getProperty("mostrarMediaOtrasOperacionesPrueba"));
    }

    /**
     * Inicia la estadisticas de la sentencia actual.
     */
    public final void iniciarEstadisticaDeConsultaActual() {
        borrarEstructuraTmpEnBD(conexion);
        iniciarEstructuraTmpEnBD(conexion);
    }
}
