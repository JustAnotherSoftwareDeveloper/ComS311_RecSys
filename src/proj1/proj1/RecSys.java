package proj1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RecSys {
	private HashMap<Float,Integer> pointsToUser; //Prof said I could for recsys
	private float[] userPoints; //I chose to divide up the matrix given into two. Come at me bro
	private int[][] ratingsMatrix;
	private NearestPoints userNearPoints;
	
	public RecSys(String mrMatrix) { //I'm assuming the string is correctly formatted;
		Scanner readFile=null;
		try {
			readFile = new Scanner(new File(mrMatrix));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int numUsers=readFile.nextInt();
		int numMovies=readFile.nextInt();
		ArrayList<Float> pointSet=new ArrayList<>();
		pointsToUser=new HashMap<>();
		userPoints=new float[numUsers];
		ratingsMatrix=new int[numUsers][numMovies];
		for(int i=0; i<numUsers; i++) {
			userPoints[i]=readFile.nextFloat();
			pointSet.add(userPoints[i]); //Adding for nearestPoints
			pointsToUser.put(userPoints[i], i); //Adding for lookup
			for(int j=1;j<=numMovies; j++) {
				ratingsMatrix[i][j-1]=readFile.nextInt();
			}
		}
		readFile.close();
		//Construct nearPoints
		userNearPoints=new NearestPoints(pointSet);
		userNearPoints.buildDataStructure();
	}
	/**
	 * 
	 * @param u
	 * @param m
	 * @return
	 */
	public float ratingOf(int u,int m) {
		// B.C UserId and Movie Id both start at 1
		int realUserId=u-1;
		int realMovieId=m-1;
		if (ratingsMatrix[realUserId][realMovieId]!=0) {
			return (float) ratingsMatrix[realUserId][realMovieId];
		}
		else { //Average Recommendation  
			float userPoint=userPoints[realUserId];
			ArrayList<Float> nearPoints=userNearPoints.npHashNearestPoints(userPoint);
			float totalRating=0;
			int actualsize=0; //Because 0 0 0 0 5 needs to average out to 1
			for(Float p: nearPoints) {
				int userId=pointsToUser.get(p); //Because we need the userId to get the movie
				int rating=ratingsMatrix[userId][realMovieId];
				totalRating+=rating;
				if (rating!=0) { actualsize++;}
			}
			if (actualsize==0) { return 0;}
			return (totalRating/ (float) actualsize);
			
		}
	}
}
