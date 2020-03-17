package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.Map.Entry;

public class Pair<K, V> implements Entry<K, V> {

	private K key = null;
	private V value = null;
		
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return this.key;
	}

	public V getValue() {
		return this.value;
	}

	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

}
