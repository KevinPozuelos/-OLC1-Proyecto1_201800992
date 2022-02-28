/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

/**
 *
 * @author aller
 */
public class Transicion {
    String terminal = "";
    int[] conjunto;
    
    public Transicion(String nombre){
        this.terminal = nombre;
    }
}
