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
 * Lanzador de script con procesos SQL.
 * @author Jose Antonio Jamilena Daza.
 */
public class SqlRunner {

    /** Logger. */
    private static Logger logger = Logger.getLogger(SqlRunner.class);
    /** conexion. */
    private Connection conexion = null;
    /** lista de consultas. */
    private List < String > listaDeConsultas = null;

    /**
     * Lanzador SQL.
     * @param connection coneexión JDBC.
     */
    public SqlRunner(final Connection connection) {
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
    public SqlRunner(final String driver, final String url,
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
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conexion.createStatement();
        try {
            rs = stmt.executeQuery(s); //lanzador de consulta
        } catch (SQLException ex) {
            logger.error(ex);
        }
        rs.close();
        stmt.close();
    }

    /**
     * leer script SQL.
     * @param reader reader.
     * @throws java.io.IOException error de lectura.
     * @throws java.sql.SQLException fallo JDBC.
     */
    public final void runScript(final Reader reader)
            throws SQLException, IOException {
        BufferedReader br = new BufferedReader(reader);
        listaDeConsultas = new LinkedList < String > ();
        String bloque = "";
        String tmp = br.readLine();
        // System.err.println(tmp);
        while (tmp != null) {
            // System.err.println(bloque);
            // System.err.println(tmp);
            if (tmp.startsWith("--") || (tmp.startsWith("//"))) {
                bloque = "";
            } else if (!tmp.endsWith(";")) {
                bloque = bloque + " " + tmp;
            } else {
                // System.err.println(bloque);
                bloque = bloque + " " + tmp.replace(";", "");
                listaDeConsultas.add(bloque.toUpperCase());
                bloque = "";
            }
            tmp = br.readLine();
        }
        br.close();
        for (String s : listaDeConsultas) {
            // System.err.println(s);
            this.consultaSQL(s);
        }
    }

//    public static void main(String[] args)
//        throws IOException, SQLException, ClassNotFoundException {
//        // PropertyConfigurator.configure("log4j.properties");
//        System.err.println((new java.util.Date()));
//        (new SqlRunner("oracle.jdbc.driver.OracleDriver",
//            "jdbc:oracle:thin:@192.168.2.17:1521:XE", "oracle", "oracle")
//             ).runScript(new BufferedReader(new FileReader("ejemplo.sql")));
//        System.err.println((new java.util.Date()));
//    }


    public SqlRunner(final String driver, final String url,
            final String username, final String password)
            throws IOException, SQLException, ClassNotFoundException {
        Class.forName(driver);
        this.conexion = DriverManager.getConnection(url, username, password);
    }

    public void runQuery(final String s) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conexion.createStatement();
        rs = stmt.executeQuery(s); //lanzador de consulta
        rs.close();
        stmt.close();
    }

    public List<String[]> runQueryResult(final String s) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<String[]> res = new LinkedList<String[]>();
        stmt = conexion.createStatement();
        rs = stmt.executeQuery(s); //lanzador de consulta
        ResultSetMetaData rsmt = rs.getMetaData();
        int numColumn = rsmt.getColumnCount();
        while (rs.next()) {
        //if (rs.next()) {
            String[] resLine = new String[numColumn];
            for (int i = 0; i <= numColumn; i++) {
                resLine[i] = rs.getString(i);
            }
            res.add(resLine);
        }
        rs.close();
        stmt.close();
        return res;
    }

}
