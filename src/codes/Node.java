package codes;

public class Node {

	// Variables
	private int data;
	private boolean[] possible = { true, true, true, true, true, true, true, true, true, true };
	private Node up, down, left, right;
	private int boxID;

	// Construct
	public Node() {
		this.data = 0;
		up = null;
		left = null;
		right = null;
		down = null;
		boxID = 0;
	}

	// Overload
	public Node(int data) {
		this.data = data;
		up = null;
		left = null;
		right = null;
		down = null;
		boxID = 0;
	}
	
	// Getters and Setters
	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
		for (int x = 0; x < 10; x++)
			possible[x] = false;
	}

	public Node getUp() {
		return up;
	}

	public void setUp(Node up) {
		this.up = up;
	}

	public Node getDown() {
		return down;
	}

	public void setDown(Node down) {
		this.down = down;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public boolean isPossible(int number) {
		return possible[number];
	}

	public int getBoxID() {
		return boxID;
	}

	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}
	
	// Get number of possibilities
	public int numberPossible() {
		int count = 0;
		for (int x = 1; x < 10; x++)
			if (possible[x] == true)
				count++;
		return count;
	}
	
	// Set possibility false
	public void setFalse(int number) {
		possible[number] = false;
	}
	
	// Get first possibility
	public int getFirstPossible() {
		for(int x = 1; x < 10; x++) {
			if(possible[x] == true) {
				return x;
			}
		}
		return -1;
	}
	
	// Get second possibility
	public int getSecondPossible() {
		int counter = 0;
		for(int x = 1; x < 10; x++) {
			if(possible[x] == true && counter == 0) {
				counter++;
			}
			else if(possible[x] == true && counter != 0) {
				return x;
			}
		}
		return -1;
	}
}