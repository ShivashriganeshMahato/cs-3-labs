import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListBinaryTree<E>
{
	protected List<E> list;
	
    public ListBinaryTree() 
    {
    	list = new ArrayList<E>();
    }
    
    //add data to this Complete Binary Tree
    public void add(E data)
    {
    	list.add(data);
    }
    
    //Add all the elements from the provided list to this Complete Binary Tree
    public void addAll(List<E> data)
    {
    	for (E datum : data)
    	    add(datum);
    }
    
    //Return the size of this tree
    public int size()
    {
    	return list.size();
    }
    
    public String preorder()
    {
    	return Arrays.toString(preorderHelper(0, "").split("[ ]+"));
    }
    
    private String preorderHelper(int index, String ret)
    {
        int left = getLeftIndex(index), right = getRightIndex(index);
        ret += list.get(index) + " ";
        if (left != -1)
            ret += preorderHelper(left, "") + " ";
        if (right != -1)
            ret += preorderHelper(right, "") + " ";
    	return ret;
    }
    
    public String inorder()
    {
        return Arrays.toString(inorderHelper(0, "").split("[ ]+"));
    }
    
    private String inorderHelper(int index, String ret)
    {
        int left = getLeftIndex(index), right = getRightIndex(index);
        if (left != -1)
            ret += inorderHelper(left, "") + " ";
        ret += list.get(index) + " ";
        if (right != -1)
            ret += inorderHelper(right, "") + " ";
        return ret;
    }
    
    public String postorder()
    {
        return Arrays.toString(postorderHelper(0, "").split("[ ]+"));
    }
    
    private String postorderHelper(int index, String ret)
    {
        int left = getLeftIndex(index), right = getRightIndex(index);
        if (left != -1)
            ret += postorderHelper(left, "") + " ";
        if (right != -1)
            ret += postorderHelper(right, "") + " ";
        ret += list.get(index) + " ";
        return ret;
    }

    private boolean isOutOfBounds(int index) {
        return index < 0 || index >= list.size();
    }
    
    //return the value stored at the specified index
    //or null if the index is out of bounds
    public E getValueAt(int index)
    {
        if (isOutOfBounds(index))
    	    return null;
        return list.get(index);
    }
    
    //return the index of the parent of the node located at the specified index
    //if index is out of bounds, return -1
    //if the node at the specified index does not have a parent, return -1
    public int getParentIndex(int index)
    {
        int parent = (int) Math.floor((index - 1) / 2.0);
        return isOutOfBounds(index) || isOutOfBounds(parent) ? -1 : parent;
    }
    
    //return the index of the left child of the node at the specified index
    //if there is no left child, return -1
    public int getLeftIndex(int parentIndex)
    {
        int index = 2 * parentIndex + 1;
    	return isOutOfBounds(parentIndex) || isOutOfBounds(index) ? -1 : index;
    }
    
    //return the index of the right child of the node at the specified index
    //if there is no right child, return -1
    public int getRightIndex(int parentIndex)
    {
        int index = 2 * parentIndex + 2;
        return isOutOfBounds(parentIndex) || isOutOfBounds(index) ? -1 : index;
    }
    
    //a useful utility function to swap two nodes in the tree
    //swap the values located at indexA and indexB
    public void swap(int indexA, int indexB)
    {
    	E valA = list.get(indexA);
    	list.set(indexA, list.get(indexB));
    	list.set(indexB, valA);
    }

    protected E removeLast() {
        return list.remove(size() - 1);
    }
    		
}