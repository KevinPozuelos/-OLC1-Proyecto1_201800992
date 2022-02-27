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
public class Conjunto {
    String nombre;
    ArrayList<String> simbolos = new ArrayList<>();
    
    public Conjunto(String name, String cadena){
        this.nombre = name;
        //System.out.println(cadena);
        cadena = cadena.replaceAll("\\s","");
        //System.out.println(cadena);
        if((cadena.length() == 3) && (cadena.charAt(1) == '~')){
            this.DefinirConjuntoSeparador(cadena);
        }else{
            this.DefinirConjuntoComas(cadena);
        }
        //this.ImprimirConjunto();
    }
    
    public void DefinirConjuntoSeparador(String cadena){
        //Se definira el conjunto por el separador ~
        int primero, ultimo;
        char caracter;
        
        primero = (int) cadena.charAt(0);
        ultimo = (int) cadena.charAt(2);
        
        for(int i = primero; i <= ultimo; i++){
            caracter = (char) i;
            this.simbolos.add(Character.toString(caracter));
        }
    }
    
    public void DefinirConjuntoComas(String cadena){
        //Se definira el conjunto por comas 
        for(int i = 0; i < cadena.length(); i++){
            if((i%2) == 0){
                this.simbolos.add(Character.toString(cadena.charAt(i)));
            }
        }
    }
    
    public void ImprimirConjunto(){
        System.out.print(this.nombre + ":   ");
        for(int i = 0; i < this.simbolos.size(); i++){
            System.out.print(this.simbolos.get(i) + ", ");
        }
        System.out.println(" ");
        System.out.println(" ");
    }
}
