package traitement;

import dbUtils.DBConnection;
import model.ApprovisionnementModel;
import model.StationModel;

import java.sql.*;
import java.util.*;

public class ApprovDAO {

    
    
    
    
    
    public int enregistrerAvecMajStock(ApprovisionnementModel approv) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdateStock = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String insertSQL = "INSERT INTO tabapprovisionnement (idApp, idStation, TypeCarburant, quantite, dateApprov, fournisseur) VALUES (?, ?, ?, ?, ?, ?)";
            psInsert = con.prepareStatement(insertSQL);
            psInsert.setString(1, approv.getIdApp());
            psInsert.setString(2, approv.getIdStation());
            psInsert.setString(3, approv.getTypeCarburant());
            psInsert.setInt(4, approv.getQuantite());
            psInsert.setTimestamp(5, new Timestamp(approv.getDateApprov().getTime()));
            psInsert.setString(6, approv.getFournisseur());

            int n1 = psInsert.executeUpdate();

            String updateStockSQL;
            if ("gazoline".equalsIgnoreCase(approv.getTypeCarburant())) {
                updateStockSQL = "UPDATE tabstation SET quantiteGasoline = quantiteGasoline + ? WHERE idStation = ?";
            } else if ("diesel".equalsIgnoreCase(approv.getTypeCarburant())) {
                updateStockSQL = "UPDATE tabstation SET quantiteDiesel = quantiteDiesel + ? WHERE idStation = ?";
            } else {
                throw new SQLException("Type carburant inconnu : " + approv.getTypeCarburant());
            }

            psUpdateStock = con.prepareStatement(updateStockSQL);
            psUpdateStock.setInt(1, approv.getQuantite());
            psUpdateStock.setString(2, approv.getIdStation());

            int n2 = psUpdateStock.executeUpdate();

            if (n1 > 0 && n2 > 0) {
                con.commit();
                return 1;
            } else {
                con.rollback();
                return 0;
            }

        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (psInsert != null) psInsert.close();
            if (psUpdateStock != null) psUpdateStock.close();
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public List<ApprovisionnementModel> listerApprovisionnementsFiltres(Map<String, Object> filtres) throws ClassNotFoundException, SQLException {
        List<ApprovisionnementModel> liste = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM tabapprovisionnement WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (filtres.containsKey("idStation")) {
            sql.append(" AND idStation = ? ");
            params.add(filtres.get("idStation"));
        }
        if (filtres.containsKey("fournisseur")) {
            sql.append(" AND fournisseur LIKE ? ");
            params.add("%" + filtres.get("fournisseur") + "%");
        }
        if (filtres.containsKey("dateApprov")) {
            sql.append(" AND DATE(dateApprov) = ? ");
            params.add(filtres.get("dateApprov"));
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ApprovisionnementModel a = new ApprovisionnementModel();
                    a.setIdApp(rs.getString("idApp"));
                    a.setIdStation(rs.getString("idStation"));
                    a.setTypeCarburant(rs.getString("TypeCarburant"));
                    a.setQuantite(rs.getInt("quantite"));
                    a.setDateApprov(rs.getTimestamp("dateApprov"));
                    a.setFournisseur(rs.getString("fournisseur"));
                    liste.add(a);
                }
            }
        }

        return liste;
    }

    public List<StationModel> listerStations() throws ClassNotFoundException, SQLException {
        List<StationModel> stations = new ArrayList<>();
        String sql = "SELECT * FROM tabstation";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StationModel s = new StationModel();
                s.setIdStation(rs.getString("idStation"));
                s.setAdresseGeog(rs.getString("adresseGeog"));
                s.setCapaciteStockGasoline(rs.getDouble("capaciteStockGasoline"));
                s.setCapaciteStockDiesel(rs.getDouble("capaciteStockDiesel"));
                s.setQuantiteGasoline(rs.getInt("quantiteGasoline"));
                s.setQuantiteDiesel(rs.getInt("quantiteDiesel"));
                stations.add(s);
            }
        }
        return stations;
    }

    public boolean stationExiste(String idStation) throws ClassNotFoundException, SQLException {
        String sql = "SELECT COUNT(*) FROM tabstation WHERE idStation = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idStation);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    
}
