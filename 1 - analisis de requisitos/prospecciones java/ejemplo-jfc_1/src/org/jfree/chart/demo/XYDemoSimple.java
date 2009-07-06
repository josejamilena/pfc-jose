package org.jfree.chart.demo;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class XYDemoSimple extends ApplicationFrame {

    public XYDemoSimple(final String title) {
        super(title);
        XYSeries s1 = new XYSeries("Series 1");

        s1.add(1,1);
        s1.add(2,11);
        s1.add(3,11111);
        s1.add(4,5);
        s1.add(5,5);
        s1.add(6,5);
        s1.add(7,1342);
        s1.add(8,12);
        s1.add(9,1143);
        s1.add(10,145);
        s1.add(11,16);
        s1.add(12,176);
        s1.add(13,78671);
        s1.add(14,198);
        s1.add(15,121);
        s1.add(16,1234);
        s1.add(17,143);
        s1.add(18,165);



        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Log Axis Demo", // chart title
                "Category", // domain axis label
                "Value", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true,
                true);

        XYPlot plot = chart.getXYPlot();
        NumberAxis domainAxis = new NumberAxis("x");
        NumberAxis rangeAxis = new LogarithmicAxis("Log(y)");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);
        chart.setBackgroundPaint(Color.white);
        plot.setOutlinePaint(Color.black);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    *
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final XYDemoSimple demo = new XYDemoSimple("XY Log Axes Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
}


