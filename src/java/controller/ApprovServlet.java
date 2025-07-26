package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ApprovisionnementModel;
import model.StationModel;
import traitement.ApprovDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "ApprovServlet", urlPatterns = {"/ApprovServlet"})
public class ApprovServlet extends HttpServlet {

    private ApprovDAO approvdao = new ApprovDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
       try (PrintWriter out = response.getWriter()) {
           /* TODO output your page here. You may use following sample code. */
           out.println("<!DOCTYPE html>");
           out.println("<html>");
           out.println("<head>");
           out.println("<title>Servlet ApprovServlet</title>");
           out.println("</head>");
           out.println("<body>");
           out.println("<h1>Servlet ApprovServlet at " + request.getContextPath() + "</h1>");
           out.println("</body>");
           out.println("</html>");
       }
   } 

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       String action = request.getParameter("action");
       if (action == null) action = "accueil";

       try {
           switch (action) {
               case "afficherFormAjout":
                   afficherFormAjout(request, response);
                   break;
               case "afficherListe":
                   afficherListe(request, response);
                   break;
               default:
                   response.sendRedirect("Approvisionnement/index.jsp");
                   break;
           }
       } catch (Exception ex) {
           request.setAttribute("Erreur", "Erreur inattendue : " + ex.getMessage());
           request.getRequestDispatcher("Approvisionnement/index.jsp").forward(request, response);
       }
   }

   private void afficherFormAjout(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
       List<StationModel> stations = approvdao.listerStations();
       request.setAttribute("stations", stations);
       request.getRequestDispatcher("/Approvisionnement/AjoutApprov.jsp").forward(request, response);
   }

   private void afficherListe(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
       Map<String, Object> filtres = new HashMap<>();

       String filterStation = request.getParameter("filterStation");
       String filterFournisseur = request.getParameter("filterFournisseur");
       String filterDate = request.getParameter("filterDate");

       if (filterStation != null && !filterStation.trim().isEmpty()) filtres.put("idStation", filterStation);
       if (filterFournisseur != null && !filterFournisseur.trim().isEmpty()) filtres.put("fournisseur", filterFournisseur);
       if (filterDate != null && !filterDate.trim().isEmpty()) filtres.put("dateApprov", filterDate);

       List<ApprovisionnementModel> listeApprovs = approvdao.listerApprovisionnementsFiltres(filtres);
       List<StationModel> stations = approvdao.listerStations();

       request.setAttribute("listeApprovs", listeApprovs);
       request.setAttribute("stations", stations);

       // Pour pré-remplir les filtres dans la JSP
       request.setAttribute("filterStation", filterStation);
       request.setAttribute("filterFournisseur", filterFournisseur);
       request.setAttribute("filterDate", filterDate);

       request.getRequestDispatcher("/Approvisionnement/AffichApprovs.jsp").forward(request, response);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       String action = request.getParameter("action");
       if ("ajouterApprov".equals(action)) {
           try {
               ajouterApprovisionnement(request, response);
           } catch (Exception ex) {
               request.setAttribute("Erreur", "Erreur lors de l'ajout : " + ex.getMessage());
               try {
                   afficherFormAjout(request, response);
               } catch (Exception e) {
                   throw new ServletException(e);
               }
           }
       } else {
           doGet(request, response);
       }
   }

   private void ajouterApprovisionnement(HttpServletRequest request, HttpServletResponse response) throws Exception {
       String idStation = request.getParameter("idStation");
       String typeCarburant = request.getParameter("typeCarburant");
       String fournisseur = request.getParameter("fournisseur");
       String quantiteStr = request.getParameter("quantite");

       // Validation simple (champs obligatoires)
       if (idStation == null || idStation.isEmpty() ||
           typeCarburant == null || typeCarburant.isEmpty() ||
           fournisseur == null || fournisseur.isEmpty() ||
           quantiteStr == null || quantiteStr.isEmpty()) {

           request.setAttribute("Erreur", "Tous les champs sont obligatoires.");
           afficherFormAjout(request, response);
           return;
       }

       int quantite;
       try {
           quantite = Integer.parseInt(quantiteStr);
           if (quantite <= 0) throw new NumberFormatException();
       } catch (NumberFormatException e) {
           request.setAttribute("Erreur", "Quantité invalide.");
           afficherFormAjout(request, response);
           return;
       }

       if (!approvdao.stationExiste(idStation)) {
           request.setAttribute("Erreur", "Station inexistante.");
           afficherFormAjout(request, response);
           return;
       }

       // **Vérification de la capacité restante**
       StationModel station = null;
       List<StationModel> stations = approvdao.listerStations();
       for (StationModel s : stations) {
           if (s.getIdStation().equals(idStation)) {
               station = s;
               break;
           }
       }

       if (station == null) {
           request.setAttribute("Erreur", "Station non trouvée.");
           afficherFormAjout(request, response);
           return;
       }

       int quantiteDisponibleRestante;
       if ("gazoline".equalsIgnoreCase(typeCarburant)) {
           quantiteDisponibleRestante = (int)(station.getCapaciteStockGasoline() - station.getQuantiteGasoline());
       } else if ("diesel".equalsIgnoreCase(typeCarburant)) {
           quantiteDisponibleRestante = (int)(station.getCapaciteStockDiesel() - station.getQuantiteDiesel());
       } else {
           request.setAttribute("Erreur", "Type de carburant inconnu.");
           afficherFormAjout(request, response);
           return;
       }

       if (quantite > quantiteDisponibleRestante) {
           request.setAttribute("Erreur", "Quantité trop élevée. Capacité restante disponible : " + quantiteDisponibleRestante);
           afficherFormAjout(request, response);
           return;
       }

       // Date actuelle automatique (timestamp)
       Date dateActuelle = new Date();

       // Générer ID approvisionnement unique
       String idApp = genererIdApprovisionnement();

       ApprovisionnementModel approv = new ApprovisionnementModel();
       approv.setIdApp(idApp);
       approv.setIdStation(idStation);
       approv.setTypeCarburant(typeCarburant);
       approv.setFournisseur(fournisseur);
       approv.setQuantite(quantite);
       approv.setDateApprov(dateActuelle);

       int res = approvdao.enregistrerAvecMajStock(approv);

       if (res > 0) {
           String msg = "Approvisionnement enregistré avec succès le " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateActuelle);
           request.setAttribute("successMessage", msg);
           afficherFormAjout(request, response);
       } else {
           request.setAttribute("Erreur", "Erreur lors de l'enregistrement.");
           afficherFormAjout(request, response);
       }
   }

   private String genererIdApprovisionnement() {
       return "APP" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (new Random().nextInt(900) + 100);
   }
}
