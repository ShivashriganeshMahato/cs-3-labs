import java.util.ArrayList;
import java.util.List;

public class ListMap<K, V> implements Map {
    private ListSet<K> keys;
    private ArrayList<Node> nodes;

    public ListMap() {
        keys = new ListSet<K>();
        nodes = new ArrayList<Node>();
    }

    @Override
    public boolean containsKey(Object key) {
        return key != null && keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) return false;
        for (Node node : nodes) {
            if (node.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public Object get(Object key) {
        if (!containsKey(key)) return null;
        for (Node node : nodes) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        if (keys.add((K) key)) {
            if (key != null && value != null)
                nodes.add(new Node(key, value));
        } else {
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                if (node.getKey().equals(key)) {
                    Object obj = node.getValue();
                    nodes.set(i, new Node(key, value));
                    return obj;
                }
            }
        }
        return null;
    }

    @Override
    public Object remove(Object key) {
        if (key != null) {
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                if (node.getKey().equals(key)) {
                    keys.remove(key);
                    return nodes.remove(i).getValue();
                }
            }
        }
        return null;
    }

    @Override
    public List values() {
        ArrayList<V> values = new ArrayList<V>();
        for (Node node : nodes) {
            values.add((V) node.getValue());
        }
        return values;
    }

    @Override
    public Set keySet() {
        return keys;
    }
}