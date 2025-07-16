/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

/**
 *
 * @author BELCEUS
 */
public interface IDAOVentes <T> {
    int enregistrerVente(T e);
    double calculRevenuVente();
}
