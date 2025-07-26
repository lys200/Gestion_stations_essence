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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Utilisateurs;
import traitement.LoginDao;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    LoginDao ldao = null;

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
            out.println("<title>Servlet ConnectionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConnectionServlet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        switch (action) {
            case "Connection":
                recupererInfoFormulaire(request, response);
                break;
            case "S'inscrire":
                inscription(request, response);
                break;
            default:
                System.out.println("redirection");
        }

    }

    private void recupererInfoFormulaire(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //        recuperation des parametres du formulaire
        String nomUtil = request.getParameter("nomutilisateur");
        String modpas = request.getParameter("password");
//        instancier LoginDao
        ldao = new LoginDao();
        if (nomUtil != null && modpas != null) {

            try {
                Utilisateurs ut = ldao.rechercher(nomUtil, modpas);

                if (ut != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", ut);
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else {

                    request.setAttribute("Erreur", "Cet utilisateur n'existe pas. Veuillez creer un compte");
                    request.getRequestDispatcher("/connection.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            response.sendRedirect("connection.jsp");
        }
    }
//    methpde gerer l'inscription

    public void inscription(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utilisateurs ut = new Utilisateurs();
        LoginDao ldao = new LoginDao();
//       recuperer les names du formulaire
        String nomUtil = request.getParameter("nomutilisateur");
        String modpas = request.getParameter("password");
        if (nomUtil != null && modpas != null) {
            try {
                //          creer un utilisateur
                ut.setNomUtilisateur(nomUtil);
                ut.setMotDePasse(modpas);
                ut.setEtat("A");
                int res = ldao.inscription(ut);

                if (res > 0) {
                    request.setAttribute("msg", "Inscription réussie. Veuillez vous connecter.");
                    request.getRequestDispatcher("connection.jsp").forward(request, response);
                } else {
                    request.setAttribute("Erreur", "Erreur lors de l'inscription.");
                    request.getRequestDispatcher("Inscription.jsp").forward(request, response);
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("Erreur", "Ce nom d'utilisateur existe deja dans la base de donnees Veuillez choisir un autre nom et un autre mot de passe.");
                request.getRequestDispatcher("Inscription.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("Erreur", "Tous les champs sont obligatoires !");
        request.getRequestDispatcher("Inscription.jsp").forward(request, response);

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
