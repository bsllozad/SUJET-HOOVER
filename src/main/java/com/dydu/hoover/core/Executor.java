package com.dydu.hoover.core;

import java.util.Arrays;

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
	
	private final String FILE_READ = "/room.txt";
	private final String WALL_CHAR = "M";
	private final String FREE_CHAR = " ";
	private final String SEPARATOR = " - ";
	
	private String[][] room = null;
	private int valueX = 1;
	private int valueY = -1;
	private int countSteps = 0;
	private int countFullRoom = 0;
	private int direction = 1;
	private boolean execute = true;
	
	private int[] coordinate = null;
	
	public void run(int[] coordinate) {
		this.coordinate = coordinate;
		getMatrixMap();
		validatePosition();
		
		while(execute) {
			countSteps += 1;
			System.out.println("Recorridos: " + countSteps);
			
			if (countSteps == 20) {
				System.out.println();
			}
			
			processCoordinate();
			nextPoint2();
			validateEndProcess();
			System.out.println(Arrays.deepToString(this.room));
			
		}
		
	}
	

	private void getMatrixMap() {
		MatrixFileReader matrixFileReader = new MatrixFileReader();
		this.room = matrixFileReader.readFile(FILE_READ);
		
		System.out.println(this.room.length);
		System.out.println(this.room[0].length);
		
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
	
	private void nextPoint2() {
		
		if (direction == 1) {
			if (!validateWallBlock(coordinate[0]-1, coordinate[1])) {
				if (validateFreeBlock(coordinate[0]-1, coordinate[1])) {
					coordinate[0] += -1;
				} else {
					int side = findFreeBlockSide(2,3,4);
					if (side == 0) {
						direction = 2;
					} else {
						direction = side;
					}
				}
			} else {
				direction = 2;
			}
		}
		
		if (direction == 2) {
			if (!validateWallBlock(coordinate[0], coordinate[1]+1)) {
				if (validateFreeBlock(coordinate[0], coordinate[1]+1)) {
					coordinate[1] += 1;
				} else {
					int side = findFreeBlockSide(1,3,4);
					if (side == 0) {
						direction = 3;
					} else {
						direction = side;
					}
				}
			} else {
				direction = 3;
			}
		}
		
		if (direction == 3) {
			if (!validateWallBlock(coordinate[0]+1, coordinate[1])) {
				if (validateFreeBlock(coordinate[0]+1, coordinate[1])) {
					coordinate[0] += 1;
				} else {
					int side = findFreeBlockSide(1,2,4);
					if (side == 0) {
						direction = 4;
					} else {
						direction = side;
					}
				}
			} else {
				direction = 4;
			}
		}
		
		if (direction == 4) {
			if (!validateWallBlock(coordinate[0], coordinate[1]-1)) {
				if (validateFreeBlock(coordinate[0], coordinate[1]-1)) {
					coordinate[1] += -1;
				} else {
					int side = findFreeBlockSide(1,2,3);
					if (side == 0) {
						if (findSideNotEqualsWall() == 1) {
							coordinate[0] += -1;
						} else if (findSideNotEqualsWall() == 2) {
							coordinate[1] += 1;
						} else if (findSideNotEqualsWall() == 3) {
							coordinate[0] += 1;
						} else if (findSideNotEqualsWall() == 4) {
							coordinate[1] += -1;
						}
					} else {
						direction = side;
					}
				}
			} else {
				direction = 2;
			}
		}
		
		
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
	
	private int findSideNotEqualsWall() {
		for (int i = 0; i < 4; i++) {
			if (!validateWallBlock(coordinate[0]-1, coordinate[1])) {
				return 1;
			} else if (!validateWallBlock(coordinate[0], coordinate[1]+1)) {
				return 2;
			} else if (!validateWallBlock(coordinate[0]+1, coordinate[1])) {
				return 3;
			} else if (!validateWallBlock(coordinate[0], coordinate[1]-1)) {
				return 4;
			} else {
				execute = false;
			}
		}
		
		return 0;
	}
	
	
	private void nextPoint() {
		
		if (!validateWallBlock(coordinate[0], coordinate[1] + (valueY>0? valueY: valueY*-1)) && 
				validateFreeBlock(coordinate[0], coordinate[1] + (valueY>0? valueY: valueY*-1))) {
			if (valueY > 0) {
				valueY *= -1;
			}
			coordinate[1] += valueY;
			return;
		}
		
		if (!validateWallBlock(coordinate[0] + valueX<0? valueX: valueX*-1, coordinate[1]) && 
				validateFreeBlock(coordinate[0] + valueX<0? valueX: valueX*-1, coordinate[1])) {
			if (valueX < 0) {
				valueX *= -1;
			}
			coordinate[0] += valueX;
			return;
		}
		
		if (!validateWallBlock(coordinate[0], coordinate[1] + valueY<0? valueY: valueY*-1) && 
				validateFreeBlock(coordinate[0], coordinate[1] + valueY<0? valueY: valueY*-1)) {
			if (valueY < 0) {
				valueY *= -1;
			}
			coordinate[1] += valueY;
			return;
		}
		
		if (!validateWallBlock(coordinate[0] + valueX>0? valueX: valueX*-1, coordinate[1]) && 
				validateFreeBlock(coordinate[0] + valueX>0? valueX: valueX*-1, coordinate[1])) {
			if (valueX > 0) {
				valueX *= -1;
			}
			coordinate[0] += valueX;
			return;
		}
		
		if (!validateWallBlock(coordinate[0], coordinate[1] + valueY>0? valueY: valueY*-1) && 
				!validateFreeBlock(coordinate[0], coordinate[1] + valueY>0? valueY: valueY*-1)) {
			if (valueY > 0) {
				valueY *= -1;
			}
			coordinate[1] += valueY;
			return;
		}
		
		if (!validateWallBlock(coordinate[0] + valueX<0? valueX: valueX*-1, coordinate[1]) && 
				!validateFreeBlock(coordinate[0] + valueX<0? valueX: valueX*-1, coordinate[1])) {
			if (valueX < 0) {
				valueX *= -1;
			}
			coordinate[0] += valueX;
			return;
		}
		
		if (!validateWallBlock(coordinate[0], coordinate[1] + valueY<0? valueY: valueY*-1) && 
				!validateFreeBlock(coordinate[0], coordinate[1] + valueY<0? valueY: valueY*-1)) {
			if (valueY < 0) {
				valueY *= -1;
			}
			coordinate[1] += valueY;
			return;
		}
		
		if (!validateWallBlock(coordinate[0] + valueX>0? valueX: valueX*-1, coordinate[1]) && 
				!validateFreeBlock(coordinate[0] + valueX>0? valueX: valueX*-1, coordinate[1])) {
			if (valueX > 0) {
				valueX *= -1;
			}
			coordinate[0] += valueX;
			return;
		}
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
		if (countFullRoom >= (this.room.length * this.room[0].length)) {
			this.execute = false;
		}
	}
	

}
