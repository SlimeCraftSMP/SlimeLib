package me.phoenix.slimelib.metrics.object;

import me.phoenix.slimelib.other.TypeUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Json object builder.
 */
public class JsonObjectBuilder{

	private static final String NULL_ERROR = "JSON value must not be null";

	private StringBuilder builder = new StringBuilder();

	private boolean hasAtLeastOneField = false;

	/**
	 * Instantiates a new Json object builder.
	 */
	public JsonObjectBuilder(){
		builder.append("{");
	}

	private static String escape(String value){
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < value.length(); i++){
			char c = value.charAt(i);
			if(c == '"'){
				builder.append("\\\"");
			} else if(c == '\\'){
				builder.append("\\\\");
			} else if(c <= '\u000F'){
				builder.append("\\u000").append(Integer.toHexString(c));
			} else if(c <= '\u001F'){
				builder.append("\\u00").append(Integer.toHexString(c));
			} else{
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * Append null to json object builder.
	 *
	 * @param key the key
	 *
	 * @return the json object builder
	 */
	public JsonObjectBuilder appendNull(String key){
		appendFieldUnescaped(key, "null");
		return this;
	}

	/**
	 * Append field to json object builder.
	 *
	 * @param key the key
	 * @param value the value
	 *
	 * @return the json object builder
	 */
	public JsonObjectBuilder appendField(String key, String value){
		if(value == null){
			throw new IllegalArgumentException(NULL_ERROR);
		}
		appendFieldUnescaped(key, "\"" + escape(value) + "\"");
		return this;
	}

	/**
	 * Append field to json object builder.
	 *
	 * @param key the key
	 * @param value the value
	 *
	 * @return the json object builder
	 */
	public JsonObjectBuilder appendField(String key, int value){
		appendFieldUnescaped(key, TypeUtils.asString(value));
		return this;
	}

	/**
	 * Append field to json object builder.
	 *
	 * @param key the key
	 * @param object the object
	 *
	 * @return the json object builder
	 */
	public JsonObjectBuilder appendField(String key, JsonObject object){
		if(object == null){
			throw new IllegalArgumentException("JSON object must not be null");
		}
		appendFieldUnescaped(key, object.toString());
		return this;
	}

	/**
	 * Append field to json object builder.
	 *
	 * @param key the key
	 * @param values the values
	 *
	 * @return the json object builder
	 */
	public JsonObjectBuilder appendField(String key, String[] values){
		if(values == null){
			throw new IllegalArgumentException(NULL_ERROR);
		}
		String escapedValues = Arrays.stream(values).map(value -> "\"" + escape(value) + "\"").collect(Collectors.joining(","));
		appendFieldUnescaped(key, "[" + escapedValues + "]");
		return this;
	}

	/**
	 * Append field to json object builder.
	 *
	 * @param key the key
	 * @param values the values
	 *
	 * @return the json object builder
	 */
	public JsonObjectBuilder appendField(String key, int[] values){
		if(values == null){
			throw new IllegalArgumentException(NULL_ERROR);
		}
		String escapedValues = Arrays.stream(values).mapToObj(String::valueOf).collect(Collectors.joining(","));
		appendFieldUnescaped(key, "[" + escapedValues + "]");
		return this;
	}

	/**
	 * Append field to json object builder.
	 *
	 * @param key the key
	 * @param values the values
	 *
	 * @return the json object builder
	 */
	public JsonObjectBuilder appendField(String key, JsonObject[] values){
		if(values == null){
			throw new IllegalArgumentException(NULL_ERROR);
		}
		String escapedValues = Arrays.stream(values).map(JsonObject::toString).collect(Collectors.joining(","));
		appendFieldUnescaped(key, "[" + escapedValues + "]");
		return this;
	}

	private void appendFieldUnescaped(String key, String escapedValue){
		if(builder == null){
			throw new IllegalStateException("JSON has already been built");
		}
		if(key == null){
			throw new IllegalArgumentException("JSON key must not be null");
		}
		if(hasAtLeastOneField){
			builder.append(",");
		}
		builder.append("\"").append(escape(key)).append("\":").append(escapedValue);
		hasAtLeastOneField = true;
	}

	/**
	 * Build json object.
	 *
	 * @return the json object
	 */
	public JsonObject build(){
		if(builder == null){
			throw new IllegalStateException("JSON has already been built");
		}
		JsonObject object = new JsonObject(builder.append("}").toString());
		builder = null;
		return object;
	}
}
