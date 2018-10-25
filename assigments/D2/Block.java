import java.io.UnsupportedEncodingException;
/*
 * Devoir 2 CSI 2510
 * Robert Laganiere
 * 
 * 
 * Block.java
 * 
 * Maxime Cote-Gane(8851539)
 */
public class Block{
  private int index;                // the index of the block in the list

  private java.sql.Timestamp timestamp;  // time at which transaction
                                         // has been processed
  private Transaction transaction;  // the transaction object

  private String nonce;             // random string (for proof of work)

  private String previousHash;   // previous hash (set to "" in first block)

  private String hash;  // hash of the block (hash of string obtained

                       //from previous variables via toString() method)

  public Block(int index,java.sql.Timestamp timestamp,Transaction transsaction, String nonce, String previousHash) throws UnsupportedEncodingException {
    this.index=index;
    this.timestamp= timestamp;
    this.transaction = transsaction;
    this.nonce=nonce;
    this.previousHash=previousHash;
 
    
  }
  
  
  public int getIndex(){return index;}
  public Transaction getTransaction(){return transaction;}
  public void setHash(String hash){this.hash=hash;}
  public String getNonce(){return nonce;}
  public String getPreviousHash(){return previousHash;}
  public String getHash(){return hash;}
  
  
  
  public String toString() {
   return timestamp.toString() + ":" + transaction.toString() + "." + nonce+ previousHash;

  }
}