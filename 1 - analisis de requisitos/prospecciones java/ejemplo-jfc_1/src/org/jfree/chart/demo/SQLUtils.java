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

    public static Map<String, XYSeriesCollection> tiemposScriptCadaServidorXYDataset(final Connection conn) {
        Map<String, XYSeriesCollection> mxys = new HashMap<String, XYSeriesCollection>();
        XYSeriesCollection xys = null;
        List<String> lsHost = SQLUtils.listaHostSgbd(conn);
        List<String> lsScript = SQLUtils.listaScript(conn);
        Statement stmt = null;
        ResultSet rs = null;
        XYSeries serie = null;
        try {
        for (String host : lsHost) {
            xys = new XYSeriesCollection();
            for (String script : lsScript) {
                String consulta = "select tiempo from estadisticas where " + "host_sgbd=\'" + host + "\' and tipo=\'" + script + "\'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(consulta);
                int i = 0;
                while (rs.next()) {
                    serie = new XYSeries("Tiempo " + script);
                    serie.add(i, rs.getFloat(1));
                    ++i;
                }
                xys.addSeries(serie);
            }
            mxys.put(host, xys);
        }
        rs.close();
        stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.err.println(mxys.size());
            System.err.println(mxys.values().size());
            return mxys;
        }
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
        System.err.println(listaHostSgbd(conn));
        System.err.println(listaHostCliente(conn));
        System.err.println(listaScript(conn));
    }
}
