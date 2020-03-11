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
		try {
			return (((2 * index) + 1) < heap.size()) ? heap.get((2 * index) + 1) : null;
		}catch(Exception e) {
			return null;
		}
	}

	public INode<T> getRightChild() {
		try {
			return (((2 * index) + 2) < heap.size()) ? heap.get((2 * index) + 2) : null;
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
	public int getIndex(){return index;}

}
