
 package service;

import model.ApprovisionnementModel;
 import java.util.List;

public interface IDAOApprov {
    ApprovisionnementModel rechercherStationAvecInfos(int idStation);
    void modifierStockage(int idStation, int nouvelleQte);
}

