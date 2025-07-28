/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author BELCEUS
 */
public interface IDAOVentes <T> {
    int enregistrerVente(T e) throws ClassNotFoundException, SQLException;
    double calculRevenuVente() throws ClassNotFoundException, SQLException;
    List<T> listerVente() throws ClassNotFoundException, SQLException;
}
