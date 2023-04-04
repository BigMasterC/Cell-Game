package model;

/**
 * This class represents the logic of a game where a
 * board is updated on each step of the game animation.
 * The board can also be updated by selecting a board cell.
 * 
 * @author Dept of Computer Science, UMCP & Chibundu Onwuegbule
 */

public abstract class GameModel { //is an abstract class...similar to an Interface but you can have instance variables
	protected BoardCell[][] board;  // "board" is a 2D array of BoardCells
	/*
	 * BoardCell of type protected so that means only classes inside the same package, class, or its subclasses
	 * can access the BoardCell
	 */
	protected int rows; //represents the rows on the game board
	protected int cols; //represents the columns on the game board

	/**
	 * Creates a rectangular board of the specified size, filling it with
	 * BoardCell.EMPTY cells.
	 * @param rows number of rows on the board
	 * @param cols number of columns on the board
	 */
	public GameModel(int rows, int cols) { //constructor SEE IF THIS WORKS
		this.rows = rows;
		this.cols = cols;
		//QUESTION: but wait, since the colors/cells in the BoardCell are immutable, wouldn't that mean I would need the make a deep copy?
		this.board = new BoardCell[rows][cols]; //initializing the board 2D array we declared (up there) and...
	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            board[i][j] = BoardCell.EMPTY; //filling it with BoardCell.EMPTY
	        }
	    }
	}

	/** Returns the number of rows on the board
	 * 
	 * @return number of rows
	 */
	public int getRows() {
		return rows;
	}

	/** Returns the number of columns on the board
	 * 
	 * @return number of columns
	 */
	public int getCols() {
		return cols;
	}
	
	/**
	 * Returns a reference to the board without making any kind of copy.
	 * 
	 * @return the board
	 */
	public BoardCell[][] getBoard() {
		return board;   // This is done for you. Just return the reference.
	}

	/** 
	 * Sets the cell at the specified location to value provided.
	 * @param rowIndex row to set
	 * @param colIndex column to set
	 * @param boardCell value to assign to the board
	 */
	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		board[rowIndex][colIndex] = boardCell; //we're assigning the value of the boardCell (that should be passed in
											   //we're setting that specific coordinate (row,col) to whatever value boardCell is
	}
	
	/** 
	 * Returns the value at a given location of the board
	 * @param rowIndex row to access
	 * @param colIndex column to access
	 * @return value of board at the specified location
	 */
	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		return board[rowIndex][colIndex];
	}
	
	/** 
	 * Provides a string representation of the board 
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Board(Rows: " + board.length + ", Cols: " + board[0].length + ")\n");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++)
				buffer.append(board[row][col].getName());
			buffer.append("\n");
		}
		return buffer.toString();
	}
	/**
	 * [Abstract method that will me implemented when the abstract class is extended]
	 * @return returning a boolean value to test if the game is over.
	 */
	public abstract boolean isGameOver();
	
	/**
	 * [Abstract method that will me implemented when the abstract class is extended]
	 * @return returns an int value used to determine the player's score
	 */
	public abstract int getScore();

	/**
	 * [Abstract method that will me implemented when the abstract class is extended]
	 * Advances the animation one step. 
	 */
	public abstract void nextAnimationStep();

	/**
	 * [Abstract method that will me implemented when the abstract class is extended]
	 * Adjust the board state according to the current board
	 * state and the selected cell.
	 * @param rowIndex row that user has clicked
	 * @param colIndex column that user has clicked
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}