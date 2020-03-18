package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RBTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private INode<T, V> root;
	private INode<T, V> leaf = new RBNode<T, V>(true);

	public INode<T, V> getRoot() {
		return this.root;
	}

	public boolean isEmpty() {
		return ((root == null) || (this.root == leaf));
	}

	public void clear() {
		if(root != null) {
			recurClr(root);
		}
	}

	public V search(T key) {
		if(key == null) {
			throw new RuntimeErrorException(null);
		}
		if(!isEmpty()) {
			INode<T, V> searchRes = recurSearch(this.root, key);
			return (searchRes == null) ? null : searchRes.getValue();
		}
		return null;
	}

	public boolean contains(T key) {
		if(key == null) {
			throw new RuntimeErrorException(null);
		}
		if(!isEmpty()) {
			return (recurSearch(this.root, key) == null) ? false : true;
		}
		return false;
	}

	public void insert(T key, V value) {
		if((key == null) || (value == null)) {
			throw new RuntimeErrorException(null);
		}
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
				return;
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
	private void swapColor(INode<T, V> one,INode<T, V> two){
		boolean temp;
		temp=one.getColor();
		one.setColor(two.getColor());
		two.setColor(temp);
	}

	private void fixUpInsert(INode<T,V>node){if(node.getColor()==INode.BLACK)return;
		if(node==root){node.setColor(INode.BLACK);return;}
		INode<T, V> father=node.getParent();
		if(father.getColor()==INode.BLACK){return;}
		INode<T, V> grandfather=father.getParent();
		INode<T, V> uncle=getUncle(node);
		boolean level0left,level1left;
		if(uncle.getColor()==INode.RED){
			uncle.setColor(INode.BLACK);
			father.setColor(INode.BLACK);
			grandfather.setColor(INode.RED);
			fixUpInsert(grandfather);
			return;
		}
		else{
			level0left=(grandfather.getLeftChild()==father);
			level1left=(father.getLeftChild()==node);
			if(level0left&&level1left){
				rightRotate(grandfather);
				swapColor(grandfather,father);
				return;
			}
			else if(level0left&&!level1left){
				leftRotate(father);
				node=father;
				father=node.getParent();
				grandfather=father.getParent();
				uncle=getUncle(node);
				rightRotate(grandfather);
				swapColor(grandfather,father);
				return;
			}
			else if(!level1left){
				leftRotate(grandfather);
				swapColor(grandfather,father);
				return;
			}
			else{
				rightRotate(father);
				node=father;
				father=node.getParent();
				grandfather=father.getParent();
				uncle=getUncle(node);
				leftRotate(grandfather);
				swapColor(grandfather,father);
				return;
			}

		}
	}
	private void leftRotate(INode<T, V> node) {
		INode<T, V> right = node.getRightChild();
		node.setRightChild(right.getLeftChild());
		if ((right.getLeftChild() != null) && !right.getLeftChild().isNull()) {
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
		if(left==null||(left.isNull()))return;
		if(node==root){
			root=left;
			INode<T, V> rightright=left.getRightChild();
			root.setRightChild(node);
			node.setLeftChild(rightright);
			if(!rightright.isNull()){rightright.setParent(node);}
			node.setParent(root);
			root.setParent(null);
			return;
		}
		INode<T, V> parent=node.getParent();
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
		if(!node.getLeftChild().isNull()){node.getLeftChild().setParent(node);}
		left.setRightChild(node);

	}


	/*public void insert(T key, V value) {
		System.out.println("#0");
		if((key == null) || (value == null)) {
			throw new RuntimeErrorException(null);

		}
		//creating the node
		INode<T, V> node=new RBNode<T, V>(false);
		node.setKey(key);
		node.setValue(value);
		node.setRightChild(leaf);
		node.setLeftChild(leaf);
		node.setColor(INode.RED);
		System.out.println("#1");
		insertNode(node);
	}
	private void insertNode(INode node) {System.out.println("*0");
		INode temp = root;
		if (root == null) {System.out.println("*1");
			root = node;
			node.setColor(INode.BLACK);
			node.setParent(null);
		} else {System.out.println("*2");
			node.setColor(INode.RED);
			while (true) {System.out.println("*3");
				if (temp.getKey().compareTo(node.getKey())>0) {System.out.println("*4");
					if (temp.getLeftChild().isNull()) {System.out.println("*5");
						temp.setLeftChild(node);
						node.setParent(temp);
						break;
					} else {System.out.println("*6: node: " + node.getKey() + " temp: " + temp.getKey());
						temp = temp.getLeftChild();
					}
				} else if (temp.getKey().compareTo(node.getKey())<=0) {System.out.println("*7");
					if (temp.getRightChild().isNull()) {System.out.println("*8");
						temp.setRightChild(node);
						node.setParent(temp);
						break;
					} else {System.out.println("*9: node: " + node.getKey() + " temp: " + temp.getKey());
						temp = temp.getRightChild();
					}
				}
			}
			System.out.println("*10");
			fixUpInsert(node);
		}
	}

	//Takes as argument the newly inserted node
	private void fixUpInsert(INode node) {System.out.println("$0");
		while (node.getParent()!=null&&node.getParent().getColor() == INode.RED) {System.out.println("$1");
			INode uncle=leaf;
			if (node.getParent() == node.getParent().getParent().getLeftChild()) {System.out.println("$2");
				uncle = node.getParent().getRightChild();

				if (uncle != leaf && uncle.getColor() ==INode.RED) {System.out.println("$3");
					node.getParent().setColor(INode.BLACK);
					uncle.setColor(INode.BLACK);
					node.getParent().getParent().setColor(INode.RED);
					node = node.getParent().getParent();
					continue;
				}
				if (node == node.getParent().getRightChild()) {System.out.println("$4");
					//Double rotation needed
					node = node.getParent();
					leftRotate(node);
				}
				node.getParent().setColor(INode.BLACK);
				node.getParent().getParent().setColor(INode.RED);
				//if the "else if" code hasn't executed, this
				//is a case where we only need a single rotation
				rightRotate(node.getParent().getParent());
				System.out.println("$5");
			} else {System.out.println("$6");
				uncle = node.getParent().getParent().getLeftChild();
				if (uncle != leaf && uncle.getColor() ==INode.RED) {System.out.println("$7");
					node.getParent().setColor(INode.BLACK);
					uncle.setColor(INode.BLACK);
					node.getParent().getParent().setColor(INode.RED);
					node = node.getParent().getParent();
					continue;
				}
				if (node == node.getParent().getLeftChild()) {System.out.println("$8");
					//Double rotation needed
					node = node.getParent();
					rightRotate(node);
				}
				System.out.println("$9");
				node.getParent().setColor(INode.BLACK);
				node.getParent().getParent().setColor(INode.RED);
				//if the "else if" code hasn't executed, this
				//is a case where we only need a single rotation
				leftRotate(node.getParent().getParent());
			}
		}
		root.setColor(INode.BLACK);
		System.out.println("$10");
	}*/

//	void leftRotate(INode node) {System.out.println("@0");
//		if (node.getParent() != null) {System.out.println("@1");
//			if (node == node.getParent().getLeftChild()) {System.out.println("@2");
//				node.getParent().setLeftChild(node.getRightChild());
//			} else {System.out.println("@3");
//				node.getParent().setRightChild(node.getRightChild());
//			}
//			node.getRightChild().setParent(node.getParent());
//			node.setParent(node.getRightChild());
//			System.out.println("@4");
//			if (node.getRightChild().getLeftChild().isNull()) {System.out.println("@5");
//				node.getRightChild().getLeftChild().setParent(node);
//			}System.out.println("@6");
//			node.setRightChild(node.getRightChild().getLeftChild());
//			node.getParent().setLeftChild(node);
//		} else {//Need to rotate root
//			System.out.println("@7");
//			INode right = root.getRightChild();
//			root.setRightChild(right.getLeftChild());
//			right.getLeftChild().setParent(root);
//			root.setParent(right);
//			right.setLeftChild(root);
//			right.setParent(null);
//			root = right;
//		}
//	}

	/*void rightRotate(INode node) {System.out.println("@8");
		if (node.getParent() != null) {System.out.println("@9");
			if (node == node.getParent().getLeftChild()) {System.out.println("@10");
				node.getParent().setLeftChild(node.getLeftChild());
			} else {System.out.println("@11");
				node.getParent().setRightChild(node.getLeftChild());
			}

			node.getLeftChild().setParent(node.getParent());
			node.setParent(node.getLeftChild());
			if (!(node.getLeftChild().getRightChild().isNull())) {System.out.println("@12");
				node.getLeftChild().getRightChild().setParent(node);
			}
			node.setLeftChild(node.getLeftChild().getRightChild());
			node.getParent().setRightChild(node);
			System.out.println("@13");
		} else {//Need to rotate root
			System.out.println("@14");
			INode left = root.getLeftChild();
			root.setLeftChild(root.getLeftChild().getRightChild());
			left.getRightChild().setParent(root) ;
			root.setParent(left);
			left.setRightChild(root);
			left.setParent(null);
			root = left;
		}
	}*/

	public boolean delete(T key) {
		if(key == null) {
			throw new RuntimeErrorException(null);
		}
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
			if(node != leaf) {
				INode<T, V> successor = getSuccessor(node.getRightChild());
				node.setKey(successor.getKey());
				node.setValue(successor.getValue());
				recurDelete(node.getRightChild(), successor.getKey());
			}
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
				deleted.getParent().setColor(INode.RED);
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
	
	private void recurClr(INode<T, V> node) {
		if(node.isNull()) return;
		recurClr(node.getLeftChild());
		recurClr(node.getRightChild());
		deleteNode(node);
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
