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

package josejamilena.pfc.servidor.chartserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Utilidades SQL.
 * @author Jose Antonio Jamilena Daza
 */
final class SQLUtils {

    /** No se permiten instancias. */
    private SQLUtils() {
    }

    /**
     * Obtiene datos desde SQL y los convierte en Grafico.
     * @param conn conexion JDBC
     * @param s sentencia SQL
     * @return Grafico
     */
    public static Grafico consultaSQL2Grafico(final Connection conn,
            final String s) {
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
            LinkedList < String > lista = new LinkedList < String >();

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

    /**
     * Obtiene una lista de grupos de consulta.
     * @param conn conexion JDBC
     * @return lista de grupos de consulta
     */
    public static List<GrupoConsulta> listaGruposConsultas(
            final Connection conn) {
        List<GrupoConsulta> res = null;
        try {
            res = new LinkedList<GrupoConsulta>();
            Statement stmt = null;
            ResultSet rs = null;
            ResultSetMetaData md = null;
            String nombreGrupo;
            stmt = conn.createStatement();
            //--
            rs = stmt.executeQuery("select host_sgbd from estadisticas group "
                    + "by host_sgbd");
            md = rs.getMetaData();
            List<String> listaHostSgbd = new LinkedList<String>();
            nombreGrupo = rs.getString(1);
            while (rs.next()) {
                String tmp = "select tiempo, fecha from estadisticas where "
                        + "host_sgbd=\'" + rs.getString(1) + "\'";
                listaHostSgbd.add(tmp);
            }
            res.add(new GrupoConsulta(nombreGrupo, listaHostSgbd));
            //--
            rs = stmt.executeQuery("select host_cliente from estadisticas "
                    + "group by host_cliente");
            md = rs.getMetaData();
            List < String > listaHostClientes = new LinkedList < String >();
            nombreGrupo = rs.getString(1);
            while (rs.next()) {
                String tmp = "select tiempo, fecha from estadisticas where "
                        + "host_cliente=\'" + rs.getString(1) + "\'";
                listaHostClientes.add(tmp);
            }
            res.add(new GrupoConsulta(nombreGrupo, listaHostClientes));
            //--
            rs = stmt.executeQuery(
                    "select tipo from estadisticas group by tipo");
            md = rs.getMetaData();
            List < String > listaHostTipo = new LinkedList < String >();
            nombreGrupo = rs.getString(1);
            while (rs.next()) {
                String tmp = "select tiempo, fecha from estadisticas where "
                        + "tipo=\'" + rs.getString(1) + "\'";
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
