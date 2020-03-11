package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Heap<T extends Comparable<T>> implements IHeap<T> {
	int lastindex=0;
	public Heap() {
		this.heap = new ArrayList<INode<T>>();
	}
	
	private ArrayList<INode<T>> heap;

	public INode<T> getRoot() {
		if(heap.isEmpty()||lastindex==0){return null;}
		else{return heap.get(0);}
	}

	public int size() {
		return lastindex;
	}

	public void heapify(INode<T> node) {
		INode<T> comparedTo=node.getLeftChild();
		if(comparedTo==null||comparedTo.getIndex()>lastindex){return;}//this node is a leaf node
		if(node.getRightChild()!=null&&node.getRightChild().getIndex()<lastindex){//puts in compared to the larger of the children
			int cc=comparedTo.getValue().compareTo(node.getRightChild().getValue());
			if(cc<0){
				comparedTo=node.getRightChild();
			}
		}
		int cc=node.getValue().compareTo( comparedTo.getValue());
		if(cc<0){
			T value=node.getValue();
			T comparedToValue=comparedTo.getValue();
			comparedTo.setValue(value);
			node.setValue(comparedToValue);
			heapify(comparedTo);
		}
	}

	public T extract() {
		if(heap.isEmpty()||lastindex==0){return null;}
		T lastValue=heap.get(lastindex-1).getValue();
		T Value=heap.get(0).getValue();
		heap.get(0).setValue(lastValue);
		heap.get(lastindex-1).setValue(Value);
		lastindex--;
		if(lastindex!=0)heapify(heap.get(0));
		return Value;
	}

	public void insert(T element) {
		HeapNode<T> node=new HeapNode<T>(heap,lastindex);

		node.setValue(element);
		this.heap.add(lastindex,node);
		lastindex++;
		heapifyBottomUp();
	}

	private void heapifyBottomUp(){
		int i= (int) Math.floor((lastindex-2)/2);
		while(i>=0){
			heapify(heap.get(i));
			if(i==0)break;
			i=(int)Math.floor((i-1)/2);
		}
	}
	public void build(Collection<T> unordered) {
		heap=new ArrayList<INode<T>>((Collection<? extends INode<T>>) unordered);
		lastindex=heap.size();
		heapifyBottomUp();

	}

}
