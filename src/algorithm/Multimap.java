package algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.util.Callback;

/**
 * 
 * @author sascha
 *
 *         The Multimap implements a Binary search function on the key of
 *         objects, and stores different objects with same keys in a node. To
 *         allow this functionality a key getter callback must be provided. This
 *         callback should return a key for the object.It is important that the
 *         callback always have to return the same value for the same object.
 * 
 *         TODO multiple values with the same key can be removed or added to
 *         this map effectively like it was one;
 * 
 *         It
 *
 * @param <T>
 *            the type of the value to store
 */
public class Multimap<K, V> {

	private Callback<V, K> keyGetter; // callback used to get the key of the
										// value
	private HashMap<K, SetNode<K, V>> map = new HashMap<K, SetNode<K, V>>();
	private int size = 0;

	public Multimap(Callback<V, K> keyGetter) {
		this.keyGetter = keyGetter;
	}

	/**
	 * Uses the callback to get the id of the value. if an entry with the id
	 * does not exist a new node is generated and added to the map if an entry
	 * with the id is found this node may be added to the node if it is not
	 * already contained in the node
	 * 
	 * @param val
	 *            the value to add
	 * @return the node where the element was added or null if it was allready
	 *         containde
	 */
	public SetNode<K, V> put(V val) {
		// get the id from the object via callback
		K id = keyGetter.call(val);

		// try to get a matching node
		SetNode<K, V> node = map.get(id);

		if (node != null) {
			// if the map contains the key try to add the value to the existing
			// node
			if (!node.getValues().add(val))
				node = null;
		} else {
			// if the key is not contained create a new node
			node = new SetNode<K, V>(keyGetter.call(val), val);
			// and add id to the map and return the new node
			map.put(id, node);
		}

		// count it if it was an add
		if (node != null)
			size++;
		return node;
	}

	/**
	 * removes all values with the given id
	 * 
	 * @param id
	 *            the id to delete
	 * @return the removed node or null if not found
	 */
	public SetNode<K, V> removeKey(K key) {
		SetNode<K, V> node = map.remove(key);
		// count how many values where removed
		size = node != null ? size - node.getValues().size() : size;
		return node;
	}

	/**
	 * removes the element from the node if it is contained. if the node is
	 * empty after the value was removed the key will be removed from the
	 * mapping
	 * 
	 * @param val
	 *            the value to search for
	 * @return the node from which it was removed or null if it is not contained
	 */
	public SetNode<K, V> remove(V val) {
		SetNode<K, V> node = map.get(keyGetter.call(val));
		if (node != null) {// check if the node exists
			if (node.getValues().remove(val)) {
				if (node.getValues().isEmpty())
					map.remove(node.getKey());
			} else// no value removed
				node = null;
		}
		size = node == null ? size : size - 1;
		return node;
	}

	/**
	 * check if the multimap contains this element
	 * 
	 * @param val
	 *            the value to search for
	 * @return true if the value is contained in the multimap
	 */
	public boolean contains(V val) {
		SetNode<K, V> node = map.get(keyGetter.call(val));
		return node!=null && node.getValues().contains(val);
	}

	/**
	 * check if the multimap contains values with the id
	 * 
	 * @param key
	 *            the id to search for
	 * @return true if the id is contained
	 */
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public SetNode<K, V> get(K key) {
		return map.get(key);
	}

	public Callback<V, K> getKeyGeter() {
		return keyGetter;
	}

	/**
	 * a count of all currently contained values
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * returns a set of all values contained in this map
	 * @return
	 */
	public List<V> values(){
		Collection<SetNode<K, V>> collection = map.values();
		ArrayList<V> values = new ArrayList<>();
		for (Iterator<SetNode<K, V>> iterator = collection.iterator(); iterator.hasNext();) {
			SetNode<K, V> node = (SetNode<K, V>) iterator.next();
			values.addAll(node.getValues());			
		}
		return values;
	}
	public Set<K> keySet(){
		return map.keySet();
	}
}
