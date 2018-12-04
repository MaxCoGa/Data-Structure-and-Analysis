//package d4;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
/**
 * 
 * Devoir 4 CSI 2510
 * Robert Laganiere
 * 
 * PasswordCracker.java
 * cette classe va insérer des mots de passe dans le dictionnaire. Elle
 * va aussi essayer de décoder les mots de passe.
 * 
 * @author Maxime Côté-Gagné (8851539)
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PasswordCracker {

	public void createDatabase(ArrayList<String> commonPassword, DatabaseInterface database){
		// receives list of passwords and populates database with entries consisting
		// of (key,value) pairs where the value is the password and the key is the
		// encrypted password (encrypted using Sha1)
		// in addition to passwords in commonPasswords list, this class is
		// responsible to add mutated passwords according to rules 1-5.
		
		/*RULES
		 * 1. Mettre en majuscule la première lettre de chacun des mots de passe (si ils commencent par une lettre), e.g. dragon devient Dragon //tmpMaj
		 * 2. Ajouter l’année courante au mot de passe, e.g. dragon devient dragon2018 //tmpAnnee
		 * 3. Remplacer les a par le caractère @, e.g. dragon devient dr@gon //tmpA
		 * 4. Remplacer les e par le chiffre 3, e.g. baseball devient bas3ball //tmp3
		 * 5. Remplacer les i par le chiffre 1, e.g. michael devient m1chael //tmp1
		 */
		
		String password;
		String encryptedPassword = null;
		String tmpMaj,tmpAnnee,tmpA,tmp3,tmp1;//tmp pour Majuscule,Annee,@,3,1
		int date = LocalDateTime.now().getYear();
		//int ctr = 1;
		for (int i = 0; i < commonPassword.size(); i++) {
			//next();	//eait utilisé pour clear les tmp value global mais je les ai enlevées
			//clear();
			
			password = commonPassword.get(i);
			ArrayList<String> tmp = new ArrayList<String>();
			int ctr = 1;
			tmp.add(password);
			for (int index = 0; index < password.length(); index++) {
				//ORDRE 3,4,5,1,2 fonctionne!!!
				
				//3. Remplacer les a par le caractère @, e.g. dragon devient dr@gon //tmpA
				if (password.charAt(index) == 'a') {
					for (int n = 0; n < ctr; n++) {
						tmpA = tmp.get(n).substring(0, index) + "@" + tmp.get(n).substring(index+1);
						tmp.add(tmpA);
					}
					ctr *= 2;
				}
				
				//4. Remplacer les e par le chiffre 3, e.g. baseball devient bas3ball //tmp3
				if (password.charAt(index) == 'e') {
					for (int n = 0; n <ctr; n++) {
						tmp3 = tmp.get(n).substring(0, index) + "3" + tmp.get(n).substring(index+1);
						tmp.add(tmp3);
					}
					ctr *= 2;
				}
				
				//5. Remplacer les i par le chiffre 1, e.g. michael devient m1chael //tmp1
				if (password.charAt(index) == 'i'){
					for (int n = 0; n < ctr; n++) {
						tmp1 = tmp.get(n).substring(0, index) + "1" + tmp.get(n).substring(index+1);
						tmp.add(tmp1);
					}
					ctr *= 2;
				}
			}
			
			//1. Mettre en majuscule la première lettre de chacun des mots de passe (si ils commencent par une lettre), e.g. dragon devient Dragon //tmpMaj
			for (int save = 0; save < ctr; save++) {
				tmpMaj = tmp.get(save);
				if (Character.isLetter(tmpMaj.charAt(0)))
					if (Character.isLowerCase(tmpMaj.charAt(0))) {
						tmpMaj = tmpMaj.substring(0, 1).toUpperCase() + tmpMaj.substring(1);
						if (!tmp.contains(tmpMaj)) {
							tmp.add(tmpMaj);
							ctr++;
						}
					}
			}
			//ctr*=2;//avec crash
			
			//2. Ajouter l’année courante au mot de passe, e.g. dragon devient dragon2018 //tmpAnnee
			for (int save = 0; save<ctr; save++) {
				tmpAnnee = tmp.get(save) + date;
				tmp.add(tmpAnnee);
			}
			ctr*=2;//compte pour la regle 1 et 2
			
			//Enregistre le password courrant dans la databse
		    for (int save = 0; save<ctr; save++) {
		    	password = tmp.get(save);
		    	try {
					encryptedPassword = Sha1.hash(password);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	database.save(password, encryptedPassword);
		    }
		}
	}
	
	public String crackPassword(String encryptedPassword, DatabaseInterface database) {
		//uses database to crack encrypted password, returning the original password
		return database.decrypt(encryptedPassword);
	}
}