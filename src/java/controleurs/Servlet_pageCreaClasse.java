/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import classMetier.Classe;
import classMetier.Professeur;
import designpattern.ICommand;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	Servlet de controle de la page de creation d une classe
 * @author Franck.Jakuboowski
 * @version 4.0
 */
@WebServlet(name = "Servlet_pageCreaClasse", urlPatterns = {"/Servlet_pageCreaClasse"})
public class Servlet_pageCreaClasse extends HttpServlet implements ICommand {
  
  // Declaration des variables contenant les saisies du formulaire
  public static String CHAMP_NOM = "classeName";
  public static String CHAMP_CHOIX_PROF = "choix-prof";
  // Declaration des variables contenant les messages de validation du formulaire
  public static String MESSAGES_ERREURS = "erreur";
  public static String MESSAGES_RESULTAT = "resultat";
  // Instanciation de la class de controle du formulaire
  Control_Formulaire controlClasse = new Control_Formulaire();
  
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
	  // On instancie une liste de classes
	  List<Classe> lesClasses = em.createQuery("SELECT c FROM Classe c").getResultList();
	  // On récupère le nombre de classes
	  request.setAttribute("nbClasse", lesClasses.size() );
	  // On recupere laliste des classes et on l envoie
	  request.setAttribute("lesClasses", lesClasses);
	  // On instancie une liste de professeurs
	  List<Professeur> lesProfesseurs = em.createQuery("SELECT p FROM Professeur p").getResultList();
	  // On recupere laliste des professeurs et on l envoie
	  request.setAttribute("lesProfs", lesProfesseurs);
	  // Recuperation des champs du formulaire
	  String nom = request.getParameter(CHAMP_NOM);
	  int professeurID = Integer.parseInt(request.getParameter(CHAMP_CHOIX_PROF));
	  // On envoie la valeur de l index selectionne dans la liste deroulante
	  request.setAttribute("profID", professeurID);	
	  // Validation du nom de la classe
	  try {
		controlClasse.validationNomClasse(nom);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_NOM, ex.getMessage());
	  }
	  // Validation du choix de la matiere
	  try {
		controlClasse.validationChoix(professeurID);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_CHOIX_PROF, ex.getMessage());
	  }
	  // Verification que l eleve associe a cette classe n existe pas
	  /*===============================================================
	  ======					IN PROGRESS		    			  =====
	  =================================================================*/
	  // Initialisation du résultat global de la validation.
	  // Si la Map ne contient aucun message
	  if ( mErreurs.isEmpty()) {
		
		// On instancie un objet Classe avec le nom
		Classe addClasse = new Classe();
		addClasse.setNom(nom);
		// On recupere les informations du professeur selectione dans la liste deroulante
		Professeur leProfesseur = em.find(Professeur.class, professeurID);
		// On relie la classe au professeur grace a la relation @ManyToMany/@ManyToOne
		addClasse.addProfesseur(leProfesseur);
		
		try {
		  // Debut de la transaction
		  transac.begin();	
		  // Ajout de l eleve
		  em.persist(addClasse);
		  // Fin de la transaction
		  transac.commit();
		  // Mise a jour du tableau des eleves
		  List<Classe> nouveauxEleves = em.createQuery("SELECT c FROM Classe c").getResultList();
		  // On recupere le nombre d eleves
		  request.setAttribute("nombre", nouveauxEleves.size() );
		  // on envoie l'objet "classe"
		  request.setAttribute("lesClasses", nouveauxEleves );
		  // Message d information si la creation s est bien deroulee
		  resultForm = "Succès de la création de la classe \"" + addClasse.getNom() 
				  + "\"  associé à la classe \"" + leProfesseur.getNom() + " " + leProfesseur.getPrenom() + "\"...";
		  
		} catch (Exception ex) {
		  ex.printStackTrace();
		  //  Message si un probleme a eu lieu lors de la transaction
		  resultForm = "Echec de la création de la classe...";
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
	return ("jsp/PageCreaClasse.jsp");
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
	response.setContentType("text/html;charset=UTF-8");
	try (PrintWriter out = response.getWriter()) {
	  /* TODO output your page here. You may use following sample code. */
	  out.println("<!DOCTYPE html>");
	  out.println("<html>");
	  out.println("<head>");
	  out.println("<title>Servlet Servlet_pageCreaClasse</title>");	  
	  out.println("</head>");
	  out.println("<body>");
	  out.println("<h1>Servlet Servlet_pageCreaClasse at " + request.getContextPath() + "</h1>");
	  out.println("</body>");
	  out.println("</html>");
	}
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
