/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 *
 * @author aller
 */
public class Arbol {
    String nombre;
    NodoArbol raiz;
    ArrayList<NodoDeSiguientes> tablaSiguientes = new ArrayList<>();
    ArrayList<Estado> estados = new ArrayList<>();
    NodoDeSiguientes[] FollowTable;
    String[] terminales;
    ArrayList<String[]> tablaTransiciones = new ArrayList<>();
    ArrayList<Conjunto> conjuntos = new ArrayList<>();
    
    public Arbol(String name, NodoArbol root, ArrayList<Conjunto> conj){
        NodoArbol hashtag = new NodoArbol("#", null, null);
        NodoArbol principalRoot = new NodoArbol("pt", root, hashtag);  
        this.conjuntos = conj;
        this.raiz = principalRoot;
        this.nombre = name;
        
        System.out.println("");
        
        //Las siquientes configuraciones
    }
    
    public void inorden(){
        this.enorden(this.raiz);
    }
    
    public void enorden(NodoArbol raiz){
        if (raiz != null){
            this.enorden(raiz.izquierdo);
            if("pt".equals(raiz.contenido)){
                System.out.print(".");
            }else if("kleen".equals(raiz.contenido)){
                System.out.print("*");
            }else if("dis".equals(raiz.contenido)){
                System.out.print("|");
            }else if("pos".equals(raiz.contenido)){
                System.out.print("+");
            }else if("uoc".equals(raiz.contenido)){
                System.out.print("?");
            }else{
                System.out.print(raiz.contenido);
            }
            this.enorden(raiz.derecho);
        }
    }
    
    public boolean ExisteEnConjunto(String caracter, String nombreConjunto){
        nombreConjunto = nombreConjunto.substring(1, nombreConjunto.length() - 1);
        for(int i = 0; i < this.conjuntos.size(); i++){
            if(nombreConjunto.equals(this.conjuntos.get(i).nombre)){
                if(this.conjuntos.get(i).simbolos.contains(caracter)){
                    return true;
                }
            }
        }
        return false;
    }  



    public ObjetoError GenerarJSON(String cadena, boolean valida){
        String validada;
        Gson g = new Gson();
        if(valida){
            validada = "Cadena Valida";
        }else{
            validada = "Cadena Invalida";
        }
        ObjetoError validacion = new ObjetoError(cadena, this.nombre, validada);
        return validacion;
    }
    
    public ObjetoError ReconocerCadena(String cadena){
        
        cadena = cadena.substring(1, cadena.length() - 1);
        
        int estadoActual = 0;
        int posicionletra = -1;
        String[] movimientos;
        for(int i = 0; i < cadena.length(); i++){
            String caracter = Character.toString(cadena.charAt(i));
            if(caracter.equals("\\")){
                caracter += Character.toString(cadena.charAt(i+1));
                i++;
            }
            for(int j = 0; j < this.terminales.length; j++){
                //Esto funciona solo para caracteres no para conjuntos
                if(caracter.equals(this.terminales[j])){
                    posicionletra = j;
                }
            }
            if(posicionletra == -1){
                //Intentaremos buscar en los conjuntos, pero por el momento muere
                
                movimientos = new String[this.tablaTransiciones.get(estadoActual).length];
                movimientos = this.tablaTransiciones.get(estadoActual);
                for(int j = 0; j < movimientos.length; j++){
                    if(!"--".equals(movimientos[j])){
                        if(('{' == this.terminales[j].charAt(0)) && (this.terminales[j].length() > 1)){
                        //Haremos un metodo para ver si pertenece al conjunto 
                            if(this.ExisteEnConjunto(caracter, this.terminales[j])){
                                posicionletra = j;
                            }
                        }
                    }
                }
                //Buscaremos en que terminales tenemos movimientos
                if(posicionletra == -1){
                    System.out.println("No hay transicion con el caracter ingresado");
                    return this.GenerarJSON(cadena, false);
                }
            }
            String sigEstado = this.tablaTransiciones.get(estadoActual)[posicionletra];
            if("--".equals(sigEstado)){
                System.out.println("No hay transicion con el caracter ingresado");
                return this.GenerarJSON(cadena, false);
            }
            sigEstado = sigEstado.replace('S', '0');
            estadoActual = Integer.parseInt(sigEstado);
            posicionletra = -1;
            if(i == (cadena.length()-1)){
                if(this.estados.get(estadoActual).aceptacion){
                    System.out.println("Cadena valida");
                    return this.GenerarJSON(cadena, true);
                }else{
                    System.out.println("Cadena invalida");
                    return this.GenerarJSON(cadena, false);
                }
            }
            
        }
        return new ObjetoError("Error", "Validacion", "Valida");
    }
    
    
}
