/* Evan Liu
 * Period 6
 * Binary Tree
 * This program creates a Binary Tree data structure using an array passed in that works with a comparable type
 * and provides several recursive methods to determine the nature of the tree, find values, and printing it out.
 * 
 * I think the method allGolden is more inefficient than the others because 
 * for each node it calls a recursive method to determine whether it meets the conditions or not
 * and compare its height using another recursive method. 
 */
import java.util.*;

public class Liu_BinaryTree<E extends Comparable<E>> {

	private TreeNode root;

	public Liu_BinaryTree(E[] elements) {

		root = arrayHelper(elements, 0);
	}

	//recursively builds and returns a binary tree
	private TreeNode arrayHelper(E[] elements, int curIndex) {

		if(curIndex >= elements.length || elements[curIndex] == null) {
			return null;
		}

		else  {
			return new TreeNode(elements[curIndex], arrayHelper(elements, 2*curIndex + 1), arrayHelper(elements, 2*curIndex + 2));
		}
	}

	public Liu_BinaryTree(Liu_BinaryTree other) {

		root = copyTree(other.root);
	}
	//helper method that copies treenode passed in
	private TreeNode copyTree(TreeNode r) {

		if(r == null) {
			return null;
		}

		TreeNode copy = new TreeNode(r.data, null, null);

		copy.left = copyTree(r.left);
		copy.right = copyTree(r.right);

		return copy;
	}

	public void inOrder() {

		inOrder(root);
	}

	//prints data in between recursive calls
	private void inOrder(TreeNode r) {

		if(r == null) {
			return;
		}

		inOrder(r.left);
		System.out.println(r.data);
		inOrder(r.right);
	}

	public void preOrder() {

		preOrder(root);
	}

	//prints data before recursive calls
	private void preOrder(TreeNode r) {

		if(r == null) {
			return;
		}

		System.out.println(r.data);
		preOrder(r.left);
		preOrder(r.right);
	}

	public void postOrder() {

		postOrder(root);
	}

	//prints data after recursive calls
	private void postOrder(TreeNode r) {

		if(r == null) {
			return;
		}

		postOrder(r.left);
		postOrder(r.right);
		System.out.println(r.data);
	}

	//returns true if the item is found within the tree
	public boolean find(E item) {

		return find(root, item);
	}

	//uses recursion to search the tree for parameter item
	private boolean find(TreeNode r, E item) {

		if(r == null) {
			return false;
		}

		//if current node is item
		if(r.data.equals(item)) {
			return true;
		}

		//recursively call left tree
		if(find(r.left, item)) {
			return true; 
		}

		//then call right if not found in left
		return find(r.right, item);
	}

	//returns the height of the tree (total number of levels)
	public int height() {

		return height(root);
	}

	//recursively determines the maximum number of levels in the tree
	private int height(TreeNode r) {

		if(r == null) {
			return 0;
		}

		int lheight = 1 + height(r.left);
		int rheight = 1 + height(r.right);

		//compares the left height to the right height and returns largest value
		if(lheight > rheight) {
			return lheight;
		}

		return rheight;
	}

	//returns the largest value in the tree
	public E max() {

		return max(root);
	}

	//recursively compares each node with each other to determine the largest node
	private E max(TreeNode r) {

		if(r == null) {
			return null;
		}

		//overall max of the entire tree
		E max = r.data;

		//max of left and right subtrees
		E lmax = null;
		E rmax = null;

		lmax = max(r.left);
		rmax = max(r.right);

		//if left larger than right, set max to left
		if(lmax != null && rmax != null && lmax.compareTo(rmax) > 0) {
			max = lmax;
		}

		if(rmax != null && rmax.compareTo(max) > 0) {
			max = rmax;
		}

		return max;
	}

	//returns true if all nodes have 2 children or are leafs or the tree is empty
	public boolean allLeafs() {

		return allLeafs(root);
	}

	//recursively determines if every node is a leaf, has two children, or it is empty
	private boolean allLeafs(TreeNode r) {

		//empty tree
		if(r == null) {
			return true;
		}

		//checks if it is a leaf
		if(r.left == null && r.right == null ) {
			return true;
		}

		//if left and right are both not null, they have children
		if(r.left != null && r.right != null) {
			return (allLeafs(r.left) && allLeafs(r.right));
		}

		return false;
	}

	//returns true if tree is empty, node has zero kids, or has 2 kids of the same length
	public boolean isGolden() {

		return isGolden(root);
	}

	//determines if node is leaf or has 2 children and compares height
	private boolean isGolden(TreeNode r) {

		if(r == null) {
			return true;
		}

		//leaf node
		if(r.left == null && r.right == null) {
			return true;
		}

		if(r.left != null && r.right != null) {
			return (isGolden(r.left) && isGolden(r.right) && height(r.left) == height(r.right));
		}

		return false;
	}

	//prints out all possible paths from root to leaf
	public void printAllPaths() {

		printAllPaths(root, new String());
	}

	//uses a string variable to maintain the current path/subtree printing out 
	private void printAllPaths(TreeNode r, String path) {

		//adds data of current node into string
		path = path + r.data + " ";

		//end of the "path", prints everything out
		if(r.left == null && r.right == null) {
			System.out.println(path);
		} 

		//right child only
		else if(r.left == null && r.right != null) {
			printAllPaths(r.right, path);
		} 

		//left child only
		else if(r.left != null && r.right == null) {
			printAllPaths(r.left, path);
		} 

		//has two children
		else {
			printAllPaths(r.left, path);
			printAllPaths(r.right, path);
		}
	}

	//represents one node in the tree - can have zero, one, or two children
	public class TreeNode {

		private E data;
		private TreeNode left;
		private TreeNode right;

		public TreeNode(E d, TreeNode l, TreeNode r) {

			data = d;
			left = l;
			right = r;
		}
	}
}
