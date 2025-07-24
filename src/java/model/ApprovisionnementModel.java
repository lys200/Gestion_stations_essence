/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author BELCEUS
 */
public class ApprovisionnementModel {
    private String idApp;
    private String TypeCarburant;
    private int quantite;
    private Date dateApprov;
    private String fournisseur;
    private String idStation;
    
    public ApprovisionnementModel(){}


  

    public ApprovisionnementModel(String idApp, String TypeCarburant, int quantite, Date dateApprov, String fournisseur, String idStation) {

        this.idApp = idApp;
        this.TypeCarburant = TypeCarburant;
        this.quantite = quantite;
        this.dateApprov = dateApprov;
        this.fournisseur = fournisseur;
        this.idStation = idStation;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getTypeCarburant() {
        return TypeCarburant;
    }

    public void setTypeCarburant(String TypeCarburant) {
        this.TypeCarburant = TypeCarburant;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDateApprov() {
        return dateApprov;
    }

    public void setDateApprov(Date dateApprov) {
        this.dateApprov = dateApprov;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getIdStation() {
        return idStation;
    }

    public void setIdStation(String idStation) {
        this.idStation = idStation;
    }

    
}
