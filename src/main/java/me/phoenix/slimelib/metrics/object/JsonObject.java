package me.phoenix.slimelib.metrics.object;

/**
 * Json object.
 */
public class JsonObject{

	private final String value;

	/**
	 * Instantiates a new Json object.
	 *
	 * @param value the value
	 */
	JsonObject(String value){
		this.value = value;
	}

	@Override
	public String toString(){
		return value;
	}
}
