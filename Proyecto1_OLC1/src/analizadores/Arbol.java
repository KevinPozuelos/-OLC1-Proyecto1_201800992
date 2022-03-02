/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadores;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

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
        
        this.enumerarIdentificadores();
        this.definirAnulabilidad();
        this.DefinirPrimeros();
        this.DefinirUltimos();
        this.imprimirPrimerosYUltimos();
        System.out.println("");
        this.DefinirSiguientes();
        this.PrintFollowTable();
        this.DefinirTermianles();
        this.TablaTransiciones();
        this.PrintTransitionTable();
        this.GraficarArbol();
        this.GraficarSiguientes();
        this.GraficarTransiciones();
        this.GraficarAFD();
        this.GraficarAFN();
        
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
    
    public int enumId(NodoArbol nodito, int id){
        if (nodito != null){
            if (nodito.izquierdo == null && nodito.derecho == null){
                nodito.identificador = id;
                System.out.println(nodito.contenido + " Identificador: " + nodito.identificador);
                id++;
                return id;
            }
            
            id = this.enumId(nodito.izquierdo, id);
            if (nodito.izquierdo == null && nodito.derecho == null){
                nodito.identificador = id;
                System.out.println(nodito.contenido + " Identificador: " + nodito.identificador);
                id++;
            }
            id = this.enumId(nodito.derecho, id);
        } 
        return id;
    }
    
    public void enumerarIdentificadores(){
        int id = 1;
        this.enumId(this.raiz, id);
    }
    
    public void defineNullable(NodoArbol nodito){
        if (nodito != null){
            if (nodito.identificador != 0){
                nodito.anulabilidad = false;
            }
            this.defineNullable(nodito.izquierdo);
            //Aqui ya se paso por el nodo izquierdo
            if ("kleen".equals(nodito.contenido)){
                nodito.anulabilidad = true;
            } else if (("pos".equals(nodito.contenido)) && (nodito.izquierdo.anulabilidad)){
                nodito.anulabilidad = true;
            }else if ("uoc".equals(nodito.contenido)){
                nodito.anulabilidad = true;
            }
            System.out.println(nodito.contenido + " " + nodito.anulabilidad);
            this.defineNullable(nodito.derecho);
            //Aqui ya se paso por los dos nodos, hacer instrucciones que involucran ambos
            if (("pt".equals(nodito.contenido)) && (nodito.izquierdo.anulabilidad) && (nodito.derecho.anulabilidad)){
                nodito.anulabilidad = true;
            }else if(("dis".equals(nodito.contenido)) && ((nodito.izquierdo.anulabilidad) || (nodito.derecho.anulabilidad))){
                nodito.anulabilidad = true;
            }
        }
    }
    
    public void definirAnulabilidad(){
        this.defineNullable(this.raiz);
    }
    
    public void DefineFirst(NodoArbol nodito){
        if (nodito != null){
            if (nodito.identificador != 0){
                nodito.primeros.add(nodito.identificador);
            }
            
            this.DefineFirst(nodito.izquierdo);
            //Aqui ya se paso por el nodo izquierdo 
            if ("kleen".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.primeros.size(); i++){
                    nodito.primeros.add(nodito.izquierdo.primeros.get(i));
                }
            } else if ("pos".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.primeros.size(); i++){
                    nodito.primeros.add(nodito.izquierdo.primeros.get(i));
                }
            }else if ("uoc".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.primeros.size(); i++){
                    nodito.primeros.add(nodito.izquierdo.primeros.get(i));
                }
            }
            
            this.DefineFirst(nodito.derecho);
            //Aqui ya se paso por los dos nodos, hacer instrucciones que involucran ambos
            if ("pt".equals(nodito.contenido)){
                if(nodito.izquierdo.anulabilidad){
                    //Si c1 es anulable es la union de los primeros
                    for(int i = 0; i < nodito.izquierdo.primeros.size(); i++){
                        nodito.primeros.add(nodito.izquierdo.primeros.get(i));
                    }
                    for(int i = 0; i < nodito.derecho.primeros.size(); i++){
                        nodito.primeros.add(nodito.derecho.primeros.get(i));
                    }
                }else{//De no ser anulable los primeros solo seran los de c1
                    for(int i = 0; i < nodito.izquierdo.primeros.size(); i++){
                        nodito.primeros.add(nodito.izquierdo.primeros.get(i));
                    }
                }
            }else if("dis".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.primeros.size(); i++){
                    nodito.primeros.add(nodito.izquierdo.primeros.get(i));
                }
                for(int i = 0; i < nodito.derecho.primeros.size(); i++){
                    nodito.primeros.add(nodito.derecho.primeros.get(i));
                }
            }
            
        }
    }
    
    public void DefinirPrimeros(){
        this.DefineFirst(this.raiz);
    }
    
    public void DefineLast(NodoArbol nodito){
        if (nodito != null){
            if (nodito.identificador != 0){
                nodito.ultimos.add(nodito.identificador);
            }
            
            this.DefineLast(nodito.izquierdo);
            //Aqui ya se paso por el nodo izquierdo 
            if ("kleen".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.ultimos.size(); i++){
                    nodito.ultimos.add(nodito.izquierdo.ultimos.get(i));
                }
            } else if ("pos".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.ultimos.size(); i++){
                    nodito.ultimos.add(nodito.izquierdo.ultimos.get(i));
                }
            }else if ("uoc".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.ultimos.size(); i++){
                    nodito.ultimos.add(nodito.izquierdo.ultimos.get(i));
                }
            }
            
            this.DefineLast(nodito.derecho);
            //Aqui ya se paso por los dos nodos, hacer instrucciones que involucran ambos
            if ("pt".equals(nodito.contenido)){
                if(nodito.derecho.anulabilidad){
                    //Si c1 es anulable es la union de los primeros
                    for(int i = 0; i < nodito.izquierdo.ultimos.size(); i++){
                        nodito.ultimos.add(nodito.izquierdo.ultimos.get(i));
                    }
                    for(int i = 0; i < nodito.derecho.ultimos.size(); i++){
                        nodito.ultimos.add(nodito.derecho.ultimos.get(i));
                    }
                }else{//De no ser anulable los primeros solo seran los de c1
                    for(int i = 0; i < nodito.derecho.ultimos.size(); i++){
                        nodito.ultimos.add(nodito.derecho.ultimos.get(i));
                    }
                }
            }else if("dis".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.ultimos.size(); i++){
                    nodito.ultimos.add(nodito.izquierdo.ultimos.get(i));
                }
                for(int i = 0; i < nodito.derecho.ultimos.size(); i++){
                    nodito.ultimos.add(nodito.derecho.ultimos.get(i));
                }
            }
            
        }
    }
    
    public void DefinirUltimos(){
        this.DefineLast(this.raiz);
    }
    
    public void printFirstAndLast(NodoArbol nodito){
        if (nodito != null){
            this.printFirstAndLast(nodito.izquierdo);
            
            if("pt".equals(nodito.contenido)){
                System.out.print(".");
            }else if("kleen".equals(nodito.contenido)){
                System.out.print("*");
            }else if("dis".equals(nodito.contenido)){
                System.out.print("|");
            }else if("pos".equals(nodito.contenido)){
                System.out.print("+");
            }else if("uoc".equals(nodito.contenido)){
                System.out.print("?");
            }else{
                System.out.print(nodito.contenido);
            }
            System.out.println("");
            System.out.print("Primeros: ");
            for(int i = 0; i < nodito.primeros.size(); i++){
                System.out.print(nodito.primeros.get(i) + ", ");
            }
            System.out.println("");
            System.out.print("Ultimos: ");
            for(int i = 0; i < nodito.ultimos.size(); i++){
                System.out.print(nodito.ultimos.get(i) + ", ");
            }
            System.out.println("");
            
            this.printFirstAndLast(nodito.derecho);
        }
    }
    
    public void imprimirPrimerosYUltimos(){
        this.printFirstAndLast(this.raiz);
    }
    
    public void DefineFollowTable(NodoArbol nodito){
        if (nodito != null){
            if (nodito.identificador != 0){
                NodoDeSiguientes node = new NodoDeSiguientes(nodito.identificador, nodito.contenido);
                this.tablaSiguientes.add(node);
            }
            this.DefineFollowTable(nodito.izquierdo);
            //Aqui ya se paso por el nodo izquierdo
            
            this.DefineFollowTable(nodito.derecho);
            //Aqui ya se paso por los dos nodos, hacer instrucciones que involucran ambos
            
        }
    }
    
    
    public void DefineFollow(NodoArbol nodito){
        if (nodito != null){
          
            this.DefineFollow(nodito.izquierdo);
            //Aqui ya se paso por el nodo izquierdo
            if ("kleen".equals(nodito.contenido) || "pos".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.ultimos.size(); i++){
                    for(int j = 0; j < this.FollowTable.length; j++){
                        if(nodito.izquierdo.ultimos.get(i) == this.FollowTable[j].identificador){
                            for(int k = 0; k < nodito.izquierdo.primeros.size(); k++){
                                if(!this.FollowTable[j].siguientes.contains(nodito.izquierdo.primeros.get(k))){
                                    //Si aun no esta incluido en la tabla
                                    this.FollowTable[j].siguientes.add(nodito.izquierdo.primeros.get(k));
                                }
                            }
                           
                        }
                    }
                }
            }
            
            this.DefineFollow(nodito.derecho);
            //Aqui ya se paso por los dos nodos, hacer instrucciones que involucran ambos
            if("pt".equals(nodito.contenido)){
                for(int i = 0; i < nodito.izquierdo.ultimos.size(); i++){
                    for(int j = 0; j < this.FollowTable.length; j++){
                        if(nodito.izquierdo.ultimos.get(i) == this.FollowTable[j].identificador){
                            for(int k = 0; k < nodito.derecho.primeros.size(); k++){
                                if(!this.FollowTable[j].siguientes.contains(nodito.derecho.primeros.get(k))){
                                    //Si aun no esta incluido en la tabla
                                    this.FollowTable[j].siguientes.add(nodito.derecho.primeros.get(k));
                                }
                            }
                          
                        }
                    }
                }
            }
            
        }
    }
    
    public void DefinirSiguientes(){
        this.DefineFollowTable(this.raiz);
        this.FollowTable = new NodoDeSiguientes[this.tablaSiguientes.size()];
        for (int i = 0; i < this.FollowTable.length; i++){
            this.FollowTable[i] = this.tablaSiguientes.get(i);
        }
        this.DefineFollow(this.raiz);
    }
    
    public void PrintFollowTable(){
        System.out.println("Lexema|Id       Siguientes");
        for(int i = 0; i < this.FollowTable.length; i++){
            System.out.print(this.FollowTable[i].contenido + "|" + this.FollowTable[i].identificador + "          ");
            for(int j = 0; j < this.FollowTable[i].siguientes.size(); j++){
                System.out.print(this.FollowTable[i].siguientes.get(j) + ", ");
            }
            System.out.println(" ");
        }
    }
    
    public void DefinirTermianles(){
        ArrayList<String> terminal = new ArrayList<>();
        for(int i = 0; i < this.FollowTable.length; i++){
            if(!terminal.contains(this.FollowTable[i].contenido)){
                terminal.add(this.FollowTable[i].contenido);
            }
        }
        this.terminales = new String[terminal.size()];
        for(int i = 0; i < this.terminales.length; i++){
            this.terminales[i] = terminal.get(i);
        }
    }
    
    public int[] burbuja(int arreglo[]){
        int auxiliar;
        for(int i = 0; i < arreglo.length - 1; i++)
        {
            for(int j = i+1; j < arreglo.length; j++)
            {
              if(arreglo[i] > arreglo[j])
              {
                auxiliar = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = auxiliar;
              }   
            }
        }
       
        return arreglo;
    }
    
    
    public Transicion[] DefinirConjunto(int sig[]){//Me retorna el conjunto de cada letra con cierto estado
        String[] caracteres;
        int temp; //Para ir a buscar a los followa
        Transicion[] transiciones;
        transiciones = new Transicion[this.terminales.length]; //Definimos transicion por cada terminal
        ArrayList<Integer> conj = new ArrayList<>();
        ArrayList<Integer> temporalito = new ArrayList<>();
        int[] conjunto;
        caracteres = new String[sig.length];
        for(int i = 0; i < sig.length; i++){
            for(int j = 0; j < this.FollowTable.length; j++){
                if(sig[i] == this.FollowTable[j].identificador){
                    caracteres[i] = this.FollowTable[j].contenido;//Pasar a los caracteres 
                }
            }
        }
        
        for(int i = 0; i < this.terminales.length; i++){//Recorremos los terminales para saber con cuales hay transiciones
            Transicion tra = new Transicion(this.terminales[i]);
            transiciones[i] = tra;
            for(int j = 0; j < caracteres.length; j++){
                //Recorremos los caracteres 
                if(this.terminales[i].equals(caracteres[j])){//Si el caracter coincide con el terminal 
                    temp = sig[j]; //Posicion de la transicion en los follow (Restarle 1)
                    temporalito = this.FollowTable[temp - 1].siguientes; //Siguientes del terminal con respectivo ID
                    for(int k = 0; k < temporalito.size(); k++){
                        if(!conj.contains(temporalito.get(k))){
                            conj.add(temporalito.get(k)); //En teoria con esto voy metiendo los sig
                        }
                    }
                }
            }
            
            if(conj.isEmpty()){
                transiciones[i].conjunto = null;
            }else{
                conjunto = new int[conj.size()];
                for(int l = 0; l < conjunto.length; l++){
                    conjunto[l] = conj.get(l);//Pasamos el arraylist a un vector
                }
                conjunto = this.burbuja(conjunto);
                transiciones[i].conjunto = conjunto;
                conj.clear();//vacia el array para la siguiente iteracion 
            }
        }
        
        
        return transiciones;
    }
    
    public boolean CompararVectores(int a[], int b[]){
        if(a.length != b.length){
            return false;
        }
        
        int comparacion = 0;
        
        for(int i = 0; i < a.length; i++){
            if (a[i] == b[i]){
                comparacion++; //Si los elementos coinciden se suma el contador
            }
        }
        
        if (comparacion == a.length){
            return true;
        }
        return false;
    }
    
    public void TablaTransiciones(){
        Transicion[] transicionesEstado; //Se va guardando lo del metodo definir conjunto
        if(this.estados.isEmpty()){
            Estado primero = new Estado("S0");
            primero.siguientes = new int[this.raiz.primeros.size()];
            for(int i = 0; i < primero.siguientes.length; i++){
                primero.siguientes[i] = this.raiz.primeros.get(i);
            }
            if(this.raiz.primeros.contains((this.FollowTable.length))){
                //Si contiene la posicion con #, que siempre sera la ultima
                primero.aceptacion = true;
            }
            primero.siguientes = this.burbuja(primero.siguientes);
            this.estados.add(primero);
        }
        
        //En teoria la tabla de estados se incrementara en el metodo al llegar al punto
        //que no surjan mas estados, ya no iterara
        for(int i = 0; i < this.estados.size(); i++){
            String[] trans = new String[this.terminales.length];
            transicionesEstado = this.DefinirConjunto(estados.get(i).siguientes);
            for(int j = 0; j < transicionesEstado.length; j++){
                if(transicionesEstado[j].conjunto != null){
                    String estado = null; //Donde guardare el nombre en caso haya estado
                    for(int k = 0; k < estados.size(); k++){
                        if(this.CompararVectores(transicionesEstado[j].conjunto, this.estados.get(k).siguientes)){
                            //Se busca si ya existe algun conjunto, si es asi se guarda el nombre del mismo
                            estado = this.estados.get(k).nombre;
                        }
                    }
                    //Salimos del for 
                    if(estado == null){
                        //Quiere decir que no existe ningun estado con esas transiciones
                        //Por lo cual creamos un nuevo estado
                        String nombre = "S" + Integer.toString(estados.size());
                        Estado nuevo = new Estado(nombre);
                        nuevo.siguientes = new int[transicionesEstado[j].conjunto.length];
                        nuevo.siguientes = transicionesEstado[j].conjunto;
                        for(int h = 0; h < transicionesEstado[j].conjunto.length; h++){
                            if(transicionesEstado[j].conjunto[h] == (this.FollowTable.length)){
                                //Si tiene el simbolo # es de aceptacion
                                nuevo.aceptacion = true;
                            }
                        }
                        this.estados.add(nuevo);
                        trans[j] = nombre; //Si es un estado nuevo
                    }else{
                        trans[j] = estado; //Si es un estado que si coincidio
                    }
                }else{
                    trans[j] = "--";
                }
            }
            this.tablaTransiciones.add(trans); //Agregamos la fila de tansiciones 
        }
    }
    
    public void PrintTransitionTable(){
        System.out.println("");
        System.out.print("     ");
        for(int i = 0; i < this.terminales.length; i++){
            System.out.print(this.terminales[i] + "  ");
        }
        System.out.println("");
        for(int i = 0; i < this.tablaTransiciones.size(); i++){
            System.out.print("S" + Integer.toString(i) + "|   ");
            for(int j = 0; j < this.tablaTransiciones.get(i).length; j++){
                System.out.print(this.tablaTransiciones.get(i)[j] + "  ");
            }
            System.out.println("");
        }
        
    }
    
    public int EnumNodos(NodoArbol nodito, int id){
        if (nodito != null){
            if (nodito.izquierdo == null && nodito.derecho == null){
                nodito.idUnico = id;
                System.out.println(nodito.idUnico + "|");
                id++;
                return id;
            }
            
            id = this.EnumNodos(nodito.izquierdo, id);
            if (nodito.izquierdo != null){
                nodito.idUnico = id;
                System.out.println(nodito.idUnico + "|");
                id++;
            }
            id = this.EnumNodos(nodito.derecho, id);
        } 
        return id;
        
    }
    
    public String HacerLista(Vector<Integer> lista){
        String retorno = "";
        for (int i = 0; i < lista.size(); i++){
            if((lista.size() - 1) != i){
                retorno = retorno + Integer.toString(lista.get(i)) + ", ";
            }else{
                retorno = retorno + Integer.toString(lista.get(i));
            }
        }
        return retorno;
    }
    
    public void GraphTree(NodoArbol nodito, BufferedWriter bw) throws IOException{
        if (nodito != null){
            if((nodito.izquierdo == null) && (nodito.derecho == null)){
                bw.write("tabla" + Integer.toString(nodito.idUnico) + "[shape=plaintext,fontsize=12, label=<");
                bw.write("<TABLE BORDER=\"0.1\">");
                bw.write("<TR><TD></TD><TD>N</TD><TD></TD></TR>");
                bw.write("<TR> <TD>" + this.HacerLista(nodito.primeros) + "</TD><TD BORDER=\"1\">" + nodito.contenido + "</TD><TD>" + this.HacerLista(nodito.ultimos) + "</TD> </TR>");
                bw.write("<TR> <TD></TD><TD>" + Integer.toString(nodito.identificador) + "</TD><TD></TD> </TR>");
                bw.write("</TABLE>");
                bw.write(">];");
            }
            this.GraphTree(nodito.izquierdo, bw);
            //Aqui ya se paso por el nodo izquierdo
            if ("kleen".equals(nodito.contenido) || "pos".equals(nodito.contenido) || "uoc".equals(nodito.contenido)){
                String cadena = "";
                if("kleen".equals(nodito.contenido)){
                    cadena = "*";
                }else if("pos".equals(nodito.contenido)){
                    cadena = "+";
                }else if("uoc".equals(nodito.contenido)){
                    cadena = "?";
                }
                bw.write("tabla" + Integer.toString(nodito.idUnico) + "[shape=plaintext,fontsize=12, label=<");
                bw.write("<TABLE BORDER=\"0.1\">");
                String anulabilidad;
                if(nodito.anulabilidad){
                   anulabilidad = "A"; 
                }else{
                    anulabilidad = "N";
                }
                bw.write("<TR><TD></TD><TD>" + anulabilidad + "</TD><TD></TD></TR>");
                bw.write("<TR> <TD>" + this.HacerLista(nodito.primeros) + "</TD><TD BORDER=\"1\">" + cadena + "</TD><TD>" + this.HacerLista(nodito.ultimos) + "</TD> </TR>");
                bw.write("</TABLE>");
                bw.write(">];");
                bw.write("tabla" + Integer.toString(nodito.idUnico) + " -> " + "tabla" + Integer.toString(nodito.izquierdo.idUnico) + ";");
            } 
            
            this.GraphTree(nodito.derecho, bw);
            //Aqui ya se paso por los dos nodos, hacer instrucciones que involucran ambos
            if ("pt".equals(nodito.contenido) || "dis".equals(nodito.contenido)){
                String cadena = "";
                if("pt".equals(nodito.contenido)){
                    cadena = ".";
                }else if("dis".equals(nodito.contenido)){
                    cadena = "|";
                }
                bw.write("tabla" + Integer.toString(nodito.idUnico) + "[shape=plaintext,fontsize=12, label=<");
                bw.write("<TABLE BORDER=\"0.1\">");
                String anulabilidad;
                if(nodito.anulabilidad){
                   anulabilidad = "A"; 
                }else{
                    anulabilidad = "N";
                }
                bw.write("<TR><TD></TD><TD>" + anulabilidad + "</TD><TD></TD></TR>");
                bw.write("<TR> <TD>" + this.HacerLista(nodito.primeros) + "</TD><TD BORDER=\"1\">" + cadena + "</TD><TD>" + this.HacerLista(nodito.ultimos) + "</TD> </TR>");
                bw.write("</TABLE>");
                bw.write(">];");
                bw.write("tabla" + Integer.toString(nodito.idUnico) + " -> " + "tabla" + Integer.toString(nodito.izquierdo.idUnico) + ";");
                bw.write("tabla" + Integer.toString(nodito.idUnico) + " -> " + "tabla" + Integer.toString(nodito.derecho.idUnico) + ";");
            } 
            
        }
    }
    
    public void GraficarArbol(){
        try{
            this.EnumNodos(this.raiz, 0);
            String name = "Arbol" + this.nombre + ".dot";
            String nombre = "ARBOLES_201800992/Arbol" + this.nombre + ".png";
            File file = new File(name);
            
            if(file.exists()){
                file.delete();
            }
            
            file.createNewFile();
            
            
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("digraph G {");
            
            this.GraphTree(this.raiz, bw);
            
            bw.write("}");
            
            //Cerrar el archivo hasta el final 
            bw.close();
            String comando = "dot -Tpng " + name + " -o " + nombre;
            Runtime.getRuntime().exec(comando);
            
            
        
        }catch(Exception e){
        
        }
    }
    
    public String HacerListaAL(ArrayList<Integer> lista){
        String retorno = "";
        for (int i = 0; i < lista.size(); i++){
            if((lista.size() - 1) != i){
                retorno = retorno + Integer.toString(lista.get(i)) + ", ";
            }else{
                retorno = retorno + Integer.toString(lista.get(i));
            }
        }
        return retorno;
    }
    
    public String HacerListaVI(int[] lista){
        String retorno = "";
        for (int i = 0; i < lista.length; i++){
            if((lista.length - 1) != i){
                retorno = retorno + Integer.toString(lista[i]) + ", ";
            }else{
                retorno = retorno + Integer.toString(lista[i]);
            }
        }
        return retorno;
    }
    
    public void GraficarSiguientes(){
        try{
            this.EnumNodos(this.raiz, 0);
            String name = "Siguientes" + this.nombre + ".dot";
            String nombre = "SIGUIENTES_201800992/Siguientes" + this.nombre + ".png";
            File file = new File(name);
            
            if(file.exists()){
                file.delete();
            }
            
            file.createNewFile();
            
            
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("digraph G {");
            
            bw.write("tabla1[shape=plaintext,fontsize=12, label=<");
            bw.write("<TABLE BORDER=\"1\">");
            bw.write("<TR><TD>CARACTER</TD><TD>ID</TD><TD>SIGUIENTES</TD></TR>");
            
            for (int i = 0; i < this.FollowTable.length; i++){
                String siguientes;
                if(this.FollowTable[i].siguientes.isEmpty()){
                    siguientes = "--";
                }else{
                    siguientes = this.HacerListaAL(this.FollowTable[i].siguientes);
                }
                bw.write("<TR> <TD>" + this.FollowTable[i].contenido + "</TD><TD>" + Integer.toString(this.FollowTable[i].identificador) + "</TD><TD>" + siguientes + "</TD> </TR>");
            }
            
            bw.write("</TABLE>");
            bw.write(">];");
            bw.write("}");
            
            //Cerrar el archivo hasta el final 
            bw.close();
            String comando = "dot -Tpng " + name + " -o " + nombre;
            Runtime.getRuntime().exec(comando);
            
            
            
        
        }catch(Exception e){
        
        }
    }
    
    public void GraficarTransiciones(){
        try{
            this.EnumNodos(this.raiz, 0);
            String name = "Transiciones" + this.nombre + ".dot";
            String nombre = "TRANSICIONES_201800992/Transiciones" + this.nombre + ".png";
            File file = new File(name);
            
            if(file.exists()){
                file.delete();
            }
            
            file.createNewFile();
            
            
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("digraph G {");
            
            bw.write("tabla1[shape=plaintext,fontsize=12, label=<");
            bw.write("<TABLE BORDER=\"1\">");
            String cabecera = "<TR><TD>Estados</TD>";
            for (int i = 0; i < (this.terminales.length - 1); i++){
                cabecera = cabecera + "<TD>" + this.terminales[i] + "</TD>";
            }
            cabecera = cabecera + "</TR>";
            
            bw.write(cabecera);
            
            for(int i = 0; i < this.estados.size(); i++){
                String transicion = "<TR>";
                transicion = transicion + "<TD>" + this.estados.get(i).nombre + " {" + this.HacerListaVI(this.estados.get(i).siguientes) + "}</TD>";
                for (int j = 0; j < (this.tablaTransiciones.get(i).length - 1); j++){
                    transicion = transicion + "<TD>" + this.tablaTransiciones.get(i)[j] + "</TD>";
                }
                transicion = transicion + "</TR>";
                bw.write(transicion);
            }
            
            bw.write("</TABLE>");
            bw.write(">];");
            bw.write("}");
            
            //Cerrar el archivo hasta el final 
            bw.close();
            String comando = "dot -Tpng " + name + " -o " + nombre;
            Runtime.getRuntime().exec(comando);
            
            
            
            
        
        }catch(Exception e){
        
        }
    }
    
    public void GraficarAFD(){
        try{
            String name = "AFD" + this.nombre + ".dot";
            String nombre = "AFD_201800992/AFD" + this.nombre + ".png";
            File file = new File(name);
            
            if(file.exists()){
                file.delete();
            }
            
            file.createNewFile();
            
            
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("digraph G {");
            
            bw.write("rankdir = LR;");
            bw.write("node [shape=circle];");
            bw.write("inicio [shape=plaintext label=\"Inicio\"];");
            bw.write("inicio -> S0;");           
            
            for (int i = 0; i < (this.tablaTransiciones.size()); i++){
                String estadoSalida = "S" + Integer.toString(i);
                if(this.estados.get(i).aceptacion){
                    bw.write("S" + Integer.toString(i) + " [peripheries=2];"); 
                }
                for (int j = 0; j < this.tablaTransiciones.get(i).length; j++){
                    if (!"--".equals(this.tablaTransiciones.get(i)[j])){
                        bw.write(estadoSalida + " -> " + this.tablaTransiciones.get(i)[j] + " [label=\"" + this.terminales[j] + "\"];"); 
                    }
                }
            }
            
            bw.write("}");
            
            //Cerrar el archivo hasta el final 
            bw.close();
            String comando = "dot -Tpng " + name + " -o " + nombre;
            Runtime.getRuntime().exec(comando);
            
        
        }catch(Exception e){
        
        }
    }
    
    public void GraficarAFN(){
        try{
            LinkedList<TransicionAFN> transiAFN;
            transiAFN = this.GraphAFN(this.raiz);
            ArrayList<Integer> comprobador = new ArrayList<>();
            
            for(int i = 0; i < transiAFN.size(); i++){
                if(!comprobador.contains(transiAFN.get(i).estadoInicio)){
                    comprobador.add(transiAFN.get(i).estadoInicio);
                }
                
                if(!comprobador.contains(transiAFN.get(i).EstadoDestino)){
                    comprobador.add(transiAFN.get(i).EstadoDestino);
                }
            }
            
            Collections.sort(comprobador);
            
            String name = "AFN" + this.nombre + ".dot";
            String nombre = "AFND_201800992/AFN" + this.nombre + ".png";
            File file = new File(name);
            
            if(file.exists()){
                file.delete();
            }
            
            file.createNewFile();
            
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("digraph G { \n");
            bw.write("rankdir=LR; \n");
            bw.write("node [shape=circle]; \n");
            
            for(int i = 0; i < comprobador.size(); i++){
                if(i == (comprobador.size() - 1)){
                    bw.write("node" + comprobador.get(i) + " [label=\"S" + comprobador.get(i) + "\", shape=doublecircle]; \n");
                }else{
                    bw.write("node" + comprobador.get(i) + " [label=\"S" + comprobador.get(i) + "\"]; \n");
                }
            }
            
            for(int i = 0; i <transiAFN.size(); i++){
                bw.write("node" + transiAFN.get(i).estadoInicio + " -> node" + transiAFN.get(i).EstadoDestino + 
                        " [label=\"" + transiAFN.get(i).caracterTransicion + "\"]; \n");
            }
            bw.write("} \n");
            bw.close();
            String comando = "dot -Tpng " + name + " -o " + nombre;
            Runtime.getRuntime().exec(comando);
            
            
        }catch(Exception e){
            
        }
    }
    
    public LinkedList<TransicionAFN> ReajusteLista(LinkedList<TransicionAFN> desajustada, int ajuste){
        LinkedList<TransicionAFN> ajustada = new LinkedList<>();
        for(int i = 0; i < desajustada.size(); i++){
            TransicionAFN nodoAjustado = new TransicionAFN(desajustada.get(i).estadoInicio + ajuste, 
            desajustada.get(i).caracterTransicion, desajustada.get(i).EstadoDestino + ajuste);
            ajustada.add(nodoAjustado);
        }
        return ajustada;
    }
    
    public int ObtenerNumeroDeNodos(LinkedList<TransicionAFN> transiciones){
        int numeroNodos = 0;
        ArrayList<Integer> estaditos = new ArrayList<>();
        for(int i = 0; i < transiciones.size(); i++){
            if(!estaditos.contains(transiciones.get(i).estadoInicio)){
                numeroNodos++;
                estaditos.add(transiciones.get(i).estadoInicio);
            }
            
            if(!estaditos.contains(transiciones.get(i).EstadoDestino) && transiciones.get(i).EstadoDestino > -1){
                numeroNodos++;
                estaditos.add(transiciones.get(i).EstadoDestino);
            }
        }
        return numeroNodos;
    }
    
    public LinkedList<TransicionAFN> GraphAFN(NodoArbol nodito){
        LinkedList<TransicionAFN> transiciones = new LinkedList<>();
        int contador = 0;
        if (nodito != null){
            LinkedList<TransicionAFN> izq = GraphAFN(nodito.izquierdo);
            LinkedList<TransicionAFN> der = GraphAFN(nodito.derecho);
            
            
            //Llegando a este punto comenzamos con los metodos de definicion
            //COMPROBANDO EL TIPO DE CADA UNO DE LOS NODOS DEL ARBOL
            
            if((nodito.izquierdo == null) && (nodito.derecho == null)){ //Es un nodo hoja
                //Se hace la concatenacion mas basica 
                TransicionAFN transicionTemporal = new TransicionAFN(contador, nodito.contenido, contador + 1);
                transiciones.add(transicionTemporal);
                TransicionAFN punteroNulo = new TransicionAFN(contador + 1, "vacio", -100);
                transiciones.add(punteroNulo);
                return transiciones;
            }else{ //De no ser una hoja debemos comprobar que operacion es 
                if("dis".equals(nodito.contenido)){
                    TransicionAFN nueva = new TransicionAFN(contador, "ε", contador + 1);
                    transiciones.add(nueva);
                    izq = this.ReajusteLista(izq, 1);
                    int ajuste2 = this.ObtenerNumeroDeNodos(izq) + 1;
                    der = this.ReajusteLista(der, ajuste2);
                    transiciones.add(new TransicionAFN(contador, "ε", ajuste2));
                    ajuste2 = ajuste2 + this.ObtenerNumeroDeNodos(der);                   
                    izq.getLast().caracterTransicion = "ε";
                    izq.getLast().EstadoDestino = ajuste2;
                    der.getLast().caracterTransicion = "ε";
                    der.getLast().EstadoDestino = ajuste2;
                    transiciones.addAll(izq);
                    transiciones.addAll(der);
                    transiciones.add(new TransicionAFN(ajuste2, "vacio", -100));
                    return transiciones;
                }else if("kleen".equals(nodito.contenido)){
                    TransicionAFN nueva = new TransicionAFN(contador, "ε", contador + 1);
                    transiciones.add(nueva);
                    izq = this.ReajusteLista(izq, 1);                 
                    izq.getLast().caracterTransicion = "ε";
                    izq.getLast().EstadoDestino = izq.getFirst().estadoInicio;
                    transiciones.addAll(izq);
                    int ajuste = this.ObtenerNumeroDeNodos(izq) + 1;
                    transiciones.add(new TransicionAFN(izq.getLast().estadoInicio, "ε", ajuste));
                    transiciones.add(new TransicionAFN(contador, "ε", ajuste));
                    transiciones.add(new TransicionAFN(ajuste, "vacio", -100));
                    return transiciones;
                }else if("uoc".equals(nodito.contenido)){
                    TransicionAFN nueva = new TransicionAFN(contador, "ε", contador + 1);
                    transiciones.add(nueva);
                    izq = this.ReajusteLista(izq, 1); 
                    int ajuste = this.ObtenerNumeroDeNodos(izq) + 1;
                    izq.getLast().caracterTransicion = "ε";
                    izq.getLast().EstadoDestino = ajuste;
                    transiciones.addAll(izq);
                    

                    transiciones.add(new TransicionAFN(contador, "ε", ajuste));
                    transiciones.add(new TransicionAFN(ajuste, "vacio", -100));
                    return transiciones;
                }else if("pos".equals(nodito.contenido)){
                    TransicionAFN nueva = new TransicionAFN(contador, "ε", contador + 1);
                    transiciones.add(nueva);
                    izq = this.ReajusteLista(izq, 1);                 
                    izq.getLast().caracterTransicion = "ε";
                    izq.getLast().EstadoDestino = izq.getFirst().estadoInicio;
                    transiciones.addAll(izq);
                    int ajuste = this.ObtenerNumeroDeNodos(izq) + 1;
                    transiciones.add(new TransicionAFN(izq.getLast().estadoInicio, "ε", ajuste));

                    transiciones.add(new TransicionAFN(ajuste, "vacio", -100));
                    return transiciones;
                }else if("pt".equals(nodito.contenido)){
                    if(nodito.derecho.contenido.equals("#")){
                        izq.removeLast();
                        return izq;
                    }else{
                        izq.removeLast();
                        int ajuste;
                        ajuste = this.ObtenerNumeroDeNodos(izq) - 1;
                        der = this.ReajusteLista(der, ajuste);
                        
                        transiciones.addAll(izq);
                        transiciones.addAll(der);
                        return transiciones;
                    }
                }
            }
           
        } 
        return transiciones;  
    }
    
    
}
