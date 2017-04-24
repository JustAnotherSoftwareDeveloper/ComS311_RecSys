package proj1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class NearestPoints {
	ArrayList<Float> pointSet; // using object wrapper
	HashTable pointsTable;

	/**
	 * Reads in a data file and puts it in a data structure. Sets pointsTable to
	 * null;
	 * 
	 * @param dataFile
	 */
	public NearestPoints(String dataFile) {
		pointSet = new ArrayList<>();
		Scanner readFile=null;
		try {
			readFile = new Scanner(new File(dataFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (readFile.hasNextFloat()) {
			pointSet.add(readFile.nextFloat());
		}
		readFile.close();
		pointsTable = null;
	}

	/**
	 * Simply sets the pointset data structure
	 * 
	 * @param pointSet
	 */
	public NearestPoints(ArrayList<Float> pointSet) {
		// I'm assuming you're not being a **** and adding a null value
		// someonewhere in the set
		this.pointSet = pointSet;
		this.pointsTable = null;
	}

	/**
	 * Simply goes through every point and adds them if they're near Assuming no
	 * duplicate points
	 * 
	 * @param p
	 *            a float point
	 * @return an arrayList of points that are "near" to the p
	 */
	public ArrayList<Float> naiveNearestPoints(float p) {
		ArrayList<Float> near = new ArrayList<>();
		int pfloor = (int) Math.floor(p);
		for (Float point : pointSet) {
			int pointFloor=(int) Math.floor(point);
			float compare = Math.abs(p - point);
			if (compare <= 1) { // We don't want identical
												// points being added. Then
												// it'll add itself
				near.add(point);
			}
		}
		return near;
	}
	/**
	 * Builds the HashTable. MUST BE CALLED BEFORE ANY OF THE 
	 * HASH FUNCTIONS ARE USED. 
	 */
	public void buildDataStructure() {
		int tableSize= (int) (pointSet.size()*.85); //Setting it at the exact size of pointSet is sorta pointless, No?
		this.pointsTable=new HashTable(tableSize);
		for(Float p: pointSet) {
			int pKey=((int) Math.floor(p)); //Why does a floor function return a double???????
			pointsTable.add(new Tuple(pKey,p));
		}
		
	}
	/**
	 * This uses the hash table to get the nearest points. The basic logic is that it gets the values that correspond to key floor(p),floor(p+1),floor(p-1)
	 * in HashTable then checks them to make sure they're actually close
	 * @param p
	 * @return an <code>ArrayList<Float></code> of points near to p
	 */
	public ArrayList<Float> npHashNearestPoints(float p) {
		ArrayList<Float> nearPoints=new ArrayList<>();
		ArrayList<Tuple> possibleHits=new ArrayList<>();
		int pKey=(int) Math.floor(p);
		possibleHits.addAll(this.pointsTable.search(pKey));
		possibleHits.addAll(this.pointsTable.search(pKey-1));
		possibleHits.addAll(this.pointsTable.search(pKey+1));
		//Now we have to go through the possible hits and find the actual hits
		for(Tuple t: possibleHits) {
			if (Math.abs(p-t.getValue())<=1) {
				nearPoints.add(t.getValue());
			}
		}
		return nearPoints;
	}

	/**
	 * Simply <code>naiveNearestPoints(float p)</code> run in a loop
	 */
	public void allNearestPointsNaive() {
		ArrayList<ArrayList<Float>> pointsOnPoints = new ArrayList<>();
		// Get Point Sets
		for (Float p : this.pointSet) {
			ArrayList<Float> points = this.naiveNearestPoints(p);
			pointsOnPoints.add(points);

		}
		this.writeToFile("NaiveSolution.txt", pointsOnPoints);
	}
	/**
	 * This is simply a loop that uses <code>npHashNearestPoints(float p)</code> for
	 * all points in PointSet, then writes it to a file
	 * @return
	 */
	public void allNearestPointsHash() {
		ArrayList<ArrayList<Float>> pointsOnPoints = new ArrayList<>();
		// Get Point Sets
		for (Float p : this.pointSet) {
			ArrayList<Float> points = this.npHashNearestPoints(p);
			pointsOnPoints.add(points);

		}
		this.writeToFile("HashSolution.txt", pointsOnPoints);
	}
	/*
	 * The whole write to file thing leaves a lot of questions about formatting
	 */
	private void writeToFile(String name, ArrayList<ArrayList<Float>> pointsOnPoints) {
		PrintWriter pointWriter = null;
		try {
			pointWriter = new PrintWriter(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		// This is some complex BS that basically negates every thing we did.
		String eol = System.getProperty("line.separator");
		for (ArrayList<Float> points : pointsOnPoints) {
			//
			for (int i = 0; i < points.size() ; i++) {
				pointWriter.print(points.get(i) + " ");
			}
			pointWriter.print(eol);
		}
		pointWriter.close();
	}
}
