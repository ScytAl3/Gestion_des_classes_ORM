/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import classMetier.Matiere;
import classMetier.Professeur;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import designpattern.ICommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *	Servlet de controle de la page de creation d un professeur
 * @author Franck.Jakuboowski
 * @version 4.0
 */
@WebServlet(name = "Servlet_pageLesProfesseurs", urlPatterns = {"/Servlet_pageLesProfesseurs"})
public class Servlet_pageLesProfesseurs extends HttpServlet implements ICommand {
  
  // Declaration des variables contenant les saisies du formulaire

  /**
   *
   */
  public static String CHAMP_NOM = "lastName";

  /**
   *
   */
  public static String CHAMP_PRENOM = "firstName";

  /**
   *
   */
  public static String CHAMP_CHOIX_MATIERE = "choix-matiere";
  // Declaration des variables contenant les messages de validation du formulaire

  /**
   *
   */
  public static String MESSAGES_ERREURS = "erreur";

  /**
   *
   */
  public static String MESSAGES_RESULTAT = "resultat";
  // Instanciation de la class de controle du formulaire
  Control_Formulaire controlProfesseur = new Control_Formulaire();

  /**
   * Implemente execute(HttpServletRequest, HttpServletResponse) de l interface ICommand
   * @param request
   * @param response
   * @return 
   */
  public String execute (HttpServletRequest request, HttpServletResponse response) {
	
	// Variables pour la gestion des messages lies au formulaire
	String resultForm = null;
	Map<String, String> mErreurs = new HashMap<String, String>();
	
	// Interactions entre la base de données et les beans entités assurées par un objet EntityManager
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Gestion_des_classes_ORM_PU");
	EntityManager em = emf.createEntityManager();
	EntityTransaction transac = em.getTransaction();
	
	try {
	  // On instancie une liste de Professeurs
	  List<Professeur> lesProfesseurs = em.createQuery("SELECT p FROM Professeur p").getResultList();
	  // On recupere le nombre de professeurs
	  request.setAttribute("nombre", lesProfesseurs.size() );
	  // On envoie l'objet liste de professeurs
	  request.setAttribute("lesProfesseurs", lesProfesseurs );
	  // On instancie une liste de Matieres
	  List<Matiere> lesMatieres = em.createQuery("SELECT m FROM Matiere m").getResultList();
	  // On recupere la liste des matieres et on l envoie
	  request.setAttribute("lesMatieres", lesMatieres);
	  // Recuperation des champs du formulaire
	  String nom = request.getParameter(CHAMP_NOM);
	  String prenom = request.getParameter(CHAMP_PRENOM);
	  int matiereID = Integer.parseInt(request.getParameter(CHAMP_CHOIX_MATIERE));
	  // On envoie la valeur de l index selectionne dans la liste deroulante
	  request.setAttribute("matiereID", matiereID);
	  // Validation du nom
	  try {
		controlProfesseur.validationNom(nom);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_NOM, ex.getMessage());
	  }
	  // Validation du prenom
	  try {
		controlProfesseur.validationPrenom(prenom);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_PRENOM, ex.getMessage());
	  }
	  // Validation du choix de la matiere
	  try {
		controlProfesseur.validationChoix(matiereID);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_CHOIX_MATIERE, ex.getMessage());
	  }
	  // Verification que le professeur associe a cette matiere n existe pas
	  /*===============================================================
	  ======					IN PROGRESS						  =====
	  =================================================================*/
	  // Initialisation du résultat global de la validation.
	  // Si la Map ne contient aucun message
	  if ( mErreurs.isEmpty() ) {
		
		// On instancie un objet Professeur avec le nom et le prenom du professeur
		Professeur addProfesseur = new Professeur();
		addProfesseur.setNom(nom);
		addProfesseur.setPrenom(prenom);
		// On recupere les informations de la matiere selectionee dans la liste deroulante
		Matiere laMatiere = em.find(Matiere.class, matiereID);
		// On relie le professeur a la matiere grace a la relation @OneToMany de Matiere
		addProfesseur.setMatiere(laMatiere);
		
		try {
		  // Debut de la transaction
		  transac.begin();	
		  // Ajout de l eleve
		  em.persist(addProfesseur);
		  // Fin de la transaction
		  transac.commit();
		  // Mise a jour du tableau des professeurs
		  List<Professeur> nouveauxProfesseurs = em.createQuery("SELECT p FROM Professeur p").getResultList();
		  // On recupere le nombre d eleves
		  request.setAttribute("nombre", nouveauxProfesseurs.size() );
		  // on envoie l'objet "classe"
		  request.setAttribute("lesProfesseurs", nouveauxProfesseurs );
		  // Message d information si la creation s est bien deroulee
		  resultForm = "Succès de la création du professeur \"" + addProfesseur.getNom() 
				  + "\" \"" + addProfesseur.getPrenom() + "\"  associé à la matière \"" 
				  + laMatiere.getNom() + "\"...";
		  
		} catch (Exception ex) {
		  ex.printStackTrace();
		  //  Message si un probleme a eu lieu lors de la transaction
		  resultForm = "Echec de la création du professeur...";
		}
	  } else {
		// Message d information si la creation s est mal deroulee
		resultForm = "Problème lors de la validation du formulaire...";
	  }
	}catch (Exception ex) {
	  
	  ex.printStackTrace();
	}
	// Stockage du resultat et des messages d erreur dans l objet request
	request.setAttribute( MESSAGES_ERREURS, mErreurs );
	request.setAttribute( MESSAGES_RESULTAT, resultForm );
	return ("jsp/pageLesProfesseurs.jsp");
  }
  
  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {
	processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {
	processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
	return "Short description";
  }// </editor-fold>

}
