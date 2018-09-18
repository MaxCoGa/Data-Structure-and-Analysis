package lecture_1;

/*
 * unsorted array of integers
 *
 * CSI2510 Algortihmes et Structures de Donnees
 * www.uottawa.ca
 *
 * Robert Laganiere, 2017
 *
*/ 
public class SimpleArray {

  private int[] data; 
  private int size = 0;

  // construct with fixed capacity
  public SimpleArray(int capacity) {
       
    data = new int[capacity]; 
  }

  // add a new integer (most efficient)
  public boolean add(int value) {

    if (size == data.length) 
	 return false;
	  
    data[size++]= value;
	
    return true;
  }

  // add a new integer (least efficient)
  public boolean addOppositeSide(int value) {

    if (size == data.length) 
	 return false;
	 
	for (int i= size++; i>0; i--)
	  data[i]= data[i-1];	
	  
    data[0]= value;
	
    return true;
  }

  // search if a given integer is in the array
  public int search(int value) {

    int i=0;
	while (i<size && data[i]!=value) 
	  i++;
	  
	if (i==size) { 
	  return -1;
	} else {
	  return i;
    }	
  }

  // remove a given integer (first occurrence of)
  public boolean searchAndRemove(int value) {

    int i= search(value);
	  
	if (i<0) { 
	  return false;
	  
	} else {

	  data[i]= data[--size];
	  return true;
    }	
  }
  
  // remove element at a given index
  public boolean removeAt(int index) {

    if (index<0 || index>=size)
	  return false;
	  
	data[index]= data[--size];
	return true;
  }
    
  // get the element at a given index
  public int elementAt(int index) {
  
	return data[index];
  }
    
  // get the element at a given index
  public int getSize() {
  
	return size;
  }
    
  // string representation
  public String toString() {
  
    StringBuffer s = new StringBuffer("");
  
    for (int i=0; i<size; i++) {
	  s.append("["+data[i]+"]");
	}
	
	return s.toString();
  }
  
  public static void main(String[] args) {
  
    SimpleArray tab= new SimpleArray(10);
	
	tab.add(34);
	tab.add(93);
	tab.add(67);
	tab.add(23);
	tab.add(51);
	System.out.println("A:" + tab);
	
	tab.removeAt(1);
	System.out.println("B:" + tab);
	
	tab.searchAndRemove(23);
	System.out.println("C:" + tab);
	
	System.out.println("D:" + tab.search(67));
	System.out.println("E:" + tab.search(99));
	
	tab.addOppositeSide(44);
	System.out.println("F:" + tab);
  }
}
