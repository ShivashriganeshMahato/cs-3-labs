import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class WeightedAdjacencyListGraph<E> extends AdjacencyListGraph<E> implements WeightedGraph<E>
{
	//INSTANCE VARIABLES
	/**
	 *	A mapping between a Node and a map of Neighbor->Weight
	 */
	private Map<E, Map<E, Integer>> weights;

	//CONSTRUCTORS
    public WeightedAdjacencyListGraph() 
    {
    	//initialize instance variables
    	weights = new HashMap<E, Map<E, Integer>>();
    }
    
    //METHODS
    /**
     *	Add a node to the graph
     *
     *	Add a node to the graph exactly like an AdjacencyGraph,
     *	then create a Map to keep track of weight associations between neighbors
     *
     *	@param node the data to add to the graph. Duplicate data will not be added.
     *	@return true if the data was successfully added to graph
     */
    @Override
    public boolean add(E node)
    {
		if (super.add(node)) {
			weights.put(node, new HashMap<>());
			return true;
		}
		return false;
    }
    
    /**
     *	Set the weight between two nodes
     *
     *	@param from the node to set the weight from
     *	@param to the node to set the weight to
     *	@param weight the weight to set
     */
    public void setWeight(E from, E to, int weight)
    {
		if (getNeighbors(from).contains(to))
			weights.get(from).put(to, weight);
    }
    
    /**
     *	Set the weight between two nodes
     *
     *	If there is no edge between the nodes, this will return 0.
     *	If no weight has been assigned to these nodes, then this will return 0.
     *	
     *	@param from the node to get the weight from
     *	@param to the node to get the weight to
     *	@return the weight between from and to or 0 if no edge or weight exists.
     */
	public int getWeight(E from, E to)
	{
		if (weights.containsKey(from) && weights.get(from).get(to) != null) {
			return weights.get(from).get(to);
		}
		return 0;
	}
	
	/**
	 *	Return a Mapping of Node -> Weight for all neighbors of node
	 *
	 *	@param node the node to get weight mappings from
	 *	@return mappings from node to weight
	 */
	public Map<E, Integer> getNeighborWeights(E node)
	{
		return weights.get(node);
	}
	
	/**
	 *	Return a string representation of the weighted graph data
	 *
	 *	@return a string representation of the weighted graph
	 */
	@Override
	public String toString()
	{
		List<String> ret = new ArrayList<String>();
		
		for(E node: getNodes())
		{
			List<String> neighborStrings = new ArrayList<String>();
			for(E neighbor : getNeighbors(node))
				neighborStrings.add(neighbor + "(" + getWeight(node, neighbor) + ")");
			
			ret.add(node + "->" + neighborStrings.toString());
		}
		
		return ret.toString();
	}
}