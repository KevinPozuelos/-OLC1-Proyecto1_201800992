/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

import java.util.Vector;

/**
 *
 * @author aller
 */
public class NodoArbol {
     NodoArbol izquierdo = null;
    NodoArbol derecho = null;
    String contenido = null;
    int identificador = 0;
    int idUnico;
    boolean anulabilidad = false;
    Vector<Integer> primeros = new Vector<Integer>();
    Vector<Integer> ultimos = new Vector<Integer>();
    
    public NodoArbol(String content, NodoArbol left, NodoArbol right){
        this.contenido = content;
        this.izquierdo = left;
        this.derecho = right;
    
    }
}
