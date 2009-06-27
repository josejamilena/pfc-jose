package ToritoWebServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedList;

public class SQLtoGrafico {

    public static Grafico consultaSQL(final Connection conn, final String s) {
        Grafico gfc = new Grafico();
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(s);
            ResultSetMetaData md = rs.getMetaData();

//            for (int i = 1; i <= md.getColumnCount(); i++) {
//                System.out.print(md.getColumnLabel(i) + " ");
//            }

            gfc.setTituloEjeX(md.getColumnLabel(1));
            gfc.setTituloEjeY(md.getColumnLabel(2));

//            System.out.println();

            LinkedList<String> lista = new LinkedList<String>();

            while (rs.next()) {
//                for (int i = 1; i <= md.getColumnCount(); i++) {
//                    System.out.print(rs.getString(i) + " ");
//                }
//                System.out.print(c + " ");
//                System.out.println();
                lista.add(rs.getString(1));
//                System.out.println(rs.getString(1));
            }
            gfc.setLista(lista);
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            System.err.println(gfc.getTituloEjeX());
//            System.err.println(gfc.getTituloEjeY());
//            for (String i : gfc.getLista()) {
//                System.err.println(i);
//            }
            return gfc;
        }
    }
}
