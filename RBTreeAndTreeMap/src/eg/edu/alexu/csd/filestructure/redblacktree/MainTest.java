package eg.edu.alexu.csd.filestructure.redblacktree;

public class MainTest {

	public static void main(String[] args) {
		IRedBlackTree<Integer, Integer> rbTree = new RBTree<Integer, Integer>();
		rbTree.insert(5, 5);
		rbTree.insert(4, 4);
		rbTree.insert(3, 3);
		rbTree.insert(2, 2);
		rbTree.insert(1, 1);
		rbTree.insert(6,6);
		rbTree.insert(15,15);
		inOrderTraversal(rbTree.getRoot());
	}

	private static void inOrderTraversal(INode<Integer, Integer> root) {
		if(root.isNull()) return;
		inOrderTraversal(root.getLeftChild());
		System.out.println("(" + root.getKey() + ", " + root.getValue() + "): " + ((root.getColor() == INode.RED) ? "RED" : "BLACK"));
		inOrderTraversal(root.getRightChild());
	}
}
