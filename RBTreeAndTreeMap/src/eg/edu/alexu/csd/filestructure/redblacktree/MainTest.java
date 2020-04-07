package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.junit.Assert;

import eg.edu.alexu.csd.filestructure.redblacktree.tester.TestRunner;

public class MainTest {
	
	private static final int COUNT = 0;

	public static void main(String[] args) {

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
