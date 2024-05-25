package me.phoenix.slimelib.metrics.chart.pie;

import me.phoenix.slimelib.metrics.chart.CustomChart;
import me.phoenix.slimelib.metrics.object.JsonObject;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;

import java.util.concurrent.Callable;

/**
 * A Simple pie chart.
 */
public class SimplePie extends CustomChart{

	private final Callable<String> callable;

	/**
	 * Instantiates a new Simple pie chart.
	 *
	 * @param chartId the chart id
	 * @param callable the callable
	 */
	public SimplePie(String chartId, Callable<String> callable){
		super(chartId);
		this.callable = callable;
	}

	@Override
	protected JsonObject getChartData() throws Exception{
		final String value = callable.call();
		if(value == null || value.isEmpty()){
			return null;
		}
		return new JsonObjectBuilder().appendField("value", value).build();
	}
}
