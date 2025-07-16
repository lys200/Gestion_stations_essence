/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

/**
 *
 * @author BELCEUS
 */
public interface IDAOApprov <T>{
    int enregistrerLivraison(T e);
    int modifierNiveau(T e);
}
