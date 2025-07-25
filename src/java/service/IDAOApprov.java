package service;

import model.ApprovisionnementModel;
import model.StationModel;

import java.sql.SQLException;
import java.util.List;

public interface IDAOApprov {

    int enregistrerAvecMajStock(ApprovisionnementModel approv) throws SQLException, ClassNotFoundException;

    List<ApprovisionnementModel> listerApprovisionnementsFiltres(java.util.Map<String, Object> filtres) throws SQLException, ClassNotFoundException;

    List<StationModel> listerStations() throws SQLException, ClassNotFoundException;

    boolean stationExiste(String idStation) throws SQLException, ClassNotFoundException;
}
