import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RefNearestPoints {
	public ArrayList<Float> points;
	RefHashTable hashTable;
	
	public RefNearestPoints(ArrayList<Float> points) {
		this.points = points;
		hashTable = new RefHashTable(points.size());
		for (Float point : points) {
			hashTable.add(new RefTuple(point.intValue(), point));
		}
	}
	
	public RefNearestPoints(String inputFileName) {
		points = getPointsFromFile(inputFileName);
		hashTable = new RefHashTable(points.size());
		for (Float point : points) {
			hashTable.add(new RefTuple(point.intValue(), point));
		}
	}
	
	public static ArrayList<Float> getPointsFromFile(String fileName) {
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
	
	public ArrayList<Float> naiveNearestPoints(float p) {
		ArrayList<Float> neighbors = new ArrayList<Float>();
		for(Float point : points) {
			if (Math.abs(p - point) <= 1) {
				neighbors.add(point);
			}
		}
		return neighbors;
	}
	
	public ArrayList<Float> npHashNearestPoints(float p) {
		ArrayList<Float> neighbors = new ArrayList<Float>();
		ArrayList<RefTuple> current, previous, next;
		current = hashTable.get(p);
		previous = hashTable.get(p - 1);
		next = hashTable.get(p + 1);
		for (RefTuple tuple : current) {
			if (Math.abs(p - tuple.getValue()) <= 1) {
				neighbors.add(tuple.getValue());
			}
		}
		if (previous != null) {
			for (RefTuple tuple : previous) {
				if (Math.abs(p - tuple.getValue()) <= 1) {
					neighbors.add(tuple.getValue());
				}
			}
		}
		if (next != null) {
			for (RefTuple tuple : next) {
				if (Math.abs(p - tuple.getValue()) <= 1) {
					neighbors.add(tuple.getValue());
				}
			}
		}
		return neighbors;
	}
	
	public static void main(String[] args) {
		RefNearestPoints  nearestPoints = new RefNearestPoints("C:\\Users\\Sriram\\workspace\\311PA1\\test_inputs.txt");
		long runtimeNaive = 0;
		ArrayList<Float> results1 = new ArrayList<Float>();
		for (Float p : nearestPoints.points) {
			long startTime = System.currentTimeMillis();
			ArrayList<Float> temp = nearestPoints.naiveNearestPoints(p);
			runtimeNaive += (System.currentTimeMillis() - startTime);
			results1.addAll(temp);
		}
		long runtimeHash = 0;
		ArrayList<Float> results2 = new ArrayList<Float>();
		for (Float p : nearestPoints.points) {
			long startTime = System.currentTimeMillis();
			ArrayList<Float> temp = nearestPoints.naiveNearestPoints(p);
			runtimeHash += (System.currentTimeMillis() - startTime);
			results2.addAll(temp);
		}
		System.out.println("Naive - " + results1.size() + ". Time - " + runtimeNaive);
		System.out.println("Hash - " + results2.size() + ". Time - " + runtimeHash);
	}
}
