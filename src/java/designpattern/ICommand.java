/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designpattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Interface qui sera implementee pour chaque nouvelle commande d un servlet de controle
 * @author Franck.Jakuboowski
 * @version 4.0
 */
public interface ICommand {

  /**
   *
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
