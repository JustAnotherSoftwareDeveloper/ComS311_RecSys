package proj1;


public class TestHashFunction {
	
	public static final String NOT_A_PRIME = "HashFunction did not set size to a prime for input size ";
	public static final String EXCEPTION = "Exception when checking for hash function. Input - ";
	public static final String DEDUCT_FIVE = "(-5 points). ";
	static StringBuilder comments = new StringBuilder("");
	static int points = 0;
	
	private static void testIfSizeIsPrime() {
		try {
			checkPrimeGenerationForSize(10);
			checkPrimeGenerationForSize(15);
			checkPrimeGenerationForSize(17);
			checkPrimeGenerationForSize(2);
			checkPrimeGenerationForSize(100000);
		} catch (Exception e) {
			System.out.println(0);
			System.out.println("Exception while testing hash function(-25 points)");
		}
		System.out.println(points);
		System.out.println(comments);
	}

	private static void checkPrimeGenerationForSize(int testInput) {
		try {
			HashFunction hashFunction = new HashFunction(testInput);
			if(isPrime(hashFunction.getP())) {
				points += 5;
			} else {
				comments.append(NOT_A_PRIME + testInput + DEDUCT_FIVE);
			}
		} catch (Exception e) {
			comments.append(EXCEPTION + testInput + ".Exception - " + e.getClass().getCanonicalName() + DEDUCT_FIVE);
		}
	}

	private static boolean isPrime(int p) {
		if (p <= 1) {
			return false;
		} else if (p == 2 || p == 3) {
			return true;
		} else {
			int limit = (int) Math.sqrt(p);
			for (int i = 2; i <= limit + 1; i++) {
				if ((p % i) == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		TestHashFunction.testIfSizeIsPrime();
	}
}

