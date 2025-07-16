/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author BELCEUS
 */
public class StationModel {
    private String idStation;
    private String adresseGeog;
    private double capaciteStockGasoline;
    private double capaciteStockDiesel;
    private int quantiteGasoline;
    private int quantiteDiesel;
    
    public StationModel(){}

    public StationModel(String idStation, String adresseGeog, double capaciteStockGasoline, double capaciteStockDiesel, int quantiteGAsoline, int quantiteDiesel) {
        this.idStation = idStation;
        this.adresseGeog = adresseGeog;
        this.capaciteStockGasoline = capaciteStockGasoline;
        this.capaciteStockDiesel = capaciteStockDiesel;
        this.quantiteGasoline = quantiteGAsoline;
        this.quantiteDiesel = quantiteDiesel;
    }

    public String getIdStation() {
        return idStation;
    }

    public void setIdStation(String idStation) {
        this.idStation = idStation;
    }

    public String getAdresseGeog() {
        return adresseGeog;
    }

    public void setAdresseGeog(String adresseGEog) {
        this.adresseGeog = adresseGEog;
    }

    public double getCapaciteStockGasoline() {
        return capaciteStockGasoline;
    }

    public void setCapaciteStockGasoline(double capaciteStockGasoline) {
        this.capaciteStockGasoline = capaciteStockGasoline;
    }

    public double getCapaciteStockDiesel() {
        return capaciteStockDiesel;
    }

    public void setCapaciteStockDiesel(double capaciteStockDiesel) {
        this.capaciteStockDiesel = capaciteStockDiesel;
    }

    public int getQuantiteGAsoline() {
        return quantiteGasoline;
    }

    public void setQuantiteGAsoline(int quantiteGAsoline) {
        this.quantiteGasoline = quantiteGAsoline;
    }

    public int getQuantiteDiesel() {
        return quantiteDiesel;
    }

    public void setQuantiteDiesel(int quantiteDiesel) {
        this.quantiteDiesel = quantiteDiesel;
    }
    
    
}
