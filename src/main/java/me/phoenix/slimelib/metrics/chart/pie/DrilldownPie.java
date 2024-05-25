package me.phoenix.slimelib.metrics.chart.pie;

import me.phoenix.slimelib.metrics.chart.CustomChart;
import me.phoenix.slimelib.metrics.object.JsonObject;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * A Drilldown pie chart.
 */
public class DrilldownPie extends CustomChart{

	private final Callable<Map<String, Map<String, Integer>>> callable;

	/**
	 * Instantiates a new Drilldown pie chart.
	 *
	 * @param chartId the chart id
	 * @param callable the callable
	 */
	public DrilldownPie(String chartId, Callable<Map<String, Map<String, Integer>>> callable){
		super(chartId);
		this.callable = callable;
	}

	@Override
	public JsonObject getChartData() throws Exception{
		final JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
		final Map<String, Map<String, Integer>> map = callable.call();
		if(map == null || map.isEmpty()){
			return null;
		}
		boolean reallyAllSkipped = true;
		for(Map.Entry<String, Map<String, Integer>> entryValues : map.entrySet()){
			final JsonObjectBuilder valueBuilder = new JsonObjectBuilder();
			boolean allSkipped = true;
			for(Map.Entry<String, Integer> valueEntry : map.get(entryValues.getKey()).entrySet()){
				valueBuilder.appendField(valueEntry.getKey(), valueEntry.getValue());
				allSkipped = false;
			}
			if(!allSkipped){
				reallyAllSkipped = false;
				valuesBuilder.appendField(entryValues.getKey(), valueBuilder.build());
			}
		}
		if(reallyAllSkipped){
			return null;
		}
		return new JsonObjectBuilder().appendField("values", valuesBuilder.build()).build();
	}
}
