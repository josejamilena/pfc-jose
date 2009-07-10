package josejamilena.pfc.analizador;

import josejamilena.pfc.analizador.sql.SQLUtils;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraficoPorCliente extends JDialog {

    public GraficoPorCliente(final String cliente) throws ClassNotFoundException, SQLException {
        int max = 0;
        double media = 0.0;

        XYSeriesCollection dataset = new XYSeriesCollection();

        List<String> listaScript = SQLUtils.listaScript(App.conn);
//        System.err.println(sgbd);
//        System.err.println(listaScript);

        for (String script : listaScript) {
            List<String> res = new LinkedList<String>();
            Statement stmt = null;
            ResultSet rs = null;
            String consulta = "select tiempo from estadisticas where tipo=\'" + script + "\' and host_cliente=\'" + cliente + "\'";
            stmt = App.conn.createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                res.add(rs.getString(1));
                media = media + Double.parseDouble(rs.getString(1));
            }
            rs.close();
            stmt.close();

            int nuevoMax = res.size();
            if (max < nuevoMax) {
                max = nuevoMax;
            }

            XYSeries s1 = new XYSeries("Script: " + script);

            int i = 0;
            for (String tmp : res) {
                s1.add(i, Double.parseDouble(tmp));
                i++;
            }

            dataset.addSeries(s1);
        }

        XYSeries s2 = new XYSeries("Media");
        int j = 0;
        double valorMedia = media / max;
        while (max > 0) {
            s2.add(j, valorMedia);
            j++;
            max--;
        }
        dataset.addSeries(s2);

        JFreeChart chart = ChartFactory.createXYLineChart(
                cliente, // chart title
                "Iteración", // domain axis label
                "Duración (milisegundos)", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true,
                false);


        XYPlot plot = chart.getXYPlot();
        chart.setBackgroundPaint(Color.white);
        plot.setOutlinePaint(Color.black);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));
        setContentPane(chartPanel);
    }

//    public static void main(final String[] args) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        App.conn = DriverManager.getConnection("jdbc:sqlite:.\\estadisticas.db3");
//        final GraficoPorCliente demo = new GraficoPorCliente("vmware");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//        //demo.setDefaultCloseOperation(EXIT_ON_CLOSE);
//    }
}


