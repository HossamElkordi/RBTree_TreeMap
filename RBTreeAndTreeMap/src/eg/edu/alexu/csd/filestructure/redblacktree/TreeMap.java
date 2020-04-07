package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {
	
	private IRedBlackTree<T, V> rbTree = new RBTree<T, V>();
	private int size = 0;

	public Map.Entry<T, V> ceilingEntry(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				return new Entry<T, V>(node.getKey(), node.getValue());
			}else {
				if(node == node.getParent().getLeftChild()) {
					return new Entry<T, V>(node.getParent().getKey(), node.getParent().getValue());
				}
				INode<T, V> successor = getSuccessor(node);
				return new Entry<T, V>(successor.getKey(), successor.getValue());
			}
		}
		return null;
	}

	public T ceilingKey(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				return node.getKey();
			}else {
				if(node == node.getParent().getLeftChild()) {
					return node.getParent().getKey();
				}
				INode<T, V> successor = getSuccessor(node);
				return successor.getKey();
			}
		}
		return null;
	}

	public void clear() {
		if(rbTree != null) {
			rbTree.clear();
			size = 0;
		}
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

	public Set<Map.Entry<T, V>> entrySet() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			Set<Map.Entry<T, V>> set = new LinkedHashSet<Map.Entry<T, V>>();
			inOrderFilling(rbTree.getRoot(), set);
			return set;
		}
		return null;
	}

	public Map.Entry<T, V> firstEntry() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> least = getLeast(rbTree.getRoot());
			return new Entry<T, V>(least.getKey(), least.getValue());
		}
		return null;
	}

	public T firstKey() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> least = getLeast(rbTree.getRoot());
			return least.getKey();
		}
		return null;
	}

	public Map.Entry<T, V> floorEntry(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				return new Entry<T, V>(node.getKey(), node.getValue());
			}else {
				if(node == node.getParent().getRightChild()) {
					return new Entry<T, V>(node.getParent().getKey(), node.getParent().getValue());
				}
				INode<T, V> predeccessor = getPredeccessor(node);
				return new Entry<T, V>(predeccessor.getKey(), predeccessor.getValue());
			}
		}
		return null;
	}

	public T floorKey(T key) {
		if(key == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			INode<T, V> node = findNode(key);
			if(!node.isNull()) {
				return node.getKey();
			}else {
				if(node == node.getParent().getRightChild()) {
					return node.getParent().getKey();
				}
				INode<T, V> predeccessor = getPredeccessor(node);
				return predeccessor.getKey();
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

	public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
		if(toKey == null) throw new RuntimeErrorException(null);
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			ArrayList<Map.Entry<T, V>> fill = new ArrayList<Map.Entry<T, V>>();
			Iterator<Map.Entry<T, V>> entrySetIter = entrySet().iterator();
			while (entrySetIter.hasNext()) {
				Map.Entry<T, V> entry = entrySetIter.next();
				if(entry.getKey().equals(toKey)) break;
				fill.add(entry);
			}
			return fill;
		}
		return null;
	}

	public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
		if(toKey == null) throw new RuntimeErrorException(null);
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			ArrayList<Map.Entry<T, V>> fill = new ArrayList<Map.Entry<T, V>>();
			Iterator<Map.Entry<T, V>> entrySetIter = entrySet().iterator();
			while (entrySetIter.hasNext()) {
				Map.Entry<T, V> entry = entrySetIter.next();
				if(entry.getKey().equals(toKey)) {
					if(inclusive) fill.add(entry);
					break;
				}
				fill.add(entry);
			}
			return fill;
		}
		return null;
	}

	public Set<T> keySet() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			Set<T> set = new LinkedHashSet<T>();
			inOrderKeys(this.rbTree.getRoot(), set);
			return set;
		}
		return null;
	}

	public Entry<T, V> lastEntry() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> greatest = getLargest(rbTree.getRoot());
			return new Entry<T, V>(greatest.getKey(), greatest.getValue());
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

	public Map.Entry<T, V> pollFirstEntry() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> least = getLeast(rbTree.getRoot());
			remove(least.getKey());
			return new Entry<T, V>(least.getKey(), least.getValue());
		}
		return null;
	}

	public Map.Entry<T, V> pollLastEntry() {
		if((rbTree != null) && (rbTree.getRoot() != null)) {
			INode<T, V> greatest = getLargest(rbTree.getRoot());
			remove(greatest.getKey());
			return new Entry<T, V>(greatest.getKey(), greatest.getValue());
		}
		return null;
	}

	public void put(T key, V value) {
		if((key == null) || value == null) throw new RuntimeErrorException(null);
		if(rbTree != null) {
			if(!rbTree.contains(key)) {
				size++;
			}
			rbTree.insert(key, value);
		}
	}

	public void putAll(Map<T, V> map) {
		if(map == null) throw new RuntimeErrorException(null);
		if(rbTree == null) {
			rbTree = new RBTree<T, V>();
		}
		Set<Map.Entry<T, V>> mappings = map.entrySet();
		Iterator<Map.Entry<T, V>> iter = mappings.iterator();
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
			return fill;
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
	
	private void inOrderFilling(INode<T, V> root, Set<Map.Entry<T, V>> fillSet) {
		if(root.isNull()) return;
		inOrderFilling(root.getLeftChild(), fillSet);
		fillSet.add(new Entry<T, V>(root.getKey(), root.getValue()));
		inOrderFilling(root.getRightChild(), fillSet);
	}
	
	private INode<T, V> getSuccessor(INode<T, V> node) {
		if(!node.getRightChild().isNull()) return getLeast(node.getRightChild());
		INode<T, V> parent = node.getParent();
		while((parent != null) && node.equals(parent.getRightChild())) {
			node = parent;
			parent = parent.getParent();
		}
		return parent;
	}
	
	private INode<T, V> getLeast(INode<T, V> root){
		if(root.getLeftChild().isNull()) return root;
		return getLeast(root.getLeftChild());
	}
	
	private INode<T, V> getPredeccessor(INode<T, V> node) {
		if(!node.getLeftChild().isNull()) return getLargest(node.getLeftChild());
		INode<T, V> parent = node.getParent();
		while ((parent != null) && node.equals(parent.getLeftChild())) {
			node = parent;
			parent = parent.getParent();
		}
		return parent;
	}
	
	private INode<T, V> getLargest(INode<T, V> root) {
		if(root.getRightChild().isNull()) return root;
		return getLargest(root.getRightChild());
	}
	
	private static class Entry<K, V> implements Map.Entry<K, V>{

		private K key = null;
		private V value = null;
			
		public Entry(K key, V value) {
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
		
		@SuppressWarnings("unchecked")
		public boolean equals(Object obj) {
			if(!(obj instanceof Map.Entry)) return false;
			return (key.equals(((Map.Entry<K,V>) obj).getKey()) && value.equals(((Map.Entry<K,V>) obj).getValue()));
		}
		
	}
	
}
