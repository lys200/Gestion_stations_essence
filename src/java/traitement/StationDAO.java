/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package traitement;

import dbUtils.DBConnection;
import java.util.List;
import model.StationModel;
import service.IDAOStation;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BELCEUS
 */
public class StationDAO implements IDAOStation<StationModel> {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public int enregistrer(StationModel e) throws ClassNotFoundException, SQLException {
//        recuperer la connection a la base de donne
        con = DBConnection.getConnection();
//            requete d'insertion
        String requete = "INSERT INTO tabstation(AdresseGeog, capaciteStockGasoline, capaciteStockDiesel, quantiteGasoline, quantiteDiesel) values(?,?,?,?,?)";
//        utilisation de prepareStatement
        ps = con.prepareStatement(requete);
//        passage des parametres
        ps.setString(1, e.getAdresseGeog());
        ps.setDouble(2, e.getCapaciteStockGasoline());
        ps.setDouble(3, e.getCapaciteStockDiesel());
        ps.setInt(4, e.getQuantiteGasoline());
        ps.setInt(5, e.getQuantiteDiesel());

//        executer les requetes
        int n = ps.executeUpdate();
      
//        fermer les connections
        DBConnection.fermetureCon(rs, ps, con);

        return n;
    }

    @Override
    public int modifier(StationModel e)
            throws ClassNotFoundException, SQLException {
        int nb = 0;
//recuperer la connection
        con = DBConnection.getConnection();
//definir la requete pour modifier
        String req = "UPDATE  TABSTATION set capaciteStockGasoline =?,capaciteStockDiesel =? WHERE idStation =?";
//        appel a la methode prepareStatement
        ps = con.prepareStatement(req);
//        passage des parmetres
        ps.setDouble(1, e.getCapaciteStockGasoline());
        ps.setDouble(2, e.getCapaciteStockDiesel());
        ps.setString(3, e.getIdStation());
//        execution de la requete
        nb = ps.executeUpdate();
//fermeture de la connection
        DBConnection.fermetureCon(rs, ps, con);

        return nb;

    }

    @Override
    public List<StationModel> lister()
            throws ClassNotFoundException, SQLException {
        List<StationModel> lm = new ArrayList<StationModel>();

        //        recuperer la connection
        con = DBConnection.getConnection();
//            definir la requete
        String req = "SELECT * FROM TABSTATION";
//            utilisation de prepareStatement
        ps = con.prepareStatement(req);
//            utiliser le resultSet 
        rs = ps.executeQuery();
//            parcourir le resultSet
        StationModel sm = null;
        while (rs.next()) {
            sm = new StationModel();
            sm.setIdStation(rs.getString("IdStation"));
            sm.setAdresseGeog(rs.getString("AdresseGeog"));
            sm.setCapaciteStockGasoline(rs.getDouble("capaciteStockGasoline"));
            sm.setCapaciteStockDiesel(rs.getDouble("capaciteStockDiesel"));
            sm.setQuantiteGasoline(rs.getInt("quantiteGasoline"));
            sm.setQuantiteDiesel(rs.getInt("quantiteDiesel"));
//            ajout des stations 
            lm.add(sm);
        }

//            fermeture de la connection
        DBConnection.fermetureCon(rs, ps, con);
        return lm;

    }

    @Override
    public int ListequantiteGAzDispoParPourcentage(StationModel e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    

    @Override
    public StationModel rechercher(String id) throws ClassNotFoundException, SQLException {
//        recuperer la connexion de la base de donnees
        con = DBConnection.getConnection();
//definir la requete pour la recherche
        String req = "SELECT * FROM TABSTATION WHERE idStation =?";
//utilisation de prepareStatement
        ps = con.prepareStatement(req);
//        passage de parametre
        ps.setString(1, id);
//utilisation de resultSet
        rs = ps.executeQuery();
//        parcourir le resultSet
        StationModel sm = new StationModel();
        if (rs.next()) {

            sm.setIdStation(rs.getString("IdStation"));
            sm.setAdresseGeog(rs.getString("AdresseGeog"));
            sm.setCapaciteStockGasoline(rs.getDouble("capaciteStockGasoline"));
            sm.setCapaciteStockDiesel(rs.getDouble("capaciteStockDiesel"));
            sm.setQuantiteGasoline(rs.getInt("quantiteGasoline"));
            sm.setQuantiteDiesel(rs.getInt("quantiteDiesel"));
        }
//        fermeture de la connection
        DBConnection.fermetureCon(rs, ps, con);
//retour de l'objet sm
        return sm;
    }

}
