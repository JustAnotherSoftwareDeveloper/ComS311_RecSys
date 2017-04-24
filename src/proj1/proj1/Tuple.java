package proj1;

/**
 * 
 * @author michael
 * Class to represent the Tuple class. Pretty Simple
 */
public class Tuple {
	//Always use immutable when possible
	private final int key;
	private final float value;
	
	public Tuple(int keyP,float valueP) {
		key=keyP;
		value=valueP;
	}
	/**
	 * simple getter
	 * @return Integer Key
	 */
	public int getKey() {
		return this.key;
	}
	/**
	 * simple getter
	 * @return
	 */
	public float getValue() {
		return this.value;
	}
	/**
	 * equals() method, takes in a tuple so NOT OVERRIDDEN
	 * @param t tuple to take in 
	 * @return True if key and value are identical, false otherwise
	 */
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0.getClass().equals(Tuple.class))) {
			return false;
		}
		Tuple t=(Tuple)arg0;
		return ((this.key==t.key)&&(this.value==t.value));
	}
}
