package proj1;

import java.util.Random;
/**
 * Hashfunction class as described in project specifications
 * @author michael
 *
 */
public class HashFunction {
	private int P;
	private int A,B;
	public HashFunction(int range) {
		P=this.findPrime(range);
		//Generate A & B
		Random gen=new Random();
		A=gen.nextInt(P);
		B=gen.nextInt(P);
	}
	/*
	 * Taken from Class
	 */
	private  boolean isPrime(int p) {
		double root=Math.sqrt(p);
		for(int i=2; i<=root; i++) {
			if (p%i==0) {
				return false;
			}
		}
		return true;
	}
	/*
	 * Not exactly the most simple but does the job
	 * Finds the smallest prime greater or equal to n
	 * 
	 */
	public Integer findPrime(int n) {
		Integer currVal=n;
		while(!isPrime(currVal)) {
			currVal++;
		}
		return currVal;
	}
	/*
	 * Getters and Setters
	 */
	public int getA() {
		return this.A;
	}
	
	public int getB() {
		return this.B;
	}
	
	public int getP() {
		return this.P;
	}
	
	public void setA(int x) {
		this.A=(x%P);
	}
	
	public void setB(int y) {
		this.B=(y%P);
	}
	/**
	 * Do you alter A & B also?
	 * @param x
	 */
	public void setP(int x) {
		this.P=this.findPrime(x);
		//Set A and B as posted on forum
		Random gen=new Random();
		A=gen.nextInt(P);
		B=gen.nextInt(P);
	}
	/**
	 * 
	 * @param x integer to hash
	 * @return the integer as determined by ax+b %p
	 */
	public int hash(int x) {
		int returnval=Math.abs((A*x + B)%P);
		return returnval;
	}
	
}
