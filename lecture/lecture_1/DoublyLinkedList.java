package lecture_1;

/*
 * doubly linked list of integers 
 * (with dummy element)
 *
 * CSI2510 Algortihmes et Structures de Donnees
 * www.uottawa.ca
 *
 * Robert Laganiere, 2017
 *
*/
public class DoublyLinkedList {

  private class Node {
  
    public int element;
	public Node prev;
	public Node next;
	
	public Node(int v, Node p, Node n) { 
	  element= v;
	  prev= p;
	  next= n;
	}	
  }
 
  private Node head; 
  private int size;
 
  // construct empty list
  public DoublyLinkedList() {
       
	head= new Node(-1, null, null);
	head.prev= head.next= head; 
	size= 0;
  }
  
  // get the list size
  public int getSize() {
  
	return size;
  }

  // add a new integer (most efficient)
  public boolean add(int value) {

    Node newNode= new Node(value, head, head.next);
	head.next.prev= newNode;
	head.next= newNode;
		
	size++;
	
    return true;
  }
  
   // add a new integer (least efficient)
  public boolean addOppositeSide(int value) {
 
    Node newNode= new Node(value, head.prev, head);
	head.prev.next= newNode;
	head.prev= newNode;
		
	size++;
	
    return true;
  }

  // search if a given integer is in the array
  public boolean search(int value) {

    Node n= head.next;
	
    while (n!=head && n.element!=value)
	  n= n.next;
	  
	if (n==head)
	  return false;
	else 
	  return true;
  }

  // remove a given integer (first occurrence of)
  public boolean searchAndRemove(int value) {

	Node n= head.next;
	
    while (n!=head && n.element!=value) 
	  n= n.next;
	

    if (n!=head) {

	  n.prev.next= n.next;
	  n.next.prev= n.prev;
	  size--;
      return true;
	  
	} else {
	
	  return false;
	}	
  }

  // remove element at a given index
  public boolean removeAt(int index) {

    if (index<0 || index>=size)
	  return false;
	
	size--;
	
	Node n= head.next;
    for (int i=0; i<index; i++)
	  n= n.next;

	n.prev.next= n.next;
	n.next.prev= n.prev;
	  
	return true;
  }
  
  // return the first element of the list
  public int getFirst() {
  
	return head.next.element;
  }
 
  // string representation
  public String toString() {
  
    StringBuffer s = new StringBuffer("");
  
    for (Node node= head.next; node!= head; node= node.next) {
	  s.append("["+node.element+"]");
	}
	
	s.append("("+size+")");
	  
	return s.toString();
  }
  
  public static void main(String[] args) {
  
    DoublyLinkedList list= new DoublyLinkedList();
	
	list.add(34);
	list.add(93);
	list.add(67);
	list.add(23);
	list.add(51);
	System.out.println("A:" + list);
	
	list.addOppositeSide(33);
	System.out.println("B:" + list);
	
	list.searchAndRemove(51);
	System.out.println("C:" + list);
	list.searchAndRemove(67);
	System.out.println("D:" + list);
	
	System.out.println("E1:" + list.search(93));
	System.out.println("E2:" + list.search(11));

	list.removeAt(1);
	System.out.println("F1:" + list);
	list.removeAt(0);
	System.out.println("F2:" + list);
	list.removeAt(list.getSize()-1);
	System.out.println("F3:" + list);
	
  }
}
