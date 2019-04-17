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
import javax.persistence.Table;

/**
 *	class Matiere
 * @author Franck.Jakuboowski
 * @version 4.0
 */
@Entity(name = "Matiere")
@Table(name = "matiere")
public class Matiere {
  
  //ID
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID_Matiere")
  private int id = 0;
  
  //Nom de la matiere
  @Column(name="Nom_Matiere", nullable=false, length=30)
  private String nom = "";     
  
  /**
   * Constructeur
   * @param id
   * @param nom 
   */
  public Matiere(int id, String nom) {
    this.id = id;
    this.nom = nom;
  }

  /**
   * Constructeur par defaut
   */
  public Matiere(){}

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
}
