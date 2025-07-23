/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package traitement;

import dbUtils.DBConnection;
import model.Utilisateurs;
import java.sql.*;

/**
 *
 * @author BELCEUS
 */
public class LoginDao {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Utilisateurs rechercher(String nomUtilisateur, String motDePasse)
            throws SQLException, ClassNotFoundException {
//        recuperer la connection
        con = DBConnection.getConnection();
//            requete
        String requete = "SELECT * FROM tabutilisateur where nomUtilisateur=? and motDePasse=?";
//            objet de prepareStatement
        ps = con.prepareStatement(requete);
        ps.setString(1, nomUtilisateur);
        ps.setString(2, motDePasse);
//        executer les requetes
        ps.executeQuery();

//        fermer les connections
        DBConnection.fermetureCon(rs, ps, con);
        
        return new Utilisateurs();
    }

}
