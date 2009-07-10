package josejamilena.pfc.analizador.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Utilidades SQL.
 * @author Jose Antonio Jamilena Daza
 */
public final class SQLUtils {

    /** No se permiten instancias. */
    private SQLUtils() {
    }

    /**
     * Lista de scripts.
     * @param conn Conexión JDBC
     * @return lista
     */
    public static List<String> listaScript(final Connection conn)
            throws SQLException {
        List<String> res = new LinkedList<String>();
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
        return res;
    }

    /**
     * Lista de host servidores de BBDD.
     * @param conn Conexión JDBC
     * @return lista
     */
    public static List<String> listaHostSgbd(final Connection conn)
            throws SQLException {
        List<String> res = new LinkedList<String>();
        Statement stmt = null;
        ResultSet rs = null;
        String consulta = "select host_sgbd from estadisticas group by "
                + "host_sgbd";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(consulta);
        while (rs.next()) {
            res.add(rs.getString(1));
        }
        rs.close();
        stmt.close();
        return res;
    }

    /**
     * Lista de host clientes.
     * @param conn Conexión JDBC
     * @return lista
     */
    public static List<String> listaHostCliente(final Connection conn) 
            throws SQLException {
        List<String> res = new LinkedList<String>();

        Statement stmt = null;
        ResultSet rs = null;
        String consulta = "select host_cliente from estadisticas group by "
                + "host_cliente";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(consulta);
        while (rs.next()) {
            res.add(rs.getString(1));
        }
        rs.close();
        stmt.close();
        return res;
    }
}



