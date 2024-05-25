package me.phoenix.slimelib.metrics.chart.bar;

import me.phoenix.slimelib.metrics.chart.CustomChart;
import me.phoenix.slimelib.metrics.object.JsonObject;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * An Advanced bar chart.
 */
public class AdvancedBarChart extends CustomChart{

	private final Callable<Map<String, int[]>> callable;

	/**
	 * Instantiates a new Advanced bar chart.
	 *
	 * @param chartId the chart id
	 * @param callable the callable
	 */
	public AdvancedBarChart(String chartId, Callable<Map<String, int[]>> callable){
		super(chartId);
		this.callable = callable;
	}

	@Override
	protected JsonObject getChartData() throws Exception{
		final JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
		final Map<String, int[]> map = callable.call();
		if(map == null || map.isEmpty()){
			return null;
		}
		boolean allSkipped = true;
		for(Map.Entry<String, int[]> entry : map.entrySet()){
			if(entry.getValue().length == 0){
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
