package com.dydu.hoover.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * @comment Test for executor class
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project SUJET HOOVER
 * @class ExecutorTest
 * @date Feb 23, 2020
 */
public final class ExecutorTest {

	@Test
	public void runHoover1() {
		int[] coordinate = { 1, 1 };
		String fileRead = "/hoover1.txt";

		Executor executor = new Executor();

		Assert.assertEquals(executor.run(coordinate, fileRead), true);
	}

	@Test
	public void runHoover1Error() {
		int[] coordinate = { 1, 0 };
		String fileRead = "/hoover1.txt";

		Executor executor = new Executor();

		Assert.assertEquals(executor.run(coordinate, fileRead), false);
	}

	@Test
	public void runMaze1() {
		int[] coordinate = { 1, 1 };
		String fileRead = "/maze1.txt";

		Executor executor = new Executor();

		Assert.assertEquals(executor.run(coordinate, fileRead), true);
	}

	@Test
	public void runMaze1Error() {
		int[] coordinate = { 0, 0 };
		String fileRead = "/maze1.txt";

		Executor executor = new Executor();

		Assert.assertEquals(executor.run(coordinate, fileRead), false);
	}

	@Test
	public void runRoomOne() {
		int[] coordinate = { 4, 3 };
		String fileRead = "/room.txt";

		Executor executor = new Executor();

		Assert.assertEquals(executor.run(coordinate, fileRead), true);
	}

	@Test
	public void runRoomOneError() {
		int[] coordinate = { 1, 1 };
		String fileRead = "/room1.txt";

		Executor executor = new Executor();

		Assert.assertEquals(executor.run(coordinate, fileRead), false);
	}

	@Test
	public void runRoomTwo() {
		int[] coordinate = { 5, 2 };
		String fileRead = "/room2.txt";

		Executor executor = new Executor();

		Assert.assertEquals(executor.run(coordinate, fileRead), true);
	}

	@Test
	public void runRoomTwoError() {
		int[] coordinate = { 1, 2 };
		String fileRead = "/room2.txt";

		Executor executor = new Executor();

		Assert.assertEquals(executor.run(coordinate, fileRead), false);
	}

}
