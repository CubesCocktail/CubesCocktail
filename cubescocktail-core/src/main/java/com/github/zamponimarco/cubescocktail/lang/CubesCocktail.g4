grammar CubesCocktail;

object
    : ID parameter_map
    | ID
    | list
    ;

parameter_map
    : '{' parameter (',' parameter)* '}'
    ;

parameter
    : ID':'object
    ;

list
    : '[' object (',' object)* ']'
    ;

WS : [ \t\r\n]+ -> skip ;
ID : [a-zA-Z_] [a-zA-Z_0-9]* ;
