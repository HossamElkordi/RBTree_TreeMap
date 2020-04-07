package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RBTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private INode<T, V> root;
	private boolean isDeleted = false;

	public INode<T, V> getRoot() {
		return this.root;
	}

	public boolean isEmpty() {
		return ((root == null) || (this.root.isNull()));
	}

	public void clear() {
		if(root != null) {
			recurClr(root);
		}
		root = null;
	}

	public V search(T key) {
		if(key == null) {
			throw new RuntimeErrorException(null);
		}
		if(!isEmpty()) {
			INode<T, V> searchRes = recurSearch(this.root, key);
			return ((searchRes == null) || searchRes.isNull()) ? null : searchRes.getValue();
		}
		return null;
	}

	public boolean contains(T key) {
		if(key == null) {
			throw new RuntimeErrorException(null);
		}
		if(!isEmpty()) {
			INode<T, V> searchRes = recurSearch(this.root, key);
			return ((searchRes == null) || searchRes.isNull()) ? false : true;
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
		INode<T, V> child1=new RBNode<T, V>(true);
		child1.setParent(node);
		node.setRightChild(child1);
		child1=new RBNode<T, V>(true);
		child1.setParent(node);
		node.setLeftChild(child1);
		node.setColor(INode.RED);
		if(root==null || root.isNull()){node.setColor(INode.BLACK);
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
		if(node == null || node.isNull()) return;
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
		if(node == null || node.isNull()) return;
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
	
	public boolean delete(T key) {
		if(key == null) {
			throw new RuntimeErrorException(null);
		}
		isDeleted = false;
		helpDelete(key);
		if(isDeleted) {
			return true;
		}
		return false;
	}
	
	private void helpDelete(T key) {
		INode<T, V> iter = root;
		while(!iter.isNull()) {
			if(key.compareTo(iter.getKey()) < 0) {
				iter = iter.getLeftChild();
			}else if(key.compareTo(iter.getKey()) > 0) {
				iter = iter.getRightChild();
			}else {
				isDeleted = true;
				break;
			}
		}
	    
		if(iter.isNull()) return;
		delNode(iter);
	}
	
	private void delNode(INode<T, V> toDelete) {
		INode<T, V> replacement = getReplacement(toDelete);
		
		boolean doubleBlack = ((replacement.getColor() == INode.BLACK) && (toDelete.getColor() == INode.BLACK));
		if(replacement.isNull()) {
			if(toDelete.equals(root) || toDelete == root) {
				root = replacement;
			}else {
				if(doubleBlack) {
					delFix(toDelete);
				}else {
					INode<T, V> sib = getSibling(toDelete, toDelete.getParent());
					if(!(sib == null || sib.isNull())) sib.setColor(INode.RED);
				}
				if(toDelete.equals(toDelete.getParent().getLeftChild())) {
					toDelete.getParent().setLeftChild(replacement);
				}else {
					toDelete.getParent().setRightChild(replacement);
				}
				replacement.setParent(toDelete.getParent());
			}
			return;
		}
		
		if(!toDelete.getLeftChild().isNull() && !toDelete.getRightChild().isNull()) {
			this.swapKeyValue(toDelete, replacement);
			delNode(replacement);
		}else {
			swapNodesParents(toDelete, replacement);
			if(doubleBlack) {
				delFix(replacement);
			}else {
				replacement.setColor(INode.BLACK);
			}
		}
	}
	
	private INode<T, V> getReplacement(INode<T, V> toDelete) {
		INode<T, V> replacement = null;
		if(!toDelete.getLeftChild().isNull() && !toDelete.getRightChild().isNull()) {
			replacement = this.getSuccessor(toDelete.getRightChild());
		}else if(toDelete.getLeftChild().isNull() && toDelete.getRightChild().isNull()) {
			replacement = toDelete.getLeftChild();
		}else {
			if(toDelete.getLeftChild().isNull()) {
				replacement = toDelete.getRightChild();
			}else {
				replacement = toDelete.getLeftChild();
			}
		}
		return replacement;
	}
	
	private void delFix(INode<T, V> node) {
		if((root == node) || root.equals(node)) return;
		INode<T, V> parent = node.getParent();
		INode<T, V> sibling = this.getSibling(node, parent);
		if(sibling == null) return;
		if(sibling.isNull()) {
			delFix(parent);
			return;
		}
		if(sibling.getColor() == INode.RED) {
			sibling.setColor(INode.BLACK);
			parent.setColor(INode.RED);
			if(sibling.equals(parent.getLeftChild())) {
				rightRotate(parent);
			}else {
				leftRotate(parent);
			}
			delFix(node);
		}else {
			if((sibling.getLeftChild().getColor() == INode.BLACK) && (sibling.getRightChild().getColor() == INode.BLACK)) {
				sibling.setColor(INode.RED);
				if(parent.getColor() == INode.BLACK) {
					delFix(parent);
				}else {
					parent.setColor(INode.BLACK);
				}
			}else {
				if(sibling.getLeftChild().getColor() == INode.RED) {
					if(sibling.equals(parent.getLeftChild())) {
						sibling.getLeftChild().setColor(sibling.getColor());
						sibling.setColor(parent.getColor());
						rightRotate(parent);
					}else {
						sibling.getLeftChild().setColor(parent.getColor()); 
			            rightRotate(sibling); 
			            leftRotate(parent);
					}
				}else {
					if(sibling.equals(parent.getLeftChild())) {
						sibling.getRightChild().setColor(parent.getColor()); 
			            leftRotate(sibling); 
			            rightRotate(parent);
					}else {
						sibling.getRightChild().setColor(sibling.getColor());
						sibling.setColor(parent.getColor());
						leftRotate(parent);
					}
				}
				if(parent != null) parent.setColor(INode.BLACK);
			}
		}
	}
	
	private void swapKeyValue(INode<T, V> node1, INode<T, V> node2) {
		T ktemp = node1.getKey();
		node1.setKey(node2.getKey());
		node2.setKey(ktemp);
		V vtemp = node1.getValue();
		node1.setValue(node2.getValue());
		node2.setValue(vtemp);
	}
	
//	private void delHelper(T key) {
//		INode<T, V> toDelete = null;
//		INode<T, V> i = root;
//		while(!i.isNull()) {
//			if(key.compareTo(i.getKey()) < 0) {
//				i = i.getLeftChild();
//			}else if(key.compareTo(i.getKey()) > 0) {
//				i = i.getRightChild();
//			}else {
//				isDeleted = true;
//				toDelete = i;
//				break;
//			}
//		}
//		if(i.isNull()) {
//			return;
//		}
//		
//		boolean deletedColor = toDelete.getColor();
//		INode<T, V> toFix = null;
//		INode<T, V> firstSibling = null;
////		INode<T, V> firstParent = null;
////		if(toDelete.getLeftChild().isNull() && toDelete.getRightChild().isNull()) {
////			toFix = leaf;
////			firstSibling = getSibling(toDelete, toDelete.getParent());
////		}
//		if(toDelete.getLeftChild().isNull()) {
//			toFix = toDelete.getRightChild();
//			firstSibling = getSibling(toDelete, toDelete.getParent());
//			swapNodesParents(toDelete, toDelete.getRightChild());
//			if(toDelete.equals(root)) {
//				root = toFix;
//			}
//		}else if(toDelete.getRightChild().isNull()) {
//			toFix = toDelete.getLeftChild();
//			firstSibling = getSibling(toDelete, toDelete.getParent());
//			swapNodesParents(toDelete, toDelete.getLeftChild());
//			if(toDelete.equals(root)) {
//				root = toFix;
//			}
//		}else {
//			INode<T, V> successor = getSuccessor(toDelete.getRightChild());
//			toFix = successor.getRightChild();
//			firstSibling = getSibling(toDelete, toDelete.getParent());
//			deletedColor = successor.getColor();
//			if(!successor.equals(toDelete.getRightChild())) {
//				swapNodesParents(successor, successor.getRightChild());
//				successor.setRightChild(toDelete.getRightChild());
//				toDelete.getRightChild().setParent(successor);
//			}else {
//				toFix.setParent(successor);
//			}
//			swapNodesParents(toDelete, successor);
//			if(toDelete.equals(root)) {
//				root = successor;
//			}
//			successor.setLeftChild(toDelete.getLeftChild());
//			toDelete.getLeftChild().setParent(successor);
//			successor.setColor(toDelete.getColor());
//		}
//		
//		if(deletedColor == INode.BLACK) {
//			fixDeletion(toFix, firstSibling, toDelete.getParent());
//		}
//		
//	}
	
	private void swapNodesParents(INode<T, V> node1, INode<T, V> node2) {
		if(root.equals(node1)) {
			root = node2;
		}else if(node1.getParent().getRightChild().equals(node1)) {
			node1.getParent().setRightChild(node2);
		}else {
			node1.getParent().setLeftChild(node2);
		}
		node2.setParent(node1.getParent());
	}
	
//	private void fixDelete(INode<T, V> x) {
//	    INode<T, V> s;
//	    while ((x != root || !root.equals(x)) && x.getColor() == false) {
//	      if (x == x.getParent().getLeftChild()) {
//	        s = x.getParent().getRightChild();
//	        if (s.getColor() == true) {
//	          s.setColor(false);
//	          x.getParent().setColor(true);
//	          leftRotate(x.getParent());
//	          s = x.getParent().getRightChild();
//	        }
//
//	        if (s.getLeftChild().getColor() == false && s.getRightChild().getColor() == false) {
//	          s.setColor(true);
//	          x = x.getParent();
//	        } else {
//	          if (s.getRightChild().getColor() == false) {
//	            s.getLeftChild().setColor(false);
//	            s.setColor(true);
//	            rightRotate(s);
//	            s = x.getParent().getRightChild();
//	          }
//
//	          s.setColor(x.getParent().getColor());
//	          x.getParent().setColor(false);
//	          s.getRightChild().setColor(false);
//	          leftRotate(x.getParent());
//	          x = root;
//	        }
//	      } else {
//	        s = x.getParent().getLeftChild();
//	        if (s.getColor() == true) {
//	          s.setColor(false);
//	          x.getParent().setColor(true);
//	          rightRotate(x.getParent());
//	          s = x.getParent().getLeftChild();
//	        }
//
//	        if (s.getRightChild().getColor() == false && s.getLeftChild().getColor() == false) {
//	          s.setColor(true);
//	          x = x.getParent();
//	        } else {
//	          if (s.getLeftChild().getColor() == false) {
//	            s.getRightChild().setColor(false);
//	            s.setColor(true);
//	            leftRotate(s);
//	            s = x.getParent().getLeftChild();
//	          }
//
//	          s.setColor(x.getParent().getColor());
//	          x.getParent().setColor(false);
//	          s.getLeftChild().setColor(false);
//	          rightRotate(x.getParent());
//	          x = root;
//	        }
//	      }
//	    }
//	    x.setColor(false);
//	  }
	
	public INode<T, V> minimum(INode<T, V> node) {
	    while (!node.getLeftChild().isNull()) {
	      node = node.getLeftChild();
	    }
	    return node;
	  }
	
//	private void fixDeletion(INode<T, V> node, INode<T, V> firstSibling, INode<T, V> firstParent) {
//		INode<T, V> sibling = firstSibling;
//		INode<T, V> parent = firstParent;
//		while(!node.equals(root) && (node.getColor() == INode.BLACK)) {
//			if(sibling.getColor() == INode.RED) {
//				sibling.setColor(INode.BLACK);
//				parent.setColor(INode.RED);
//				if(sibling.equals(parent.getRightChild())) {
//					leftRotate(parent);
//				}else {
//					rightRotate(parent);
//				}
//				sibling = getSibling(node, parent);
//			}
//			if(node.equals(root) || sibling == null) break;
//			if(sibling.isNull() || ((sibling.getLeftChild().getColor() == INode.BLACK) && (sibling.getRightChild().getColor() == INode.BLACK))) {
//				if(!sibling.isNull()) {
//					sibling.setColor(INode.RED);
//					node = sibling.getParent();
//				}else {
//					node = parent;
//				}
//				
////				sibling.setColor(INode.RED);
////				node = sibling.getParent();
//				parent = node.getParent();
//				sibling = getSibling(node, parent);
//			}else {
//				if(sibling.equals(parent.getRightChild())) {
//					if((sibling.getRightChild() == null) || (sibling.getRightChild().getColor() == INode.BLACK)) {
//						sibling.setColor(INode.RED);
//						sibling.getLeftChild().setColor(INode.BLACK);
//						rightRotate(sibling);
//						sibling = getSibling(node, parent);
//					}
//					sibling.setColor(parent.getColor());
//					parent.setColor(INode.BLACK);
//					sibling.getRightChild().setColor(INode.BLACK);
//					leftRotate(parent);
//					node = root;
//				}else {
//					if((sibling.getLeftChild() == null) || (sibling.getLeftChild().getColor() == INode.BLACK)) {
//						sibling.setColor(INode.RED);
//						sibling.getRightChild().setColor(INode.BLACK);
//						leftRotate(sibling);
//						sibling = getSibling(node, parent);
//					}
//					sibling.setColor(parent.getColor());
//					parent.setColor(INode.BLACK);
//					sibling.getLeftChild().setColor(INode.BLACK);
//					rightRotate(parent);
//					node = root;
//				}
//			}
//		}
//		node.setColor(INode.BLACK);
//	}
	
	private INode<T, V> getSuccessor(INode<T, V> node){
		return (!node.getLeftChild().isNull()) ? getSuccessor(node.getLeftChild()) : node;
	}
	
	private void deleteNode(INode<T, V> node) {
		node.setValue(null);
		node.setKey(null);
		node.setLeftChild(null);
		node.setRightChild(null);
		node.setParent(null);
	}

	private INode<T, V> recurSearch(INode<T, V> root, T key) {
		if (root.isNull() || (root.getKey().equals(key))) {
			return root;
		}

		if (key.compareTo(root.getKey()) < 0) {
			return recurSearch(root.getLeftChild(), key);
		} else {
			return recurSearch(root.getRightChild(), key);
		}
	}
	
	private void recurClr(INode<T, V> node) {
		if(node.isNull()) return;
		recurClr(node.getLeftChild());
		recurClr(node.getRightChild());
		deleteNode(node);
		node = null;
	}


	private INode<T, V> getUncle(INode<T, V> node){
		if(node != null) {
			if(node.getParent() == null) return null;
			if(node.getParent().getParent() == null) return null;
			INode<T, V> parent = node.getParent();
			INode<T, V> grandParent = node.getParent().getParent();
			if(grandParent.getLeftChild() == parent) {
				return grandParent.getRightChild();
			}else {
				return grandParent.getLeftChild();
			}
		}
		return null;
//		if((node != null) && (node.getParent() == null) && (node.getParent().getParent() != null))return null;
//		INode<T, V> parent=node.getParent();
//		if(parent.getParent().getRightChild()==parent)return parent.getParent().getLeftChild();
//		else return parent.getParent().getRightChild();
	}

//	private INode<T, V> getSibling(INode<T, V> node){
//		if((node != null) && !node.isNull()) {
//			if(node.getParent() != null) {
//				if (node.getParent().getLeftChild().equals(node)) {
//					return node.getParent().getRightChild();
//				} else {
//					return node.getParent().getLeftChild();
//				}
//			}
//		}
//		return null;
//	}
	
	private INode<T, V> getSibling(INode<T, V> node, INode<T, V> parent){
		if(parent != null) {
			if(parent.getLeftChild().equals(node)) {
				return parent.getRightChild();
			}else {
				return parent.getLeftChild();
			}
		}
		return null;
	}
	
}
