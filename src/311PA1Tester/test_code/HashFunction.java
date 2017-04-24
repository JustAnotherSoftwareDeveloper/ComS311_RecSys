/**
 * @author Sriram Balasubramanian
 */
public class HashFunction {
	private int a,b,p;
	
	public HashFunction(int range) {
		if (range <= 7) {
			p = 7;
		} else if (range <= 11) {
			p = 11;
		} else if (range <= 23 ) {
			p = 23;
		} else if (range <= 50) {
			p = 53;
		} else {
			p = 100003;
		}
		a = 4;
		b = 5;
	}
	
	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}
	
	public int hash(int x) {
		return (a*x + b)%p;
	}
}
