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
public class TransicionAFN {
    int estadoInicio;
    String caracterTransicion;
    int EstadoDestino;
    
    public TransicionAFN(int inicio, String cont, int destino){
        this.estadoInicio = inicio;
        this.caracterTransicion = cont;
        this.EstadoDestino = destino;
    }
}
