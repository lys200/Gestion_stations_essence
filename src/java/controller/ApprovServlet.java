package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ApprovisionnementModel;
import traitement.ApprovDAO;

@WebServlet(name = "ApprovServlet", urlPatterns = {"/ApprovServlet"})
public class ApprovServlet extends HttpServlet {

    private ApprovDAO approvdao;

    private final List<String> fournisseursAutorises = Arrays.asList("TOTAL", "PETROHAITI", "ESSO");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            approvdao = new ApprovDAO();
            List<?> stations = approvdao.listerStations();
            request.setAttribute("stations", stations);
            request.getRequestDispatcher("/Approvisionnement/AjoutApprov.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ApprovServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("Erreur", "Erreur lors du chargement des stations.");
            request.getRequestDispatcher("/Approvisionnement/AjoutApprov.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("ajouterApprov".equals(action)) {
            try {
                enregistrerApprov(request, response);
            } catch (Exception ex) {
                Logger.getLogger(ApprovServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("Erreur", "Erreur lors de l'enregistrement : " + ex.getMessage());
                doGet(request, response);
            }
        } else {
            doGet(request, response);
        }
    }

    private void enregistrerApprov(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, ParseException {

        approvdao = new ApprovDAO();

        String idStation = request.getParameter("idStation");
        String typeCarburant = request.getParameter("typeCarburant");
        String fournisseur = request.getParameter("fournisseur");
        String quantiteStr = request.getParameter("quantite");
        String dateApprovStr = request.getParameter("dateApprov");

        // Validation des paramètres
        if (idStation == null || idStation.isEmpty()
                || typeCarburant == null || typeCarburant.isEmpty()
                || fournisseur == null || fournisseur.isEmpty()
                || quantiteStr == null || quantiteStr.isEmpty()
                || dateApprovStr == null || dateApprovStr.isEmpty()) {
            request.setAttribute("Erreur", "Tous les champs sont obligatoires.");
            doGet(request, response);
            return;
        }

        if (!fournisseursAutorises.contains(fournisseur)) {
            request.setAttribute("Erreur", "Fournisseur invalide.");
            doGet(request, response);
            return;
        }

        if (!approvdao.stationExiste(idStation)) {
            request.setAttribute("Erreur", "Station inexistante.");
            doGet(request, response);
            return;
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteStr);
            if (quantite <= 0) {
                request.setAttribute("Erreur", "La quantité doit être un entier positif.");
                doGet(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("Erreur", "Quantité invalide.");
            doGet(request, response);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateApprov;
        try {
            dateApprov = sdf.parse(dateApprovStr);
        } catch (ParseException e) {
            request.setAttribute("Erreur", "Date invalide.");
            doGet(request, response);
            return;
        }

        String idApp = genererIdApprovisionnement();

        ApprovisionnementModel appModel = new ApprovisionnementModel();
        appModel.setIdApp(idApp);
        appModel.setIdStation(idStation);
        appModel.setTypeCarburant(typeCarburant);
        appModel.setFournisseur(fournisseur);
        appModel.setQuantite(quantite);
        appModel.setDateApprov(dateApprov);

        int result = approvdao.enregistrer(appModel);

        if (result > 0) {
            // Mise à jour automatique du stock
            int majStock = approvdao.mettreAJourStock(idStation, typeCarburant, quantite);

            if (majStock > 0) {
                request.setAttribute("successMessage", "Approvisionnement et mise à jour du stock réussis ! ID : " + idApp);
            } else {
                request.setAttribute("Erreur", "Approvisionnement enregistré mais échec de la mise à jour du stock.");
            }
            doGet(request, response);
        } else {
            request.setAttribute("Erreur", "Échec de l'enregistrement.");
            doGet(request, response);
        }
    }

    private String genererIdApprovisionnement() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(new Date());
        int randomNum = new Random().nextInt(900) + 100; // 100 à 999
        return "APP" + dateStr + randomNum;
    }
}
