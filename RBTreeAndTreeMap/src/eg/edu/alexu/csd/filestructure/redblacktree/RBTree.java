package eg.edu.alexu.csd.filestructure.redblacktree;

public class RBTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private INode<T, V> root;
	private INode<T, V> leaf = new RBNode<T, V>(true);

	public INode<T, V> getRoot() {
		return this.root;
	}

	public boolean isEmpty() {
		return (this.root == leaf);
	}

	public void clear() {

	}

	public V search(T key) {
		INode<T, V> searchRes = recurSearch(this.root, key);
		return (searchRes == null) ? null : searchRes.getValue();
	}

	public boolean contains(T key) {
		return (recurSearch(this.root, key) == null) ? false : true;
	}

	public void insert(T key, V value) {
		//creating the node
		INode<T, V> node=new RBNode<T, V>(false);
		node.setKey(key);
		node.setValue(value);
		node.setRightChild(leaf);
		node.setLeftChild(leaf);
		if(root==null){node.setColor(INode.BLACK);
			root=node;
			return;
		}
		//looking for its place
		INode<T, V> temp=root;
		while(true) {
			int comp=temp.getKey().compareTo(key);
			if (comp == 0) {
				temp.setValue(value);
				break;
			} else if(comp<0) {
				if(temp.getLeftChild().isNull()){break;}
				else{temp=temp.getLeftChild();}
			}
			else{
				if(temp.getRightChild().isNull()){break;}
				else{temp=temp.getRightChild();}
			}
		}
		//putting it into its place
		if(temp.getKey().compareTo(key)<0){temp.setLeftChild(node);}
		else{temp.setRightChild(node);
			node.setParent(temp);
		}
		if(temp.getColor()==INode.BLACK){return;}
		fixUpInsert(node);
	}

	private void fixUpInsert(INode<T,V>node){
		if(node.getColor()==INode.BLACK){return;}
		if(node.getParent().getColor()== INode.BLACK){return;}
		INode<T, V> uncle=getUncle(node);
		INode<T, V> father=node.getParent();
		INode<T, V> grandfather=father.getParent();
		if(uncle.getColor() == INode.RED){
			if(grandfather == root) {
				return;
			}
			if(grandfather!=root){
				grandfather.setColor(INode.RED);
			}
			father.setColor(INode.BLACK);
			uncle.setColor(INode.BLACK);
			fixUpInsert(grandfather);
			return;
		}
		else{
			if(father.getRightChild()==node){leftRotate(father);
				fixUpInsert(node);
				return;
			}
			else{
				grandfather.setColor(INode.RED);
				father.setColor(INode.BLACK);
				rightRotate(grandfather);
			}
		}
	}

	public boolean delete(T key) {
		return false;
	}

	private INode<T, V> recurSearch(INode<T, V> root, T key) {
		if (root.isNull() || (root.getKey() == key)) {
			return root;
		}

		if (root.getKey().compareTo(key) < 0) {
			recurSearch(root.getLeftChild(), key);
		} else {
			recurSearch(root.getRightChild(), key);
		}
		return null;
	}

	private void leftRotate(INode<T, V> node) {
		INode<T, V> right = node.getRightChild();
		node.setRightChild(right.getLeftChild());
		if (right.getLeftChild() != null) {
			right.getLeftChild().setParent(node);
		}
		if (node.getParent() != null) {
			right.setParent(node.getParent());
			if (node.getParent().getLeftChild() == node) {
				node.getParent().setLeftChild(right);
			} else {
				node.getParent().setRightChild(right);
			}
		} else {
			right.setParent(null);
			this.root = right;
		}
		right.setLeftChild(node);
		node.setParent(right);
	}
	private void rightRotate(INode<T,V> node){
		INode<T, V> left=node.getLeftChild();
		INode<T, V> parent=node.getParent();
		if(left.isNull())return;
		if(parent!=null) {
			if (parent.getRightChild() == node) {
				parent.setRightChild(left);
			} else {
				parent.setLeftChild(left);
			}
		}
		left.setParent(node.getParent());
		node.setParent(left);
		node.setLeftChild(left.getRightChild());
		left.setRightChild(node);

	}

	private INode<T, V> getUncle(INode<T, V> node){
		if((node != null) && (node.getParent() == null) && (node.getParent().getParent() != null))return null;
		INode<T, V> parent=node.getParent();
		if(parent.getParent().getRightChild()==parent)return parent.getParent().getLeftChild();
		else return parent.getParent().getRightChild();
	}

	private INode<T, V> getSibling(INode<T, V> node){
		if(node != null) {
			if(node.getParent() != null) {
				if (node.getParent().getLeftChild() == node) {
					return node.getParent().getRightChild();
				} else {
					return node.getParent().getLeftChild();
				}
			}
		}
		return null;
	}

}
