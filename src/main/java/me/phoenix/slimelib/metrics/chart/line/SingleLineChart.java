package me.phoenix.slimelib.metrics.chart.line;

import me.phoenix.slimelib.metrics.chart.CustomChart;
import me.phoenix.slimelib.metrics.object.JsonObject;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;

import java.util.concurrent.Callable;

/**
 * A Single line chart.
 */
public class SingleLineChart extends CustomChart{

	private final Callable<Integer> callable;

	/**
	 * Instantiates a new Single line chart.
	 *
	 * @param chartId the chart id
	 * @param callable the callable
	 */
	public SingleLineChart(String chartId, Callable<Integer> callable){
		super(chartId);
		this.callable = callable;
	}

	@Override
	protected JsonObject getChartData() throws Exception{
		final int value = callable.call();
		if(value == 0){
			return null;
		}
		return new JsonObjectBuilder().appendField("value", value).build();
	}
}
