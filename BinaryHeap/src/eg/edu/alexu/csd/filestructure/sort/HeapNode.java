package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class HeapNode<T extends Comparable<T>> implements INode<T> {

	private ArrayList<INode<T>> heap;
	public int index;
	private T value;
	
	public HeapNode(ArrayList<INode<T>> heap, int index) {
		this.heap = heap;
		this.index = index;
	}

	public INode<T> getLeftChild() {
		return (((2 * index) + 1) < heap.size()) ? heap.get((2 * index) + 1) : null;
	}

	public INode<T> getRightChild() {
		return (((2 * index) + 2) < heap.size()) ? heap.get((2 * index) + 2) : null;
	}

	public INode<T> getParent() {
		return ((index / 2) >= 0) ? heap.get(index / 2) : null;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	public int getIndex(){return index;}

}
