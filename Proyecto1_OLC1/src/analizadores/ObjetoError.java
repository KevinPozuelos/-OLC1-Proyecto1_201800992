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
public class ObjetoError {
    String Valor;
    String ExpresionRegular;
    String Resultado;
    
    public ObjetoError(String value, String regex, String res){
        this.Valor = value;
        this.ExpresionRegular = regex;
        this.Resultado = res;
    }
    
    public String getValor() {
        return this.Valor;
    }
    public void setValor(String value) {
        this.Valor = value;
    }
    public String getExpresionRegular() {
        return this.ExpresionRegular;
    }
    public void setExpresionRegular(String regex) {
        this.ExpresionRegular = regex;
    }
    public String getResultado() {
        return this.Resultado;
    }
    public void setResultado(String res) {
        this.Resultado = res;
    }
}
