package josejamilena.pfc.analizador;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficoPorSGBD extends JDialog {

    public GraficoPorSGBD(final String sgbd, final String script) throws ClassNotFoundException, SQLException {

        Map<String, String> res = new TreeMap<String, String>();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String consulta = "select tiempo, fecha from estadisticas where tipo=\'" + script + "\' and host_sgbd=\'" + sgbd + "\'";
        res.put("14:00","12312");
        res.put("14:05", "3312");
        res.put("14:10", "1812");
        res.put("14:15", "1231");
        res.put("14:20", "2666");
        res.put("14:25", "1131");
        res.put("14:30", "9131");
        res.put("14:35", "3131");
        res.put("14:40", "6131");
        res.put("14:45", "1231");
        res.put("14:50", "5631");
        res.put("14:55", "1131");
        res.put("15:00", "2781");
        res.put("15:05", "2131");

        Iterator it = res.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            dataset.setValue(Double.parseDouble(pairs.getValue().toString()),
                    script, pairs.getKey().toString());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                sgbd, // chart title
                "Hora", // domain axis label
                "Duración (milisegundos)", // range axis label
                dataset, // data
                PlotOrientation.HORIZONTAL,
                false, // include legend
                true,
                false);

        CategoryPlot plot = chart.getCategoryPlot();
        chart.setBackgroundPaint(Color.white);
        plot.setOutlinePaint(Color.black);
        ChartPanel chartPanel = new ChartPanel(chart);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add((new JPanel()).add(chartPanel));
        setContentPane(scrollPane);
    }
}


