package eg.edu.alexu.csd.filestructure.redblacktree;

public class RBNode<T extends Comparable<T>, V> implements INode<T, V> {
	
	private INode<T, V> parent, leftChild, rightChild;
	private T key;
	private V value;
	private boolean color;
	private boolean nil;
	
	public RBNode(boolean nil) {
		this.nil = nil;
		if(nil) {
			this.color = INode.BLACK;
		}else {
			this.color = INode.RED;
		}
	}

	public void setParent(INode<T, V> parent) {
		this.parent = parent;
	}

	public INode<T, V> getParent() {
		return this.parent;
	}

	public void setLeftChild(INode<T, V> leftChild) {
		this.leftChild = leftChild;
	}

	public INode<T, V> getLeftChild() {
		return this.leftChild;
	}

	public void setRightChild(INode<T, V> rightChild) {
		this.rightChild = rightChild;
	}

	public INode<T, V> getRightChild() {
		return this.rightChild;
	}

	public T getKey() {
		return this.key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public V getValue() {
		return this.value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public boolean getColor() {
		return this.color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public boolean isNull() {
		return this.nil;
	}

}
