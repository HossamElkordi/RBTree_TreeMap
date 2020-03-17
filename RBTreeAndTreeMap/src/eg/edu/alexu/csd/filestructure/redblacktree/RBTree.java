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
		if(root != null) {
			INode<T, V> searchRes = recurSearch(this.root, key);
			return (searchRes == null) ? null : searchRes.getValue();
		}
		return null;
	}

	public boolean contains(T key) {
		if(root != null) {
			return (recurSearch(this.root, key) == null) ? false : true;
		}
		return false;
	}

	public void insert(T key, V value) {
		//creating the node
		INode<T, V> node=new RBNode<T, V>(false);
		node.setKey(key);
		node.setValue(value);
		node.setRightChild(leaf);
		node.setLeftChild(leaf);
		node.setColor(INode.RED);
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
			} else if(comp<0) {//temp<key
				if(temp.getRightChild().isNull()){break;}
				else{temp=temp.getRightChild();}
			}
			else{//temp>key
				if(temp.getLeftChild().isNull()){break;}
				else{temp=temp.getLeftChild();}
			}
		}
		//putting it into its place
		if(temp.getKey().compareTo(key)<0){temp.setRightChild(node);node.setParent(temp);}
		else{temp.setLeftChild(node);
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
		if(uncle.getColor()==INode.RED){
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
				fixUpInsert(father);
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
		INode<T, V> deleted = recurDelete(root, key);
		if(deleted == null) {
			return false;
		}
		return true;
	}
	
	private INode<T, V> recurDelete(INode<T, V> node, T key) {
		if((node == null) || (node.isNull())) {
			return null;
		}
		if(key.compareTo(node.getKey()) < 0) {
			node.setLeftChild(recurDelete(node.getLeftChild(), key));
		}else if(key.compareTo(node.getKey()) > 0) {
			node.setRightChild(recurDelete(node.getRightChild(), key));
		}else {
			if(node.getLeftChild().isNull() && node.getRightChild().isNull()) {
				fixUpDelete(node, leaf);
				node = leaf;
			}else if(node.getLeftChild().isNull()) {
				INode<T, V> right = node.getRightChild();
				copyNode(right, node);
				fixUpDelete(node,right);
				deleteNode(right);
				return node;
			}else if(node.getRightChild().isNull()) {
				INode<T, V> left = node.getRightChild();
				copyNode(left, node);
				fixUpDelete(node,left);
				deleteNode(left);
				return node;
			}
			
			INode<T, V> successor = getSuccessor(node.getRightChild());
			node.setKey(successor.getKey());
			node.setValue(successor.getValue());
			recurDelete(node.getRightChild(), successor.getKey());
		}
		return node;
	}
	
	private void fixUpDelete(INode<T, V> deleted, INode<T, V> child) {
		if((deleted.getColor() == INode.RED) || (child.getColor() == INode.RED)) {
			return;
		}
		INode<T, V> sibling = null;
		while((deleted != root) && (deleted.getColor() == INode.BLACK)) {
			sibling = getSibling(deleted);
			if(sibling.getColor() == INode.RED) {
				sibling.setColor(INode.BLACK);
				deleted.setColor(INode.RED);
				if(deleted.getParent().getLeftChild() == deleted) {
					leftRotate(deleted.getParent());
				}else {
					rightRotate(deleted.getParent());
				}
				sibling = getSibling(deleted);
			}
			
			if((sibling.getLeftChild().getColor() == INode.BLACK) && (sibling.getRightChild().getColor() == INode.BLACK)) {
				sibling.setColor(INode.RED);
				deleted = deleted.getParent();
			}else {
				if(sibling.getParent().getLeftChild() == sibling) {
					if(sibling.getLeftChild().getColor() == INode.BLACK) {
						sibling.setColor(INode.RED);
						sibling.getRightChild().setColor(INode.BLACK);
						leftRotate(sibling);
						sibling = getSibling(deleted);
					}
					sibling.getLeftChild().setColor(INode.BLACK);
					rightRotate(deleted.getParent());
					break;
				}else {
					if(sibling.getRightChild().getColor() == INode.BLACK) {
						sibling.setColor(INode.RED);
						sibling.getLeftChild().setColor(INode.BLACK);
						rightRotate(sibling);
						sibling = getSibling(deleted);
					}
					sibling.getRightChild().setColor(INode.BLACK);
					leftRotate(deleted.getParent());
					break;
				}
			}
		}
		root.setColor(INode.BLACK);
	}
	
	private INode<T, V> getSuccessor(INode<T, V> node){
		return (!node.getLeftChild().isNull()) ? getSuccessor(node.getLeftChild()) : node;
	}
	
	private void copyNode(INode<T, V> from, INode<T, V> to) {
		to.setKey(from.getKey());
		to.setValue(from.getValue());
		to.setLeftChild(from.getLeftChild());
		to.setRightChild(from.getRightChild());
	}
	
	private void deleteNode(INode<T, V> node) {
		node.setValue(null);
		node.setKey(null);
		node.setLeftChild(null);
		node.setRightChild(null);
		node.setParent(null);
		node = null;
	}

	private INode<T, V> recurSearch(INode<T, V> root, T key) {
		if (root.isNull() || (root.getKey() == key)) {
			return root;
		}

		if (key.compareTo(root.getKey()) < 0) {
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
		if(node==root){
			INode<T, V> left=node.getLeftChild();
			root=left;
			INode<T, V> rightright=left.getRightChild();
			root.setRightChild(node);
			node.setLeftChild(rightright);
			node.setParent(root);
			root.setParent(null);
			return;
		}
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
