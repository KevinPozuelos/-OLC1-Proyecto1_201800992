# Manual Técnico <h1>

Descripción de la Solución
-----------------------

ExpAnalyzer es un programa realizado con las librerias de Jlex y cup las cuales realizan un analisis lexico y sintactico respectivamente, para la validacion de un archivo de entrada y con ello realizar dos metodos de analisis lexico y la generacion de reportes de los mismos. Cuenta con un reporte de errores ya sean lexicos o sintacticos como un reporte de errores en las cadenas propuestas en el archivo de entrada el cual es de formato JSON. 

Requerimientos Funcionales del Sistema
-----------------------
• Crear, cargar, modificar y guardar archivos .exp

• Análisis léxico y sintáctico de archivos .exp

• Generar AFD por medio del método del Árbol.

• Generar AFN por medio del método de Thompson.

• Analisis y validación de cadenas por medio de un AFD.

Diccionario de Clases 
-----------------------

Clase |  Definición 
`Arbol`| Estructura de datos arbol binario que contiene la irformacion y los metodos para realizar los dos metodos solicitados en el enunciado del proyecto.
`NodoArbol`| Clase que inicializa los metodos y atributos de la clase Arbol.
`Conjunto`| Objeto conjunto que posee una lista para almacenar datos y metodos para separar la parte de los Conjuntos.
`Estado`| Objeto estado. 
`Excepcion` Objeto que modela el tipo de error que se puede precentar en el analisis del archivo. 
`Lexico` | Clase que tiene los métodos necesarios para que funcione el analizador léxico.
`Sintactico` | Clase que tiene los métodos necesarios para que funcione el analizador sintáctico.
`NodoDeSiguientes`| Clase que posee una lista para almacenar los siguientes del metodo del arbol.
`ObjetoError`| Objeto que modela los errores en las cadenas a validar en el archivo de entrada.
`Transicion`| Objeto que modela las transiciones del metodo del arbol.
`TransicionAFN`| Objeto que modela las transiciones del metodo de Thompson.

### Funciones de la clase Arbol ###
Función |  Definición 
------------ | -------------
`ReconocerCadena`| Funcion para evaluar las cadenas en el archivo de entrada.
`enumId`| Funcion para enumerar las hojas del arbol.
`defineNullable`| Define que nodos del arbol son anulables.
`DefineFirst`| Define los primeros de cada nodo hoja.
`DefineLast`| Define los ultimos de cada nodo hoja.
`DefineFollowTable`| Se encarga de almacenar la tabla de siguientes. 
`DefineFollow`| Define los siguientes de las cerraduras de kleene y concatenaciones. 
`DefinirConjunto`| Define los conjuntos del archivo de entrada. 
`TablaTransiciones`| Funcion para almacenar la tabla de transiciones.
`GraphTree`| Grafica el metodo del arbol
`GraficarArbol`| Metodo para guardar la grafica del metodo del arbol en su carpeta respectiva 
`GraficarSiguientes`|  Grafica tabla de siguientes
` GraficarTransiciones`| Grafica tabla de transiciones.
`GraficarAFD`|  Grafica el automata determinista finito.
`GraficarAFN`|  Grafica automata finito no determinista.


### Funciones de la clase Conjunto ###
Función |  Definición 
------------ | -------------
`Conjunto` | Constructor de la clase conjunto.
`DefinirConjuntoSeparador` | se define conjunto separador ~
`DefinirConjuntoComas` | Se define el conjunto por comas
`ImprimirConjunto` | Debug para ver si esta guardo correctamente los conjuntos.