/**
 * @author harrisonle 
 * Email: hal117@ucsd.edu
 * 
 * 
 * This file will implement the default map interface. We implement this file with 
 * the binary search tree data structure. 
 * 
 */
import java.util.ArrayList;
import java.util.List;

/**
 * @param <K> The type of the keys of this BST. They need to be comparable by nature of the BST
 * "K extends Comparable" means that BST will only compile with classes that implement Comparable
 * interface. This is because our BST sorts entries by key. Therefore keys must be comparable.
 * @param <V> The type of the values of this BST. 
 */
public class BST<K extends Comparable<? super K>, V> implements DefaultMap<K, V> {
	/* 
	 * TODO: Add instance variables 
	 * You may add any instance variables you need, but 
	 * you may NOT use any class that implements java.util.SortedMap
	 * or any other implementation of a binary search tree
	 */
	private int size;
	private Node<K, V> root;
	
	
	    
	/**
	 * Adds the specified key, value pair to this DefaultMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		// TODO Auto-generated method stub
	    if (key == null) {
	        throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
	    }

	    if (root == null) {
	        root = new Node<>(key, value);
	        size++;
	        return true;
	    }

	    Node<K, V> current = root;
	    Node<K, V> parent;

	    while (true) {
	        parent = current;
	        int comp = key.compareTo(current.key);

	        if (comp < 0) {
	            current = current.left;
	            if (current == null) {
	                parent.left = new Node<>(key, value);
	                size++;
	                return true;
	            }
	        } else if (comp > 0) {
	            current = current.right;
	            if (current == null) {
	                parent.right = new Node<>(key, value);
	                size++;
	                return true;
	            }
	        } 
	        else {
	            return false;
	        }
	    }

	}

	/**
	 * Replaces the value that maps to the key if it is present
	 * @param key The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		
		if(containsKey(key)) {
			set(key, newValue);
			return true;
		}
		return false;
	}
	
	/**
	 * Helper method for remove method that removes the node with the specified key.
	 * @param node: Start node
	 * @param keyToRemove: Key to remove 
	 * @return The root node
	 */
	private Node<K, V> remove(Node<K, V> node, K keyToRemove) {
	    if (node == null) {
	        return null;
	    }

	    int comp = keyToRemove.compareTo(node.getKey());
	    if (comp < 0) {
	        node.left = remove(node.left, keyToRemove);
	    } else if (comp > 0) {
	        node.right = remove(node.right, keyToRemove);
	    } else {
	        if (node.left == null) {
	            return node.right;
	        }
	        if (node.right == null) {
	            return node.left;
	        }

	        Node<K, V> successor = findSuccessor(node.right);
	        node.key = successor.getKey();
	        node.setValue(successor.getValue());
	        node.right = remove(node.right, node.getKey());
	    }
	    
	    return node;
	}
	
	/**
	 * Helper method for the remove method (find the successor)
	 * @param node to find the successor
	 * @return the successor node
	 */
	private Node<K, V> findSuccessor(Node<K, V> node) {
	    while (node.left != null) {
	        node = node.left;
	    }
	    return node;
	}

	
	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if an entry for the given key was removed
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		this.root = remove(this.root, key);
		
		if(size() == keys().size()) {
			return false;
		}
		this.size--;
		return true;
	}
	
	/**
	 * Helper method for the set method which sets the value of a node with a key.
	 * If the key is not found, a new node with the key and value is created and added to the tree.
	 * @param node: Start node
	 * @param key: Key to set the value for
	 * @param value: The new value to set
	 * @return
	 */
	private Node<K, V> setNode(Node<K, V> node, K key, V value){
		if(node == null) {
			size++;
			return new Node<K, V>(key, value);
		}
		int comp = key.compareTo(node.getKey());
	    	if (comp < 0) {
	    		node.left = setNode(node.left, key, value);
	    } 
	    		else if (comp > 0) {
	    			node.right = setNode(node.right, key, value);
	    } 
	    		else {
	    			node.setValue(value);
	    }
	    return node;
	}

	/**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		root = setNode(root, key, value);
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Helper method for get method that searches for the node with the key
	 * @param node: Starting node
	 * @param key: The key to search for 
	 * @return the node with specified key, or null if not found
	 * @throws IllegalArgumentException
	 */
	private Node<K, V> getNode(Node<K, V> node, K key) throws IllegalArgumentException{
		if(node == null || key.equals(node.getKey())) {
			return node;
		}
		int comp = key.compareTo(node.getKey());
		if(comp < 0) {
			return getNode(node.left, key);
		}
		else {
			return getNode(node.right, key);
		}
		
	}
	/**
	 * @return the value corresponding to the specified key
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public V get(K key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		Node<K, V> node = getNode(root, key);
		if(node != null) {
			return node.getValue();
		}
		return null;
	}
	
	/**
	 * 
	 * @return The number of (key, value) pairs in this DefaultMap
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * 
	 * @return true iff this.size() == 0 is true
	 */
	@Override
	public boolean isEmpty() {
		if(this.size == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the tree contains a node with this key
	 * @return true if the specified key is in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		return getNode(root, key) != null;
	}

	// Keys must be in ascending sorted order
	// You CANNOT use Collections.sort() or any other sorting implementations
	// You must do in order traversal of the trees.
	/**
	 * 
	 * @return an array containing the keys of this DefaultMap. If this DefaultMap is 
	 * empty, returns array of length zero. 
	 */
	@Override
	public List<K> keys() {
		List<K> keys = new ArrayList<>();
		inOrderTraversal(root, keys);
		return keys;
	}
	
	/**
	 * In order Traversal of BST
	 * @param node the starting node for traversal
	 * @param key the list to connect the keys
	 */
	private void inOrderTraversal(Node<K, V> node, List<K> key) { //inOrderTraversal from piazza??? nvm yay it works (from Question @406)
		if(node == null) {
			return;
		}
		else {
			inOrderTraversal(node.left, key);
			key.add(node.key);
			inOrderTraversal(node.right, key);
	}
}
	/**
	 * Node class
	 * @author harrisonle
	 *
	 * @param <K> The type of the keys of this BST. They need to be comparable by nature of the BST
	 * "K extends Comparable" means that BST will only compile with classes that implement Comparable
	 * @param <V> The type of the values of this BST. 
	 */
	private static class Node<K extends Comparable<? super K>, V> 
								implements DefaultMap.Entry<K, V> {
		/* 
		 * TODO: Add instance variables
		 */
		K key;
		V value;
		Node<K, V> left;
		Node<K, V> right;
		
		private Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
		}

		@Override
		public K getKey() {
			// TODO Auto-generated method stub
			return key;
		}

		@Override
		public V getValue() {
			// TODO Auto-generated method stub
			return value;
		}

		@Override
		public void setValue(V value) {
			// TODO Auto-generated method stub
			this.value = value;
			
		}
		
		
	}
	 
}