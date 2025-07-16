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

/**
 *
 * @author BELCEUS
 */
public class StationDAO implements IDAOStation<StationModel> {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public int enregistrer(StationModel e) throws ClassNotFoundException, SQLException{
//        recuperer la connection a la base de donne
        con = DBConnection.getConnection();
//            requete d'insertion
        String requete = "INSERT INTO tabstation values(?,?,?,?,?,?)";
//        utilisation de prepareStatement
        ps = con.prepareStatement(requete);
//        passage des parametres
        ps.setInt(1, 0);
        ps.setString(2, e.getAdresseGeog());
        ps.setDouble(3, e.getCapaciteStockGasoline());
        ps.setDouble(4, e.getCapaciteStockDiesel());
        ps.setInt(5, e.getQuantiteGAsoline());
        ps.setInt(6, e.getQuantiteDiesel());
        
//        executer les requetes
        int n = ps.executeUpdate();
//        fermer les connections
        DBConnection.fermetureCon(rs, ps, con);
        
        return n;
    }

    @Override
    public int modifier(StationModel e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<StationModel> lister() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int ListequantiteGAzDispo(StationModel e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
