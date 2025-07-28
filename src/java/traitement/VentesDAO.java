/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package traitement;

import dbUtils.DBConnection;
import model.VentesModel;
import service.IDAOVentes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.StationModel;

/**
 *
 * @author BELCEUS
 */
public class VentesDAO implements IDAOVentes<VentesModel> {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public int enregistrerVente(VentesModel e) throws ClassNotFoundException, SQLException {
        int n = 0;
//        recuperer connection
        con = DBConnection.getConnection();
//          requete 
        String req = "INSERT INTO TABVENTES(quantitevendu, typeCarburantVente, dateVente, prixVente, idStation) VALUES(?,?,?,?,?)";
//        recuperer la requete
        ps = con.prepareStatement(req);
//        passage des parametres
        ps.setDouble(1, e.getQuantiteVendu());
        ps.setString(2, e.getTypeCarburantVente());
        ps.setDate(3, new java.sql.Date(e.getDateVente().getTime()));
        ps.setDouble(4, e.getPrixVente());
        ps.setString(5, e.getIdStation());

//      execution des requetes
        n = ps.executeUpdate();
//        fermeture de la connection
        DBConnection.fermetureCon(rs, ps, con);

        return n;

    }

    @Override
    public double calculRevenuVente() throws ClassNotFoundException, SQLException {
        double revenuTotal = 0;
        String sql = "SELECT SUM(quantiteVendu * prixVente) AS revenuTotal FROM tabventes";
        try (
                Connection con = DBConnection.getConnection(); 
                 PreparedStatement ps = con.prepareStatement(sql); 
                ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                revenuTotal = rs.getDouble("revenuTotal");
            }
        }
        return revenuTotal;
    }

    @Override
    public List<VentesModel> listerVente() throws ClassNotFoundException, SQLException {
        List<VentesModel> vmod = new ArrayList<VentesModel>();

        //        recuperer la connection
        con = DBConnection.getConnection();
//            definir la requete
        String req = "SELECT * FROM TABVENTES";
//            utilisation de prepareStatement
        ps = con.prepareStatement(req);
//            utiliser le resultSet 
        rs = ps.executeQuery();
//            parcourir le resultSet
        VentesModel vm = null;
        while (rs.next()) {
            vm = new VentesModel();

            vm.setIdVente(rs.getString("idVente"));
            vm.setQuantiteVendu(rs.getDouble("quantiteVendu"));
            vm.setTypeCarburantVente(rs.getString("typeCarburantVente"));
            vm.setDateVente(rs.getDate("dateVente"));
            vm.setPrixVente(rs.getDouble("prixVente"));
            vm.setIdStation(rs.getString("idStation"));

//            ajout des stations 
            vmod.add(vm);
        }

//            fermeture de la connection
        DBConnection.fermetureCon(rs, ps, con);
        return vmod;
    }

}
