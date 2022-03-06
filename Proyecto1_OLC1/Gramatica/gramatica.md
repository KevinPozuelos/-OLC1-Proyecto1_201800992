# Archivo de Gramaticas <h1> 

Expresiones Regulares
-------
Espacios en blanco: <code> [\040\t]+ </code>, <code>[\040\t]* </code> <br>
Letras: <code>[a-zA-Z_]+ </code>, <code>[a-zA-Z_]</code> <br>
Digitos: <code>[0-9]+ </code>, <code> [0-9] </code> <br>
Decimales: <code> [0-9]+("."[  |0-9]+)? </code> <br>
Signos: <code>[!\"\'%+"|"¿¡#$&.+*_] </code>, <code> [!-"}"\040] </code> <br>
Saltos Multilinea: <code>"<!" </code>, <code>"!>" </code> <br>
Caracter: <code>[\']([^\t\'\"\n]|(\\\")|(\\n)|(\\\')|(\\t))?[\']</code><br>
Comentario Multilinea: <code><!<*([^<!]|[^!]"<"|"!"[^>])*!*!> </code> <br>
Conjunto: <code>({sig}{blan}"~"{blan}{sig}) </code> <br>
Conjunto con Comas: <code>{sig}({blan}","{blan}{sig})+ </code> <br>
Caracter Identificador: <code> "\""{blan}{sig}{blan}"\"" </code> <br>
Indentificaodor: <code>"-"{blan}">"</code> <br>
Caracteres especiales: <code>(\\n|\\\'|\\\") </code> <br>
Lexemas: <code> [\"](((\\\")|(\\n)|(\\\'))|[^\\\"\n])*[\"] </code> <br>
Palabras Reservadas: <code> "CONJ" </code> <br>


Terminales y No Terminales
--------
Terminales |  No Terminales 
------------ | -------------
CONCATENACION|ini
DISYUNCION |instrucciones
KLEEN|declaracionConj
POSITIVA|conjunto
PUNTOCOMA|agrupacion
DOSPUNTOS|listaCaracteres
COMILLAS|DECLARACIONREGEX
COMILLA|declaracionRegex
COMA|regex
UNAOCERO|declaracionLexema
LLAVEIZQUIERDA, LLAVEDERECHA|lexemaReconocer
PORCENTAJE,ASIGNACION,LEXEMA,LEXEMARECONOCER|faseLexema
CARACTER, ESPECIAL|
IDENTIFICADOR, ID|
ENTERO|
DECIMAL|
CONJ, DEFINICIONCONJUNTO, DEFINICIONCONJUNTOCOMAS|

Gramaticas Tipo 3
--------
## inicial <h3>
```
{ -> Intrucciones  
| 
} 
```

## Intrucciones <h3>

```
Intrucciones -> declaracionDeConjunto Instrucciones
| declaracionDeExprexionRegular Instrucciones
| declaracionConjunto
| declaracionDeExpresionRegular
| %% Lexema
| errorInstruccion
| errorPuntoComa Instrucciones
```

## Lexeman <h3>

```
Lexema -> DeclaracionLexema Lexema
| DeclaracionLexema
| Error
| ErrorLexema
| ErrorPuntoComa
```

## Conjuntos <h3>
```
Conjunto -> Agrupacion
| ListaDeCaracteres

```

## Expresiones Regulares <h3>

```
Regex -> Concatenacion
| DISYUNCION
| KLEEN
| POSITIVA
| UNAOCERO
| LLAVEIZQUIERDA
| CARACTER
| COMILLAS CONCATENACION
| COMILLAS DISYUNCION
| COMILLAS KLEEN
| COMILLAS POSITIVA
| COMILLAS UNAOCERO
| ESPECIAL
```