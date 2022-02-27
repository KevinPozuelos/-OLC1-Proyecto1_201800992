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
public class Estado {
    String nombre;
    int[] siguientes;
    boolean aceptacion = false;
    
    public Estado(String name){
        this.nombre = name;
    }
}
