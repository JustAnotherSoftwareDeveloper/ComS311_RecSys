import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TestEverythingElse {

	static StringBuilder comments = new StringBuilder("");
	static int points = 0;
	public String name;
	
	public static final String WORKING_DIRECTORY = "C:\\Users\\Sriram\\Desktop\\311PA1Tester\\";
	
	public static final int TIMEOUT_MINUTES = 10;

	public static final String INVALID_SEARCH = "HashTable returned incorrect value for search key ";
	public static final String INCORRECT_MAX_LOAD = "HashTable returned incorrect value for max load ";
	public static final String INCORRECT_AVG_LOAD = "HashTable returned incorrect value for average load ";
	public static final String INCORRECT_SIZE = "HashTable returned incorrect value for size ";
	public static final String INCORRECT_NUM_ELEM = "HashTable returned incorrect value for numElements ";
	public static final String POST_RESIZE = "post resize ";
	public static final String INCORRECT_REC_SYS_RESULT = "RecSys returned incorrect rating for ";
	public static final String INCORRECT_NAIVE_NEAREST_POINTS = "Naive nearest points returned incorrect value for ";
	public static final String INCORRECT_HASH_NEAREST_POINTS = "Hash nearest points returned incorrect value for ";

	public static final String DEDUCT_TWENTY_FIVE = " (-25 points). ";
	public static final String DEDUCT_TWENTY = " (-20 points). ";
	public static final String DEDUCT_FIFTEEN = " (-15 points). ";
	public static final String DEDUCT_TEN = " (-10 points). ";
	public static final String DEDUCT_FIVE = " (-5 points). ";

	public static final String REC_INPUT_TAB_FILENAME = WORKING_DIRECTORY + "rec_input_tabspace.txt";
	public static final String REC_INPUT_SPACE_FILENAME = WORKING_DIRECTORY + "rec_input_white_space.txt";
	
	public static final String NEAREST_NEIGHBORS_TEST_FILE = WORKING_DIRECTORY + "test_inputs.txt";
	
	public static final String RUNTIMES_FILE = WORKING_DIRECTORY + "runtimes.csv";
	public static final String FINAL_OUTPUT = WORKING_DIRECTORY + "points_and_comments.csv";

	public static final float[] hashTableTestInput = new float[] { 2.5f, 13.7f, 24.4f, 4.6f, 15.7f, 27.7f };
	public static final float[] hashTableResizedTestInput = new float[] { 2.5f, 13.7f, 24.4f, 4.6f, 15.7f, 27.7f, 1.1f,
			5.9f, 10.6f };
	
	public static final float[] nearestPointsInput = new float[] { 0.2f, 0.5f, 0.7f, 0.8f, 1.1f, 1.4f, 2.1f,
			2.2f, 2.5f, 2.7f, 4.5f, 5.6f, 6.3f, 58.4f, 111.8f };
	public static ArrayList<Float> nearestPointsInputArray = new ArrayList<Float>();
	
	public TestEverythingElse(String name) throws Exception {
		this.name = name;
		BufferedReader reader = new BufferedReader(new FileReader("test_hash_function_output.txt"));
		String line = null;
		if ((line = reader.readLine()) != null) {
			points = Integer.parseInt(line);
		} else {
			reader.close();
			throw new Exception("Hash function not tested");
		}
		if ((line = reader.readLine()) != null) {
			comments.append(line);
		}
		reader.close();
		for(Float value:nearestPointsInput) {
			nearestPointsInputArray.add(value);
		}
	}

	public void testHashTable() {
		HashTable hashTable = null;
		boolean error = false;
		try {
			hashTable = new HashTable(11);
			for (Float value : hashTableTestInput) {
				hashTable.add(new Tuple(value.intValue(), value));
			}
		} catch (Exception e) {
			comments.append("Exception when creating hash table. Could not test hash table. Exception - " + e.getClass().getCanonicalName() + "(- 75 points)");
			error = true;
		}
		if (error) {
			return;
		}
		checkSearchResult(hashTable, 2, 5, true);
		checkSearchResult(hashTable, 13, 5, true);
		checkSearchResult(hashTable, 35, 5, false);
		checkSearchResult(hashTable, 37, 5, false);
		checkSearchResult(hashTable, 7, 5, false);
		try {
			if (hashTable.maxLoad() != 3) {
				comments.append(INCORRECT_MAX_LOAD + DEDUCT_FIFTEEN);
			} else {
				points += 15;
			}
		} catch (Exception e) {
			comments.append("Exception when testing max load " + ". Exception - " + e.getClass().getCanonicalName() + DEDUCT_FIFTEEN);
		}
		try {
			if (hashTable.averageLoad() != 2.0f) {
				comments.append(INCORRECT_AVG_LOAD + DEDUCT_FIFTEEN);
			} else {
				points += 15;
			}
		} catch (Exception e) {
			comments.append("Exception when testing avg load " + ". Exception - " + e.getClass().getCanonicalName() + DEDUCT_FIFTEEN);
		}
		try {
			if (hashTable.size() != 11) {
				comments.append(INCORRECT_SIZE + DEDUCT_TEN);
			} else {
				points += 10;
			}
		} catch (Exception e) {
			comments.append("Exception when testing hash table size " + ". Exception - " + e.getClass().getCanonicalName() + DEDUCT_TEN);
		}
		try {
			if (hashTable.numElements() != 6) {
				comments.append(INCORRECT_SIZE + DEDUCT_TEN);
			} else {
				points += 10;
			}
		} catch (Exception e) {
			comments.append("Exception when testing hash table num of elements " + ". Exception - " + e.getClass().getCanonicalName() + DEDUCT_TEN);
		}
	}

	public void testHashTablePostResize() {
		HashTable hashTable = null;
		boolean error = false;
		try {
			hashTable = new HashTable(11);
			for (Float value : hashTableResizedTestInput) {
				hashTable.add(new Tuple(value.intValue(), value));
			}
		} catch (Exception e) {
			comments.append("Exception when creating hash table. Could not test hash table resizing. Exception - " + e.getClass().getCanonicalName() + "(- 100 points)");
			error = false;
		}
		if (error) {
			return;
		}
		checkSearchResult(hashTable, 2, 4, true);
		checkSearchResult(hashTable, 13, 4, true);
		checkSearchResult(hashTable, 35, 4, false);
		checkSearchResult(hashTable, 37, 4, false);
		checkSearchResult(hashTable, 7, 4, false);
		try {
			if (!(hashTable.maxLoad() < 3)) {
				comments.append(INCORRECT_MAX_LOAD + POST_RESIZE + DEDUCT_TWENTY_FIVE);
			} else {
				points += 25;
			}
		} catch (Exception e) {
			comments.append("Exception when testing max load " + POST_RESIZE + ". Exception - " + e.getClass().getCanonicalName() + DEDUCT_TWENTY_FIVE);
		}
		try {
			if (!(hashTable.averageLoad() < 2.5f)) {
				comments.append(INCORRECT_AVG_LOAD + POST_RESIZE + DEDUCT_TWENTY_FIVE);
			} else {
				points += 25;
			}
		} catch (Exception e) {
			comments.append("Exception when testing avg load " + POST_RESIZE + ". Exception - " + e.getClass().getCanonicalName() + DEDUCT_TWENTY_FIVE);
		}
		try {
			if (hashTable.size() != 23) {
				comments.append(INCORRECT_SIZE + POST_RESIZE + DEDUCT_TWENTY);
			} else {
				points += 20;
			}
		} catch (Exception e) {
			comments.append("Exception when testing hash table size " + POST_RESIZE + ". Exception - " + e.getClass().getCanonicalName() + DEDUCT_TWENTY);
		}
		try {
			if (hashTable.numElements() != 9) {
				comments.append(INCORRECT_NUM_ELEM + POST_RESIZE + DEDUCT_TEN);
			} else {
				points += 10;
			}
		} catch (Exception e) {
			comments.append("Exception when testing hash table num of elements " + POST_RESIZE + ". Exception - " + e.getClass().getCanonicalName() + DEDUCT_TEN);
		}
	}

	private void checkSearchResult(HashTable hashTable, int testInput, int point, boolean mustReturnAnswer) {
		if (mustReturnAnswer) {
			try {
				checkValidSearchResult(hashTable, testInput, point);
			} catch (Exception e) {
				comments.append("Exception when check hastable search for input - " + testInput + ".Exception - " + e.getClass().getCanonicalName() + "( -" + point + " points).");
			}
		} else {
			try {
				checkInvalidSearchResult(hashTable, testInput, point);
			} catch (Exception e) {
				comments.append("Exception when check hastable search for input - " + testInput + ".Exception - " + e.getClass().getCanonicalName() + "( -" + point + " points).");
			}
		}

	}

	private void checkInvalidSearchResult(HashTable hashTable, int testInput, int point) throws Exception {
		ArrayList<Tuple> searchResult = hashTable.search(testInput);
		if (searchResult == null || searchResult.size() < 1) {
			points += point;
		} else {
			comments.append(INVALID_SEARCH + testInput + "( -" + point + " points).");
			}
		}

	private void checkValidSearchResult(HashTable hashTable, int testInput, int point) throws Exception {
		ArrayList<Tuple> searchResult = hashTable.search(testInput);
		if (searchResult == null || searchResult.size() < 1) {
			comments.append(INVALID_SEARCH + testInput + "( -" + point + " points).");
		} else {
			boolean invalidAnswer = false;
			for (Tuple item : searchResult) {
				if (item.getKey() != testInput) {
					invalidAnswer = true;
					break;
				}
			}
			if (!invalidAnswer) {
				points += point;
			} else {
				comments.append(INVALID_SEARCH + testInput + "( -" + point + " points).");
			}
		}
	}
	
	public void testNearestPoints() {
		NearestPoints nearestPoints = null;
		boolean error = false;
		try {
			nearestPoints = new NearestPoints(nearestPointsInputArray);
			nearestPoints.buildDataStructure();
		} catch (Exception e) {
			comments.append("Exception when creating NearestPoints object. Could not test section.(-175 points) ");
			error = true;
		}
		if (error) {
			return;
		}
		checkNearestPoint(nearestPoints, 1.4f, new Float[] {0.5f, 0.7f, 0.8f, 1.1f, 2.1f, 2.2f});
		checkNearestPoint(nearestPoints, 2.5f, new Float[] {2.7f, 2.2f, 2.1f});
		checkNearestPoint(nearestPoints, 0.2f, new Float[] {0.5f, 0.7f, 0.8f, 1.1f});
		checkNearestPoint(nearestPoints, 4.5f, new Float[] {});
		checkNearestPoint(nearestPoints, 5.6f, new Float[] {6.3f});
	}
	
	private void verifyNPOutput(HashSet<Float> results, Float input, Float[] expectedOutput, String error, int point) {
		if (results == null || results.size() < 1) {
			if (expectedOutput.length == 0) {
				points += point;
			} else {
				comments.append(error);
			}
			return;
		} else {
			// remove input if its in the results
			results.remove(input);
			for(Float output : expectedOutput) {
				if (results.contains(output)) {
					results.remove(output);
				} else {
					comments.append(error);
					return;
				}
			}
			if (results.size() < 1) {
				points += point;
			} else {
				comments.append(error);
			}
		}
	}
	
	private void checkNearestPoint(NearestPoints nearestPoints, Float input, Float[] expectedOutput) {
		HashSet<Float> results = null;
		try {
			results = new HashSet<Float> (nearestPoints.naiveNearestPoints(input));
			verifyNPOutput(results,input, expectedOutput, INCORRECT_NAIVE_NEAREST_POINTS + input + DEDUCT_FIFTEEN, 15);
		} catch (Exception e) {
			comments.append("Exception in NearestPoints.naiveNearestPoints() for input - " + input + " .Exception - " + e.getClass().getCanonicalName() + DEDUCT_FIFTEEN);
		}
		try {
			results = new HashSet<Float> (nearestPoints.npHashNearestPoints(input));
			verifyNPOutput(results,input, expectedOutput, INCORRECT_HASH_NEAREST_POINTS + input + DEDUCT_FIFTEEN, 20);
		} catch (Exception e) {
			comments.append("Exception in NearestPoints.npHashNearestPoints() for input - " + input + " .Exception - " + e.getClass().getCanonicalName() + DEDUCT_FIFTEEN);
		}
	}
	
	public void testPerformance()  throws Exception{
		System.gc();
		StringBuilder performanceResults = null;
		try {
			ArrayList<Float> points = getPointsFromFile(NEAREST_NEIGHBORS_TEST_FILE);
			RefNearestPoints  nearestPointsRef = new RefNearestPoints(points);
			NearestPoints  nearestPoints = new NearestPoints(points);
			nearestPoints.buildDataStructure();
			long runtimeNaiveRef = 0,runtimeHashRef = 0, runtimeNaive = 0, runtimeHash = 0;
			ArrayList<Float> results1 = new ArrayList<Float>();
			for (Float p : points) {
				long startTime = System.currentTimeMillis();
				ArrayList<Float> temp = nearestPointsRef.naiveNearestPoints(p);
				runtimeNaiveRef += (System.currentTimeMillis() - startTime);
				results1.addAll(temp);
			}
			ArrayList<Float> results2 = new ArrayList<Float>();
			for (Float p : points) {
				long startTime = System.currentTimeMillis();
				ArrayList<Float> temp = nearestPointsRef.npHashNearestPoints(p);
				runtimeHashRef += (System.currentTimeMillis() - startTime);
				results2.addAll(temp);
			}
			ArrayList<Float> results3 = new ArrayList<Float>();
			for (Float p : points) {
				long startTime = System.currentTimeMillis();
				ArrayList<Float> temp = nearestPoints.naiveNearestPoints(p);
				runtimeNaive += (System.currentTimeMillis() - startTime);
				results3.addAll(temp);
			}
			ArrayList<Float> results4 = new ArrayList<Float>();
			for (Float p : points) {
				long startTime = System.currentTimeMillis();
				ArrayList<Float> temp = nearestPoints.npHashNearestPoints(p);
				runtimeHash += (System.currentTimeMillis() - startTime);
				results4.addAll(temp);
			}
			performanceResults = new StringBuilder(name + "," + runtimeNaiveRef + "," + runtimeNaive + "," + runtimeHashRef + "," + runtimeHash);
		} catch (Exception e) {
			performanceResults = new StringBuilder(name + "," + "Exception testing run time - " + e.getClass().getCanonicalName());
		}
		appendToFile(performanceResults.toString(),RUNTIMES_FILE);
	}

	public void testRecSys() {
		RecSys rec = null;
		boolean error = false;
		try {
			rec = new RecSys(REC_INPUT_SPACE_FILENAME);
		} catch (Exception e) {
			error = true;
			comments.append("Exception creating RecSys. Exception - " + e.getClass().getCanonicalName() + "(-75 points)");
		}
		if (error) {
			return;
		}
		checkRecSysResult(rec, 3, 2, 1.0f);
		checkRecSysResult(rec, 3, 3, 2.5f);
		checkRecSysResult(rec, 2, 5, 3.5f);
		checkRecSysResult(rec, 4, 3, 1.0f);
		checkRecSysResult(rec, 5, 4, 1.0f);
	}

	private void checkRecSysResult(RecSys rec, int user, int movie, Float expectedValue) {
		try {
			Float rating = rec.ratingOf(user, movie);
			if (!(rating.equals(expectedValue))) {
				comments.append(INCORRECT_REC_SYS_RESULT + "user - " + user + ", movie - " + movie + DEDUCT_FIFTEEN);
			} else {
				points += 15;
			}
		} catch (Exception e) {
			comments.append("Exception testing RecSys.ratingOf(). Exception - " + e.getClass().getCanonicalName() + DEDUCT_FIFTEEN);
		}
	}
	
	private ArrayList<Float> getPointsFromFile(String fileName) {
		ArrayList<Float> points = new ArrayList<Float>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String line = null;
			while (reader.ready() && (line = reader.readLine()) != null) {
				Float value = Float.parseFloat(line);
				points.add(value);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return points;
	}
	
	public static void appendToFile(String content, String filePath) {
		try {
			FileWriter csvWriter = new FileWriter(filePath, true);
			csvWriter.append(content + System.lineSeparator());
			csvWriter.flush();
			csvWriter.close();
			System.out.println("Results added to file");
		} catch (Exception e){
			System.out.println("Error writing to file. Write it yourself!");
			System.out.println("Result : " + content);
		} 
	}

	public void writeTestResults() {
		appendToFile(name + "," + points + "," + comments.toString(),FINAL_OUTPUT);
		System.out.println("Total Points : " + points);
		System.out.println("Comments : " + comments.toString());
	}
	
	public void doTest() {
		try {
			final ExecutorService service = Executors.newSingleThreadExecutor();
			final Future<?> f = service.submit(new TimedBoundTestingService());
			f.get(TIMEOUT_MINUTES, TimeUnit.MINUTES);
			service.shutdown();
		} catch(Exception e) {
			TestEverythingElse.appendToFile(name + ", -----------------Timed Out-----------------", FINAL_OUTPUT);
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		try {
			TestEverythingElse tester = new TestEverythingElse(args[0]);
			tester.doTest();
		} catch (Exception e) {
			TestEverythingElse.appendToFile(args[0] + ", -----------------Error-----------------", FINAL_OUTPUT);
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	class TimedBoundTestingService implements Runnable {
		@Override
		public void run() {
			try {
				testHashTable();
				testHashTablePostResize();
				testNearestPoints();
				testPerformance();;
				testRecSys();
				writeTestResults();
			} catch (Exception e) {
				TestEverythingElse.appendToFile(name + ", -----------------Error-----------------", FINAL_OUTPUT);
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		
	}
}
