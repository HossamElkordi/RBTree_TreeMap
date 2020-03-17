package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {
	
	IRedBlackTree<T, V> rbTree = new RBTree<T, V>();

	public Entry<T, V> ceilingEntry(T key) {
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				INode<T, V> ceil = getLeast(node.getRightChild());
				return new Pair<T, V>(ceil.getKey(), ceil.getValue());
			}
		}
		return null;
	}

	public T ceilingKey(T key) {
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				INode<T, V> ceil = getLeast(node.getRightChild());
				return ceil.getKey();
			}
		}
		return null;
	}

	public void clear() {
		if(rbTree != null) rbTree.clear();
	}

	public boolean containsKey(T key) {
		if(rbTree != null) {
			return rbTree.contains(key);
		}
		return false;
	}

	public boolean containsValue(V value) {
		return false;
	}

	public Set<Entry<T, V>> entrySet() {
		if((rbTree != null) && (rbTree.getClass() != null)) {
			Set<Entry<T, V>> set = new HashSet<Entry<T, V>>();
			inOrderFilling(this.rbTree.getRoot(), set);
			return set;
		}
		return null;
	}

	public Entry<T, V> firstEntry() {
		if((rbTree != null) && (rbTree.getClass() != null)) {
			INode<T, V> least = getLeast(rbTree.getRoot());
			return new Pair<T, V>(least.getKey(), least.getValue());
		}
		return null;
	}

	public T firstKey() {
		if((rbTree != null) && (rbTree.getClass() != null)) {
			INode<T, V> least = getLeast(rbTree.getRoot());
			return least.getKey();
		}
		return null;
	}

	public Entry<T, V> floorEntry(T key) {
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				INode<T, V> floor = getLargest(node.getLeftChild());
				return new Pair<T, V>(floor.getKey(), floor.getValue());
			}
		}
		return null;
	}

	public T floorKey(T key) {
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				INode<T, V> floor = getLargest(node.getLeftChild());
				return floor.getKey();
			}
		}
		return null;
	}

	public V get(T key) {
		if(rbTree != null) {
			return rbTree.search(key);
		}
		return null;
	}

	public ArrayList<Entry<T, V>> headMap(T toKey) {
		return null;
	}

	public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
		return null;
	}

	public Set<T> keySet() {
		return null;
	}

	public Entry<T, V> lastEntry() {
		return null;
	}

	public T lastKey() {
		return null;
	}

	public Entry<T, V> pollFirstEntry() {
		return null;
	}

	public Entry<T, V> pollLastEntry() {
		return null;
	}

	public void put(T key, V value) {
		
	}

	public void putAll(Map<T, V> map) {
		
	}

	public boolean remove(T key) {
		return false;
	}

	public int size() {
		return 0;
	}

	public Collection<V> values() {
		return null;
	}
	
	private INode<T, V> findNode(T key) {
		INode<T, V> node = rbTree.getRoot();
		while(!node.isNull()) {
			if(key.equals(node.getKey())) {
				break;
			}
			if(key.compareTo(node.getKey()) < 0) {
				node = node.getLeftChild();
			}else {
				node = node.getRightChild();
			}
		}
		return node;
	}
	
	private void inOrderFilling(INode<T, V> root, Set<Entry<T, V>> fillSet) {
		if(root.isNull()) return;
		inOrderFilling(root.getLeftChild(), fillSet);
		fillSet.add(new Pair<T, V>(root.getKey(), root.getValue()));
		inOrderFilling(root.getRightChild(), fillSet);
	}
	
	private INode<T, V> getLeast(INode<T, V> root){
		if(root.getLeftChild().isNull()) return root;
		return getLeast(root.getLeftChild());
	}
	
	private INode<T, V> getLargest(INode<T, V> root) {
		if(root.getRightChild().isNull()) return root;
		return getLeast(root.getRightChild());
	}

}
