package net.astesana.javaluator;

import java.util.HashMap;
import java.util.Map;

public class StaticVariableSet<T> implements AbstractVariableSet<T> {
	private final Map<String, T> varToValue;
	
	public StaticVariableSet() {
		this.varToValue = new HashMap<String, T>();
	}
	
	public T getVariableValue(String variableName) {
		return this.varToValue.get(variableName);
	}
	
	public void set(String variableName, T value) {
		this.varToValue.put(variableName, value);
	}
}
