package org.jfree.chart.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

public class GraficaPorScript extends JDialog {

    public GraficaPorScript(final String title) {
        this.setTitle(title);
        JFreeChart chart = createCombinedChart();
        ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);
        panel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(panel);
    }

    private JFreeChart createCombinedChart() {
        CombinedDomainXYPlot plot = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\estadisticas.db3");
            List<String> listaServidores = SQLUtils.listaHostSgbd(conn);
            plot = new CombinedDomainXYPlot(new NumberAxis("Tiempo"));
            for (String tmp : listaServidores) {
                System.err.println(tmp);
                plot.add(new XYPlot(createDataset(conn, tmp),
                        null,
                        new NumberAxis("Range " + tmp),
                        new StandardXYItemRenderer()));
            }
            plot.setOrientation(PlotOrientation.VERTICAL);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return new JFreeChart("Script por servidor", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        }
    }

    private XYDataset createDataset(Connection conn, String servidor) throws Exception {
        return SQLUtils.tiemposScriptCadaServidorXYDataset(conn).get(servidor);
    }
}

