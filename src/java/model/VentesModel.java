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
public class VentesModel {
    private String idVente;
    private int quantiteVendu;
    private String TypeCarburantVente;
    private Date DateVente;
    private double prixVente;
    
    public VentesModel(){}

    public VentesModel(String idVente, int quantiteVendu, String TypeCarburantVente, Date DateVente, double prixVente) {
        this.idVente = idVente;
        this.quantiteVendu = quantiteVendu;
        this.TypeCarburantVente = TypeCarburantVente;
        this.DateVente = DateVente;
        this.prixVente = prixVente;
    }

    public String getIdVente() {
        return idVente;
    }

    public void setIdVente(String idVente) {
        this.idVente = idVente;
    }

    public int getQuantiteVendu() {
        return quantiteVendu;
    }

    public void setQuantiteVendu(int quantiteVendu) {
        this.quantiteVendu = quantiteVendu;
    }

    public String getTypeCarburantVente() {
        return TypeCarburantVente;
    }

    public void setTypeCarburantVente(String TypeCarburantVente) {
        this.TypeCarburantVente = TypeCarburantVente;
    }

    public Date getDateVente() {
        return DateVente;
    }

    public void setDateVente(Date DateVente) {
        this.DateVente = DateVente;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }
    
    
}
