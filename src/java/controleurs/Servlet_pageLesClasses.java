/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import classMetier.Classe;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import designpattern.ICommand;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *	Servlet de controle de la page d affichage des informations sur les classes
 * @author Franck.Jakuboowski
 * @version 4.0
 */
@WebServlet(name = "Servlet_pageLesClasses", urlPatterns = {"/Servlet_pageLesClasses"})
public class Servlet_pageLesClasses extends HttpServlet implements ICommand {

  /**
   * Implemente execute(HttpServletRequest, HttpServletResponse) de l interface ICommand
   * @param request
   * @param response
   * @return 
   */
  public String execute (HttpServletRequest request, HttpServletResponse response) {
	
	// Interactions entre la base de données et les beans entités assurées par un objet EntityManager
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Gestion_des_classes_ORM_PU");
	EntityManager em = emf.createEntityManager();
	EntityTransaction transac = em.getTransaction();
	
	try{
	  // Debut de la transaction
	  transac.begin();
	  // On instancie une liste de Classes
	  List<Classe> lesClasses = em.createQuery("SELECT c FROM Classe c").getResultList();
	  // Fin de la transaction
	  transac.commit();
	  // On recupere le nombre de classes
	  request.setAttribute("nbClasse", lesClasses.size() );
	  // On recupere laliste des matieres et on l envoie
	  request.setAttribute("lesClasses", lesClasses);
	  // Recuperation de l index lors du choix dans la liste deroulante
	  int classeID = Integer.parseInt(request.getParameter("choix-classe"));
	  // On recupere les informations de la classe selectionee dans la liste deroulante
	  Classe laClasse = em.find(Classe.class, classeID);
	  // On envoie l'objet "classe"
	  request.setAttribute("laclasse", laClasse );
	  // On envoie le nombre des élèves
	  request.setAttribute("nombre", laClasse.getListEleve().size() );
	  
	  // On envoie la valeur de l index selectionne dans la liste deroulante pour
	  // ne pas reset la selection dans la liste deroulante
	  request.setAttribute("classeID", classeID);
	  
	}catch (Exception ex) {
	  ex.printStackTrace();	  
	}
	return ("jsp/pageLesClasses.jsp");
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