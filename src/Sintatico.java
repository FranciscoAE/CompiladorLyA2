import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Sintatico {
    //Sintactico
    Nodo p;
    Frame fr;
     
    //Para semantico
    String TipoDato, Linea, Id;
    int token;
    ArrayList<TablaSimbolos>  a = new ArrayList<TablaSimbolos>();
    ArrayList<Hoja>  b = new ArrayList<Hoja>();
    TablaSimbolos R;
    Nodo temp;
    
    //Para Intermedio 
    Intermedio intermedio = new Intermedio();
    //Constructor
    public Sintatico(Nodo nodo, Frame Fr)
    {
        p = nodo;
        fr = Fr;
    }
    
    String[][] Errores =
    {
        {"Se esperaba la palabra: main",                                                "1"},
        {"Se esperaba una apertura de parentesis:: (" ,                                 "2"},
        {"Se esperaba un cierre de parentesis: ) ",                                     "3"},
        {"Se esperaba una apertura de llave: {",                                        "4"},
        {"Se esperaba un cierre de llave: } ",                                          "5"},
        {"Se esperaba un identificador: id",                                            "6"},
        {"Se esperaba una palabra reservada: if /while/print/id",                       "7"},
        {"Se esperaba un cierre de instruccion: ;",                                     "8"},
        {"Se esperaba la declaracion de alguna variable: bool/int/float/string",        "9"},
        {"Se esperaba la declaracion de alguna sentencia",                             "10"},
        {"Se esperaba el simbolo igualitario: =",                                      "11"},
        {"Se esperaba un operador aditivo",                                            "12"},
        {"Se esperaba un operador multiplicativo",                                     "13"},
        {"Se esperaba la declaracion de un signo",                                     "14"},
        {"Se esperaba la declaracion de una expresion relacional",                     "15"},
        {"Se esperaba un signo de exclamacion: !",                                     "16"},
        {"Se espera un cierre de cadena",                                              "17"},
        {"Se espera el uso de una coma: , ",                                           "18"},
        {"Se esperaba la palabra reservada: new ",                                     "19"},
        {"Se espera un identificador(id) o una cadena ",                               "20"},
        {"Se esperaba un valor de asignación:",                                        "21"},
        //Semantico
        {"La variable aun NO ha sido declarada",                                       "22"},
        {"La variable YA ha sido declarada",                                           "23"},
        {"Desbordamiento. La variable no puede almacenar mas de 10 digitos",           "24"},
        {"Desbordamiento. La variable no puede almacenar mas de 100 caracteres",       "25"},
        {"El valor asignado no concuerda con el tipo de la variable",                  "26"},
        {"Los valores no concuerdan para ser comparados logicamente",                  "27"},
    }; 
      
    public void pawn()
    {
        while(p != null)
        {
            if(p != null && ( p.getToken() == 203))
            {
                p  = p.getUnion();
                if(p != null && (p.getToken() == 117))
                {
                    p  = p.getUnion();
                    if(p != null && (p.getToken() == 118))
                    {
                        p  = p.getUnion();
                        if(p != null &&(p.getToken() == 119))
                        {
                            p = p.getUnion();
                            while(p != null && (p.getToken() == 207))
                                {
                                    variable();
                                }
                            if(p != null && (p.getToken() == 201 || p.getToken() == 204 || p.getToken() == 206 || p.getToken() == 100))
                            {
                                while(p != null && (p.getToken() != 120))
                                {
                                    statement();
                                }
                                if(p != null && (p.getToken() == 120))
                                {
                                    p = p.getUnion();
                                }
                                else
                                {
                                    ImprimirError(5);
                                    ErrorSintatico();
                                    throw new TerminacionMetodoException("");
                                }
                            }
                            else
                            {
                                  if(p != null && (p.getToken() == 120) )
                                  {
                                     p = p.getUnion();
                                  }
                                  else
                                  {
                                     ImprimirError(19);
                                     ErrorSintatico();
                                     throw new TerminacionMetodoException("");
                                  }
                            }
                        }
                        else
                        {
                            ImprimirError(4);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                    else
                    {
                        ImprimirError(3);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                    ImprimirError(2);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
            }
            else
            {
            ImprimirError(1);
            ErrorSintatico();
            throw new TerminacionMetodoException("");
            }
        }
       System.out.println("Analizador Sintatico/Semantico Terminado. \n");
       System.out.println( "\tDato\tId\tRenglon\tToken\tValor\t\n");
       for (TablaSimbolos  b :  a) {
            System.out.println("\t"+b.getDato() + "\t" + b.getId() + "\t" + b.getRenglon() + "\t" + b.getToken() + "\t" + b.getValor()+"\t");
       }
       intermedio = new Intermedio(b, a);
       intermedio.CrearIntermedio();
    }

    public void variable(){
        if(p != null &&(p.getToken() == 207)){
            p = p.getUnion();
            tipos();
            if(p != null && (p.getToken() == 100)) {
                temp = p;
                p = p.getUnion();
                if(p != null && (p.getToken() == 124))
                {
                    guardarVariable();
                    variableRecursividad();
                    if(p != null &&(p.getToken() == 125))
                    {
                        guardarVariable();
                        p = p.getUnion();
                    }
                    else{
                        ImprimirError(8);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else if(p != null && (p.getToken() == 123))
                {
                    p = p.getUnion();
                    exp_simp();
                    arbolAsignacion(temp, 0);
                    if(p != null && (p.getToken() == 124))
                    {
                        guardarVariable();
                        variableRecursividad();
                        if(p != null &&(p.getToken() == 125))
                        {
                            guardarVariable();
                            p = p.getUnion();
                        }
                        else
                        {
                            ImprimirError(8);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                    else
                    {
                        if(p != null &&(p.getToken() == 125))
                        {
                            guardarVariable();
                            p = p.getUnion();
                        }
                        else
                        {
                            ImprimirError(8);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                }
                else {
                    if(p != null &&(p.getToken() == 125))
                    {
                        guardarVariable();
                        p = p.getUnion();
                    }
                    else
                    {
                        ImprimirError(8);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
            }
            else {
                ImprimirError(6);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
            }
        }
        else {
            ImprimirError(19);
            ErrorSintatico();
            throw new TerminacionMetodoException("");
        }
    }

    public void variableRecursividad()
    {
        if(p != null &&(p.getToken() == 124))
        {
            p = p.getUnion();
            if(p != null &&(p.getToken() == 100))
            {   
                temp = p;
                p = p.getUnion();
                if(p != null && (p.getToken() == 123))
                {
                    p = p.getUnion();
                    exp_simp();
                    arbolAsignacion(temp, 0);
                    if(p != null &&(p.getToken() == 124))
                    {
                        guardarVariable();
                        variableRecursividad();
                    }
                }
                else
                {
                    if(p != null &&(p.getToken() == 124))
                    {
                        guardarVariable();
                        variableRecursividad();
                    }
                }

            }
            else
            {
                ImprimirError(6);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
            }
        }
        else
        {
            ImprimirError(18);
            ErrorSintatico();
            throw new TerminacionMetodoException("");
        }
    }

    public void statement()
    {
        switch (p.getToken()) {
            case 201:
                //if - else
                p = p.getUnion();
                if(p != null &&(p.getToken() == 117))
                {
                    p = p.getUnion();
                    Nodo temp = p;
                    exp_Cond();
                    if(p != null &&(p.getToken() == 118))
                    {
                        arbolAsignacion(temp, 1);
                        p = p.getUnion();
                        if(p != null &&(p.getToken() == 119))
                        {
                            p = p.getUnion();
                            while(p.getToken() != 120 && p!= null)
                            {
                                statement();
                            }
                            if(p != null &&(p.getToken() == 120))
                            {
                                p = p.getUnion();
                                if(p != null &&(p.getToken() == 202))
                                {
                                    p = p.getUnion();
                                    if(p != null &&(p.getToken() == 119))
                                    {
                                        p = p.getUnion();
                                        while(p != null && (p.getToken() != 120))
                                        {
                                            statement();
                                        }
                                        if(p != null &&(p.getToken() == 120))
                                        {
                                            p = p.getUnion();
                                        }
                                        else
                                        {
                                            ImprimirError(5);
                                            ErrorSintatico();
                                            throw new TerminacionMetodoException("");
                                        }
                                    }
                                    else
                                    {
                                    ImprimirError(4);
                                    ErrorSintatico();
                                    throw new TerminacionMetodoException("");
                                    }
                                }
                                
                            }
                            else
                            {
                                ImprimirError(5);
                                ErrorSintatico();
                                throw new TerminacionMetodoException("");
                            }
                        }
                        else
                        {
                            ImprimirError(4);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                    else
                    {
                        ImprimirError(3);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                        ImprimirError(2);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                }
                break;
            case 204:
                //while
                p = p.getUnion();
                if(p != null &&(p.getToken() == 117))
                {
                    p = p.getUnion();
                    Nodo temp = p;
                    exp_Cond();
                    if(p != null &&(p.getToken() == 118))
                    {
                        arbolAsignacion(temp, 1);
                        p = p.getUnion();
                        if(p != null &&(p.getToken() == 119))
                        {
                            p = p.getUnion();
                            while(p.getToken() != 120 && p.getUnion() != null)
                            {
                                statement();
                            }
                            if(p != null &&(p.getToken() == 120))
                            {
                                p = p.getUnion();
                            }
                            else
                            {
                                ImprimirError(5);
                                ErrorSintatico();
                                throw new TerminacionMetodoException("");
                            }
                        }
                        else
                        {
                            ImprimirError(4);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                    else
                    {
                        ImprimirError(3);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                    ImprimirError(2);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
                break;
            case 206:
                //print
                p = p.getUnion();
                if(p != null &&(p.getToken() == 117))
                {
                    p = p.getUnion();
                    if(p != null &&(p.getToken()==100 || p.getToken() == 122))
                    {
                        if(p.getToken() == 100)
                        {
                            ValidarExistencia(a);
                        }
                        p = p.getUnion();
                            if(p != null &&(p.getToken() == 124))
                            {
                                PrintRecursividad();
                                if(p != null &&(p.getToken() == 118))
                                {
                                    p = p.getUnion();
                                if(p != null &&(p.getToken() == 125))
                                    {
                                        p = p.getUnion();
                                    }
                                else
                                {
                                    ImprimirError(8);
                                    ErrorSintatico();
                                    throw new TerminacionMetodoException("");
                                }
                                }
                                else
                                {
                                    ImprimirError(3);
                                    ErrorSintatico();
                                    throw new TerminacionMetodoException("");
                                }
                            }
                            else
                            {
                                if(p != null &&(p.getToken() == 118))
                                {
                                    p = p.getUnion();
                                    if(p != null &&(p.getToken() == 125))
                                    {
                                        p = p.getUnion();
                                    }
                                    else
                                    {
                                        ImprimirError(8);
                                        ErrorSintatico();
                                        throw new TerminacionMetodoException("");
                                    }
                                }
                                else
                                {
                                    ImprimirError(3);
                                    ErrorSintatico();
                                    throw new TerminacionMetodoException("");
                                }
                            }
                    }
                    else
                    {
                        ImprimirError(6);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                    ImprimirError(2);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
                break;
                //Asignación de variable
            case 100:
                ValidarExistencia(a);
                Nodo temp = p;
                p = p.getUnion();
                if(p != null &&(p.getToken() ==  123))
                {
                    p = p.getUnion();
                    //getvalue
                    if(p != null && (p.getToken() == 214))
                    {
                        p = p.getUnion();
                        if(p!=null &&(p.getToken () == 117))
                        {
                            p = p.getUnion();
                            if(p!=null &&(p.getToken() == 118))
                            {
                                p = p.getUnion();
                            }
                            else
                            {
                                ImprimirError(3);
                                ErrorSintatico();
                                throw new TerminacionMetodoException("");
                            }
                        }
                        else
                        {
                            ImprimirError(2);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                    else
                    {
                        exp_simp();
                    }
                    if(p != null &&(p.getToken() == 125))
                    {
                        //Aqui ira lo de Arbol

                        arbolAsignacion(temp, 0);
                        p = p.getUnion();
                    }
                    else
                    {
                        ImprimirError(8);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                    ImprimirError(11);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
                break;
            case 207:
                variable();
                break;
            default:
                break;
        }
    }

    public void PrintRecursividad()
    {
        if(p != null &&(p.getToken() == 124))
        {
            p = p.getUnion();
            if(p != null &&(p.getToken() == 100 || p.getToken() == 122))
            {
                if(p.getToken() == 100)
                {
                    ValidarExistencia(a);
                }
                p = p.getUnion();
                if(p != null &&(p.getToken() == 124))
                {
                    PrintRecursividad();
                }

            }
            else
            {
                ImprimirError(20);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
            }
        }
        else
        {
            ImprimirError(18);
            ErrorSintatico();
            throw new TerminacionMetodoException("");
        }
    }

    public void tipos()
        {
            switch(p.getToken())
            {
                case 208:
                    TipoDato = (p.getLexema());
                    token = (p.getToken());
                    p = p.getUnion();
                    break;
                case 209:
                    TipoDato = (p.getLexema());
                    token = (p.getToken());
                    p = p.getUnion();
                    break;
                case 212:
                    TipoDato = (p.getLexema());
                    token = (p.getToken());
                    p = p.getUnion();
                    break;
                case 213:
                    TipoDato = (p.getLexema());
                    token = (p.getToken());
                    p = p.getUnion();
                    break;
                default:
                    ImprimirError(9);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
            }
        }

    public void exp_Cond() 
        {
            //aqui comienza
            exp_simp();
            exp_relac();
            exp_simp();
            if(p != null && (p.getToken() == 115 || p.getToken() == 114))
            {
                exp_AdCond();
                exp_Cond();
            }
        }
    
    public void exp_simp() 
    {
        termino();
            
        if(p != null &&(p.getToken() == 103 || p.getToken() == 104))
        {
            op_aditivo();
            exp_simp();
        }
            
    }
    
    public void exp_AdCond()
    {
        if(p != null &&(p.getToken() ==115 || p.getToken() ==114))
        {
            p = p.getUnion();
        }
        else
        {
            ImprimirError(13);
            ErrorSintatico();
            throw new TerminacionMetodoException("");
        }
    }

    private void termino() 
    {
        factor();
        if(p != null &&(p.getToken() == 105 || p.getToken() == 106))
        {
            op_mult();
            termino();
        }
    }
    
    private void factor()
    {
        switch(p.getToken())
        {
            case 100:
                ValidarExistencia(a);
                p = p.getUnion();
                break;
            case 101:
                p = p.getUnion();
                break;
            case 102:
                p = p.getUnion();
                break;
            case 122:
                p = p.getUnion();
                break;
            case 211:
                p = p.getUnion();
                break;
            case 210:
                p = p.getUnion();
                break;
            case 116:
                p = p.getUnion();
                factor();
                break;
            case 103:
                signo();
                factor();
                break;
            case 104:
                signo();
                factor();
                break;
            case 117:
                p = p.getUnion();
                exp_simp();
                if(p != null &&(p.getToken() == 118))
                {
                    p = p.getUnion();
                }
                else
                {
                    ImprimirError(3);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
                break;
                default:
                    ImprimirError(21);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
        }
    }
    
    public void exp_relac()
    {
    switch(p.getToken())
    {
        case 108:
            p = p.getUnion();
            break;
        case 109:
            p = p.getUnion();
            break;
        case 110:
            p = p.getUnion();
            break;
        case 111:
            p = p.getUnion();
            break;
        case 112:
            p = p.getUnion();
            break;
        case 113:
            p = p.getUnion();
            break;
        default:
                ImprimirError(15);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
    }
    
    }
    
    private void signo() 
    {
        if(p != null &&(p.getToken() == 103 || p.getToken() == 104))
        {
            p = p.getUnion();

        }
        else
        {
            ImprimirError(14);
            ErrorSintatico();
            throw new TerminacionMetodoException("");
        }
    }

    public void op_mult()
        {
            if(p != null &&(p.getToken() == 105 || p.getToken() ==106 || p.getToken() ==114))
            {
                p =p.getUnion();
            }
            else
            {
                ImprimirError(13);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
            }
        }

    public void op_aditivo()
    {
      
       if(p != null &&(p.getToken()== 103 || p.getToken()==104 || p.getToken()==115))
       {
            p = p.getUnion();
       }
      
       else
      {
          ImprimirError(12);
          ErrorSintatico();
          throw new TerminacionMetodoException("");
      }
  }
    
    public void ImprimirError(int bandError)
    {
        for(String[] Error : Errores)
        {
            if(bandError == (Integer.valueOf(Error[1])))
            {
                if(p != null){
                    System.out.println("El error es:  "  + Error[0]  + " en la linea: " + p.getRenglon());
                }
                else
                {
                    System.out.println("El error es:  " + Error[0] );
                }
            }
        }
    }

//SEMANTICO SECCION
//Fase semantica.

//Detección de variable declarada 

//variable duplicada 

//tipo de dato

//capacidad del tipo de dato

    //variables para usar
    Stack<Integer> semantico = new Stack<Integer>();
    int tipo;
    
    public void guardarVariable()
    {
        Id = temp.getLexema();
        Linea = String.valueOf(temp.getRenglon());
        ValidarIgualdad(a);
        R = new TablaSimbolos(TipoDato,token, Id, Linea);
        a.add(R);
    }

    public void ValidarExistencia(ArrayList<TablaSimbolos> a)// Detección de variable declarada
    {
        for(TablaSimbolos b : a)
        {
            if(p.getLexema().equals(b.getId()))
            {
                return;
            }
        }
        System.out.println("Error con el identificador: " + p.getLexema());
        ImprimirError(22);
        ErrorSintatico();
        throw new TerminacionMetodoException(""); 

    }

    public void ValidarIgualdad(ArrayList<TablaSimbolos> a) // Variable Duplicada
    {
        
        for(TablaSimbolos  b : a)
        {
            if(temp.getLexema().equals(b.getId()))
            {
                System.out.println("Error con el identificador: " + temp.getLexema());
                ImprimirError(23);
                ErrorSintatico();
                throw new TerminacionMetodoException(""); 
            }
                
        }
    }

    public void arbolAsignacion(Nodo n, int opc){
        Stack<Nodo> Simbolos = new Stack<Nodo>(); 
        Stack<Hoja> cantidades = new Stack<Hoja>();
        if(opc == 0){
            while(n.getToken() != 125 && n.getToken() != 124){

                if(n.getToken() == 100 || n.getToken() == 101 || n.getToken() == 102 || n.getToken() == 122 || 
                n.getToken() == 210 ||  n.getToken() == 211){  //id,entero,decimal,cadena,true,false
                //Aqui se evalua el error longitud de variable.
                    switch(n.getToken()){
                        case 100:
                            break;
                        case 101:
                            if(n.getLexema().length() > 10){
                                System.out.println("Error con el identificador: " + n.getToken());
                                ImprimirError(24);
                                fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                                fr.getLabelSem().setText("----X----");;
                                fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                                fr.getLabelSin().setText("----?----");
                                throw new TerminacionMetodoException(""); 
                            }
                            break;
                        case 102:
                            if(n.getLexema().length() > 10)
                            {
                                fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                                fr.getLabelSem().setText("----X----");;
                                fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                                fr.getLabelSin().setText("----?----");
                                System.out.println("Error con el identificador: " + n.getToken());
                                ImprimirError(24);
                                throw new TerminacionMetodoException(""); 
                            }
                            break;
                        case 122:
                            if(n.getLexema().length() > 100)
                            {
                                fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                                fr.getLabelSem().setText("----X----");;
                                fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                                fr.getLabelSin().setText("----?----");
                                System.out.println("Error con el identificador: " + n.getToken());
                                ImprimirError(25);
                                throw new TerminacionMetodoException(""); 
                            }
                            break;
                        default:
                            break;
                    }
                    Hoja acc = new Hoja(n);
                    cantidades.push(acc);
                    n = n.getUnion();
                }
                else if(n.getToken() == 117){ // ( 
                    if(n.getUnion().getToken() == 103 || n.getUnion().getToken() == 104)
                    {

                        Simbolos.push(n); 
                        n = n.getUnion();
                        
                        
                        Nodo op = n;
                        n = n.getUnion();
                        Hoja d2 = new Hoja(n);
                        Hoja d1 = new Hoja(new Nodo("0",101,n.getRenglon()));
                        Hoja Tope = new Hoja(op);
                        Tope.setIzq(d1);
                        Tope.setDer(d2); 
                        cantidades.push(Tope); 

                        n = n.getUnion();
                    
                    }
                    else{
                        Simbolos.push(n); 
                        n = n.getUnion(); 
                    }
                }
                else if(n.getToken() == 118){ // )
                    while(!Simbolos.isEmpty() && !(Simbolos.peek().getToken() == 117)) 
                    { 
                        Nodo op = Simbolos.pop(); 
                        Hoja d2 = cantidades.pop(); 
                        Hoja d1 = cantidades.pop(); 
                        Hoja Tope = new Hoja(op); 
                        //Creamos el nodo 
                        Tope.setIzq(d1); 
                        Tope.setDer(d2); 
                        cantidades.push(Tope); 
                    }
                    Simbolos.pop();
                    n = n.getUnion();
                }

                else // cualquier otro operador como -,*,/,+
                {
                    if(n.getUnion().getToken() == 103 || n.getUnion().getToken() == 104){ //Si el siguiente valor tiene un signo
                        Simbolos.push(n); 
                        n = n.getUnion();

                        Nodo op = n;
                        n = n.getUnion();
                        Hoja d2 = new Hoja(n);
                        Hoja d1 = new Hoja(new Nodo("0",101,n.getRenglon()));
                        Hoja Tope = new Hoja(op);
                        Tope.setIzq(d1);
                        Tope.setDer(d2); 
                        cantidades.push(Tope); 

                        n = n.getUnion();

                    }
                    else{
                        while(!Simbolos.isEmpty() && prioridad(n) <= prioridad(Simbolos.peek())) 
                        { 
                            Nodo op = Simbolos.pop(); 
                            Hoja d2 = cantidades.pop(); 
                            Hoja d1 = cantidades.pop(); 
                            Hoja Tope = new Hoja(op); 
                            //Creamos el nodo 
                            Tope.setIzq(d1); 
                            Tope.setDer(d2); 
                            cantidades.push(Tope); 
                        }    
                        Simbolos.push(n); 
                        n = n.getUnion();
                    }
                    
                }
            }

            while (!Simbolos.isEmpty()) 
            { 
                Nodo op = Simbolos.pop(); 
                Hoja d2 = cantidades.pop(); 
                Hoja d1 = cantidades.pop(); 
                Hoja Tope = new Hoja(op); 
                //Creamos el nodo 
                Tope.setIzq(d1); 
                Tope.setDer(d2); 
                cantidades.push(Tope); 
            }

            Hoja peek = cantidades.peek();
            AnalisisValor(peek,0);
            b.add(peek);

            if(!(peek.getDer().getIzq() != null && peek.getDer().getIzq() != null))
            {
                buscarVariable(peek.getIzq().getNodo(), peek.getDer().getNodo());
            }

        }
        else{
            while(n.getToken() != 118){

                if(n.getToken() == 100 || n.getToken() == 101 || n.getToken() == 102 || n.getToken() == 122 || 
                   n.getToken() == 210 ||  n.getToken() == 211){
                    
                    Hoja acc = new Hoja(n);
                    cantidades.push(acc);
                    n = n.getUnion();
                }
                else
                {
                    while(!Simbolos.isEmpty() && prioridad(n) <= prioridad(Simbolos.peek())) 
                    { 
                        Nodo op = Simbolos.pop(); 
                        Hoja d2 = cantidades.pop(); 
                        Hoja d1 = cantidades.pop(); 
                        Hoja Tope = new Hoja(op); 
                        //Creamos el nodo 
                        Tope.setIzq(d1); 
                        Tope.setDer(d2); 
                        cantidades.push(Tope); 
                    } 
                    Simbolos.push(n); 
                    n = n.getUnion();
                }
    
            }
    
            while (!Simbolos.isEmpty()) 
            { 
                Nodo op = Simbolos.pop(); 
                Hoja d2 = cantidades.pop(); 
                Hoja d1 = cantidades.pop(); 
                Hoja Tope = new Hoja(op); 
                //Creamos el nodo 
                Tope.setIzq(d1); 
                Tope.setDer(d2); 
                cantidades.push(Tope); 
            }
            Hoja peek = cantidades.peek();
            AnalisisValor(peek, 1);
            b.add(peek);

        }
        
    }
                                                            //Tipo de dato
    private void AnalisisValor(Hoja peek, int opc) {
        if(opc == 0){
            if(peek != null)
            {
                AnalisisValor(peek.getIzq(),0);
                AnalisisValor(peek.getDer(),0);
                if(peek.n.getToken() == 100 || peek.n.getToken() == 101 || peek.n.getToken() == 102 || peek.n.getToken() == 122 || 
                peek.n.getToken() == 210 ||  peek.n.getToken() == 211 ){
                    //token
                    if(peek.n.getToken() == 100)
                    {
                        int t = buscarVariable(peek.n);
                        semantico.push(t); 
                    }
                    else if(peek.n.getToken() == 101){
                        int t = 209;
                        semantico.push(t); 
                    }
                    else if(peek.n.getToken() == 102){
                        int t = 208;
                        semantico.push(t); 
                    }
                    else if(peek.n.getToken() == 122){
                        int t = 212;
                        semantico.push(t); 
                    }
                    else
                    {
                        int t = 213;
                        semantico.push(t); 
                    }
                }
                else if(peek.n.getToken() == 103 || peek.n.getToken() == 104 || peek.n.getToken() == 105 || peek.n.getToken() == 106){

                    int d2 = semantico.pop();
                    int d1 = semantico.pop();
                    tipo = TablaTipos(peek.n.getToken(), d1, d2);
                    semantico.push(tipo);
                }
                else{
                    int d2 = semantico.pop(); 
                    int d1 = semantico.pop();

                    if(d1 != d2){
                        System.out.println("Error en la asignacion: ");
                        ImprimirError(26);
                        fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                        fr.getLabelSem().setText("----X----");;
                        fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                        fr.getLabelSin().setText("----?----");
                        throw new TerminacionMetodoException(""); 
                    }
                }
            }
        }
        else{
            if(peek != null) { 
                AnalisisValor(peek.getIzq(),1);
                AnalisisValor(peek.getDer(),1);
                if(peek.n.getToken() == 100 || peek.n.getToken() == 101 || peek.n.getToken() == 102 || peek.n.getToken() == 122 || 
                peek.n.getToken() == 210 ||  peek.n.getToken() == 211 ){
                    //token
                    if(peek.n.getToken() == 100)
                    {
                        int t = buscarVariable(peek.n);
                        semantico.push(t); 
                    }
                    else if(peek.n.getToken() == 101){
                        int t = 209;
                        semantico.push(t); 
                    }
                    else if(peek.n.getToken() == 102){
                        int t = 208;
                        semantico.push(t); 
                    }
                    else if(peek.n.getToken() == 122){
                        int t = 212;
                        semantico.push(t); 
                    }
                    else
                    {
                        int t = 213;
                        semantico.push(t); 
                    }
                }
                else{

                    int d2 = semantico.pop();
                    int d1 = semantico.pop();
                    int t = TablaTipos(peek.n.getToken(),d1,d2);
                    semantico.push(t);
                    if(213 != t)
                    {
                        System.out.println("Error en la asignacion: ");
                        ImprimirError(27);
                        fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                        fr.getLabelSem().setText("----X----");;
                        fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                        fr.getLabelSin().setText("----?----");
                        throw new TerminacionMetodoException(""); 
                    }
                }
                
            }
        }
        
    }


    //Nexos de ayuda
    private int TablaTipos(int op, int d1, int d2) {
        Map<Integer, Map<Integer,Integer>> Sum = new HashMap<>();
        Sum.put(209,new HashMap<>());
        Sum.get(209).put(208, 208);
        Sum.get(209).put(209, 209);
        Sum.get(209).put(212, 0);
        Sum.get(209).put(213, 0);

        Sum.put(208,new HashMap<>());
        Sum.get(208).put(208, 208);
        Sum.get(208).put(209, 208);
        Sum.get(208).put(212, 0);
        Sum.get(208).put(213, 0);

        Sum.put(212,new HashMap<>());
        Sum.get(212).put(208, 0);
        Sum.get(212).put(209, 0);
        Sum.get(212).put(212, 212);
        Sum.get(212).put(213, 0);

        Sum.put(213,new HashMap<>());
        Sum.get(213).put(208, 0);
        Sum.get(213).put(209, 0);
        Sum.get(213).put(212, 0);
        Sum.get(213).put(213, 0);

        Map<Integer,Map<Integer,Integer>> res = new HashMap<>();

        res.put(209,new HashMap<>());
        res.get(209).put(208, 208);
        res.get(209).put(209, 209);
        res.get(209).put(212, 0);
        res.get(209).put(213, 0);

        res.put(208,new HashMap<>());
        res.get(208).put(208, 208);
        res.get(208).put(209, 208);
        res.get(208).put(212, 0);
        res.get(208).put(213, 0);

        res.put(212,new HashMap<>());
        res.get(212).put(208, 0);
        res.get(212).put(209, 0);
        res.get(212).put(212, 0);
        res.get(212).put(213, 0);

        res.put(213,new HashMap<>());
        res.get(213).put(208, 0);
        res.get(213).put(209, 0);
        res.get(213).put(212, 0);
        res.get(213).put(213, 0);

        Map<Integer,Map<Integer,Integer>> mul = new HashMap<>();

        mul.put(209,new HashMap<>());
        mul.get(209).put(208, 208);
        mul.get(209).put(209, 209);
        mul.get(209).put(212, 0);
        mul.get(209).put(213, 0);

        mul.put(208,new HashMap<>());
        mul.get(208).put(208, 208);
        mul.get(208).put(209, 208);
        mul.get(208).put(212, 0);
        mul.get(208).put(213, 0);

        mul.put(212,new HashMap<>());
        mul.get(212).put(208, 0);
        mul.get(212).put(209, 0);
        mul.get(212).put(212, 0);
        mul.get(212).put(213, 0);

        mul.put(213,new HashMap<>());
        mul.get(213).put(208, 0);
        mul.get(213).put(209, 0);
        mul.get(213).put(212, 0);
        mul.get(213).put(213, 0);

        Map<Integer,Map<Integer,Integer>> div = new HashMap<>();

        div.put(209,new HashMap<>());
        div.get(209).put(208, 208);
        div.get(209).put(209, 208);
        div.get(209).put(212, 0);
        div.get(209).put(213, 0);

        div.put(208,new HashMap<>());
        div.get(208).put(208, 208);
        div.get(208).put(209, 208);
        div.get(208).put(212, 0);
        div.get(208).put(213, 0);

        div.put(212,new HashMap<>());
        div.get(212).put(208, 0);
        div.get(212).put(209, 0);
        div.get(212).put(212, 0);
        div.get(212).put(213, 0);

        div.put(213,new HashMap<>());
        div.get(213).put(208, 0);
        div.get(213).put(209, 0);
        div.get(213).put(212, 0);
        div.get(213).put(213, 0);

        Map<Integer, Map<Integer,Integer>> menor = new HashMap<>();

        menor.put(209,new HashMap<>());
        menor.get(209).put(208, 213);
        menor.get(209).put(209, 213);
        menor.get(209).put(212, 0);
        menor.get(209).put(213, 0);

        menor.put(208,new HashMap<>());
        menor.get(208).put(208, 213);
        menor.get(208).put(209, 213);
        menor.get(208).put(212, 0);
        menor.get(208).put(213, 0);

        menor.put(212,new HashMap<>());
        menor.get(212).put(208, 0);
        menor.get(212).put(209, 0);
        menor.get(212).put(212, 0);
        menor.get(212).put(213, 0);

        menor.put(213,new HashMap<>());
        menor.get(213).put(208, 0);
        menor.get(213).put(209, 0);
        menor.get(213).put(212, 0);
        menor.get(213).put(213, 0);

        Map<Integer, Map<Integer,Integer>> mayor= new HashMap<>();

        mayor.put(209, new HashMap<>());
        mayor.get(209).put(208, 213);
        mayor.get(209).put(209, 213);
        mayor.get(209).put(212, 0);
        mayor.get(209).put(213, 0);

        mayor.put(208, new HashMap<>());
        mayor.get(208).put(208, 213);
        mayor.get(208).put(209, 213);
        mayor.get(208).put(212, 0);
        mayor.get(208).put(213, 0);

        mayor.put(212, new HashMap<>());
        mayor.get(212).put(208, 0);
        mayor.get(212).put(209, 0);
        mayor.get(212).put(212, 0);
        mayor.get(212).put(213, 0);

        mayor.put(213, new HashMap<>());
        mayor.get(213).put(208, 0);
        mayor.get(213).put(209, 0);
        mayor.get(213).put(212, 0);
        mayor.get(213).put(213, 0);


        Map<Integer,Map<Integer,Integer>> menorI = new HashMap<>();

        menorI.put(209, new HashMap<>());
        menorI.get(209).put(208, 213);
        menorI.get(209).put(209, 213);
        menorI.get(209).put(212, 0);
        menorI.get(209).put(213, 0);

        menorI.put(208, new HashMap<>());
        menorI.get(208).put(208, 213);
        menorI.get(208).put(209, 213);
        menorI.get(208).put(212, 0);
        menorI.get(208).put(213, 0);

        menorI.put(212, new HashMap<>());
        menorI.get(212).put(208, 0);
        menorI.get(212).put(209, 0);
        menorI.get(212).put(212, 0);
        menorI.get(212).put(213, 0);

        menorI.put(213, new HashMap<>());
        menorI.get(213).put(208, 0);
        menorI.get(213).put(209, 0);
        menorI.get(213).put(212, 0);
        menorI.get(213).put(213, 0);


        Map<Integer,Map<Integer,Integer>> mayorI = new HashMap<>();

        mayorI.put(209, new HashMap<>());
        mayorI.get(209).put(208, 213);
        mayorI.get(209).put(209, 213);
        mayorI.get(209).put(212, 0);
        mayorI.get(209).put(213, 0);

        mayorI.put(208, new HashMap<>());
        mayorI.get(208).put(208, 213);
        mayorI.get(208).put(209, 213);
        mayorI.get(208).put(212, 0);
        mayorI.get(208).put(213, 0);

        mayorI.put(212, new HashMap<>());
        mayorI.get(212).put(208, 0);
        mayorI.get(212).put(209, 0);
        mayorI.get(212).put(212, 0);
        mayorI.get(212).put(213, 0);

        mayorI.put(213, new HashMap<>());
        mayorI.get(213).put(208, 0);
        mayorI.get(213).put(209, 0);
        mayorI.get(213).put(212, 0);
        mayorI.get(213).put(213, 0);

        Map<Integer,Map<Integer,Integer>> diff = new HashMap<>();

        diff.put(209, new HashMap<>());
        diff.get(209).put(208, 213);
        diff.get(209).put(209, 213);
        diff.get(209).put(212, 0);
        diff.get(209).put(213, 0);

        diff.put(208, new HashMap<>());
        diff.get(208).put(208, 213);
        diff.get(208).put(209, 213);
        diff.get(208).put(212, 0);
        diff.get(208).put(213, 0);

        diff.put(212, new HashMap<>());
        diff.get(212).put(208, 0);
        diff.get(212).put(209, 0);
        diff.get(212).put(212, 213);
        diff.get(212).put(213, 0);

        diff.put(213, new HashMap<>());
        diff.get(213).put(208, 0);
        diff.get(213).put(209, 0);
        diff.get(213).put(212, 0);
        diff.get(213).put(213, 0);

        Map<Integer, Map<Integer, Integer>> same = new HashMap<>();

        same.put(209, new HashMap<>());
        same.get(209).put(208, 213);
        same.get(209).put(209, 213);
        same.get(209).put(212, 0);
        same.get(209).put(213, 0);

        same.put(208, new HashMap<>());
        same.get(208).put(208, 213);
        same.get(208).put(209, 213);
        same.get(208).put(212, 0);
        same.get(208).put(213, 0);

        same.put(212, new HashMap<>());
        same.get(212).put(208, 0);
        same.get(212).put(209, 0);
        same.get(212).put(212, 213);
        same.get(212).put(213, 0);

        same.put(213, new HashMap<>());
        same.get(213).put(208, 0);
        same.get(213).put(209, 0);
        same.get(213).put(212, 0);
        same.get(213).put(213, 0);

        switch(op){
            case 103: // +
                if(Sum.containsKey(d1) && Sum.get(d1).containsKey(d2))
                {
                    return Sum.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 104: // -
                if(res.containsKey(d1) && res.get(d1).containsKey(d2))
                {
                    return Sum.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 105: // * 
                if(mul.containsKey(d1) && mul.get(d1).containsKey(d2))
                {
                    return mul.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 106: // /
                if(div.containsKey(d1) && div.get(d1).containsKey(d2))
                {
                    return div.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }  
            case 108: // <
                if(menor.containsKey(d1) && menor.get(d1).containsKey(d2))
                {
                    return menor.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 109: // >
                if(mayor.containsKey(d1) && mayor.get(d1).containsKey(d2))
                {
                    return mayor.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 110: // <=
                if(menorI.containsKey(d1) && menorI.get(d1).containsKey(d2))
                {
                    return menorI.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 111: // >=
                if(mayorI.containsKey(d1) && mayorI.get(d1).containsKey(d2))
                {
                    return mayorI.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 112: //==
                if(same.containsKey(d1) && same.get(d1).containsKey(d2))
                {
                    return same.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 113: // !=
                if(diff.containsKey(d1) && diff.get(d1).containsKey(d2))
                {
                    return diff.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            default:
                return 1;
        }
    }

    private int buscarVariable(Nodo p) {
        int result = token;
        for(TablaSimbolos  b : a)
        {
            if(p.getLexema().equals(b.getId()))
            {
               result = b.getToken();
            }
                
        }
        return result;
    }

    private void  buscarVariable(Nodo p, Nodo p2) {
        for (TablaSimbolos b : a) {
            if (p.getLexema().equals(b.getId())) {
                // Actualiza el valor de la variable en la tabla de símbolos
                b.setValor(p2.getLexema());
            }
        }
    }

    protected int prioridad(Nodo n){ 
        
        switch(n.getToken()){
            case 105: // * 
            case 106: // /
            case 108:
            case 109: 
            case 110: 
            case 111:
            case 112: 
            case 113: 
                return 2; 
            case 103: // +
            case 104: // -
            case 114:
            case 115: 
                return 1; 
            default: 
                return 0; 
        }
    }
    
    public void ErrorSintatico(){
        fr.getLabelSin().setForeground(new java.awt.Color(247, 36, 36));
        fr.getLabelSin().setText("----X----");;
        fr.getLabelSem().setForeground(new java.awt.Color(53, 97, 240));
        fr.getLabelSem().setText("----?----");
    }

}

class TerminacionMetodoException extends RuntimeException {
    public TerminacionMetodoException(String mensaje) {
        super(mensaje);
    }
}

