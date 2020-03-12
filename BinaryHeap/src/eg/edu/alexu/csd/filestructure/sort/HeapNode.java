package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class HeapNode<T extends Comparable<T>> implements INode<T> {

	private Heap<T> heapContainer;
	private ArrayList<INode<T>> heap;
	public int index;
	private T value;
	
	public HeapNode(Heap<T> heap, ArrayList<INode<T>> list) {
		this.heapContainer = heap;
		this.heap=list;
		index = heap.size();
	}

	public INode<T> getLeftChild() {
		try {
			return (((2 * index) + 1) < heapContainer.size()) ? heap.get((2 * index) + 1) : null;
		}catch(Exception e) {
			return null;
		}
	}

	public INode<T> getRightChild() {
		try {
			return (((2 * index) + 2) < heapContainer.size()) ? heap.get((2 * index) + 2) : null;
		}catch(Exception e) {
			return null;
		}
	}

	public INode<T> getParent() {
		try {
			if(index == 0) return null;
			return ((index / 2) >= 0) ? heap.get(index / 2) : null;
		}catch(Exception e) {
			return null;
		}
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
