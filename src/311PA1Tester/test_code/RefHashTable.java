import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RefHashTable {
	private int tableSize = 0,a,b;
	private Object[] table;
	private HashSet<Integer> keys = new HashSet<Integer>();
	
	public RefHashTable(Integer tableSize) {
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
	
	public void add(RefTuple t) {
		int hashValue = hashValue(t.getKey());
		if (table[hashValue] == null) {
			ArrayList<RefTuple> list = new ArrayList<RefTuple>();
			list.add(t);
			table[hashValue] = list;
			if (t != null) {
				keys.add(t.getKey());
			}
		} else {
			ArrayList<RefTuple> list = (ArrayList<RefTuple>) table[hashValue];
			list.add(t);
		}
	}
	
	public ArrayList<RefTuple> get(Float value) {
		int hashValue = hashValue(value.intValue());
		return (ArrayList<RefTuple>) table[hashValue];
	}
	
	public ArrayList<RefTuple> get(Integer value) {
		int hashValue = hashValue(value);
		return (ArrayList<RefTuple>) table[hashValue];
	}
	
	public Set<Integer> getKeys() {
		return keys;
	}
}
