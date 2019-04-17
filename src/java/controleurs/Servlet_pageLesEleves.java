/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import classMetier.Classe;
import classMetier.Eleve;
import java.io.IOException;
import java.io.PrintWriter;
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
 *	Servlet de controle de la page de creation d un eleve
 * @author Franck.Jakuboowski
 * @version 4.0
 */
@WebServlet(name = "Servlet_pageLesEleves", urlPatterns = {"/Servlet_pageLesEleves"})
public class Servlet_pageLesEleves extends HttpServlet implements ICommand {
  
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
  public static String CHAMP_CHOIX_CLASSE = "choix-classe";
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
  Control_Formulaire controlEleve = new Control_Formulaire();  
  
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
	
	try{	  
	  // On instancie une liste d Eleves
	  List<Eleve> lesEleves = em.createQuery("SELECT e FROM Eleve e").getResultList();
	  // On recupere le nombre d eleves
	  request.setAttribute("nombre", lesEleves.size() );
	  // On envoie l'objet liste d eleves
	  request.setAttribute("lesEleves", lesEleves );
	  // On instancie une liste de Classes
	  List<Classe> lesClasses = em.createQuery("SELECT c FROM Classe c").getResultList();
	  // On recupere laliste des matieres et on l envoie
	  request.setAttribute("lesClasses", lesClasses);
	  
	  // Recuperation des champs du formulaire
	  String nom = request.getParameter(CHAMP_NOM);
	  String prenom = request.getParameter(CHAMP_PRENOM);
	  int classeID = Integer.parseInt(request.getParameter(CHAMP_CHOIX_CLASSE));
	  // On envoie la valeur de l index selectionne dans la liste deroulante
	  request.setAttribute("classeID", classeID);
	  // Validation du nom
	  try {
		controlEleve.validationNom(nom);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_NOM, ex.getMessage());
	  }
	  // Validation du prenom
	  try {
		controlEleve.validationPrenom(prenom);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_PRENOM, ex.getMessage());
	  }
	  // Validation du choix de la classe
	  try {
		controlEleve.validationChoix(classeID);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_CHOIX_CLASSE, ex.getMessage());
	  }
	  // Verification que l eleve associe a cette classe n existe pas
	  /*===============================================================
	  ======					IN PROGRESS						  =====
	  =================================================================*/
	  // Initialisation du résultat global de la validation.
	  // Si la Map ne contient aucun message
	  if ( mErreurs.isEmpty()) {
		
		// On instancie un objet Eleve avec le nom et le prenom de l eleve
		Eleve addEleve = new Eleve();
		addEleve.setNom(nom);
		addEleve.setPrenom(prenom);
		// On recupere les informations de la classe selectionee dans la liste deroulante
		Classe laClasse = em.find(Classe.class, classeID);
		// On relie l eleve a la classe grace a la relation @OneToMany/@ManyToOne
		addEleve.setClasse(laClasse);
		
		try {
		  // Debut de la transaction
		  transac.begin();	
		  // Ajout de l eleve
		  em.persist(addEleve);
		  // Fin de la transaction
		  transac.commit();
		  // Mise a jour du tableau des eleves
		  List<Eleve> nouveauxEleves = em.createQuery("SELECT e FROM Eleve e").getResultList();
		  // On recupere le nombre d eleves
		  request.setAttribute("nombre", nouveauxEleves.size() );
		  // on envoie l'objet "classe"
		  request.setAttribute("lesEleves", nouveauxEleves );
		  // Message d information si la creation s est bien deroulee
		  resultForm = "Succès de la création de l'élève \"" + addEleve.getNom() 
				  + "\" \"" + addEleve.getPrenom() + "\"  associé à la classe \"" 
				  + laClasse.getNom() + "\"...";
		  
		} catch (Exception ex) {
		  ex.printStackTrace();
		  //  Message si un probleme a eu lieu lors de la transaction
		  resultForm = "Echec de la création de l'élève...";
		}		
	  } else {
		// Message d information si la creation s est mal deroulee
		resultForm = "Problème lors de la validation du formulaire...";
	  }
	  /* 	  
	  // On verifie l existence de l eleve (un n° INSEE serait mieux)
	  try {
		checkExister(monEleveDAO, nom, prenom);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_NOM, ex.getMessage());
	  }
	  */  
	}catch (Exception ex) {
	  ex.printStackTrace();
	}
	// Stockage du resultat et des messages d erreur dans l objet request
	request.setAttribute( MESSAGES_ERREURS, mErreurs );
	request.setAttribute( MESSAGES_RESULTAT, resultForm );
	return ("jsp/pageLesEleves.jsp");
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
	  out.println("<title>Servlet temp</title>");	  
	  out.println("</head>");
	  out.println("<body>");
	  out.println("<h1>Servlet temp at " + request.getContextPath() + "</h1>");
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
