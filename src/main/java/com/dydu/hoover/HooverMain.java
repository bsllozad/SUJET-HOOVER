package com.dydu.hoover;

import com.dydu.hoover.core.Executor;

public class HooverMain {
	
	public static void main(String[] args) {
		Executor executor = new Executor();
		int[] coordinate = {4,3};
		String fileRead = "/room.txt";
		executor.run(coordinate, fileRead);
		
		
		int[] coordinate2 = {5,2};
		String fileRead2 = "/room2.txt";
		Executor executor2 = new Executor();
		executor2.run(coordinate2, fileRead2);
		
		int[] coordinate3 = {1,1};
		String fileRead3 = "/hoover1.txt";
		Executor executor3 = new Executor();
		executor3.run(coordinate3, fileRead3);
		
		int[] coordinate4 = {1,1};
		String fileRead4 = "/maze1.txt";
		Executor executor4 = new Executor();
		executor4.run(coordinate4, fileRead4);
		
	}

}
