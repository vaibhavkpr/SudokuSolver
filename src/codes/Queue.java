package codes;

public class Queue {
	
	// Variables
	Node first;
	Node last;
	private int data;
	int size = 0;
	
	//Construct
	public Queue() {
		this.data = 0;
		first = null;
		last = null;	
	}
	
	//Offer: Adds an item into the back of the list
	public void Offer(int data) {
		
		Node temp = new Node(data);
		
		// No items in the list - add to last
		if(last == null) {
			last = temp;
			first = last;
			size++;
		}
		// Add to the end of the list
		else {
			last.setRight(temp);
			last = temp;
			size++;
		}		
	}
	
	//Remove: Pops the  item off of the front of the list
	public void Remove() {
		
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
