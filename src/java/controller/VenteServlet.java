/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.StationModel;

import model.VentesModel;
import traitement.StationDAO;
import traitement.VentesDAO;

/**
 *
 * @author BELCEUS
 */
@WebServlet(name = "VenteServlet", urlPatterns = {"/VenteServlet"})
public class VenteServlet extends HttpServlet {

    VentesModel vm = null;
    VentesDAO vdao = null;

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
            out.println("<title>Servlet VenteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VenteServlet at " + request.getContextPath() + "</h1>");
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
        loadVente(request, response);
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
        String action = request.getParameter("action");
        switch (action) {
            case "Ajouter_ventes":
                enregistrerVentes(request, response);
                break;
            default:
                loadVente(request, response);
        }
    }

    public void enregistrerVentes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String qteVendu = request.getParameter("qtev");
        String typecarburant = request.getParameter("typecarburant");
        String dateV = request.getParameter("datevente");
        String prixVente = request.getParameter("prix");
        String idStation = request.getParameter("idStation");
//            verifier que idstation existe
        StationDAO sdao = new StationDAO();
        try {
            StationModel smod = sdao.rechercher(idStation);
            if (smod == null) {
                request.setAttribute("msg", "Idstation inexistant.");
                request.getRequestDispatcher("/Ventes/AjoutVentes.jsp").forward(request, response);
                return;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(VenteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
//            convertion des parametres
        double qvendu = Double.parseDouble(qteVendu);
        if (qvendu <= 0) {
            request.setAttribute("msg", "La quantite vendu ne peut pas etre inferieure ou egal a zero");
            request.getRequestDispatcher("/Ventes/AjoutVentes.jsp").forward(request, response);
            return;
        }
        double prixv = Double.parseDouble(prixVente);
        if (prixv <= 0) {
            request.setAttribute("msg", "le prix ne doit pas etre inferieur ou egal a zero.");
            request.getRequestDispatcher("/Ventes/AjoutVentes.jsp").forward(request, response);
            return;
        }
//        traitement de la date pour la vente 
        java.sql.Date datevant;
        if (dateV == null || dateV.isEmpty()) {

            long millis = System.currentTimeMillis();
            datevant = new java.sql.Date(millis);
        } else {

            datevant = java.sql.Date.valueOf(dateV);
        }
        double prixTotal = qvendu * prixv;
//        instance dr ventemodel
        vm = new VentesModel();

        vm.setQuantiteVendu(qvendu);
        vm.setTypeCarburantVente(typecarburant);
        vm.setPrixVente(prixv);
        vm.setDateVente(datevant);
        vm.setIdStation(idStation);

//        instance de ventes dao 
        vdao = new VentesDAO();
        try {
            if (vdao.enregistrerVente(vm) > 0) {
                request.setAttribute("msg", "enregistrement succes");
                loadVente(request, response);
            } else {
                request.setAttribute("msg", "Échec de l'enregistrement !");
                request.getRequestDispatcher("/Ventes/index.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VenteServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VenteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//methode pour load les donnees de ventes 

    public void loadVente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            vdao = new VentesDAO();
            List<VentesModel> lvmod = vdao.listerVente();
            // Calcul du revenu total toutes stations
            double revenuTotal = vdao.calculRevenuVente();

            // Passer les ventes et le revenu total à la JSP
            request.setAttribute("ventes", lvmod);
            request.setAttribute("revenuTotal", revenuTotal);

            request.getRequestDispatcher("/Ventes/index.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException ex) {
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
