import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Lector {
    //Matriz, Palabras reservadas y Errores
    int[][] matriz={/* L,     @,     _,     d,     +,     -,     *,     /,     ^,     <,     >,     =,     !,     &,     |,     (,     ),     {,     },     ,,     ;,     ",    eb,   tab,     nl,     .,     eof,     oc  */
    /*          0,     1,     2,     3,     4,     5,     6,     7,     8,     9,    10,    11,    12,    13,    14,    15,    16,    17,    18,    19,    20,    21,    22,    23,     24,     25,     26,     27  */
    /* 0 */{    1,     1,     1,     2,   103,   104,   105,     5,   107,     8,     9,    10,    11,    12,    13,   117,   118,   119,   120,   124,   125,    14,     0,     0,      0,    505,    505,    505},
    /* 1 */{    1,     1,     1,     1,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,   100,    100,    100,    100,    100}, 
    /* 2 */{  101,   101,   101,     2,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,   101,    101,      3,    101,    101},
    /* 3 */{  500,   500,   500,     4,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,   500,    500,    500,    500,    500},
    /* 4 */{  102,   102,   102,     4,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,   102,    102,    102,    102,    102}, 
    /* 5 */{  106,   106,   106,   106,   106,   106,     6,   106,   106,   106,   106,   106,   106,   106,   106,   106,   106,   106,   106,   106,   106,   106,   106,   106,    106,    106,    106,    106},
    /* 6 */{    6,     6,     6,     6,     6,     6,     7,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,      6,      6,    501,      6}, 
    /* 7 */{    6,     6,     6,     6,     6,     6,     6,     0,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,     6,      6,      6,      6,      6},
    /* 8 */{  108,   108,   108,   108,   108,   108,   108,   108,   108,   108,   108,   110,   108,   108,   108,   108,   108,   108,   108,   108,   108,   108,   108,   108,    108,    108,    108,    108},
    /* 9 */{  109,   109,   109,   109,   109,   109,   109,   109,   109,   109,   109,   111,   109,   109,   109,   109,   109,   109,   109,   109,   109,   109,   109,   109,    109,    109,    109,    109},
    /* 10 */{ 123,   123,   123,   123,   123,   123,   123,   123,   123,   123,   123,   112,   123,   123,   123,   123,   123,   123,   123,   123,   123,   123,   123,   123,    123,    123,    123,    123},
    /* 11 */{ 116,   116,   116,   116,   116,   116,   116,   116,   116,   116,   116,   113,   116,   116,   116,   116,   116,   116,   116,   116,   116,   116,   116,   116,    116,    116,    116,    116},
    /* 12 */{ 502,   502,   502,   502,   502,   502,   502,   502,   502,   502,   502,   502,   502,   114,   502,   502,   502,   502,   502,   502,   502,   502,   502,   502,    502,    502,    502,    502},
    /* 13 */{ 503,   503,   503,   503,   503,   503,   503,   503,   503,   503,   503,   503,   503,   503,   115,   503,   503,   503,   503,   503,   503,   503,   503,   503,    503,    503,    503,    503},
    /* 14 */{  14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,    14,   122,    14,    14,    504,     14,     14,     14}

    };


    String[][] Reservadas =
    {
        {"break"  ,"200"},
        {"if"         ,"201"},
        {"else"    ,"202"},
        {"main"   ,"203"},
        {"while"   ,"204"},
        {"goto"    ,"205"},
        {"print"    ,"206"},
        {"new"     ,"207"},
        {"float"     ,"208"},
        {"int"        ,"209"},
        {"false"    ,"210"},
        {"true"     ,"211"},
        {"string"   ,"212"},
        {"bool"     ,"213"},
        {"getvalue", "214"}

    };


    String[][] Errores =
    {
        {"Se espera un digito"                           , "500"},
        {"Se espera un cierre de comentario" , "501"},
        {"Se espera un &"                                  , "502"},
        {"Se espera un |"                                    , "503"},
        {"Se espera un cierre de cadena"       , "504"},
        {"caracter no valido "                             , "505"},
    };



    //Variables a utilizar para la lectura
    Nodo Inicio = null, Puntero;
    int Estado = 0, Column,MatrizValue, Renglon = 1;
    int caracter; 
    String lexema="";
    boolean band = false;


//Archivo a leer
    private File archivo = null;

    public void setArchivo(File Archivo){
        this.archivo = Archivo;
    }

    FileReader Lector;
    BufferedReader buffer;

    public boolean Lectura(){
        band = false;
        Inicio = null;
        Renglon = 1;
        try{
            Lector = new FileReader(archivo);
            buffer = new BufferedReader(Lector);
            buffer.mark(10000000);
    
            while(caracter != -1)
            {
                caracter = buffer.read();
                if(Character.isLetter((char) caracter))
                {
                    Column = 0;
                }
                else if (Character.isDigit((char) caracter))
                {
                    Column = 3;
                }
                else
                {
                    switch ((char) caracter)
                    {
                        case '@':
                            Column = 1;
                            break;
                
                        case '_':
                            Column = 2;
                            break;
                    
                        case '+':
                            Column = 4;
                            break;
                    
                        case '-':
                            Column = 5;
                            break;
                            
                        case '*':
                            Column = 6;
                            break;
                            
                        case '/':
                            Column = 7;
                            break;
                            
                        case '^':
                            Column = 8;
                            break;
                            
                        case '<':
                            Column = 9;
                            break;
                            
                        case '>':
                            Column = 10;
                            break;
                            
                        case '=':
                            Column = 11;
                            break;
                            
                        case '!':
                            Column = 12;
                            break;
                            
                        case '&':
                            Column = 13;
                            break;
                            
                        case '|':
                            Column = 14;
                            break;
                            
                        case '(':
                            Column = 15;
                            break;
                            
                        case ')':
                            Column = 16;
                            break;
                        
                        case '{':
                            Column = 17;
                            break;
                            
                        case '}':
                            Column = 18;
                            break;
                            
                        case ',':
                            Column = 19;
                            break;
                            
                        case ';':
                            Column = 20;
                            break;
                            
                        case '"':
                            Column = 21;
                            break;
                            
                        case '.':
                            Column = 25;
                            break;
                            
                        case 10: //nl
                            Column = 24;
                            Renglon++;
                            break;
                            
                        case 13: //aoc
                            Column = 24;
                            break;
                            
                        case 9: //Tab
                            Column = 23;
                            break;
                            
                        case 32: //eb
                            Column =  22;
                            break;

                            
                        default:
                            if(caracter == -1)
                            {
                                Column = 26;
                            }
                            else
                            {
                                Column = 27;
                            }
                            break;
                    }
            
        
                }
        
                MatrizValue = matriz[Estado][Column];
                if(MatrizValue <100)
                {
                    Estado = MatrizValue;
                    if(Estado == 0)
                    {
                        lexema = "";
                    }
                    else
                    {
                        lexema = lexema + ((char)caracter);
                    }
            
                }
                else if(MatrizValue>=100 && MatrizValue < 500)
                {
                    if(MatrizValue == 100)
                    {
                        PalabraReservada();
                    }
                    

                    if(MatrizValue==100 || MatrizValue == 101 || MatrizValue == 102 || MatrizValue == 106 || MatrizValue == 108 ||
                            MatrizValue == 109 || MatrizValue == 123 || MatrizValue == 116 ||  MatrizValue == 200 || MatrizValue == 201
                            || MatrizValue == 202 || MatrizValue == 203 || MatrizValue == 204 || MatrizValue == 205 || MatrizValue == 206
                            || MatrizValue == 207 || MatrizValue == 208 || MatrizValue == 209 || MatrizValue == 210 || MatrizValue == 211
                            || MatrizValue == 212 || MatrizValue == 213 || MatrizValue == 214)
                    {
                        buffer.reset();
                    }
                    else
                    {
                        lexema = lexema +(char)caracter;
                    }

                    GenerarNodo(lexema,MatrizValue,Renglon);
                    Estado = 0;
                    lexema = "";
                    
                }
                else
                {
                    EstadoError();
                    Estado = 0;
                    lexema = "";
                    
                }
                buffer.mark(10000000);

            }
            caracter = 0;
            if(band == false)
            {
                    ImprimirNodos();
            }
    
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return band;
    }

    public void PalabraReservada(){
        for(String[] palReservadas : Reservadas)
        {
            if(lexema.equals(palReservadas[0]))
            {
                MatrizValue = Integer.valueOf(palReservadas[1]);
            }
        }
    }

    public void EstadoError(){
        if((caracter != -1 && MatrizValue >= 500) || (caracter == -1 && MatrizValue == 501 ) )
        {
        for(String[] Error : Errores)
            {
                if(MatrizValue == (Integer.valueOf(Error[1])))
                {
                    System.out.println("El error es:  " + Error[0] + "  Error:  "  + MatrizValue + " Caracter:  " + caracter + " Renglon: " + Renglon);
                }
            }
            band = true;
        }

    }

    public void GenerarNodo(String lexema, int MatrizValue, int Renglon)
{
    Nodo nodo = new Nodo(lexema,MatrizValue,Renglon);
 
    if(Inicio  == null)
    {
        Inicio = nodo;
        Puntero = Inicio;
    }
    else
    {
        Puntero.setUnion(nodo);
        Puntero = nodo;
    }
}

    public void ImprimirNodos()
    {
        Puntero = Inicio;
        while(Puntero != null)
        {
            System.out.println(Puntero.getLexema() + "  " + Puntero.getToken() + "  " + Puntero.getRenglon());
            Puntero = Puntero.getUnion();
        }
    }
}