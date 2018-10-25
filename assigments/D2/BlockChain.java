import java.io.*;
import java.util.*;
import java.io.UnsupportedEncodingException;
/*
 * Devoir 2 CSI 2510
 * Robert Laganiere
 * 
 * 
 * BlockChain.java
 * 
 * Maxime Cote-Gagne(8851539)
 */

public class BlockChain{
  
  private static ArrayList<Block> blockChain = new ArrayList<Block>();
  static int index;
  static int ctrIndex;
  //static BlockChain bC = new BlockChain();
  static Block lastBlock;
  
  private static ArrayList<Block> tmp = new ArrayList<Block>();
  
  public BlockChain(){
    blockChain = new ArrayList<Block>();
    
  }

  
  
  
  //Reading from the file
  public static BlockChain fromFile(String fileName)throws Exception  {//reading file
	  //ArrayList<Block> tmp = new ArrayList<Block>();
      
	  
	  
	BlockChain bC= new BlockChain();
    File file = new File(fileName); 
    Scanner fileScan = new Scanner(file);
    String sender="";
    String receiver="";
    String hash="";
    String prevHash="00000";
    String nonce="";
    java.sql.Timestamp timeStamp = new java.sql.Timestamp(0);
    Long temps;
    int index=-1;
    int amount=0;
    ctrIndex = -1;
    
    boolean firstTransaction = true;
    while(fileScan.hasNextLine()){
      for(int i=0; i<7 && fileScan.hasNextLine(); i++){
    	  
        switch(i){
          case 0:
        	  index=Integer.parseInt(fileScan.nextLine());
        	  break;
        	  
          case 1:
        	  temps= Long.parseLong(fileScan.nextLine());
        	  timeStamp=new java.sql.Timestamp(temps);
        	  break;
        	  
          case 2: 
        	  sender=fileScan.nextLine();
        	  break;
        	  
          case 3: 
        	  receiver=fileScan.nextLine();
        	  break;
        	  
          case 4: //verifie si amount is in the balance
        	  amount=Integer.parseInt(fileScan.nextLine());
        	  if(firstTransaction){
	        	  if (amount > bC.getBalance(sender)){
	        		  System.out.println(sender +" created the blockchain.\n");
	        		  //System.out.println("");
	        		  //amount = Integer.parseInt(keyboard.nextLine());
	        	  }
	        	  firstTransaction = false;
        	  }else if(firstTransaction == false){
        		  if (amount > bC.getBalance(sender)){
	        		  System.out.println(sender +" have try to send more bitcoin");
	        		  //System.out.println("");
	        		  //amount = Integer.parseInt(keyboard.nextLine());
	        		  System.exit(0);
	        	  }else if(amount < 0){
	        		  System.out.println(sender +" have try to send a negative amount of bitcoin!");
	        		  System.exit(0);
	        	  }
        	  }
        	  break;
        	  
          case 5:
        	  nonce= fileScan.nextLine();
        	  break;
        	  
          case 6: 
        	  hash=fileScan.nextLine();
        }
        
       
        
      }
      tmp.add(new Block(index, timeStamp, new Transaction(sender, receiver, amount), nonce, prevHash));
      
      Transaction cT = new Transaction(sender, receiver, amount);
      Block cB = new Block(index,timeStamp, cT, nonce,prevHash) ;
      lastBlock = cB;
      lastBlock.setHash(hash);
  	
      //System.out.println(cT);
      //System.out.println("\n"+cB);//use for the hash
      
      
      
      
      bC.add(cB);//add file to a block
      prevHash=hash;
      
      //equivalent
      /*System.out.println(cB.getTransaction().getSender());
      System.out.println(cB.getTransaction().getReceiver());
      System.out.println(cB.getTransaction().getAmount());
      System.out.println(cT.getAmount());*/
      
      
      System.out.println(index + " "+ timeStamp+ " "+sender+ " "+receiver+ " "+amount+ " "+nonce+ " "+hash);
      ctrIndex++;
    }
    fileScan.close();
    //System.out.println(ctrIndex);
    
  return bC;
  }
  
  
  
  
  
  public void toFile(String fileName) throws UnsupportedEncodingException{
	  //index 
	  //ctrIndex++;
	  //int cIndex = getIndex();
	  java.sql.Timestamp timeStamp = new java.sql.Timestamp(1);
	  
	  Scanner keyboard = new Scanner(System.in);
	  System.out.println("Sender:");
	  String sender = keyboard.nextLine();
	  System.out.println("Current balance of bitcoin = " + getBalance(sender));
	  
	  System.out.println("Receiver:");
	  String receiver = keyboard.nextLine();
	  
	  
	  //need to verify if the sender have the money
	  System.out.println("Amout:");
	  int amount = Integer.parseInt(keyboard.nextLine());

	  //check if the balance is ok
	 //System.out.println("bitcoin = " + getBalance(sender));
	 if(sender.equals(receiver)){
		 System.out.println("Cannot send bitcoin to himself!");
		 System.out.println("Program terminated!");
		 System.exit(0);
	 }
	 while ((amount > getBalance(sender))|| (amount <0)){
		 if(amount < 0){
			 System.out.println(sender +" can't send a negative amount of bitcoin!");
			 System.out.println("Amout:");
			 amount = Integer.parseInt(keyboard.nextLine());
		 }else{
			  System.out.println("Sender does not have this amout.");
			  System.out.println("Amout:");
			  amount = Integer.parseInt(keyboard.nextLine());
		 }
	  }
	  

	  //TIME
	  java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
	  
	  
	  
	  //increment the index
	  ++ctrIndex;
	  
	  
	  
	  
	  //PROOF OF WORK
	  //NONCE && HASH
	  int ASCIIBOT = 33;
	  int ASCIITOP = 126;
	  Random random = new Random();
	  String hash="";
	  String nonce="";
	  //int targetStringLength=10;
	  int ctr=0;
	  String previousHash = lastBlock.getHash();
	  boolean found = true;
	  System.out.println("prevhash "+previousHash);//print previous hash
	  do{
		  found =false;
		  int targetStringLength = 1 + random.nextInt(16);
		  StringBuilder buffer = new StringBuilder(targetStringLength);
		  
		  for(int i = 0; i < targetStringLength; i++) {
		       int randomLimitedInt = ASCIIBOT + (int)(random.nextFloat() * (ASCIITOP - ASCIIBOT + 1));
		       buffer.append((char) randomLimitedInt);
		  }
		  nonce = buffer.toString();
		  System.out.println("nonce "+nonce);

		  //System.out.println(previousHash);//print previous hash
		  
		  Block newBlock = new Block(ctrIndex, timestamp, new Transaction(sender,receiver,amount), nonce, previousHash);
		  System.out.println(newBlock);
		  String nB = newBlock.toString();
		  hash = Sha1.hash(nB);
		  System.out.println(hash);//print hash with at least 5 zeros
		  if(hash.startsWith("00000")){
			  found = true;
			  System.out.println(ctr);
			  break;
		  }
		  ctr++;
		  
	  }while(!found);
	  

	  
	  
	  
	  
	  //write on file
	  try{
	  	FileWriter fw = new FileWriter(fileName,true); //the true will append the new data
	  	BufferedWriter out = new BufferedWriter(fw);
	  	out.newLine();
	  	out.write(String.valueOf(ctrIndex));//add index
	    out.newLine();
	    out.write(String.valueOf(timestamp.getTime()));//add time
	    out.newLine();
	    out.write(String.valueOf(sender));//add sender
	    out.newLine();
	    out.write(String.valueOf(receiver));//add receiver
	    out.newLine();
	    out.write(String.valueOf(amount));//add amount send
	    out.newLine();
	    out.write(nonce);//add receiver
	    out.newLine();
	    out.write(hash);//add amount send
	    out.close();
	  }
	    catch(IOException ioe)
	    {
	        System.err.println("IOException: " + ioe.getMessage());
	    }
	  
	  lastBlock = new Block(index,timeStamp, new Transaction(sender, receiver, amount), nonce,previousHash);
	  lastBlock.setHash(hash);
	  blockChain.add(lastBlock);//add file to a block
	  //keyboard.close();
  }
  
  
  
  public boolean validateBlockchain() throws UnsupportedEncodingException{
	  int ctr = 0;
	  for(Block cB:blockChain){
		  //cB.toString();
		  String hash = cB.getHash();
		  int index = cB.getIndex();
		  //System.out.println(index + " = " + ctr);
		  //System.out.println(cB + "  =  "+ cB.getHash());
		  //System.out.println("prevhash: "+cB.getPreviousHash());
		  //System.out.println("currentHash "+cB.getHash());
		  String result = Sha1.hash(cB.toString());
		  //System.out.println("Result: "+result);
		 //System.out.println(result+" = "+hash);
		  if(result.equals(hash) && ctr == index){
			  ctr++;
		  }
		  else{
			  System.out.println("Invalid blockchain!");
			  return false;
		  }
		  
		  
		  
	  }
	  return true;
 }
  
  /*public int getIndex(){
	  return ctrIndex;
  }*/
  
  
  
  //verify the balance of the user
  public int getBalance(String username){
    int bitcoin=0;
    for(Block cB:blockChain){
    	
      if(cB.getTransaction().getSender().equals(username)){
    	 //System.out.println("sender found:"+cB.getTransaction().getSender());
    	 //System.out.println(cB.getTransaction());
    	 //System.out.println(cB.getTransaction().getAmount());
    	  bitcoin-= cB.getTransaction().getAmount();
      } 
      else if(cB.getTransaction().getReceiver().equals(username)){
    	  //System.out.println("receiver found:"+cB.getTransaction().getReceiver());
    	  //System.out.println(cB.getTransaction());
    	  //System.out.println(cB.getTransaction().getAmount());
    		  bitcoin+=cB.getTransaction().getAmount();
      } /*else{//in case that the username doesnt exist yet
    	  //System.out.println(username+"doesnt exist!");
    	  bitcoin = 0 ;
      }*/
    }
    return bitcoin;
  }
  
  
  //add the block to the blockchain
  public void add(Block block){
	
    this.blockChain.add(block);
    index++;
  }
  
  
  
  //copy an entire file to a new file
  private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
  
  public static void main(String[] args) throws Exception { 
	  Scanner keyboard = new Scanner(System.in);
	  System.out.println("Enter your minerID: ");
	  String userName = keyboard.nextLine();
	
		String fileName = "src/blockchain";
	    BlockChain bChain = new BlockChain();
	    bChain = fromFile(fileName+".txt");
	    //BlockChain search = new BlockChain();;
	    
	    System.out.println("\nCurrent blockchain is valid? ");
	    if(bChain.validateBlockchain()){
	    	System.out.print(true);
	    }else{
	    	System.out.print(false);
	    }
    
	    boolean done=true;
    while(done){
    	System.out.println("\nNew Transaction = T, Validate a BlockChain = V, Quit = N");
    	//while(keyboard.hasNextLine()){
	    	switch(keyboard.nextLine()){
	    	case "T":
	    		bChain.toFile(fileName);
	    		//bChain = fromFile(fileName);
	    		
	    		break;
	    	case "N":
	    		//keyboard.close();
	    		//System.exit(0);;
	    		done=false;
	    		break;
	    	case "V":
	    		System.out.println("Enter the other miner ID:");
	    		String minerID = keyboard.nextLine();
	    		bChain = fromFile(fileName+"_"+minerID+".txt");
	    		System.out.print("\nCurrent blockchain is valid? ");
	    	    if(bChain.validateBlockchain()){
	    	    	System.out.print(true);
	    	    }else{
	    	    	System.out.print(false);
	    	    }
	    		//System.out.println(fileName+"_"+minerID+".txt"+ " is a valid blockchain!");
	    		File in = new File(fileName+"_"+minerID+".txt");
	            File out = new File(fileName+"_"+minerID+"_"+userName+".txt");
	    		copyFileUsingStream(in, out);
	    		break;
	    	}
	    	//bChain = fromFile(fileName);
    	//}
    }
    System.out.println("Saved the current blockchain? Y/N");
    if(keyboard.nextLine().equals("Y")){
        System.out.println("Username: ");
        //userName = keyboard.nextLine();
        File in = new File("src/blockchain.txt");
        File out = new File("src/blockchain_"+userName+".txt");
        copyFileUsingStream(in, out);
        
        System.out.println("File saved!");
    } else{
    	System.out.println("Program terminated!");
    	System.exit(0);
    }

    
  } 
} 
