package codes;
import java.io.IOException;

public class Program {
	
	static Board board;

	public static void main(String[] args) throws IOException {
		
		// Display Intro
		System.out.println("-----------------");
		System.out.println("  Soduku Solver  ");
		System.out.println("-----------------");
		board = new Board(9);
		
		// Load and Solve
		//board.loadMedium();
		//board.loadHard();
		board.loadHard();
		board.display();
		board.runSolutions();
		board.display();
	}
}