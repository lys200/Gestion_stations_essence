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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.StationModel;
import traitement.StationDAO;

/**
 *
 * @author BELCEUS
 */
@WebServlet(name = "StationServlet", urlPatterns = {"/StationServlet"})
public class StationServlet extends HttpServlet {

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
        String action = null;
        action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "Modifier":
                    modifierStation(request, response);
                    break;
                case "Pourcentage_Gaz":
                    afficherPourcentageEssenceDispos(request, response);
                default:
                    load(request, response);
            }
        } else {
            load(request, response);
        }

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
        if (action != null) {
            switch (action) {
                case "Ajouter_station":
                    enregistrerStation(request, response);
                    break;
                case "Modifier":
                    modifierStation(request, response);
                    break;
                case "Pourcentage_Gaz":
                    afficherPourcentageEssenceDispos(request, response);
                    break;
                default:
                    load(request, response);
            }
        } else {
            load(request, response);
        }
    }

    protected void load(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            stdao = new StationDAO();
            List<StationModel> lstat = stdao.lister();
            request.setAttribute("stations", lstat);
            request.getRequestDispatcher("/station/index.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    methode enregistrer capacite de stock de gaz 

    protected void enregistrerStation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StationModel stmodel = null;
        StationDAO stdao = null;
//        creer une instance de StationModel
        stmodel = new StationModel();
//        recuperation des valeurs du formulaire avec getParameter

        String numero = request.getParameter("numero");
        int num = 0;
        if (numero != null && numero.matches("\\d+")) {
            num = Integer.parseInt(numero);
        } else {
            request.setAttribute("Erreur", "le numero doit etre des chiffres");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
        }

        String rue = request.getParameter("rue");

        String commune = request.getParameter("adresse");

        if (rue != null && commune != null) {
            String adresseComplete = numero + ", " + rue + ", " + commune;
            stmodel.setAdresseGeog(adresseComplete);
        }

//        double capacite = request.getParameter("capaciteGasoline") != null
//                ? Double.parseDouble(request.getParameter("capaciteGasoline")) : 0.0;
//        
        String capaciteGasoline = request.getParameter("capaciteGasoline");

        if (capaciteGasoline == null || capaciteGasoline.trim().isEmpty()) {
            request.setAttribute("Erreur", "La capacité de gasoline est requise.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
//verifier que des caracteres ne soient pas saisis
        if (!capaciteGasoline.matches("^-?\\d+(\\.\\d+)?$")) {
            request.setAttribute("Erreur", "La capacité doit être un nombre valide.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
        double capGaz = Double.parseDouble(capaciteGasoline);
        if (capGaz <= 0) {
            request.setAttribute("Erreur", "La capacité doit être supérieure à zéro.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
        stmodel.setCapaciteStockGasoline(capGaz);

        String capaciteDiesel = request.getParameter("capacitediesel");
        if (capaciteDiesel == null || capaciteDiesel.trim().isEmpty()) {
            request.setAttribute("Erreur", "La capacité de diesel est requise.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }

//verifier que des caracteres ne soient pas saisis
        if (!capaciteDiesel.matches("^-?\\d+(\\.\\d+)?$")) {
            request.setAttribute("Erreur", "La capacité doit être un nombre valide.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }

        double capDies = Double.parseDouble(capaciteGasoline);
        if (capDies <= 0) {
            request.setAttribute("Erreur", "La capacité doit être supérieure à zéro.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
        stmodel.setCapaciteStockDiesel(capDies);

//        verifier que la quantite ne soit pas superieure a la capacite
        String qteGaz = request.getParameter("qtegazo");

        if (qteGaz == null || qteGaz.isEmpty()) {
            request.setAttribute("Erreur", "La quantite doit etre saisie");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }

        if (!qteGaz.matches("^-?\\d+(\\.\\d+)?$")) {
            request.setAttribute("Erreur", "La quantite doit être un nombre valide.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
//         conversion de la quantite en entier
        int qtGaz = Integer.parseInt(qteGaz);
        if (qtGaz < 0) {
            request.setAttribute("Erreur", "La quantite de gasoline ne  doit être pas inferieure à zéro.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
        if (qtGaz > capGaz) {
            request.setAttribute("Erreur", "La quantite de gasoline  doit être supérieure à la capacite de stockage de gasoline.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
        stmodel.setQuantiteGasoline(qtGaz);
        
//        verifier que la quantite de diesel ne soit pas superiere a sa capacite
        String qtediesel = request.getParameter("qteDiesel");
        if (qtediesel == null || qteGaz.isEmpty()) {
            request.setAttribute("Erreur", "La quantite doit etre saisie");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
        if (!qtediesel.matches("^-?\\d+(\\.\\d+)?$")) {
            request.setAttribute("Erreur", "La quantite doit être un nombre valide.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
//         conversion de la quantite en entier
        int qtdiesel = Integer.parseInt(qtediesel);
        if (qtdiesel < 0) {
            request.setAttribute("Erreur", "La quantite de diesel ne  doit pas être  inferieure à zéro.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
        if (qtdiesel > capDies) {
            request.setAttribute("Erreur", "La quantite de diesel ne doit pas être supérieure à la capacite de stockage de diesel.");
            request.getRequestDispatcher("/station/ajouterStation.jsp").forward(request, response);
            return;
        }
        stmodel.setQuantiteDiesel(qtdiesel);

//        creer une instance de StationDAO
        stdao = new StationDAO();
        try {
            if (stdao.enregistrer(stmodel) > 0) {
                request.setAttribute("successMessage", "Enregistré avec succès !");
                load(request, response);

            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //    methode modifier capacite de stock de gaz 
    protected void modifierStation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String id = request.getParameter("id");
            StationDAO stdao = new StationDAO();

            if (id == null || id.isEmpty()) {
                request.setAttribute("Erreur", "Identifiant station introuvable.");
                load(request, response);
                return;
            }

            StationModel stmodel = stdao.rechercher(id);

            if (stmodel == null) {
                request.setAttribute("Erreur", "Station introuvable.");
                load(request, response);
                return;
            }

            // On récupère les paramètres du formulaire
            String capaciteGasoline = request.getParameter("capaciteGasoline");
            String capaciteDiesel = request.getParameter("capaciteDiesel");

            // Si on n'a pas de paramètres de modification, on affiche le formulaire
            if ((capaciteGasoline == null || capaciteGasoline.isEmpty())
                    && (capaciteDiesel == null || capaciteDiesel.isEmpty())) {

                request.setAttribute("station", stmodel);
                request.getRequestDispatcher("station/modifier.jsp").forward(request, response);
                return;
            }

            // Validation et modification capaciteGasoline
            if (capaciteGasoline != null && !capaciteGasoline.isEmpty()) {
                double capGas = Double.parseDouble(capaciteGasoline);
                if (capGas <= 0) {
                    request.setAttribute("Erreur", "La capacité de stockage gasoline doit être supérieure à zéro.");
                    request.setAttribute("station", stmodel);
                    request.getRequestDispatcher("station/modifier.jsp").forward(request, response);
                    return;
                }
                if (stmodel.getQuantiteGasoline() > capGas) {
                    request.setAttribute("Erreur", "La nouvelle capacité gasoline est inférieure à la quantité existante (" + stmodel.getQuantiteGasoline() + ").");
                    request.setAttribute("station", stmodel);
                    request.getRequestDispatcher("station/modifier.jsp").forward(request, response);
                    return;
                }
                stmodel.setCapaciteStockGasoline(capGas);
            }

            // Validation et modification capaciteDiesel
            if (capaciteDiesel != null && !capaciteDiesel.isEmpty()) {
                double capDies = Double.parseDouble(capaciteDiesel);
                if (capDies <= 0) {
                    request.setAttribute("Erreur", "La capacité de stockage du diesel doit être supérieure à zéro.");
                    request.setAttribute("station", stmodel);
                    request.getRequestDispatcher("station/modifier.jsp").forward(request, response);
                    return;
                }
                if (stmodel.getQuantiteDiesel() > capDies) {
                    request.setAttribute("Erreur", "La nouvelle capacité diesel est inférieure à la quantité existante (" + stmodel.getQuantiteDiesel() + ").");
                    request.setAttribute("station", stmodel);
                    request.getRequestDispatcher("station/modifier.jsp").forward(request, response);
                    return;
                }
                stmodel.setCapaciteStockDiesel(capDies);
            }

            // Mise à jour en base
            if (stdao.modifier(stmodel) > 0) {
                request.setAttribute("msg", "Modification effectuée avec succès.");
                request.setAttribute("station", stmodel);
                request.getRequestDispatcher("station/modifier.jsp").forward(request, response);
            } else {
                request.setAttribute("Erreur", "Échec de la modification.");
                request.setAttribute("station", stmodel);
                request.getRequestDispatcher("station/modifier.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StationServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("Erreur", "Erreur technique : " + ex.getMessage());
            request.getRequestDispatcher("station/modifier.jsp").forward(request, response);
        }
    }

//    methode pour gerer le pourcentage de essences dispos
    public void afficherPourcentageEssenceDispos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //        creer des instances de StationModel et StationDAO
            StationModel stmodel = null;
            StationDAO dao = new StationDAO();
//            recuperation de l'id du formulaire
            String id = request.getParameter("id");
            String type = request.getParameter("type");
            stmodel = dao.rechercher(id);
//            verifier que l'objet stmodel n'est pas null
            if (stmodel != null) {
                request.setAttribute("station", stmodel);
                request.setAttribute("type", type);
                request.getRequestDispatcher("station/pourcentageGazDispos.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "Station non trouvée");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //methode pour la connexion 
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
