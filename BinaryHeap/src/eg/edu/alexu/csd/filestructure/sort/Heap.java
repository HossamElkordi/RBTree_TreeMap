package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;

public class Heap<T extends Comparable<T>> implements IHeap<T> {
	
	private ArrayList<INode<T>> heap;

	public INode<T> getRoot() {
		return null;
	}

	public int size() {
		return heap.size();
	}

	public void heapify(INode<T> node) {
		
	}

	public T extract() {
		return null;
	}

	public void insert(T element) {
		
	}

	public void build(Collection<T> unordered) {
		
	}

}
