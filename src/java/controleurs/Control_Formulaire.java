/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

/**
 * Classe pour le controle regEx des saisies dans les formulaires
 * @author Franck.Jakuboowski
 * @version 4.0
 */
public class Control_Formulaire {
  
  /**
   * Methode pour controler la saisie d un nom en majuscule
   * @param inputNom
   * @throws Exception 
   */
  public void validationNom(String inputNom) throws Exception {
	//  Si le champ n est pas vide lors de la validation
	if (inputNom != null && inputNom.trim().length() != 0 ) {	  
	  //  Si la saisie ne correspondant au format demande
	  if (!inputNom.matches("^[A-Z -]{1,30}$")) {
		throw new Exception("La saisie doit être en majuscule...");
	  } 
	} else {
	  throw new Exception("Vous n'avez rien saisi...");
	}
  }
  
  /**
   * Methode pour controler la saisie d un prenom commençant par une majuscule et
   * autorisant les accents graves et aigus et le 'ç'
   * @param inputPrenom
   * @throws Exception 
   */
  public void validationPrenom( String inputPrenom) throws Exception {
	//  Si le champ n est pas vide lors de la validation
	if (inputPrenom != null && inputPrenom.trim().length() != 0 ) {	  
	  //  Si la saisie ne correspondant au format demande
	  if (!inputPrenom.matches("^[A-Z][a-zéèç -]{1,30}$")) {
		throw new Exception("La saisie doit commencée par une majuscule...");
	  } 
	} else {
	  throw new Exception("Vous n'avez rien saisi...");
	}
  }
  
  /**
   * Methode pour controler la saisie du nom de la classe
   * @param inputNom
   * @throws Exception 
   */
  public void validationNomClasse(String inputNom) throws Exception {
	//  Si le champ n est pas vide lors de la validation
	if (inputNom != null && inputNom.trim().length() != 0 ) {	  
	  //  Si la saisie ne correspondant au format demande
	  if (!inputNom.matches("^[3-6][°][A-Z]$")) {
		throw new Exception("Les classe vont de la 6° à la 3° et sont au format : ex 6°A...");
	  } 
	} else {
	  throw new Exception("Vous n'avez rien saisi...");
	}
  }
  
  /**
   * Methode qui verifie qu un choix a ete fait dans une liste deroulante
   * @param choix
   * @throws Exception 
   */
  public void validationChoix(int choix) throws Exception {
	//  Si le champ est vide lors de la validation on envoie un message
	if (choix == 0) {	  
	  throw new Exception("Vous devez faire un choix...");
	}
  }
}
