# Archivo de Gramaticas <h1> 

Token |  Exprecion Regular 
------------ | -------------
 Espacios en blanco | [\040\t]+, [\040\t]*
 Letras | [a-zA-Z_]+, [a-zA-Z_]
 Digitos | [0-9]+, [0-9]
 Decimales| [0-9] + ("."[  |0-9]+)?
signos |<code> [!\"\'%+"|"¿¡#$&.+*_] , [!-"}"\040]</code>
Comentario multilinea | "<!"