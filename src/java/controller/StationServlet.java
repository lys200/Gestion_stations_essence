/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StationModel;
import traitement.StationDAO;

/**
 *
 * @author BELCEUS
 */
@WebServlet(name = "StationServlet", urlPatterns = {"/StationServlet"})
public class StationServlet extends HttpServlet {
    StationModel stmodel = null;
    StationDAO stdao = null;
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
            out.println("<title>Servlet StationServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StationServlet at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);

    response.getWriter().println("StationServlet appelé !");


              System.out.println("StationServlet appelé !"); 
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
        enregistrerStation(request, response);
    }
    
    protected void enregistrerStation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
//        creer une instance de StationModel
        stmodel = new StationModel();
//        recuperation des valeurs du foemulaire avec getParameter
        stmodel.setAdresseGeog(request.getParameter("adresse"));
        double capacite = request.getParameter("capaciteGazoline") != null 
                ? Double.parseDouble(request.getParameter("capaciteGazoline")): 0.0;
        stmodel.setCapaciteStockGasoline(capacite);
        double capDies = request.getParameter("capaciteDiesel") != null ?
                Double.parseDouble(request.getParameter("capaciteDiesel")): 0.0;
        stmodel.setCapaciteStockDiesel(capDies);
        
        int qteGaz = request.getParameter("qtegazo") != null ?
                Integer.parseInt(request.getParameter("qtegazo")) : 0;
        stmodel.setQuantiteGAsoline(qteGaz);
        int qted = request.getParameter("qteDiesel") != null ?
                Integer.parseInt(request.getParameter("qteDiesel")) : 0;
        stmodel.setQuantiteDiesel(qted);
//        creer une instance de StationDAO
        stdao = new StationDAO();
        try {
            if(stdao.enregistrer(stmodel) > 0){
                request.getRequestDispatcher("/Stations/ajouterStation.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
