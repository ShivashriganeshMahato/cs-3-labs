public class Heap<E extends Comparable> extends ListBinaryTree<E>
{
	//Add data to this heap
	//Add data exactly like a ListBinaryTree, then propigate that value up the tree 
	//(using the addHelper method)
    @Override
    public void add(E data)
    {
    	super.add(data);
    	addHelper(size() - 1);
    }
    
    //Recursive helper method for add
    //Recusively swap the value at index and its parent while val is less than its parent
    private void addHelper(int index)
    {
        int parentInd = getParentIndex(index);
    	if (parentInd != -1 && getValueAt(index).compareTo(getValueAt(parentInd)) < 0) {
    	    swap(index, parentInd);
    	    addHelper(parentInd);
        }
    }
    
    //returns true if the value at index is less than both of its children
    public boolean meetsHeapProperty(int index)
    {
        int left = getLeftIndex(index);
        int right = getRightIndex(index);
        return left != -1 && getValueAt(left).compareTo(getValueAt(index)) >= 0 &&
                right != -1 && getValueAt(right).compareTo(getValueAt(index)) >= 0;
    }
    
    //Helper method
    //Returns the index of the child of the specified node with the smallest value
    private int getSmallestChildIndex(int index)
    {
        int left = getLeftIndex(index);
        int right = getRightIndex(index);
        if (left == -1)
            return right;
        if (right == -1)
            return left;
        return getValueAt(left).compareTo(getValueAt(right)) < 0 ? left : right;
    }
    
    
    //remove and return the value at the root of this heap
    //then reconstitute its heapness using the remove algorithm
    public E removeRoot()
    {
        E root = list.remove(0);
        list.add(0, removeLast());

        int index = 0;
        while (!meetsHeapProperty(index)) {
            int smallest = getSmallestChildIndex(index);
            if (smallest == -1)
                break;
            swap(index, smallest);
            index = smallest;
        }

    	return root;
    }
    
    public void heapify()
    {
    	for (int i = size() - 1; i >= 0; i--) {
            sink(i);
        }
    }
 
    //recursive helper method for heapify. 
    //This method "sinks" the value at index until it meets the heap property
    private void sink(int index)
    {
        int smallest = getSmallestChildIndex(index);
        if (smallest != -1 && !meetsHeapProperty(index)) {
            swap(index, smallest);
            sink(smallest);
        }
    }
    
    //do not edit this method!
    public void shuffle()
    {
    	for(int i=0; i<size(); i++)
    		swap(i, (int)(Math.random() * size()));
    }
}