package model;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game,
 * specifically.
 * 
 * @author Dept of Computer Science, UMCP & Chibundu Onwuegbule
 */

public class ClearCellGameModel extends GameModel {

	private Random random; 
	// "random" represents a randomly generated number to be used during the game when rows are created
	private int score;
	// score variable used to keep track of the player's score

	/* Include whatever instance variables you think are useful. */

	/**
	 * Defines a board with empty cells.  It relies on the
	 * super class constructor to define the board.
	 * 
	 * @param rows number of rows in board
	 * @param cols number of columns in board
	 * @param random random number generator to be used during game when
	 * rows are randomly created
	 */
	public ClearCellGameModel(int rows, int cols, Random random) {
		super(rows,cols); //call to the superclass should ALWAYS be first in the constructor
		this.random = random;
		score = 0; //initialized to "0" because we always start from "0" in games =)
	}

	/**
	 * The game is over when the last row (the one with index equal
	 * to board.length-1) contains at least one cell that is not empty.
	 */
	@Override
	public boolean isGameOver() {
		//last row is "row-1"
		final int lastRow = rows-1; 
		//I'm able to use the instance variables from the GameModel class because I made them "protected"
		//using "lastRow" variable because I need a fixed value for the last row
		//I'm only checking the last row so there's no need for me to go through each row
		for (int i = 0; i < cols; i++) {
			if(board[lastRow][i] != BoardCell.EMPTY) {
				return true; //it is true that the game is over
			}
		}
		return false;
	}

	/**
	 * Returns the player's score.  The player should be awarded one point
	 * for each cell that is cleared.
	 * 
	 * @return player's score
	 */
	@Override
	public int getScore() {
		return score;
	}

	/*
	 * Returns a random number using the Random number generator
	 */

	public Random getRandom() { //made this getter myself :) (ain't much but it's honest work)
		return random;
	}


	/**
	 * This method does NOTHING in the case where the game is over.
	 * 
	 * As long as the game is not over yet, this method will do 
	 * the following:
	 * 
	 * 1. Shift the existing rows down by one position. //that would be row + 1 (to go down)
	 * 2. Insert a row of random BoardCell objects at the top
	 * of the board. The row will be filled from left to right with cells 
	 * obtained by calling BoardCell.getNonEmptyRandomBoardCell().
	 * (The Random number generator passed to the constructor of this class should be
	 * passed as the argument to this method call.)
	 */
	@Override
	public void nextAnimationStep() {
		if(isGameOver()== true) {
			//will not need to implement code if the game is over, just return ;)
			return;//returning...NOTHING :D
		}else if(isGameOver() == false) { //if Game is NOT over...
			for(int i = rows-1; i > 0; i--) { 
				//I'm moving the rows all the way down...HOWEVER, I do not surpass the last row
				for(int j = 0; j < cols; j++) {
					//I'm checking each column (even the very last column) so I have to go all the way through
					board[i][j] = board[i-1][j]; //moving the existing rows DOWN
				}
			}//OUT OF EXTERNAL FOR-LOOP
			for (int k =0; k < cols ; k++) {
				board[0][k] = BoardCell.getNonEmptyRandomBoardCell(random);
			}
		}
	}

	/**
	 * This method is called when the user clicks a cell on the board.
	 * If the selected cell is not empty, it will be set to BoardCell.EMPTY, 
	 * along with any adjacent cells that are the same color as this one.  
	 * (This includes the cells above, below, to the left, to the right, and 
	 * all in all four diagonal directions.)
	 * 
	 * If any (of the ENTIRE) rows on the board become empty as a result of the removal of 
	 * cells then those rows will "collapse", meaning that all non-empty 
	 * rows beneath the collapsing row will shift upward. 
	 * 
	 * @throws IllegalArgumentException with message "Invalid row index" for 
	 * invalid row or "Invalid column index" for invalid column.  We check 
	 * for row validity first.
	 */
	@Override
	public void processCell(int rowIndex, int colIndex) {
		if(rowIndex < 0 || rowIndex >= rows) {
			throw new IllegalArgumentException("Invalid row index");
		}else if(colIndex < 0 || colIndex >= cols) {
			throw new IllegalArgumentException("Invalid column index");
		}
		BoardCell selectedCell = getBoardCell(rowIndex, colIndex);
		//can call instance methods from inherited classes without making an object
		if(selectedCell == BoardCell.EMPTY) {
			return;
		}

		removeCellAndAssociates(rowIndex, colIndex, selectedCell.getColor());
		for(int i = rowIndex -1; i <= rowIndex + 1; i++ ) {
			//(below) making sure the "i" variable doesn't surpass the number of rows on the board
			if(i < rows && i >= 0 && isRowEmpty(i)==true) {
				//only goes into the "if" statement is the row is empty (see helper method below for details on how I check
				rowCollapse(i);
			}
		}
	}

	/**HELPER METHOD #1 (Removes the clicked cell and its associates)
	 * 
	 * @param "rowIndex" represents the "row" value in the original method (above) in processCell
	 * @param "colIndex" represents the "col" value in the original method (above) in processCell
	 * @param "colorOfSelectedCell" represents the color of the selected cell
	 */
	private void removeCellAndAssociates(int rowIndex, int colIndex, Color colorOfSelectedCell) {
		for(int i = rowIndex -1; i <= rowIndex + 1; i++ ) { //going through each row (parameter)
			for (int j = colIndex - 1; j <= colIndex +1; j++){ //going through each column (parameter)
				//"if" the row and cols are not out of bounds AND that exact cell's color
				// is the same as the selected cell's color...
				if (i < rows && i >= 0 && j < cols && j >= 0 && 
						board[i][j].getColor() == colorOfSelectedCell) {
					board[i][j] = BoardCell.EMPTY; //first removes the current cell
					score++; //increase score by "1"
				}
			}
		}

	}
	//HELPER METHOD #2
	/**Assumes that the row is empty (therefore, we can collapse the row)
	 * Method collapses the current row (parameter) then shifting the row below it upwards
	 * @param "rowToCollapse" represents the "row" value in the original method (above) in processCell
	 */
	private void rowCollapse(int rowToCollapse) {

		for (int i = rowToCollapse+1; i < rows; i++ ) { //copying the row that is not empty upwards
			for(int k = 0; k < cols ; k++) {
				board[i-1][k] = board[i][k]; //setting the current row to the row above it
			}
		}
		for (int j = 0; j< cols; j++) {
			board[rows-1][j] = BoardCell.EMPTY; //setting the LAST row to "EMPTY" (making it completely empty)
		}

	}
	// HELPER METHOD #3
	/**
	 * Checks if the current row is empty.
	 * @param "row" represents the "row" value in the original method (above) in processCell
	 * @return returns a boolean value that states whether or not the row isEmpty
	 */
	private boolean isRowEmpty(int row) {
		for (BoardCell cell : board[row]) { //"board[row]" returns an entire row (array)
			if (cell != BoardCell.EMPTY) { // if a cell (col) in that row is NOT empty, then...
				//return false!
				return false;
			}
		}
		return true;
	}



}