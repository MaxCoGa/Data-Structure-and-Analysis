//package d4;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Devoir 4 CSI 2510
 * Robert Laganiere
 * 
 * DatabaseMine.java
 * 
 * Refaire la même tâche mais en utilisant cette fois une table de hachage que vous devez
 * programmer vous-même. Tout le code produit en Partie 1 pourra être ré-utilisé. Vous avez
 * seulement à produire un nouveau dictionnaire DatabaseMine réalisant aussi l’interface
 * DatabaseInterface.
 * 
 * @author Maxime Côté-Gagné (8851539)
 *
 */
public class DatabaseMine implements DatabaseInterface {
	/*
	 * Classe Hash de Java
	 */
	public class Hash<K, V> implements Map.Entry<K, V> {//herite de arraylist aussi
	    private final K key;
	    private V value;

	    public Hash(K key, V value) {
	        this.key = key;
	        this.value = value;
	    }

	    @Override
	    public K getKey() {
	        return key;
	    }

	    @Override
	    public V getValue() {
	        return value;
	    }

	    @Override
	    public V setValue(V value) {
	        V old = this.value;
	        this.value = value;
	        return old;
	    }
	}//FIN DE LA CLASSE HASH DE JAVA
	
	private int N; // this is a prime number that gives the number of addresses //nombre premier
	private int size;//nombre d'entrée dans la table
	private int sonde;//nombre de sondages
	private ArrayList<Hash<String,String>> hash;//La classe Hash de java est plus haut
	
    private int currentSize, maxSize;       
    private String[] keys;   
    private String[] vals; 

	
	//not use
	//String[] keys;
	//String[] vals;
	
	
	
	// these constructors must create your hash hashs with enough positions N
	// to hold the entries you will insert; you may experiment with primes N

	/**
	 * CONSTRUCTOR
	 */
	public DatabaseMine() {
		// TODO Auto-generated constructor stub
		N = 203341;//arbitary PRIME NUMBER;, might not be the best idea
		//this.keys = new String[N];
		//this.vals = new String[N];
		size = 0;
		hash = new ArrayList<>(N);
		for (int i = 0; i <= N; i++){
			hash.add(null);
		}
	}// here you pick suitable default N 
	
	
	public DatabaseMine(int N) {
		this.N = N;
		//this.keys = new String[N]; not use
		//this.vals = new String[N];
		size = 0;
		hash = new ArrayList<>(N);
		for (int i = 0; i <= N; i++){
			hash.add(null);
		}
	} // here the N is given by the user
	
	
	
	
	/* (non-Javadoc)
	 * @see d4.DatabaseInterface#save(java.lang.String, java.lang.String)
	 */
	@Override
	public String save(String plainPassword, String encryptedPassword) {
		// TODO Auto-generated method stub
		 // Stores plainPassword and corresponding encryptedPassword in a map.
		 // if there was a value associated with this key, it is replaced, 
		 // and previous value returned; otherwise, null is returned
		 // The key is the encryptedPassword the value is the plainPassword

		sonde++;
		int value = hashFunction(encryptedPassword);
		int position = findPosition(encryptedPassword);

		//si la hash existe deja on prend la meme position que avant
		if(position!=-1){
			Hash<String, String> entry = hash.get(position);
			String string = entry.getValue();
			entry.setValue(plainPassword);
			hash.set(position, entry);
			return string;
		} 
		else {
			
			//SONDAGE pour trouver la position du nouveau hash
			for(int i = 0; i < N; i++) {
				int sonder = (value + i)%N;
				Hash<String, String> entry = hash.get(sonder);
				
				//si null pour la key ou la val( key,val)
				if (entry == null || entry.getKey() == null) {
					hash.set(sonder, new Hash<String, String>(encryptedPassword, plainPassword));
					size++;
					return null;
				}
			}
		}
		return null;
	}



	/* (non-Javadoc)
	 * @see d4.DatabaseInterface#decrypt(java.lang.String)
	 */
	@Override
	public String decrypt(String encryptedPassword) {
		// TODO Auto-generated method stub
		// returns plain password corresponding to encrypted password
		
		int value = hashFunction(encryptedPassword);
		//int already = checkPosition(encryptedPassword);
		for(int i = 0; i < N; i++) {
			
			//sondage pour trouver la position
			int sonder = (value + i)%N;
			//aller chercher a la postion sonder
			Hash<String, String> entry = hash.get(sonder);
			
			//retourne null si entree null, donc rien
			if (entry == null) {
				return null;						
			}
			
			//si la postion est libre
			if (entry.getKey() == null) {
				continue;				
			}
			
			//si on elem est present a l'Entre(key) choisis
			if (entry.getKey().equals(encryptedPassword)) {
				return entry.getValue();
			}
		}		
		return null;
		//return null;
	}

	/* (non-Javadoc)
	 * @see d4.DatabaseInterface#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
				// returns the number of password pairs stored in the database
		return size;
	}

	/* (non-Javadoc)
	 * @see d4.DatabaseInterface#printStatistics()
	 */
	@Override
	public void printStatistics() {
		// TODO Auto-generated method stub
		// important statistics must be collected (here or during construction) and
		//printed here: size, number of indexes, load factor, average number of probes
		//and average number of displacements (see output example below) 
		
		System.out.println("*** DatabaseStandard Statistics ***");
		System.out.println("Size is " + size() + " passwords");
		System.out.println("Number of Indexes is " + getN());
		System.out.println("Load Factor is " + getLoadFactor());
		System.out.println("Average Number of Probes is " + getProbes());
		System.out.println("Number of displacements (due to collisions) " + getDeplacement());
		System.out.println("*** End DatabaseStandard Statistics ***");

	}


	//////////////////////////
	//NEW FUNCTIONS/METHODS//
	////////////////////////
	
	/*
	 * Votre table de hachage doit utiliser le sondage lineaire afin de résoudre les collisions et la
	 * fonction de hachage à utiliser est key.hashCode() mod N, ou N est un nombre premier.
	 */
	int hashFunction(String key) {
		int address=key.hashCode()%N;
		return (address>=0)?address:(address+N);
	}
	
	//DÉPLACEMENTS
	private int getDeplacement() {
		// TODO Auto-generated method stub
		return sonde;
	}

	//SONDAGE
	private double getProbes() {
		// TODO Auto-generated method stub
		return (double)sonde/(double)size;
	}

	//LOAD FACTOR
	private double getLoadFactor() {
		// TODO Auto-generated method stub
		return (double)size/(double)N;
	}

	//N
	private int getN() {
		// TODO Auto-generated method stub
		return N;
	}
	
	
	
	////////////////////////////TESTING///////////////////////////
    public void makeEmpty()
    {
        currentSize = 0;
        keys = new String[maxSize];
        vals = new String[maxSize];
    }
 
    public int getSize() 
    {
        return currentSize;
    }
 

    public boolean isFull() 
    {
        return currentSize == maxSize;
    }
 

    public boolean isEmpty() 
    {
        return getSize() == 0;
    }
    
    private int hash(String key) 
    {
        return key.hashCode() % maxSize;
    }    
	
	/**
	 * 
	 * @param encryptedPassword
	 * 
	 * @return le hash sonder
	 */
	private int findPosition(String encryptedPassword) {
		// TODO Auto-generated method stub
		int value = hashFunction(encryptedPassword);
		
		for(int i = 0; i < N; i++) {
			
			int sonder = (value + i)%N;
			Hash<String, String> entry = hash.get(sonder);
			
			if (entry == null) {
				return -1;						
			}
			
			if (entry.getKey() == null) {
				continue;				
			}
			
			if (entry.getKey().equals(encryptedPassword)) {
				return sonder;
			}
		}		
		return -1;
		//return 0;
	}
	/*
	private int checkPostion(String encryptedPassword){
	// TODO Auto-generated method stub
	int value = hashFunction(encryptedPassword);
		for(int i = 0; i < N; i++) {
			
			//sondage pour trouver la position
			int sonder = (value + i)%N;
			//aller chercher a la postion sonder
			Hash<String, String> entry = hash.get(sonder);
			
			//retourne null si entree null, donc rien
			if (entry == null) {
				return null;						
			}
			
			//si la postion est libre
			if (entry.getKey() == null) {
				continue;				
			}
			
			//si on elem est present a l'Entre(key) choisis
			if (entry.getKey().equals(encryptedPassword)) {
				return entry.getValue();
			}
	}
	*/


}
