package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {
	
	private IRedBlackTree<T, V> rbTree = new RBTree<T, V>();
	private int size = 0;

	public Entry<T, V> ceilingEntry(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				if(!node.getRightChild().isNull()) {
					INode<T, V> ceil = getLeast(node.getRightChild());
					return new Pair<T, V>(ceil.getKey(), ceil.getValue());
				}
				return new Pair<T, V>(node.getKey(), node.getValue());
			}
		}
		return null;
	}

	public T ceilingKey(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				if(!node.getRightChild().isNull()) {
					INode<T, V> ceil = getLeast(node.getRightChild());
					return ceil.getKey();
				}
				return node.getKey();
			}
		}
		return null;
	}

	public void clear() {
		if(rbTree != null) rbTree.clear();
	}

	public boolean containsKey(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			return rbTree.contains(key);
		}
		return false;
	}

	public boolean containsValue(V value) {
		if(value == null) throw new RuntimeErrorException(null);
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			return searchValue(rbTree.getRoot(), value);
		}
		return false;
	}

	public Set<Entry<T, V>> entrySet() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
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
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				if(!node.getLeftChild().isNull()) {
					INode<T, V> floor = getLargest(node.getLeftChild());
					return new Pair<T, V>(floor.getKey(), floor.getValue());
				}
				return new Pair<T, V>(node.getKey(), node.getValue());
			}
		}
		return null;
	}

	public T floorKey(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				if(!node.getLeftChild().isNull()) {
					INode<T, V> floor = getLargest(node.getLeftChild());
					return floor.getKey();
				}
				return node.getKey();
			}
		}
		return null;
	}

	public V get(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			return rbTree.search(key);
		}
		return null;
	}

	public ArrayList<Entry<T, V>> headMap(T toKey) {
		if(toKey == null) throw new RuntimeErrorException(null);
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			ArrayList<Entry<T, V>> fill = new ArrayList<Entry<T, V>>();
			INode<T, V> node = findNode(toKey);
			if(!node.isNull()) {
				getLessThan(node.getLeftChild(), fill);
			}
			return fill;
		}
		return null;
	}

	public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
		if(toKey == null) throw new RuntimeErrorException(null);
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			ArrayList<Entry<T, V>> fill = new ArrayList<Entry<T, V>>();
			INode<T, V> node = findNode(toKey);
			if(!node.isNull()) {
				getLessThan(node.getLeftChild(), fill);
				fill.add(new Pair<T, V>(node.getKey(), node.getValue()));
			}
			return fill;
		}
		return null;
	}

	public Set<T> keySet() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			Set<T> set = new HashSet<T>();
			inOrderKeys(this.rbTree.getRoot(), set);
			return set;
		}
		return null;
	}

	public Entry<T, V> lastEntry() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> greatest = getLargest(rbTree.getRoot());
			return new Pair<T, V>(greatest.getKey(), greatest.getValue());
		}
		return null;
	}

	public T lastKey() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> greatest = getLargest(rbTree.getRoot());
			return greatest.getKey();
		}
		return null;
	}

	public Entry<T, V> pollFirstEntry() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> least = getLeast(rbTree.getRoot());
			rbTree.delete(least.getKey());
			return new Pair<T, V>(least.getKey(), least.getValue());
		}
		return null;
	}

	public Entry<T, V> pollLastEntry() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> greatest = getLargest(rbTree.getRoot());
			rbTree.delete(greatest.getKey());
			return new Pair<T, V>(greatest.getKey(), greatest.getValue());
		}
		return null;
	}

	public void put(T key, V value) {
		if((key == null) || value == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			rbTree.insert(key, value);
			size++;
		}
	}

	public void putAll(Map<T, V> map) {
		if(map == null) throw new RuntimeErrorException(null);
		if(rbTree == null) {
			rbTree = new RBTree<T, V>();
		}
		Set<Entry<T, V>> mappings = map.entrySet();
		Iterator<Entry<T, V>> iter = mappings.iterator();
		while(iter.hasNext()) {
			Map.Entry<T, V> next = iter.next();
			put(next.getKey(), next.getValue());
		}
	}

	public boolean remove(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree.delete(key)) {
			size--;
			return true;
		}else {
			return false;
		} 
	}

	public int size() {
		return size;
	}

	public Collection<V> values() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			ArrayList<V> fill = new ArrayList<V>();
			inOrderFillValues(rbTree.getRoot(), fill);
		}
		return null;
	}
	
	private void inOrderFillValues(INode<T, V> root, Collection<V> fill) {
		if(root.isNull()) return;
		inOrderFillValues(root.getLeftChild(), fill);
		fill.add(root.getValue());
		inOrderFillValues(root.getRightChild(), fill);
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
	
	private boolean searchValue(INode<T, V> root, V value) {
		if(root.isNull()) return false;
		if(root.getValue().equals(value)) return true;
		if (searchValue(root.getLeftChild(), value)) {
			return true;
		}else {
			return searchValue(root.getRightChild(), value);
		}
	}
	
	private void inOrderKeys(INode<T, V> root, Set<T> fillSet) {
		if(root.isNull()) return;
		inOrderKeys(root.getLeftChild(), fillSet);
		fillSet.add(root.getKey());
		inOrderKeys(root.getRightChild(), fillSet);
	}
	
	private void inOrderFilling(INode<T, V> root, Set<Entry<T, V>> fillSet) {
		if(root.isNull()) return;
		inOrderFilling(root.getLeftChild(), fillSet);
		fillSet.add(new Pair<T, V>(root.getKey(), root.getValue()));
		inOrderFilling(root.getRightChild(), fillSet);
	}
	
	private void getLessThan(INode<T, V> node, ArrayList<Entry<T, V>> fill) {
		if(node.isNull()) return;
		getLessThan(node.getLeftChild(), fill);
		fill.add(new Pair<T, V>(node.getKey(), node.getValue()));
		getLessThan(node.getRightChild(), fill);
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
