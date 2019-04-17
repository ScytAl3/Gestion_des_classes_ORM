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
 *	class Professeur
 * @author Stagiaire
 */
@Entity(name = "Professeur")
@Table(name = "professeur")
public class Professeur {
  
  //ID
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID_Professeur")
  private int id = 0;
  
  //Nom du professeur
  @Column(name="Nom_Professeur", nullable=false, length=30)
  private String nom = "";
  
  //Prénom du professeur
  @Column(name="Prenom_Professeur", nullable=false, length=30)
  private String prenom = "";
  
  //  On relit le professeur avec la matiere sur la foreign_Key
  @ManyToOne
  @JoinColumn(name="ID_Matiere")
  private Matiere matiere;
  
  /**
   * Constructeur
   * @param id
   * @param nom
   * @param prenom 
   */
  public Professeur(int id, String nom, String prenom) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
  }

  /**
   * Constructeur par defaut
   */
  public Professeur(){}

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
  /*-------------------------------------------------------
   ASSESSEURS pour relier la classe Professeur et Matiere
  ---------------------------------------------------------*/

  /**
   *
   * @return
   */

  public Matiere getMatiere() {
	return matiere;
  }
  
  /**
   *
   * @param matiere
   */
  public void setMatiere(Matiere matiere) {
	this.matiere = matiere;
  }  
}
