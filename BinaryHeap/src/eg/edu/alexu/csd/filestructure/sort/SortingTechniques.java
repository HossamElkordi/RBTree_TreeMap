package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class SortingTechniques<T extends Comparable<T>> implements ISort<T> {
	
	private ArrayList<T> temp = new ArrayList<T>();

	public IHeap<T> heapSort(ArrayList<T> unordered) {if(unordered==null){return new Heap<T>();}
		IHeap<T> sorted = new Heap<T>();
		sorted.build(unordered);
		for(int i = 0; i < unordered.size(); i++) {
			sorted.extract();
		}
		((Heap<T>)(sorted)).lastindex=((Heap<T>)(sorted)).heap.size();
		return sorted;
	}

	public void sortSlow(ArrayList<T> unordered) {if(unordered==null){return ;}
		boolean swapped = true;
		for (int i = 0; (i < unordered.size()) && swapped; i++) {
			swapped = false;
			for (int j = 0; j < unordered.size()-1-i; j++) {
				if(unordered.get(j).compareTo(unordered.get(j+1)) > 0) {
					swap(unordered, j);
					swapped = true;
				}
			}
		}
	}

	public void sortFast(ArrayList<T> unordered) {if(unordered==null){return ;}
		mergeSort(unordered, 0, unordered.size()-1);
	}
	
	private void mergeSort(ArrayList<T> list, int first, int last) {
		if(first < last) {
			int mid = (first + last) / 2;
			mergeSort(list, first, mid);
			mergeSort(list, mid+1, last);
			
			merge(list, first, mid, last);
		}
	}
	
	private void merge(ArrayList<T> list, int first, int mid, int last) {
		int first1 = first;
		int first2 = mid + 1;
		while ((first1 <= mid) && (first2 <= last)) {
			if (list.get(first1).compareTo(list.get(first2)) < 0) {
				temp.add(list.get(first1++));
			} else {
				temp.add(list.get(first2++));
			}
		}
		while (first1 <= mid) {
			temp.add(list.get(first1++));
		}
		while (first2 <= last) {
			temp.add(list.get(first2++));
		}
		for(int i = first; i <= last; i++) {
			list.set(i, temp.get(i - first));
		}
		temp.clear();
	}
	
	private void swap(ArrayList<T> list, int j) {
		T temp = list.get(j);
		list.set(j, list.get(j+1));
		list.set(j+1, temp);
	}

}
