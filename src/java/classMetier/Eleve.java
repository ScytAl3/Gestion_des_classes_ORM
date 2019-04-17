/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classMetier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * class Eleve
 * @author Franck.Jakuboowski
 * @version 4.0
 */
@Entity(name = "Eleve")
@Table(name = "eleve")
public class Eleve {
  
  // ID
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID_Eleve")
  private int id = 0;
  
  // Nom de l'élève
  @Column(name="Nom_Eleve", nullable=false, length=30)
  private String nom = "";
  
  // Prénom de l'élève
  @Column(name="Prenom_Eleve", nullable=false, length=30)
  private String prenom = "";
  
  // On relit l Eleve avec la Classe sur la foreign_key
  @ManyToOne
  @JoinColumn(name="ID_Classe")
  private Classe classe;
  
  /**
   * Constructeur
   * @param id
   * @param nom
   * @param prenom 
   */
  public Eleve(int id, String nom, String prenom) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
  }
  
  /**
   * Constructeur par defaut
   */
  public Eleve(){};
  
  /*==========================================================================
									  ASSESSEURS 
  ============================================================================*/

  /**
   *
   * @return
   */

     
  public int getId() {
    return id;
  }

  /**
   *
   * @param id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   *
   * @return
   */
  public String getNom() {
    return nom;
  }

  /**
   *
   * @param nom
   */
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   *
   * @return
   */
  public String getPrenom() {
    return prenom;
  }

  /**
   *
   * @param prenom
   */
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }
  /*-----------------------------------------------
   ASSESSEURS pour relier la classe Eleve et Classe
  -------------------------------------------------*/

  /**
   *
   * @return
   */

  public Classe getClasse() {
	return classe;
  }
  
  /**
   *
   * @param classe
   */
  public void setClasse (Classe classe) {
	this.classe = classe;
  }
}
