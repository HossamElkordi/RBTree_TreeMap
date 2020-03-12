package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Heap<T extends Comparable<T>> implements IHeap<T> {
	public int lastindex=0;
	public ArrayList<INode<T>> heap;
	
	public Heap() {
		this.heap = new ArrayList<INode<T>>();
	}

	public INode<T> getRoot() {
		if(heap.isEmpty() || lastindex == 0) return null;
		else{return heap.get(0);}
	}

	public int size() {
		return lastindex;
	}

//	public void heapify(INode<T> node) {
//		INode<T> comparedTo=node.getLeftChild();
//		if(comparedTo==null||comparedTo.getIndex()>lastindex){return;}//this node is a leaf node
//		if(node.getRightChild()!=null&&node.getRightChild().getIndex()<lastindex){//puts in compared to the larger of the children
//			int cc=comparedTo.getValue().compareTo(node.getRightChild().getValue());
//			if(cc<0){
//				comparedTo=node.getRightChild();
//			}
//		}
//		int cc=node.getValue().compareTo( comparedTo.getValue());
//		if(cc<0){
//			T value=node.getValue();
//			T comparedToValue=comparedTo.getValue();
//			comparedTo.setValue(value);
//			node.setValue(comparedToValue);
//			heapify(comparedTo);
//		}
//	}
	
	public void heapify(INode<T> node) {
		if(node==null){return;}
		INode<T> biggest = node;
		INode<T> left    = node.getLeftChild();
		INode<T> right   = node.getRightChild();
		
		if((right != null)) {
			if(right.getValue().compareTo(biggest.getValue()) > 0) {
				biggest = right;
			}
		}
		if((left != null)) {
			if(left.getValue().compareTo(biggest.getValue()) > 0) {
				biggest = left;
			}
		}
		
		if(biggest != node) {
			swap(node, biggest);
			heapify(biggest);
		}
	}
	
	public T extract() {
		if(heap.isEmpty()||lastindex==0){return null;}
		T temp = this.getRoot().getValue();
		lastindex--;
		if(lastindex == 0) {
			return temp;
		}
		this.getRoot().setValue(heap.get(lastindex).getValue());
		heap.get(lastindex).setValue(temp);
		heapify(this.getRoot());
		return temp;
	}

	public void insert(T element) {
		if(element==null) return;
		HeapNode<T> node=new HeapNode<T>(this,heap);

		node.setValue(element);
		this.heap.add(lastindex,node);
		lastindex++;
		heapifyBottomUp(node);;
	}

	private void heapifyBottomUp(INode<T> lastNode){
		INode<T> parent = lastNode.getParent();
		if(parent != null) {
			if(lastNode.getValue().compareTo(parent.getValue()) > 0) {
				swap(lastNode, parent);				
				heapifyBottomUp(parent);
			}
		}
	}

	public void build(Collection<T> unordered) {if(unordered==null)return;
		Iterator<T> iter = unordered.iterator();
		while(iter.hasNext()) {
			INode<T> node = new HeapNode<T>(this, heap);
			node.setValue(iter.next());
			heap.add(lastindex++, node);
		}
		for(int i = ((heap.size() / 2) - 1); i >= 0; i--) {
			heapify(heap.get(i));
		}
	}
	
	private void swap(INode<T> first, INode<T> second) {
		T temp = first.getValue();
		first.setValue(second.getValue());
		second.setValue(temp);
	}

}
