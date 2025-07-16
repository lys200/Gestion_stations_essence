/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbUtils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BELCEUS
 */
public class DBConnection {
//    creer un objet de type connexion

    public static Connection conn;
//methode pour recuperer la connection 

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
//        chargement du pilote
        Class.forName("com.mysql.cj.jdbc.Driver");
//        chargement des parametres
        String url = "jdbc:mysql://localhost:3306/gestionStationEssence";
        String user = "root";
        String passwd = "";
//etablit la connection
        conn = DriverManager.getConnection(url, user, passwd);
        return conn;

    }

//    fermeture des connections
    public static void fermetureCon(ResultSet rs, PreparedStatement ps, Connection con) {

        try {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
