package traitement;

import dbUtils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ApprovisionnementModel;
import model.StationModel;

public class ApprovDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Enregistrer un approvisionnement
    public int enregistrer(ApprovisionnementModel approv) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String requete = "INSERT INTO tabapprovisionnement (idApp, idStation, TypeCarburant, quantite, dateApprov, fournisseur) VALUES (?, ?, ?, ?, ?, ?)";
        ps = con.prepareStatement(requete);
        ps.setString(1, approv.getIdApp());
        ps.setString(2, approv.getIdStation());
        ps.setString(3, approv.getTypeCarburant());
        ps.setInt(4, approv.getQuantite());
        ps.setDate(5, new java.sql.Date(approv.getDateApprov().getTime()));
        ps.setString(6, approv.getFournisseur());

        int n = ps.executeUpdate();
        DBConnection.fermetureCon(rs, ps, con);
        return n;
    }

    // Mise à jour automatique du stock après approvisionnement
    public int mettreAJourStock(String idStation, String typeCarburant, int quantiteAajouter) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();

        // Vérifier si le stock existe déjà
        String checkSql = "SELECT COUNT(*) FROM tabstock WHERE idStation = ? AND typeCarburant = ?";
        ps = con.prepareStatement(checkSql);
        ps.setString(1, idStation);
        ps.setString(2, typeCarburant);
        rs = ps.executeQuery();

        boolean existe = false;
        if (rs.next()) {
            existe = rs.getInt(1) > 0;
        }
        rs.close();
        ps.close();

        int resultat = 0;

        if (existe) {
            // Mise à jour stock existant
            String updateSql = "UPDATE tabstock SET quantite = quantite + ? WHERE idStation = ? AND typeCarburant = ?";
            ps = con.prepareStatement(updateSql);
            ps.setInt(1, quantiteAajouter);
            ps.setString(2, idStation);
            ps.setString(3, typeCarburant);
            resultat = ps.executeUpdate();
            ps.close();
        } else {
            // Insertion nouveau stock
            String insertSql = "INSERT INTO tabstock (idStation, typeCarburant, quantite) VALUES (?, ?, ?)";
            ps = con.prepareStatement(insertSql);
            ps.setString(1, idStation);
            ps.setString(2, typeCarburant);
            ps.setInt(3, quantiteAajouter);
            resultat = ps.executeUpdate();
            ps.close();
        }

        con.close();
        return resultat;
    }

    // Lister tous les approvisionnements
    public List<ApprovisionnementModel> lister() throws ClassNotFoundException, SQLException {
        List<ApprovisionnementModel> liste = new ArrayList<>();
        con = DBConnection.getConnection();
        String requete = "SELECT * FROM tabapprovisionnement";
        ps = con.prepareStatement(requete);
        rs = ps.executeQuery();

        while (rs.next()) {
            ApprovisionnementModel a = new ApprovisionnementModel();
            a.setIdApp(rs.getString("idApp"));
            a.setIdStation(rs.getString("idStation"));
            a.setTypeCarburant(rs.getString("TypeCarburant"));
            a.setQuantite(rs.getInt("quantite"));
            a.setDateApprov(rs.getDate("dateApprov"));
            a.setFournisseur(rs.getString("fournisseur"));

            liste.add(a);
        }

        DBConnection.fermetureCon(rs, ps, con);
        return liste;
    }

    // Rechercher un approvisionnement par id
    public ApprovisionnementModel rechercher(String idApp) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String requete = "SELECT * FROM tabapprovisionnement WHERE idApp = ?";
        ps = con.prepareStatement(requete);
        ps.setString(1, idApp);
        rs = ps.executeQuery();

        ApprovisionnementModel a = null;
        if (rs.next()) {
            a = new ApprovisionnementModel();
            a.setIdApp(rs.getString("idApp"));
            a.setIdStation(rs.getString("idStation"));
            a.setTypeCarburant(rs.getString("TypeCarburant"));
            a.setQuantite(rs.getInt("quantite"));
            a.setDateApprov(rs.getDate("dateApprov"));
            a.setFournisseur(rs.getString("fournisseur"));
        }

        DBConnection.fermetureCon(rs, ps, con);
        return a;
    }

    // Modifier un approvisionnement (optionnel)
    public int modifier(ApprovisionnementModel approv) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String requete = "UPDATE tabapprovisionnement SET idStation=?, TypeCarburant=?, quantite=?, dateApprov=?, fournisseur=? WHERE idApp=?";
        ps = con.prepareStatement(requete);
        ps.setString(1, approv.getIdStation());
        ps.setString(2, approv.getTypeCarburant());
        ps.setInt(3, approv.getQuantite());
        ps.setDate(4, new java.sql.Date(approv.getDateApprov().getTime()));
        ps.setString(5, approv.getFournisseur());
        ps.setString(6, approv.getIdApp());

        int n = ps.executeUpdate();
        DBConnection.fermetureCon(rs, ps, con);
        return n;
    }

    // Lister toutes les stations
    public List<StationModel> listerStations() throws ClassNotFoundException, SQLException {
        List<StationModel> liste = new ArrayList<>();
        con = DBConnection.getConnection();
        String requete = "SELECT idStation, adresseGeog FROM tabstation"; 
        ps = con.prepareStatement(requete);
        rs = ps.executeQuery();

        while (rs.next()) {
            StationModel station = new StationModel();
            station.setIdStation(rs.getString("idStation"));
            station.setAdresseGeog(rs.getString("adresseGeog"));
            liste.add(station);
        }

        DBConnection.fermetureCon(rs, ps, con);
        return liste;
    }

    // Vérifier si une station existe
    public boolean stationExiste(String idStation) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String requete = "SELECT COUNT(*) FROM tabstation WHERE idStation = ?";
        ps = con.prepareStatement(requete);
        ps.setString(1, idStation);
        rs = ps.executeQuery();

        boolean existe = false;
        if (rs.next()) {
            existe = rs.getInt(1) > 0;
        }

        DBConnection.fermetureCon(rs, ps, con);
        return existe;
    }
}
