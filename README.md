Descripción general del programa.
-----------------------
  
ExpAnalyzer es un programa para realizar dos metodos de analisis lexico, el metodo del arbol y el metodo de Thompson mediante la creacion de un archivo con los lineamientos descritos o subir un archivo previamente creado. Su funcion principal es validar las cadenas propuestas en los archivos de entrada y desplegar un reporte den formato JSON de las cadenas validas y no validas, como tambien la visualizacion de los automatas junto a las demas graficas para realizar los mismos. 

Requerimientos del sistema.
------------------------
• java version "1.8" 

• Java(TM) SE Runtime Environment (build 8.2)

• IDE utilizada: NetBeans IDE 8.2 RC

• Espacio en memoria: 1 MB como mínimo

• Versión de Graphviz: graphviz version 2.50.0 (20211204.2007)

• Librerias: Gson 1.8, java-cup-11b-runtime


Guia de uso de programa.
---------------------------
#### Archivo de entrada ####
El programa permite crear o cargar un archivo con la extencion .exp el cual el programa validara si no tiene ningun error lexico - sintactico.

#### Generacion de automatas ####
Tras validar el archivo de entrada el programa generara dos tipos de automatas AFN y AFD. Por medio de los metodos del arbol y de Thompson.

#### Analizar Cadenas ####
Tras la generacion de automatas se valiran las cadenas propuestas dentro del archivo de entrada de lo contrario aparecera que sucedio un error sintactico. 

#### Generacion de reporte JSON ####
Luego de realizar el la accion de analizar cadenas, se creara un archivo con formato JSON que mostrara el nombre de la regex y si la cadena con el mismo nombre de la regex es valida o no. 

#### VISUALIZACION DE REPORTES DE METODO DEL ARBOL Y DE THOMPSON ####
EL programa posee un vializador de imagenes que proporcionara las imagenes del metodo del arbol con lo siguiente: tabla de siguientes, tabla de transiciones, Grafo del metodo del arbol y su respectivo automata. Por medio el metodo de Thompson solo generara el grafo del AFN. 

Glosario de Terminos
-----------------------

Término |  Definición 
------------ | -------------
`AFD`  | Automata finito determinista en el cual existe siempre no más de una transición posible desde ese estado y con ese símbolo.
`AFND` | Es el autómata finito que tiene transiciones con Epsilom o que por cada símbolo desde un estado de origen se llega a más de un estado destino.
`Autómata` | Máquina automática programable capaz de realizar determinadas operaciones de manera autónoma.
`Expresion regular` | Son patrones utilizados para encontrar una determinada combinación de caracteres dentro de una cadena de texto.