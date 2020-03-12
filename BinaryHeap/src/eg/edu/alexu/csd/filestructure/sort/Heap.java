package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Heap<T extends Comparable<T>> implements IHeap<T> {
	private int lastindex=0;
	private ArrayList<INode<T>> heap;
	
	public Heap() {
		this.heap = new ArrayList<INode<T>>();
	}

	public INode<T> getRoot() {
		if(heap.isEmpty()) return null;
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
		INode<T> biggest = node;
		INode<T> left    = node.getLeftChild();
		INode<T> right   = node.getRightChild();
		
		if((left != null)) {
			if(left.getValue().compareTo(biggest.getValue()) > 0) {
				biggest = left;
			}
		}
		if((right != null)) {
			if(right.getValue().compareTo(biggest.getValue()) > 0) {
				biggest = right;
			}
		}
		if(biggest != node) {
			T temp = node.getValue();
			node.setValue(biggest.getValue());
			biggest.setValue(temp);
			heapify(biggest);
		}
	}
	
	public T extract() {
		if(heap.isEmpty()||lastindex==0){return null;}
//		if(lastindex == 1) {
//			lastindex--;
//			return heap.get(0).getValue();
//		}
		T temp = this.getRoot().getValue();
		this.getRoot().setValue(heap.get(lastindex-1).getValue());
		heap.get(lastindex-1).setValue(temp);
		lastindex--;
		heapify(this.getRoot());
		return temp;
	}

//	public T extract() {
//		if(heap.isEmpty()||lastindex==0){return null;}
//		T lastValue=heap.get(lastindex-1).getValue();
//		T Value=heap.get(0).getValue();
//		heap.get(0).setValue(lastValue);
//		heap.get(lastindex-1).setValue(Value);
//		lastindex--;
//		if(lastindex!=0)heapify(heap.get(0));
//		return Value;
//	}

	public void insert(T element) {
		if(element.equals(null)) return;
		HeapNode<T> node=new HeapNode<T>(this,heap);

		node.setValue(element);
		this.heap.add(lastindex,node);
		lastindex++;
		heapifyBottomUp();
//		for(int i = 0; i < lastindex; i++) {
//			System.out.print(heap.get(i).getValue() + ", ");
//		}
//		System.out.println();
	}

	private void heapifyBottomUp(){
		INode<T> parent = heap.get(lastindex-1).getParent();
		while(parent != null) {
			heapify(parent);
			parent = parent.getParent();
		}
//		int i= (int) Math.floor((lastindex-2)/2);
//		while(i>=0){
//			heapify(heap.get(i));
//			if(i==0)break;
//			i=(int)Math.floor((i-1)/2);
//		}
	}

	public void build(Collection<T> unordered) {
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

}
