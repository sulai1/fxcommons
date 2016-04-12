package algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javafx.collections.ObservableListBase;
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
public class Multimap<K, V> implements List<V> {

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
	public boolean remove(Object val) {
		Collection<SetNode<K, V>> c = map.values();
		for (Iterator<SetNode<K, V>> iterator = c.iterator(); iterator.hasNext();) {
			SetNode<K, V> node = iterator.next();
			if (node.getValues().remove(val)) {
				if (node.getValues().isEmpty())
					map.remove(node.getKey());
				size--;
				return true;
			}
		}
		return false;
	}

	/**
	 * check if the multimap contains this element
	 * 
	 * @param val
	 *            the value to search for
	 * @return true if the value is contained in the multimap
	 */
	public boolean contains(Object val) {
		
		Collection<SetNode<K, V>> node = map.values();
		for (Iterator<SetNode<K, V>> iterator = node.iterator(); iterator.hasNext();) {
			SetNode<K, V> setNode = iterator.next();
			if(setNode.getValues().contains(val))
				return true;
			
		}
		return false;
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
	 * 
	 * @return
	 */
	public List<V> values() {
		Collection<SetNode<K, V>> collection = map.values();
		ArrayList<V> values = new ArrayList<>();
		for (Iterator<SetNode<K, V>> iterator = collection.iterator(); iterator.hasNext();) {
			SetNode<K, V> node = (SetNode<K, V>) iterator.next();
			values.addAll(node.getValues());
		}
		return values;
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public V get(int index) {
		Collection<SetNode<K, V>> values = map.values();
		int ind=0;
		SetNode<K, V>  node;
		for (Iterator<SetNode<K, V>> iterator = values.iterator(); iterator.hasNext();) {
			node = iterator.next();
			if(ind+node.getValues().size()>index)
				for (Iterator<V> iterator2 = node.getValues().iterator(); iterator2.hasNext();) {
					if(++ind == index)
						return iterator2.next();
					iterator2.next();
				}
			else
				ind+= node.getValues().size();
		}
		return null;
	}

	public SetNode<K, V> removeByVal(V val) {
		K key = keyGetter.call(val);
		SetNode<K, V> node = getByKey(key);
		if(node!=null){
			node.getValues().remove(val);
			if(node.getValues().isEmpty())
				map.remove(key);
			size--;
		}
		return node;
	}

	public SetNode<K, V> getByKey(K key) {
		return map.get(key);
	}

	@Override
	public boolean add(V arg0) {
		SetNode<K, V> put = put(arg0);
		return put!=null;
	}

	@Override
	public void add(int arg0, V arg1) {
		
	}

	@Override
	public boolean addAll(Collection<? extends V> arg0) {
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends V> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int indexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<V> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastIndexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<V> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<V> listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V set(int arg0, V arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<V> subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
