package sac.stats;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * XY chart related to statistics object (charts are generated by jfreechart software,
 * http://www.jfree.org/jfreechart/).
 * 
 * @author Przemysław Klęsk (<a href="mailto: pklesk@wi.zut.edu.pl">wi.zut.edu.pl</a>)
 * @author Marcin Korzeń (<a href="mailto: mkorzen@wi.zut.edu.pl">wi.zut.edu.pl</a>)
 */
public class StatsXYChart {
	
	/**
	 * Constant - default chart width.
	 */
	private static final int DEFAULT_WIDTH = 1280;
	
	/**
	 * Constant - default chart height.
	 */
	private static final int DEFAULT_HEIGHT = 640;

	/**
	 * Reference to statistics object.
	 */
	private Stats stats;
	
	/**
	 * Plot label.
	 */
	private String plotLabel;
		
	/**
	 * Label for x axis.
	 */
	private String xAxisLabel;
	
	/**
	 * Label for y axis.
	 */
	private String yAxisLabel;
	
	/**
	 * Collection of XY series.
	 */
	private XYSeriesCollection collection;

	/**
	 * Creates a new instance of StatsXYChart.
	 * 
	 * @param stats reference to statistics object
	 * @param plotLabel wanted plot label
	 * @param xAxisLabel wanted x axis label
	 * @param yAxisLabel wanted y axis label
	 */
	public StatsXYChart(Stats stats, String plotLabel, String xAxisLabel, String yAxisLabel) {
		this.stats = stats;
		this.plotLabel = plotLabel;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
		collection = new XYSeriesCollection();
	}

	/**
	 * Calculates and adds a labeled series of XY values to this chart, and returns the reference to it.
	 * 	 
	 * @param label imposed label 
	 * @param operationType type of operation
	 * @param category category as string
	 * @param indexPositionAsXVariable position in multi index working as x (independent) variable
	 * @param multiIndexPattern multi index pattern (may contain nulls)
	 * @return reference to XY chart with a new series added to it
	 */
	public StatsXYChart addSeries(String label, StatsOperationType operationType, String category, int indexPositionAsXVariable, Object... multiIndexPattern) {
		List<Object> xs = stats.subindexValues(category, indexPositionAsXVariable, multiIndexPattern);
		XYSeries series = new XYSeries(label);
		collection.addSeries(series);

		for (Object x : xs) {
			Object[] multiIndexWithXFixed = Arrays.copyOf(multiIndexPattern, multiIndexPattern.length);
			multiIndexWithXFixed[indexPositionAsXVariable] = x;
			
			double xAsDouble = 0;
			if (x instanceof Double) xAsDouble = (Double) x;
			if (x instanceof Float) xAsDouble = (Float) x;
			else if (x instanceof Integer) xAsDouble = Double.valueOf(((Integer) x).intValue());
			else if (x instanceof Long) xAsDouble = Double.valueOf(((Long) x).intValue());
			else if (x instanceof Byte) xAsDouble = Double.valueOf(((Byte) x).intValue());
			else xAsDouble = Double.valueOf(x.toString().length());
			
			series.add(xAsDouble, stats.operation(operationType, category, multiIndexWithXFixed));
		}
		return this;
	}

	/**
	 * Saves this XY chart as a JPEG file of wanted width and height, and under given location.
	 * 
	 * @param filePath wanted location
	 * @param width chart width
	 * @param height chart height
	 * @throws IOException when something related to file operations goes wrong
	 */
	public void saveAsJPEG(String filePath, int width, int height) throws IOException {
		XYItemRenderer renderer = new XYLineAndShapeRenderer(true, true);
		NumberAxis domainAxis = new NumberAxis(xAxisLabel);
		NumberAxis rangeAxis = new NumberAxis(yAxisLabel);

		XYPlot plot = new XYPlot(collection, domainAxis, rangeAxis, renderer);
		plot.setOrientation(PlotOrientation.VERTICAL);
		JFreeChart chart = new JFreeChart(plotLabel, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		ChartUtilities.saveChartAsJPEG(new File(filePath), chart, width, height);
	}
	
	/**
	 * Saves this XY chart as a JPEG file of default width and height and under given location.
	 * 
	 * @param filePath wanted location
	 * @throws IOException when something related to file operations goes wrong
	 */
	public void saveAsJPEG(String filePath) throws IOException {
		saveAsJPEG(filePath, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}	
}