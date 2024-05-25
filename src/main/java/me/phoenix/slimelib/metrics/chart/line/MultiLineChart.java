package me.phoenix.slimelib.metrics.chart.line;

import me.phoenix.slimelib.metrics.chart.CustomChart;
import me.phoenix.slimelib.metrics.object.JsonObject;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * A Multi line chart.
 */
public class MultiLineChart extends CustomChart{

	private final Callable<Map<String, Integer>> callable;

	/**
	 * Instantiates a new Multi line chart.
	 *
	 * @param chartId the chart id
	 * @param callable the callable
	 */
	public MultiLineChart(String chartId, Callable<Map<String, Integer>> callable){
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
		boolean allSkipped = true;
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			if(entry.getValue() == 0){
				continue;
			}
			allSkipped = false;
			valuesBuilder.appendField(entry.getKey(), entry.getValue());
		}
		if(allSkipped){
			return null;
		}
		return new JsonObjectBuilder().appendField("values", valuesBuilder.build()).build();
	}
}
