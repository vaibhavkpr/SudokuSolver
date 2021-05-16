package codes;

public class Stack {
	
	// Variables
	Node first;
	Node last;
	private int data;
	int size = 0;
	
	//Construct
	public Stack() {
		this.data = 0;
		first = null;
		last = null;	
	}
	
	//Offer: Adds an item into the front of the list
	public void Push(int data) {
		
		Node temp = new Node(data);
		
		// No items in the list - add to last
		if(first == null) {
			first = temp;
			first = last;
			size++;
		}
		// Add to the front of the list
		else {
			first.setLeft(temp);
			first = temp;
			size++;
		}		
	}
	
	//Remove: Pops the  item off of the front of the list
	public void Pop() {
		
		// Check if list is empty
		if(first == null) {
			System.out.println("List is empty");
		}
		else {
			// Shift first to remove the first Node
			Node temp = first;
			first = first.getRight();
			size--;
			
			// Last item on the list removed
			if(first == null) {
				last = null;
			}			
		}
	}
}
