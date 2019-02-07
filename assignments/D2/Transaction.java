/*
 * Devoir 2 CSI 2510
 * Robert Laganiere
 * 
 * 
 * Transaction.java
 * 
 * Maxime Cote-Gagne(8851539)
 */

public class Transaction{
  
  private String sender;
  
  private String receiver;
  
  private int amount;
  
  public Transaction(String sender, String receiver, int amount){
    this.amount=amount;
    this.sender=sender;
    this.receiver=receiver;
  }
  
  public String getSender(){return sender;}
  public String getReceiver(){return receiver;}
  public int getAmount(){return amount;}
  public void setSender(String s){sender=s;}
  public void setReceiver(String r){receiver=r;}
  public void setAmount(int a){amount=a;}
  
  
  public String toString() {
    
    return sender + ":" + receiver + "=" + amount;
    
  }
}