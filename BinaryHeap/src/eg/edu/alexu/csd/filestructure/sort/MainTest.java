package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class MainTest {

	public static void main(String[] args) {
//		IHeap<Integer> heap = new Heap<Integer>();
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(5);
		data.add(8);
		data.add(2);
		data.add(1);
		data.add(9);
		data.add(6);
		ISort<Integer> sorting = new SortingTechniques<Integer>();
		IHeap<Integer> heap = sorting.heapSort(data);
//		heap.insert(5);
//		heap.insert(8);
//		heap.insert(2);
//		heap.insert(1);
//		heap.insert(9);
//		heap.insert(6);
//		for(int i = 0; i < 6; i++)
//			System.out.println(heap.extract());
	}

}
