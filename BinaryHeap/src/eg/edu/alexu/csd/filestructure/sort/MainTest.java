package eg.edu.alexu.csd.filestructure.sort;

import java.util.*;

public class MainTest {

	public static void main(String[] args) {
		IHeap heap = new Heap();
		Integer max = -2147483648;
		Integer secondMax = -2147483648;

		for(int i = 0; i < 10000; ++i) {
			Random r = new Random();
			int val = r.nextInt(2147483647);
			heap.insert(val);
			if (val > max) {
				secondMax = max;
				max = val;
			} else if (val >= secondMax) {
				secondMax = val;
			}
		}
		int ext1=(Integer) heap.extract();
		int ext2=(Integer) heap.extract();
		int i=0;
	}

}
