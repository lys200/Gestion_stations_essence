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
public interface IDAOStation <T> {
    int enregistrer(T e) throws ClassNotFoundException, SQLException;
    int modifier(T e) throws ClassNotFoundException, SQLException;
    List<T> lister();
    int ListequantiteGAzDispo(T e);
    
}
