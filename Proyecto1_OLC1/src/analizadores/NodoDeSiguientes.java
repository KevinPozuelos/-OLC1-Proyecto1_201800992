/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

import java.util.ArrayList;

/**
 *
 * @author aller
 */
public class NodoDeSiguientes {
     int identificador;
    String contenido;
    ArrayList<Integer> siguientes = new ArrayList<>();
    
    public NodoDeSiguientes(int id, String cont){
        this.identificador = id;
        this.contenido = cont;
    }
}
