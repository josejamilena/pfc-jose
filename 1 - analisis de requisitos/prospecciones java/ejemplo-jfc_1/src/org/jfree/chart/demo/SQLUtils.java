package org.jfree.chart.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Utilidades SQL.
 * @author Jose Antonio Jamilena Daza
 */
final class SQLUtils {

    /** No se permiten instancias. */
    private SQLUtils() {
    }
    
    public static List<String> listaScript(final Connection conn) {
        List<String> res = new LinkedList<String>();
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String consulta = "select tipo from estadisticas group by tipo";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                res.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return res;
        }
    }

    public static List<String> listaHostSgbd(final Connection conn) {
        List<String> res = new LinkedList<String>();
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String consulta = "select host_sgbd from estadisticas group by host_sgbd";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                res.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return res;
        }
    }

    public static List<String> listaHostCliente(final Connection conn) {
        List<String> res = new LinkedList<String>();
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String consulta = "select host_cliente from estadisticas group by host_cliente";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                res.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return res;
        }
    }

    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\estadisticas.db3");
        List<String> res = new LinkedList<String>();
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String consulta = "select tiempo from estadisticas where tipo=\'ejemplo.sql\'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                res.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            System.err.println(res);
        }
    }
}
