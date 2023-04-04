package model;

import java.awt.*;
import java.util.Random;

/**
 * This enumerated type represents a board cell.  A board cell has a
 * color (based on Color) and a name (e.g., "R").  
 * @author Dept of Computer Science, UMCP
 */

public enum BoardCell {
	RED(Color.RED, "R"), 
	GREEN(Color.GREEN, "G"),
	BLUE(Color.BLUE, "B"),
	YELLOW(Color.YELLOW, "Y"),
	EMPTY(Color.WHITE, ".");
		
	private final Color color; // color of the BoardCell
	private final String name; // Name of the Color (single letter and capitalized)
	private static int totalColors = BoardCell.values().length; //number of colors in the Enum
	
	/*
	 * Basic construct that sets the current object equal to the parameters passed in 
	 */
	private BoardCell(Color color, String name) {
		this.color = color;
		this.name = name;
	}
	/**
	 * Getter for the private instance variable "color" 
	 * @return getter returns a type "Color"
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Getter for the private instance variable "name"
	 * @return getter returns a type "String"
	 */

	public String getName() {
		return name;
	}
	
	/**
	 * getter for the total number of colors
	 * @return //returns an int value
	 */

	public static int getTotalColors() {
		return totalColors;
	}

	/** Generates a random BoardCell using the specified Random object.
	 * @param random
	 * @return random BoardCell
	 */
	
	public static BoardCell getNonEmptyRandomBoardCell(Random random) {
		int target = random.nextInt(totalColors);
		for (BoardCell boardCell : BoardCell.values()) {
			if (boardCell == BoardCell.EMPTY)
				return BoardCell.RED;
			if (target == boardCell.ordinal())
				return boardCell;
		}
		throw new IllegalArgumentException("Invalid random number generated");
	}
}