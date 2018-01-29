import java.util.List;

public class BST<E extends Comparable>
{
	private BNode<E> root;
	
    public BST() 
    {
    	root = null;
    }
    
    //add data to this BST
    public void add(E data)
    {
        BNode<E> dataNode = new BNode<>(data);
    	if (root == null)
    	    root = dataNode;
    	else
    	    addHelper(root, dataNode);
    }
    
    //Recursive helper method for add
    private void addHelper(BNode<E> node, BNode<E> addMe)
    {
    	if (addMe.data.compareTo(node.data) < 0) {
    	    BNode left = node.left;
    	    if (left != null)
    	        addHelper(left, addMe);
    	    else
    	        node.setLeft(addMe);
        } else {
            BNode right = node.right;
    	    if (right != null)
    	        addHelper(right, addMe);
    	    else
    	        node.setRight(addMe);
        }
    }
    
    public void addAll(List<E> data)
    {
    	for (E datum : data)
    	    add(datum);
    }
    
    //reutrn the size of this tree (how many nodes are in it?)
    public int size()
    {
    	return sizeHelper(root);
    }
    
    //recursive helper method for size
    public int sizeHelper(BNode<E> node)
    {
        if (!node.hasChildren())
            return 1;
        int total = 1;
        if (node.hasLeftChild())
            total += sizeHelper(node.getLeft());
        if (node.hasRightChild())
            total += sizeHelper(node.getRight());
    	return total;
    }
    
    //Return a string with the data of this BST in pre order
    public String preorder()
    {
    	return preorderHelper(root, "") + "]";
    }
    
    //Recursive helper method for preorder
    private String preorderHelper(BNode<E> node, String ret)
    {
        ret += node == root ? "[" : ", ";
        ret += node.getData();
        if (node.hasLeftChild())
            ret += preorderHelper(node.getLeft(), "");
        if (node.hasRightChild())
            ret += preorderHelper(node.getRight(), "");
    	return ret;
    }
    
    //Return a string with the data of this BST in order
    public String inorder()
    {
    	return ("[" + inorderHelper(root, "") + "]").replace(", ]", "]");
    }
    
    //Recursive helper method for inorder
    private String inorderHelper(BNode<E> node, String ret)
    {
        if (node.hasLeftChild())
            ret += inorderHelper(node.getLeft(), "");
        ret += node.getData();
        ret += ", ";
        if (node.hasRightChild())
            ret += inorderHelper(node.getRight(), "");
    	return ret;
    }
    
    //Return a string with the data of this BST in post order
    public String postorder()
    {
    	return ("[" + postorderHelper(root, "") + "]").replace(", ]", "]");
    }
    
    //Recursive helper method for postorder
    private String postorderHelper(BNode<E> node, String ret)
    {
        if (node.hasLeftChild())
            ret += postorderHelper(node.getLeft(), "");
        if (node.hasRightChild())
            ret += postorderHelper(node.getRight(), "");
        ret += node.getData();
        ret += ", ";
    	return ret;
    }
    
    //Check if this BST contains the specified data
    public boolean contains(E data)
    {
    	return containsHelper(root, data);
    }
    
    //Recursive helper method for contains
    private boolean containsHelper(BNode<E> node, E data)
    {
        if (node == null)
            return false;
        if (node.getData().equals(data))
            return true;
        return containsHelper(node.getLeft(), data) || containsHelper(node.getRight(), data);
    }
    
    //Remove the first occurance of data from this BST tree.
    //If data is successfully removed return true, otherwise return false.
    public boolean remove(E data)
    {
        BNode<E> node = root;
        BNode<E> parent = null;

        while (node != null && !node.getData().equals(data)) {
            parent = node;
            if (data.compareTo(node.getData()) < 0)
                node = node.getLeft();
            else
                node = node.getRight();
        }

        if (node == null)
            return false;

        if (node.getNumChildren() == 0) {
            if (parent.getLeft().getData().equals(data))
                parent.setLeft(null);
            else
                parent.setRight(null);
        } else if (node.getNumChildren() == 1) {
            BNode<E> child = node.hasLeftChild() ? node.getLeft() : node.getRight();
            if (parent.getLeft() == node)
                parent.setLeft(child);
            else
                parent.setRight(child);
        } else {
            BNode<E> newChild = combine(node.getLeft(), node.getRight());
            if (node == root)
                root = newChild;
            else if (parent.getLeft() == node)
                parent.setLeft(newChild);
            else
                parent.setRight(newChild);
        }

    	return true;
    }
    
    //Recursive helper method for remove. Removes the smallest descendant from the specified node.
    public BNode<E> removeSmallestChild(BNode<E> node) {
        if (!node.hasLeftChild()) {
            return node;
        } else {
            BNode<E> smallest = removeSmallestChild(node.getLeft());
            if (smallest == node.getLeft()) {
                node.setLeft(smallest.getRight());
            }
            return smallest;
        }
    }
    
    //Helper method for remove. Reforms the left and right subtrees into a single
    //BST and returns its root node.
    public BNode<E> combine(BNode<E> left, BNode<E> right)
    {
        BNode<E> smallest = removeSmallestChild(right);
        if (smallest == right)
            right = right.getRight();
        smallest.setLeft(left);
        smallest.setRight(right);
    	return smallest;
    }
    
    ///////////////////
    //	Helper Class //
    ///////////////////
    class BNode<E extends Comparable>
    {
    	private E data;
    	private BNode<E> left, right;
    	
    	public BNode(E data)
    	{this.data = data;}
    	
    	public E getData()
    	{return data;}
    	
    	public BNode<E> getLeft()
    	{return left;}
    	
    	public BNode<E> getRight()
    	{return right;}
    	
    	public void setLeft(BNode<E> left)
    	{this.left = left;}
    	
    	public void setRight(BNode<E> right)
    	{this.right = right;}
    	
    	public boolean hasLeftChild()
    	{return null != left;}
    	
    	public boolean hasRightChild()
    	{return null != right;}
  
  		public boolean hasChildren()
  		{return getNumChildren() > 0;}
  		
  		public int getNumChildren()
  		{
  			int ret = 0;
  			if(hasLeftChild())  ret++;
  			if(hasRightChild()) ret++;
  			return ret;
  		}
    }
}