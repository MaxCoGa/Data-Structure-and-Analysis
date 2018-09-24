
//package lab2.ex3;
/*  CSI2114 Lab 3 - lab3.java
 *
 *  Class to check balanced brackets in math expressions
 *
 *  Usage: java bracketsBalance <exp>
 *
 *  by Jeff Souza
 *
 */

class bracketsBalance {

	private boolean bBalance( String exp ) {

		//brackets
		String brackets = "(){}[]";

		//creating the stack
		ArrayStack s = new ArrayStack ( 10 );

		//loop to search for brackets
		for(int i = 0; i < exp.length(); i++) {
			char ch= exp.charAt (i);
			int inputPos = brackets.indexOf ( ch );

			if( inputPos % 2 == 0 ) {
				//first push
				try {
					s.push ( new Character ( ch ) );
				}
				//error catch
				catch(FullStackException e) {
				}
			} else {
				if(inputPos != -1) {
					// Process a closing bracket
	
					//error for an empty stack
					if(s.isEmpty()) {
						return false;
					}
	
					// retrieve brackets in stack
					char retrieve = '\0';
	
					try {
						retrieve = ((Character) s.pop()).charValue();
					}
					//error catch
					catch(EmptyStackException e) {
					}
	
					int bracketPos = brackets.indexOf (retrieve);
	
					//error if first and last brackets are different
					if(bracketPos + 1 != inputPos){
						return false;
					}
				}
			
			}
		}

		//empty stack if not an error will occur
		return s.isEmpty();
	} /* bBalance */


	public static void main( String [] args ) {

		bracketsBalance    b      = new bracketsBalance();
		boolean            result = b.bBalance ( args [ 0 ] );
		//boolean            result = b.bBalance ( "(2*2)" );//balanced test
		//boolean            result = b.bBalance ( "(2+2" );//not balanced test

		if( result ) System.out.println ( "The expression is balanced." );
		else System.out.println ( "The expression is NOT balanced." );
	} /* main */


}
