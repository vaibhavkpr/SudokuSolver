package codes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Board {

	private Node first;
	private int dimension;

	// Create the Sudoku board
	public Board(int dimension) {
		this.dimension = dimension;

		if (dimension == 1)
			first = new Node();
		else if (dimension <= 0)
			first = null;
		else {
			first = new Node();
			Node temp = null;
			Node rowMarker = first;
			Node columnMarker = first;

			// making the column
			for (int x = 0; x < dimension - 1; x++) {
				temp = new Node();
				columnMarker.setRight(temp);
				temp.setLeft(columnMarker);
				columnMarker = temp;
			}
			// making the row
			for (int y = 0; y < dimension - 1; y++) {
				// making the first node in the row
				temp = new Node();
				rowMarker.setDown(temp);
				temp.setUp(rowMarker);
				rowMarker = temp;

				// making the rest of the row
				columnMarker = rowMarker;
				for (int x = 0; x < dimension - 1; x++) {
					temp = new Node();
					temp.setLeft(columnMarker);
					columnMarker.setRight(temp);
					temp.setUp(temp.getLeft().getUp().getRight());
					temp.getUp().setDown(temp);
					columnMarker = temp;
				}
			}
		}
		// Establish the boxIDs
		setBoxIDs();
	}

	// Display the Board
	public void display() {
		Node temp = first;
		Node rowMarker = first;

		while (temp != null) {
			while (temp != null) {
				if (temp.getData() > 99)
					System.out.print(temp.getData() + " ");
				else if (temp.getData() > 9)
					System.out.print(temp.getData() + " ");
				else if (temp.getData() == 0)
					System.out.print(". ");
				else
					System.out.print(temp.getData() + " ");

				temp = temp.getRight();
			}
			System.out.println();
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		// Divide the solutions visually
		System.out.println("-----------------");
	}

	// Setup the BoxIDs of 3x3
	public void setBoxIDs() {
		int row = -1, column = 0;
		Node temp = first;
		Node rowMarker = first;

		while (temp != null) {
			row++;
			column = 0;
			while (temp != null) {
				// Box 1: (row 1 | column 1)
				if (row < 3 && column < 3)
					temp.setBoxID(1);
				// Box 2: (row 1 | column 2)
				else if (row < 3 && column < 6)
					temp.setBoxID(2);
				// Box 3: (row 1 | column 3)
				else if (row < 3 && column < 9)
					temp.setBoxID(3);
				// Box 4: (row 2 | column 1)
				else if (row < 6 && column < 3)
					temp.setBoxID(4);
				// Box 5: (row 2 | column 2)
				else if (row < 6 && column < 6)
					temp.setBoxID(5);
				// Box 6: (row 2 | column 3)
				else if (row < 6 && column < 9)
					temp.setBoxID(6);
				// Box 7: (row 3 | column 1)
				else if (row < 9 && column < 3)
					temp.setBoxID(7);
				// Box 8: (row 3 | column 2)
				else if (row < 9 && column < 6)
					temp.setBoxID(8);
				// Box 9: (row 3 | column 3)
				else if (row < 9 && column < 9)
					temp.setBoxID(9);

				temp = temp.getRight();
				column++;
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
	}
	
	// Load the Unsolved Soduku (Level: Hard)
	public void loadHard() throws IOException {
		// Get file
		File puzzle = new File("hard.txt");
		Scanner input = new Scanner(puzzle);

		Node temp = first;
		Node rowMarker = first;

		// Load it onto the Board
		while (temp != null) {
			while (temp != null) {
				int solution = input.nextInt();
				if (solution != 0)
					solve(temp, solution);

				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		input.close();
	}

	// Load the Unsolved Soduku (Level: Medium)
	public void loadMedium() throws IOException {
		// Get file
		File puzzle = new File("medium.txt");
		Scanner input = new Scanner(puzzle);

		Node temp = first;
		Node rowMarker = first;

		// Load it onto the Board
		while (temp != null) {
			while (temp != null) {
				int solution = input.nextInt();
				if (solution != 0)
					solve(temp, solution);

				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		input.close();
	}

	// Load the Unsolved Soduku (Level: Easy)
	public void loadEasy() throws IOException {
		// Get file
		File puzzle = new File("easy.txt");
		Scanner input = new Scanner(puzzle);

		Node temp = first;
		Node rowMarker = first;

		// Load it onto the Board
		while (temp != null) {
			while (temp != null) {
				int solution = input.nextInt();
				if (solution != 0)
					solve(temp, solution);

				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		input.close();
	}

	// Solve the node
	public void solve(Node node, int number) {
		node.setData(number);

		// Eliminate possibilities - left
		Node temp = node.getLeft();
		while (temp != null) {
			temp.setFalse(number);
			temp = temp.getLeft();
		}
		
		// Eliminate possibilities - right
		temp = node.getRight();
		while (temp != null) {
			temp.setFalse(number);
			temp = temp.getRight();
		}
		
		// Eliminate possibilities - below
		temp = node.getDown();
		while (temp != null) {
			temp.setFalse(number);
			temp = temp.getDown();
		}
		
		// Eliminate possibilities - above
		temp = node.getUp();
		while (temp != null) {
			temp.setFalse(number);
			temp = temp.getUp();
		}

		temp = first;
		Node rowMarker = first;

		// Eliminate possibilities - grids
		while (temp != null) {
			while (temp != null) {
				if (temp.getBoxID() == node.getBoxID())
					temp.setFalse(number);

				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
	}

	// SolveMethod1 looks for cells that only have 1 possibility
	public int solveMethod1() {
		int changesMade = 0;
		Node temp = first;
		Node rowMarker = first;

		while (temp != null) {
			while (temp != null) {
				if (temp.numberPossible() == 1)
					for (int x = 1; x < 10; x++)
						if (temp.isPossible(x)) {
							solve(temp, x);
							changesMade++;
						}
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		return changesMade;
	}

	// SolveMethod2 looks for the only cell in a row, column, or box that can be a number
	public int solveMethod2() {
		int changesMade = 0;
		int numPossible = 0;
		Node temp = first;
		Node check = temp;
		Node rowMarker = first;

		while (temp != null) {
			while (temp != null) {
				check = temp;
				for (int x = 1; x < 10; x++) {
					
					// Check Rows
					if (temp.isPossible(x)) {
						numPossible = 0;

						// Left Possibilities
						check = check.getLeft();
						while (check != null) {
							if (check.isPossible(x)) {
								numPossible++;
							}
							check = check.getLeft();
						}
						// Right Possibilities
						check = temp;
						check = check.getRight();
						while (check != null) {
							if (check.isPossible(x)) {
								numPossible++;
							}
							check = check.getRight();
						}
						// If no other Possibilities
						if (numPossible == 0) {
							solve(temp, x);
							changesMade++;
						}
						check = temp;
					}
					
					//  Check Columns
					if (temp.isPossible(x) && numPossible != 0) {
						numPossible = 0;
						
						// Up Possibilities
						check = check.getUp();
						while (check != null) {
							if (check.isPossible(x)) {
								numPossible++;
							}
							check = check.getUp();
						}
						
						// Down Possibilities
						check = temp;
						check = check.getDown();
						while (check != null) {
							if (check.isPossible(x)) {
								numPossible++;
							}
							check = check.getDown();
						}
						
						// If no other Possibilities
						if (numPossible == 0) {
							solve(temp, x);
							changesMade++;
						}
						check = temp;
					}
					
					// Check Boxes
					if (temp.isPossible(x) && numPossible != 0) {
						numPossible = 0;
						
						// Box Possibilities
						check = first;
						Node rowMarker2 = first;
						while (check != null) {
							while (check != null) {
								if (check.getBoxID() == temp.getBoxID()) {
									if (check.isPossible(x)) {
										numPossible++;
									}
								}
								check = check.getRight();
							}
							rowMarker2 = rowMarker2.getDown();
							check = rowMarker2;
						}
						
						// If no other Possibilities - solve
						if (numPossible == 0) {
							solve(temp, x);
							changesMade++;
						}
						check = temp;
					}
				}
				// Shift Temp
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		return changesMade;
	}

	// SolveMethod3 looks for two cells share only the same two possible numbers in the same box
	public int solveMethod3() {
		int changesMade = 0;
		Node temp = first;
		Node rowMarker = first;
		
		while(temp != null){
			while(temp != null){
				
				// Look for nodes with only 2 possibilities
				if(temp.numberPossible() == 2){
					int currentBoxID = temp.getBoxID();
					Node temp2 = first;
					Node rowMarker2 = first;
					
					// Save the two possibilities for temp
					int possible1 = temp.getFirstPossible();
					int possible2 = temp.getSecondPossible();
					
					//Look for another node
					while(temp2 != null){
						while(temp2 != null){
							
							// Check if node has only 2 possibilities, same potentials and in a possible location
							if(temp2.numberPossible() == 2 && temp2.getBoxID() == temp.getBoxID() && temp2 != temp && temp2.getFirstPossible() == temp.getFirstPossible() && temp2.getSecondPossible() == temp.getSecondPossible()){
								changesMade += eliminateTwoPotentials(possible1, possible2, temp.getBoxID(), temp, temp2);
							}
							temp2 = temp2.getRight();
						}
						rowMarker2 = rowMarker2.getDown();
						temp2 = rowMarker2;
					}
				}
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		return changesMade;
	}
	
	// SolveMethod4 looks for two cells share only the same two possible numbers in the same column
	public int solveMethod4() {
		int changesMade = 0;
		Node temp = first;
		Node rowMarker = first;
		
		while(temp != null){
			while(temp != null){
				
				// Look for nodes with only 2 possibilities
				if(temp.numberPossible() == 2){
					Node checker = temp.getUp();
					
					while(checker != null){
						
						// Find another node with same two possibilities 
						if(checker.numberPossible() == 2 && checker.getFirstPossible() == temp.getFirstPossible() && checker.getSecondPossible() == temp.getSecondPossible()){
							Node eliminator = checker.getUp();
							
							//Get to the top of the column
							while(eliminator.getUp() != null)
								eliminator = eliminator.getUp();
							
							// Get rid of possibilities other than the 2 found nodes
							while(eliminator != null){
								if(eliminator != checker && eliminator != temp){
									if(eliminator.isPossible(checker.getFirstPossible()) == true){
										eliminator.setFalse(checker.getFirstPossible());
										changesMade++;
									}
									if(eliminator.isPossible(checker.getSecondPossible()) == true){
										eliminator.setFalse(checker.getSecondPossible());	
										changesMade++;
									}									
								}
								eliminator = eliminator.getDown();
							}							
						}
						checker = checker.getUp();						
					}
				}
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		return changesMade;
	}
	
	// Rid other Nodes in a box of the 2 found possibilities
	public int eliminateTwoPotentials(int possible1, int possible2, int boxID, Node ref1, Node ref2) {
		int changesMade = 0;
		Node temp = first;
		Node rowMarker = first;
		
		while(temp != null){
			while(temp != null){
				
				// Find the Node in the same Box and check if they are not the two situational Nodes
				if(temp.getBoxID() == boxID && temp != ref1 && temp != ref2){
					
					// Eliminate Possibilities
					if(temp.isPossible(possible1)){
						temp.setFalse(possible1);
						changesMade++;
					}
					if(temp.isPossible(possible2)){
						temp.setFalse(possible2);
						changesMade++;
					}
				}
				temp = temp.getRight();
			}
			rowMarker = rowMarker.getDown();
			temp = rowMarker;
		}
		return changesMade;
	}
	
	// SolveMethod4 looks for n cells share only the same n possible numbers in the same section
	// SolveMethod5 looks only possibilities for one number in one section only occur in another section
	public int solveMethod5() {
		int changesMade = 0;
		return changesMade;
	}

	// Run solveMethods
	public void runSolutions() {
		int changesMade = 0;
		
		// Run solving methods until stuck/finished
		do {
			changesMade = 0;
			changesMade += solveMethod1();
			changesMade += solveMethod2();
			changesMade += solveMethod3();
			changesMade += solveMethod4();
			changesMade += solveMethod5();

		} while (changesMade > 0);
	}
}