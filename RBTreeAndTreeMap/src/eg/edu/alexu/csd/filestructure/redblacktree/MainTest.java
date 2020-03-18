package eg.edu.alexu.csd.filestructure.redblacktree;

public class MainTest {

	public static void main(String[] args) {
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
		inOrderTraversal(rbTree.getRoot());
	}

	private static void inOrderTraversal(INode<Integer, Integer> root) {
		if(root.isNull()) return;
		inOrderTraversal(root.getLeftChild());
		System.out.println("(" + root.getKey() + ", " + root.getValue() + "): " + ((root.getColor() == INode.RED) ? "RED" : "BLACK"));
		inOrderTraversal(root.getRightChild());
	}
}
