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

        Utilisateurs user = null;
//recuperation de connexion 
        con = DBConnection.getConnection();
        String requete = "SELECT * FROM tabutilisateur WHERE nomUtilisateur=? AND mot_de_passe=?";
        ps = con.prepareStatement(requete);
        ps.setString(1, nomUtilisateur);
        ps.setString(2, motDePasse);
        rs = ps.executeQuery();

        if (rs.next()) {
            user = new Utilisateurs();
            user.setIdUtilisateur(rs.getString("idUser"));
            user.setNomUtilisateur(rs.getString("nomUtilisateur"));
            user.setMotDePasse(rs.getString("mot_de_passe"));
            user.setEtat(rs.getString("etat"));
        }

        DBConnection.fermetureCon(rs, ps, con);

        return user;
    }

    public int inscription(Utilisateurs util) throws ClassNotFoundException, SQLException {
        int n = 0;
//        recuperer la connexion
        con = DBConnection.getConnection();
//    requete 
        String requete = "INSERT INTO tabutilisateur(nomUtilisateur, mot_de_passe, etat) VALUES(?, ?, ?)";
//objet de preparedStatement
        ps = con.prepareStatement(requete);
//        passage des parametres
        ps.setString(1, util.getNomUtilisateur());
        ps.setString(2, util.getMotDePasse());
        ps.setString(3, util.getEtat());

        n = ps.executeUpdate();
//        fermeture de la connexion
        DBConnection.fermetureCon(rs, ps, con);
        return n;
    }
}
