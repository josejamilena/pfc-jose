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



