package sqlite2csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Convierte datos SQLite a fichero CSV.
 * @author Jose Antonio Jamilena Daza
 */
public class SQLite2CSV {

    /**
     * Constructor.
     */
    public SQLite2CSV() {
    }

    /**
     * Test.
     * @param args vacio
     * @throws java.lang.ClassNotFoundException error cargando driver
     * @throws java.sql.SQLException erro BBDD
     * @throws java.io.IOException error E/S
     */
    public static void main(final String[] args)
            throws ClassNotFoundException, SQLException, IOException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =
                DriverManager.getConnection("jdbc:sqlite:.\\estadisticas.db3");
        SQLite2CSV s2c = new SQLite2CSV();
        s2c.csv("estadisticas.csv", conn, "SELECT * FROM ESTADISTICAS");
    }

    /**
     * Genera la tabla a guardar.
     * @param conn conexión JDBC
     * @param consulta consulta
     * @return tabla
     * @throws java.sql.SQLException error de BBDD
     */
    private List < List < String > > listaRegistros(
            final Connection conn, final String consulta)
            throws SQLException {
        List < List < String > >  res = new LinkedList < List < String > >();
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData md = null;
        // Get a statement from the connection
        stmt = conn.createStatement();
        // Execute the query
        rs = stmt.executeQuery(consulta);
        // Get the metadata
        md = rs.getMetaData();
        // temporal list
        LinkedList<String> tmp = new LinkedList<String>();
        // Print the column labels
        for (int i = 1; i <= md.getColumnCount(); i++) {
            tmp.add(md.getColumnLabel(i));
        }
        res.add(tmp);
        //
        tmp = null;
        tmp = new LinkedList < String >();
        // Loop through the result set
        while (rs.next()) {
            for (int i = 1; i <= md.getColumnCount(); i++) {
                tmp.add(rs.getString(i));
            }
            res.add(tmp);
            tmp = new LinkedList < String >();
        }
        // Close the result set, statement and the connection
        rs.close();
        stmt.close();
        return res;
    }

    /**
     * Obtiene el fichero CSV.
     * @param fichero nombre fichero salida
     * @param conn conexion JDBC
     * @param consulta consulta
     * @throws java.sql.SQLException error de BBDD
     * @throws java.io.IOException error E/S
     */
    public final void csv(final String fichero, final Connection conn,
            final String consulta) throws SQLException, IOException {
        BufferedWriter bw = null;
        bw = new BufferedWriter(new FileWriter(fichero));
        List < List < String > > l = listaRegistros(conn, consulta);
        for (List < String > i : l) {
            int k = 1;
            for (String j : i) {
                if (i.size() == k) {
                    bw.write(j);
                    bw.flush();
                } else {
                    bw.write(j + ",");
                    bw.flush();
                }
                k++;
            }
            bw.newLine();
            bw.flush();
        }
        bw.flush();
        bw.close();
    }
}
