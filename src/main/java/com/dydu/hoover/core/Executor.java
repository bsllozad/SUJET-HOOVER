package com.dydu.hoover.core;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dydu.hoover.utils.MatrixFileReader;
import com.dydu.hoover.utils.exceptions.HooverException;
import com.dydu.hoover.utils.exceptions.ValidateException;

/**
 * @comment Executor class has a main code for execute the Vacumm CLeaner. 
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project SUJET HOOVER
 * @class Executor
 * @date Feb 16, 2020
 *
 */
public class Executor {
	
	private static final Logger LOG = LoggerFactory.getLogger(Executor.class);
	
	private String fileRead = null;
	private final String WALL_CHAR = "M";
	private final String FREE_CHAR = " ";
	private final String SEPARATOR = " - ";
	
	private String[][] room = null;
	private int countSteps = 0;
	private int countFullRoom = 0;
	private int direction = 1;
	private boolean execute = true;
	
	private int[] coordinate = null;
	
	public boolean run(int[] coordinate, String fileRead) {
		try {
			this.fileRead = fileRead;
			this.coordinate = coordinate;
			getMatrixMap();
			validatePosition();
			
			while(execute) {
				countSteps += 1;
				LOG.info("Stepts: " + countSteps);
				
				processCoordinate();
				nextPoint();
				validateEndProcess();
				System.out.println(Arrays.deepToString(this.room));
				LOG.info("");
				
			}
		} catch (ValidateException e) {
			LOG.error(e.getMessage());
			return false;
		}  catch (HooverException e) {
			LOG.error(e.getMessage());
			return false;
		}
		
		return true;
		
	}
	

	private void getMatrixMap() {
		MatrixFileReader matrixFileReader = new MatrixFileReader();
		this.room = matrixFileReader.readFile(fileRead);
		
		for (int i = 0; i < this.room.length; i++) {
			for (int j = 0; j < this.room[0].length; j++) {
				if (this.room[i][j].equalsIgnoreCase(WALL_CHAR)) {
					countFullRoom++;
				}
				
			}
		}
	}
	
	private void validatePosition() throws HooverException {
		
		int sizeX = this.room.length, sizeY = this.room[0].length;
		if (coordinate[0] > sizeX-1 && coordinate[0] < 0 &&
				coordinate[1] > sizeY-1 && coordinate[1] < 0) {
			throw new ValidateException("You must write a correct coordinate, it is out of range.");
		}
		
		if (this.room[coordinate[0]][coordinate[1]].equalsIgnoreCase(WALL_CHAR)) {
			throw new ValidateException("You must start in a free coordinate, here there is a wall.");
		}
		
	}
	
	private void processCoordinate() {
		if (this.room[coordinate[0]][coordinate[1]].equalsIgnoreCase(FREE_CHAR)) {
			this.room[coordinate[0]][coordinate[1]] = String.valueOf(countSteps);
			this.countFullRoom++;
		} else {
			this.room[coordinate[0]][coordinate[1]] += String.valueOf(SEPARATOR + countSteps);
		}
	}
	
	private void nextPoint() {
		
		boolean foundDirection = false;
		int countDirections = 1;
		
		while (!foundDirection) {
			
			direction = getRandomDirection();
			
			switch (direction) {
			case 1:
				foundDirection = northDirection();
				break;
				
			case 2:
				foundDirection = eastDirection();
				break;
				
			case 3:
				foundDirection = southDirection();
				break;
				
			case 4:
				foundDirection = westDirection();
				break;

			default:
				break;
			}
			
			if (!foundDirection && countDirections >= 4) {
				foundDirection = true;
				direction = getRandomDirection();
			}
			
			countDirections++;
			
		}
		
	}
	
	private boolean northDirection() {
		if (!validateWallBlock(coordinate[0]-1, coordinate[1])) {
			if (validateFreeBlock(coordinate[0]-1, coordinate[1])) {
				coordinate[0] += -1;
				return true;
			} else {
				int side = findFreeBlockSide(2,3,4);
				if (side == 0) {
					return false;
				} else {
					direction = side;
					return true;
				}
			}
		} else {
			return false;
		}
		
	}
	
	private boolean eastDirection() {
		if (!validateWallBlock(coordinate[0], coordinate[1]+1)) {
			if (validateFreeBlock(coordinate[0], coordinate[1]+1)) {
				coordinate[1] += 1;
				return true;
			} else {
				int side = findFreeBlockSide(1,3,4);
				if (side == 0) {
					return false;
				} else {
					direction = side;
					return true;
				}
			}
		} else {
			return false;
		}
		
	}
	
	private boolean southDirection() {
		if (!validateWallBlock(coordinate[0]+1, coordinate[1])) {
			if (validateFreeBlock(coordinate[0]+1, coordinate[1])) {
				coordinate[0] += 1;
				return true;
			} else {
				int side = findFreeBlockSide(1,2,4);
				if (side == 0) {
					return false;
				} else {
					direction = side;
					return true;
				}
			}
		} else {
			return false;
		}
	}
	
	private boolean westDirection() {
		if (!validateWallBlock(coordinate[0], coordinate[1]-1)) {
			if (validateFreeBlock(coordinate[0], coordinate[1]-1)) {
				coordinate[1] += -1;
				return true;
			} else {
				int side = findFreeBlockSide(1,2,3);
				if (side == 0) {
					return false;
				} else {
					direction = side;
					return true;
				}
			}
		} else {
			return false;
		}
	}
	
	private int getRandomDirection() {
		return (int) (Math.random() * 4) + 1;
	}
	
	private int findFreeBlockSide(int ... sides) {
		for (int i = 0; i < sides.length; i++) {
			switch (sides[i]) {
			case 1:
				if (validateFreeBlock(coordinate[0]-1, coordinate[1])) {
					return 1;
				}
				break;
			case 2:
				if (validateFreeBlock(coordinate[0], coordinate[1]+1)) {
					return 2;
				}
				break;
			case 3:
				if (validateFreeBlock(coordinate[0]+1, coordinate[1])) {
					return 3;
				}
				break;
			case 4:
				if (validateFreeBlock(coordinate[0], coordinate[1]-1)) {
					return 4;
				}
				break;
			default:
				break;
			}
		}
		return 0;
	}
	
	private boolean validateFreeBlock(int x, int y) {
		
		int sizeX = this.room.length, sizeY = this.room[0].length;
		
		if (x <= sizeX-1 && x >= 0 && y <= sizeY-1 && y >= 0 && 
				room[x][y].equalsIgnoreCase(FREE_CHAR)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean validateWallBlock(int x, int y) {
		
		int sizeX = this.room.length, sizeY = this.room[0].length;
		
		if (x <= sizeX-1 && x >= 0 && y <= sizeY-1 && y >= 0 &&
				this.room[x][y].equalsIgnoreCase(WALL_CHAR)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void validateEndProcess() {
		LOG.info("block count = " + countFullRoom + "    Total Blocks = " + (this.room.length * this.room[0].length));
		if (countFullRoom >= (this.room.length * this.room[0].length)) {
			this.execute = false;
		}
		
		if (countSteps >= 200) {
			this.execute = false;
		}
	}
	

}
