package algorithm;

import java.util.Set;
import java.util.TreeSet;

public class SetNode<K, V> {
	
	private Set<V> values = new TreeSet<>();
	private K key;

	
	public SetNode(K key,V val) {
		this.key = key;
		values.add(val);
	}

	/**
	 * @return the values
	 */
	public Set<V> getValues() {
		return values;
	}

	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}

}
