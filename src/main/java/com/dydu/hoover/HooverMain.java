package com.dydu.hoover;

import com.dydu.hoover.core.Executor;

public class HooverMain {
	
	public static void main(String[] args) {
		Executor executor = new Executor();
		int[] coordinate = {4,3};
		executor.run(coordinate);
		
	}

}
