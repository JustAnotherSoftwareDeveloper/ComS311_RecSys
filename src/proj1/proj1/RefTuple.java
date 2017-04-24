package proj1;

public class RefTuple {
	private int key;
	private Float value;

	public RefTuple(int left, Float right) {
		this.key = left;
		this.value = right;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

}
