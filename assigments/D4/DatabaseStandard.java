//package d4;

import java.util.HashMap;

/**
 * Devoir 4 CSI 2510
 * Robert Laganiere
 * 
 * DatabaseStandard.java
 * 
 * Créer un dictionnaire de mots de passe à partir de la liste des 10000 mots de passe communs
 * augmentée avec les règles spécifiées plus haut. Ce dictionnaire se trouvera dans la classe
 * DatabaseStandard réalisant l’interface DatabaseInterface et utilisera la structure de
 * données java.util.HashMap.
 * 
 * @author Maxime Côté-Gagné (8851539)
 *
 */
public class DatabaseStandard implements DatabaseInterface {
	//var global for DatabseStandard
	private HashMap<String,String> hashMap;
	//private int N = 10007;
	/**
	 * 
	 */
	public DatabaseStandard() {
		// TODO Auto-generated constructor stub
		// this constructor must create the initial hash map
		hashMap = new HashMap<>();//put a number in it
	}

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
		return hashMap.put(encryptedPassword, plainPassword);
	}

	/* (non-Javadoc)
	 * @see d4.DatabaseInterface#decrypt(java.lang.String)
	 */
	@Override
	public String decrypt(String encryptedPassword) {
		// TODO Auto-generated method stub
		// returns plain password corresponding to encrypted password
		return hashMap.get(encryptedPassword);
	}

	/* (non-Javadoc)
	 * @see d4.DatabaseInterface#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		// returns the number of password pairs stored in the database
		//return 0;
		return hashMap.size();
	}

	/* (non-Javadoc)
	 * @see d4.DatabaseInterface#printStatistics()
	 */
	@Override
	public void printStatistics() {
		// TODO Auto-generated method stub
		// print statistics based on type of Database
		System.out.println("*** DatabaseStandard Statistics ***");
		System.out.println("Size is " + size() + " password(s)");
		System.out.println("Initial Number of Indexes when Created " + 37);//le meme numbre init
		System.out.println("*** End DatabaseStandard Statistics ***");
	}

}
