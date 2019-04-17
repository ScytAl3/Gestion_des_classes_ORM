/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classMetier;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * class Classe
 * @author Franck.Jakuboowski
 * @version 4.0
 */
@Entity(name = "Classe")
@Table(name = "classe")
public class Classe {
  
  //ID
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID_Classe")
  private int id = 0;
  
  //Nom de la classe
  @Column(name="Nom_Classe", nullable=false, length=4)
  private String nom = "";
  
  //Liste des professeurs
  @ManyToMany(cascade = {
	CascadeType.PERSIST,
	CascadeType.MERGE
  })
  @JoinTable(name = "instruire",
		  joinColumns = @JoinColumn (name = "ID_Classe"),
		  inverseJoinColumns = @JoinColumn(name = "ID_Professeur")
  )
  private Set<Professeur> listProfesseur = new HashSet<Professeur>();
  
  //Liste des élèves
  @OneToMany
  @JoinColumn(name = "ID_Classe")
  private Set<Eleve> listEleve = new HashSet<Eleve>();

  /**
   * Constructeur
   * @param id
   * @param nom 
   */
  public Classe(int id, String nom) {
    this.id = id;
    this.nom = nom;
  }
  
  /**
   * Constructeur par defaut
   */
  public Classe(){}

  /*==========================================================================
		ASSESSEURS et methode d ajout et suppression dans les listes
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
  public Set<Professeur> getListProfesseur() {
    return listProfesseur;
  }

  /**
   *
   * @param listProfesseur
   */
  public void setListProfesseur(Set<Professeur> listProfesseur) {
    this.listProfesseur = listProfesseur;
  }

  /**
   *
   * @param prof
   */
  public void addProfesseur(Professeur prof) {
    if(!listProfesseur.contains(prof))
      listProfesseur.add(prof);
  }

  /**
   *
   * @param prof
   */
  public void removeProfesseur(Professeur prof ) {
    this.listProfesseur.remove(prof);
  }

  /**
   *
   * @return
   */
  public Set<Eleve> getListEleve() {
    return listEleve;
  }

  /**
   *
   * @param listEleve
   */
  public void setListEleve(Set<Eleve> listEleve) {
    this.listEleve = listEleve;
  }

  
  //Ajoute un élève à la classe

  /**
   *
   * @param eleve
   */
  public void addEleve(Eleve eleve){
    if(!this.listEleve.contains(eleve))
      this.listEleve.add(eleve);
  }

  //Retire un élève de la classe

  /**
   *
   * @param eleve
   */
  public void removeEleve(Eleve eleve){
    this.listEleve.remove(eleve);
  }

  /**
   *
   * @param cls
   * @return
   */
  public boolean equals(Classe cls){
    return this.getId() == cls.getId();
  }   
}
