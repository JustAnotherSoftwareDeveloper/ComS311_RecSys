import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HashTable {
	private int tableSize = 0,a,b;
	private Object[] table;
	private HashSet<Integer> keys = new HashSet<Integer>();
	
	public HashTable(Integer tableSize) {
		this.tableSize = new BigInteger(tableSize.toString()).nextProbablePrime().intValue();
		table = new Object[this.tableSize];
		for(int i=0; i<this.tableSize; i++) {
			table[i] = null;
		}
		Random random = new Random();
		a = random.nextInt(tableSize) + 1;
		b = random.nextInt(tableSize) + 1;
	}
	
	public int hashValue(int value) {
		return Math.abs((a*value + b) % tableSize);
	}
	
	public void add(Tuple t) {
		
	}
	
	public void add(Float value) {
		int hashValue = hashValue(value.intValue());
		if (table[hashValue] == null) {
			ArrayList<Float> list = new ArrayList<Float>();
			list.add(value);
			table[hashValue] = list;
			if (value != null) {
				keys.add(value.intValue());
			}
		} else {
			ArrayList<Float> list = (ArrayList<Float>) table[hashValue];
			list.add(value);
		}
	}
	
	public ArrayList<Float> get(Float value) {
		int hashValue = hashValue(value.intValue());
		return (ArrayList<Float>) table[hashValue];
	}
	
	public ArrayList<Float> get(Integer value) {
		int hashValue = hashValue(value);
		return (ArrayList<Float>) table[hashValue];
	}
	
	public Set<Integer> getKeys() {
		return keys;
	}
	
	public int maxLoad() {
		return 0;
	}
	
	public int averageLoad() {
		return 0;
	}
	
	public int size() {
		return tableSize;
	}
	
	public int numElements() {
		return 0;
	}
	
	public float loadFactor() {
		return 0.0f;
	}
	
	public ArrayList<Tuple> search(float x) {
		return null;
	}
}
