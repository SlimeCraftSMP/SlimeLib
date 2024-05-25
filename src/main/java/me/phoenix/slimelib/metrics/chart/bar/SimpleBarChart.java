package me.phoenix.slimelib.metrics.chart.bar;

import me.phoenix.slimelib.metrics.chart.CustomChart;
import me.phoenix.slimelib.metrics.object.JsonObject;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * A Simple bar chart.
 */
public class SimpleBarChart extends CustomChart{

	private final Callable<Map<String, Integer>> callable;

	/**
	 * Instantiates a new Simple bar chart.
	 *
	 * @param chartId the chart id
	 * @param callable the callable
	 */
	public SimpleBarChart(String chartId, Callable<Map<String, Integer>> callable){
		super(chartId);
		this.callable = callable;
	}

	@Override
	protected JsonObject getChartData() throws Exception{
		final JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
		final Map<String, Integer> map = callable.call();
		if(map == null || map.isEmpty()){
			return null;
		}
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			valuesBuilder.appendField(entry.getKey(), new int[]{entry.getValue()});
		}
		return new JsonObjectBuilder().appendField("values", valuesBuilder.build()).build();
	}
}
