//package lecture_4;
/*
 * sorted array of integers
 *
 * CSI2510 Algortihmes et Structures de Donnees
 * www.uottawa.ca
 *
 * Robert Laganiere, 2017
 *
*/
public class SortedArray {

  private int[] data; 
  private int size = 0;

  // construct with fixed capacity
  public SortedArray(int capacity) {
       
    data = new int[capacity]; 
  }

  // add a new integer
  public boolean add(int value) {

    if (size == data.length) 
	 return false;

	int i=0;
	while (i<size && data[i]<=value)
		i++;
		  
	for (int j= size++; j>i; j--)	
		data[j]= data[j-1];
		
    data[i]= value;
	
    return true;
  }

  // search if a given integer is in the array
  public int search(int value) {

    int inf=0, sup= size-1;
	int mid= (inf+sup)/2;
	
	while (data[mid]!=value && inf<sup) {
	
		if (data[mid]<value) {
			inf= mid+1;
		} else {
			sup= mid-1;
		}
		
		mid= (inf+sup)/2;
	}
	
	if (data[mid]==value)
	  return mid;
	else
	  return -1;
  }

  // remove a given integer (first occurrence of)
  public boolean searchAndRemove(int value) {

    int i= search(value);
	
	if (i<0) {
	  return false;
	  
	} else {

	  size--;
	  for (int j= i; j<size; j++)
		data[j]= data[j+1];
		
	  return true;
    }	
  }
  
  // remove element at a given index
  public boolean removeAt(int index) {

    if (index<0 || index>=size)
	  return false;
	
	size--;
    for (int i=index; i<size; i++)
	  data[i]= data[i+1];
	  
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
  
    SortedArray tab= new SortedArray(10);
	
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
	
	System.out.println("D1:" + tab.search(67));
	System.out.println("D2:" + tab.search(93));
	System.out.println("D3:" + tab.search(23));
	System.out.println("D4:" + tab.search(99));
	System.out.println("D5:" + tab.search(9));
	System.out.println("D6:" + tab.search(59));
	
	tab.add(58);
	System.out.println("E:" + tab);
	
	System.out.println("F1:" + tab.search(67));
	System.out.println("F2:" + tab.search(93));
	System.out.println("F3:" + tab.search(23));
	System.out.println("F4:" + tab.search(99));
	System.out.println("F5:" + tab.search(9));
	System.out.println("F6:" + tab.search(59));
  }
}
