package ToritoWebServer;

import ToritoWebServer.GrupoConsulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SQLUtils {

    public static Grafico consultaSQL2Grafico(final Connection conn, final String s) {
        Grafico gfc = new Grafico();
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String consulta = s;
            stmt = conn.createStatement();
            if (!s.endsWith(";")) {
                consulta = consulta + ";";
            }
            rs = stmt.executeQuery(consulta);
            ResultSetMetaData md = rs.getMetaData();
            gfc.setTituloEjeX(md.getColumnLabel(1));
            gfc.setTituloEjeY(md.getColumnLabel(2));
            LinkedList<String> lista = new LinkedList<String>();

            while (rs.next()) {
                lista.add(rs.getString(1));
            }
            gfc.setLista(lista);
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return gfc;
        }
    }

    public static List<GrupoConsulta> ListaGruposConsultas(Connection conn) {
        List<GrupoConsulta> res = null;
        try {
            res = new LinkedList<GrupoConsulta>();
            Statement stmt = null;
            ResultSet rs = null;
            ResultSetMetaData md = null;
            String nombreGrupo;
            stmt = conn.createStatement();
            //--
            rs = stmt.executeQuery("select host_sgbd from estadisticas group by host_sgbd");
            md = rs.getMetaData();
            List<String> listaHostSgbd = new LinkedList<String>();
            nombreGrupo = rs.getString(1);
            while (rs.next()) {
                String tmp = "select tiempo, fecha from estadisticas where host_sgbd=\'" + rs.getString(1) + "\'";
                listaHostSgbd.add(tmp);
            }
            res.add(new GrupoConsulta(nombreGrupo, listaHostSgbd));
            //--
            rs = stmt.executeQuery("select host_cliente from estadisticas group by host_cliente");
            md = rs.getMetaData();
            List<String> listaHostClientes = new LinkedList<String>();
            nombreGrupo = rs.getString(1);
            while (rs.next()) {
                String tmp = "select tiempo, fecha from estadisticas where host_cliente=\'" + rs.getString(1) + "\'";
                listaHostClientes.add(tmp);
            }
            res.add(new GrupoConsulta(nombreGrupo, listaHostClientes));
            //--
            rs = stmt.executeQuery("select tipo from estadisticas group by tipo");
            md = rs.getMetaData();
            List<String> listaHostTipo = new LinkedList<String>();
            nombreGrupo = rs.getString(1);
            while (rs.next()) {
                String tmp = "select tiempo, fecha from estadisticas where tipo=\'" + rs.getString(1) + "\'";
                listaHostTipo.add(tmp);
            }
            res.add(new GrupoConsulta(nombreGrupo, listaHostTipo));
            //--
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return res;
        }
    }
}
