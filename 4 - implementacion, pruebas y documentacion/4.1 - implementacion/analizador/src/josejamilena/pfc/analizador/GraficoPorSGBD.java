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


        Statement stmt = null;
        ResultSet rs = null;
        String consulta = "select tiempo, fecha from estadisticas where tipo=\'" + script + "\' and host_sgbd=\'" + sgbd + "\'";
        stmt = App.conn.createStatement();
        rs = stmt.executeQuery(consulta);
        while (rs.next()) {
            res.put(rs.getString(2), rs.getString(1));
        }
        rs.close();
        stmt.close();

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


