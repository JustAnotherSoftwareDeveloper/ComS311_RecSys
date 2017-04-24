package proj1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * HashTable class as described in proj specifications
 * @author michael
 *
 */
public class HashTable {
	private HashFunction hashFunction;
	private LinkedList<Tuple>[] hashTable;
	private Integer countElements;
	/**
	 * 
	 * @param size Conforms to project description
	 */
	public HashTable(int size) {
		hashFunction=new HashFunction(size);
		//Make hashtable, size will be identical to p in hashfunction
		hashTable=new LinkedList[hashFunction.getP()];
		for(int i=0; i<hashFunction.getP(); i++) {
			hashTable[i]=new LinkedList<Tuple>(); //Initialize Each LinkedList
		}
		countElements=0;
		
		

	}
	/**
	 * O(size) complexity
	 * @return The largest LinkedList size 
	 */
	public int maxLoad() {
		int max=0;
		for(LinkedList<Tuple> l:hashTable) {
			if (l.size()>max) { max=l.size();}
		}
		return max;
	}
	/**
	 * The number of slots in the hash table.
	 */
	public int size() {
		return hashTable.length;
	}
	/**
	 * Simple getter 
	 * @return The number of elements in the hash table
	 */
	public int numElements() {
		return this.countElements;
	}
	/**
	 * Simple Getter
	 * @return
	 */
	public float loadFactor() {
		return this.countElements / (float) hashTable.length;
	}
	/*
	 * Just a for loop with O(n) complexity 
	 */
	public float averageLoad() {
		int cells=0,load=0;
		for (LinkedList<Tuple> l: hashTable) {
			if (!l.isEmpty()) {
				cells++;
				load+=l.size();
			}
		}
		return load / (float) cells;
	}
	
	public void add(Tuple t) {
		countElements++;
		if (this.loadFactor()>.7) {
			this.rehash();
		}
		int index=this.hashFunction.hash(t.getKey());
		this.hashTable[index].add(t);
	}
	/*
	 * Rehashes function
	 */
	private void rehash() {
		//Create New hash function and table
		this.hashFunction=new HashFunction(this.size()*2);
		LinkedList<Tuple>[] newTable=new LinkedList[this.hashFunction.getP()];
		for(int i=0; i<newTable.length; i++) {
			newTable[i]=new LinkedList<Tuple>();
		}
		//Remap all old tuples to new table
		for(LinkedList<Tuple> l: this.hashTable) {
			Iterator<Tuple> it=l.iterator();
			while(it.hasNext()) {
				Tuple t=it.next();
				int index=this.hashFunction.hash(t.getKey());
				newTable[index].add(t);
			}
		}
		//Set newtable to equal the hashtable
		this.hashTable=newTable;
	}
	/*
	 * 
	 */
	public ArrayList<Tuple> search(int k) {
		ArrayList<Tuple> elements=new ArrayList<>();
		//find index
		int index=this.hashFunction.hash(k);
		//iterate through index
		Iterator<Tuple> it=this.hashTable[index].iterator();
		while(it.hasNext()) {
			Tuple t=it.next();
			if (t.getKey()==k) {
				elements.add(t);
			}
		}
		return elements;
	}
	/*
	 * TODO ASK PROF about type
	 */
	public void remove(Tuple t) {
		int index=this.hashFunction.hash(t.getKey());
		Iterator<Tuple> it=this.hashTable[index].iterator();
		while(it.hasNext()) {
			Tuple tup=it.next();
			if (t.equals(tup)) {
				it.remove();
				this.countElements--;
				return;
			}
		}
	}
	
	protected HashFunction getHashFunction() {
		return this.hashFunction;
	}
}
