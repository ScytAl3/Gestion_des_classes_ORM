/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import classMetier.Matiere;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import designpattern.ICommand;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *	Servlet de controle de la page de creation d une matiere
 * @author Franck.Jakuboowski
 * @version 4.0
 */
@WebServlet(name = "Servlet_pageLesMatieres", urlPatterns = {"/Servlet_pageLesMatieres"})
public class Servlet_pageLesMatieres extends HttpServlet implements ICommand {
  
  // Declaration de la variables contenant la saisie du nom de la matiere
  public static String CHAMP_NOM = "matiere";
  // Declaration des variables contenant les messages de validation du formulaire
  public static String MESSAGES_ERREURS = "erreur";
  public static String MESSAGES_RESULTAT = "resultat";
  // Instanciation de la class de controle du formulaire
  Control_Formulaire controlMatiere = new Control_Formulaire();
     
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
	  // On instancie une liste de Matieres
	  List<Matiere> lesMatieres = em.createQuery("SELECT m FROM Matiere m").getResultList();
	  // On recupere le nombre de matieres
	  request.setAttribute("nombre", lesMatieres.size() );
	  // On envoie l'objet liste de matieres
	  request.setAttribute("lesProfesseurs", lesMatieres );
	  // Recuperation des champs du formulaire
	  String nomMatiere = request.getParameter(CHAMP_NOM);
	  // Validation du nom de la matiere
	  try {
		controlMatiere.validationPrenom(nomMatiere);
	  } catch (Exception ex) {
		mErreurs.put(CHAMP_NOM, ex.getMessage());
	  }
	  // Verification que la matiere n existe pas deja
	  /*===============================================================
	  ======					IN PROGRESS						  =====
	  =================================================================*/
	  // Initialisation du résultat global de la validation.
	  // Si la Map ne contient aucun message
	  if ( mErreurs.isEmpty() ) {
		
		// On instancie un objet Matiere avec le nom de la matiere
		Matiere addMatiere = new Matiere();
		addMatiere.setNom(nomMatiere);
		
		try {
		  // Debut de la transaction
		  transac.begin();	
		  // Ajout de l eleve
		  em.persist(addMatiere);
		  // Fin de la transaction
		  transac.commit();
		  // Mise a jour du tableau des matiere
		  List<Matiere> nouvellesMatieres = em.createQuery("SELECT m FROM Matiere m").getResultList();
		  //On récupère le nombre des élèves
		  request.setAttribute("nombre", nouvellesMatieres.size() );
		  // on envoie l'objet "classe"
		  request.setAttribute("lesMatieres", nouvellesMatieres );		  
		  // Message d information si la creation s est bien deroulee
		  resultForm = "Succès de la création de la matiere \"" + nomMatiere + "\"...";
		  
		} catch (Exception ex) {
		  ex.printStackTrace();
		  //  Message si un probleme a eu lieu lors de la transaction
		  resultForm = "Echec de la création de la matiere...";
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
	return ("jsp/pageLesMatieres.jsp");
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
