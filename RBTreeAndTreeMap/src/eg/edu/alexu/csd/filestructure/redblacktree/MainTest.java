package eg.edu.alexu.csd.filestructure.redblacktree;

import org.junit.Assert;

import eg.edu.alexu.csd.filestructure.redblacktree.tester.TestRunner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class MainTest {
public static final int COUNT=10;
	public static void main(String[] args) {
		IRedBlackTree<Integer, String> redBlackTree = new RBTree<Integer, String>();
	      int j = 0;
	      try {
	         Random r = new Random();
	         HashSet<Integer> list = new HashSet<Integer>();

	         for(int i = 0; i < 10; ++i) {
	            int key = r.nextInt(100);
	            list.add(key);
	            redBlackTree.insert(key, "soso" + key);
	         }

	         Iterator<Integer> var9 = list.iterator();
	         
	         while(var9.hasNext()) {
	        	 j++;
	            Integer elem = (Integer)var9.next();
	            Assert.assertTrue(redBlackTree.delete(elem));
	         }

	         INode<Integer, String> node = redBlackTree.getRoot();
	         if (node != null && !node.isNull()) {
	            Assert.fail();
	         }
	      } catch (Throwable var6) {
	    	  System.out.println(j);
	         TestRunner.fail("Fail to handle deletion", var6);
	      }

		/*for(i = 0; i < 100; ++i) {
			key = r.nextInt(10000);
			if (!list.contains(key)) {
				Assert.assertFalse(redBlackTree.delete(key));
			}
		}
		IRedBlackTree<Integer, Integer> rbTree = new RBTree<Integer, Integer>();
		rbTree.insert(3, 3);
		rbTree.insert(6, 6);
		rbTree.insert(15, 15);
		rbTree.insert(7, 7);
		rbTree.insert(10, 10);
		rbTree.insert(12, 12);
		rbTree.insert(13, 13);
		rbTree.insert(16, 16);
		rbTree.insert(17, 17);
		rbTree.insert(20, 20);
		rbTree.insert(18, 18);
		rbTree.insert(23, 23);
		inOrderTraversal(rbTree.getRoot());*/
	}

	private static void inOrderTraversal(INode<Integer, Integer> root) {
		if(root.isNull()) return;
		inOrderTraversal(root.getLeftChild());
		System.out.println("(" + root.getKey() + ", " + root.getValue() + "): " + ((root.getColor() == INode.RED) ? "RED" : "BLACK"));
		inOrderTraversal(root.getRightChild());
	}
	static void print2DUtil(INode root, int space)
	{
		// Base case
		if (root == null)
			return;

		// Increase distance between levels
		space += COUNT;

		// Process right child first
		print2DUtil(root.getRightChild(), space);

		// Print current node after space
		// count
		System.out.print("\n");
		for (int i = COUNT; i < space; i++)
			System.out.print(" ");
		String color;
		if(root.getColor())color=" R ";
		else color=" B ";
		System.out.print(root.getValue() +color+ "\n");

		// Process left child
		print2DUtil(root.getLeftChild(), space);
	}

	// Wrapper over print2DUtil()
	static void print2D(INode root)
	{
		// Pass initial space count as 0
		print2DUtil(root, 0);
	}
}
