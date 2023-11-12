import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Intermedio {
    ArrayList<Hoja>  tree = new ArrayList<Hoja>();
    ArrayList<TablaSimbolos> var = new ArrayList<TablaSimbolos>();
    ArrayList<Cuadruplo> cuadruplo = new ArrayList<Cuadruplo>();
    Stack<String> valores = new Stack<String>();
    
    int Temporales;

    //variables locales.
    String Data;
    String Code = "Segmento Code\n";

    public Intermedio(ArrayList<Hoja> a, ArrayList<TablaSimbolos> b) {
        this.tree= a;
        this.var = b;
    }

    public Intermedio() {
        
    }

    public ArrayList<Hoja> getTree()
    {
        return tree;
    }

    public void setTree(ArrayList<Hoja> a){
        this.tree = a;
    }
    
    public ArrayList<TablaSimbolos> getvar()
    {
        return var;
    }

    public void setvar(ArrayList<TablaSimbolos> a){
        this.var = a;
    }


    //Declaración de variables.
    //Metodos
    public void SegmentData()
    {
        Data = "Segmento Data \n";
        for (TablaSimbolos b : var) {
            Data = Data + "Declarar " + b.getDato() + " " + b.getId() + "\n";
        }
    }

    public void SegmentCode()
    {
        for(Cuadruplo b: cuadruplo)
        {
            switch (b.operador) {
                case 103:
                    Data = Data + "Declarar " +  "int " + b.resultado + "\n";
                    Code = Code + "SUMA " + b.op1 + "," + b.op2 + "\n";
                    Code = Code + "ASIGNAR " + b.resultado + ",SUMA\n";
                    break;

                case 104:
                    Data = Data + "Declarar " +  "int " + b.resultado + "\n";
                    Code = Code + "SUB " + b.op1 + "," + b.op2 + "\n";
                    Code = Code + "ASIGNAR " + b.resultado + ",SUB\n";
                    break;

                case 105:
                    Data = Data + "Declarar " +  "int " + b.resultado + "\n";
                    Code = Code + "MUL " + b.op1 + "," + b.op2 + "\n";
                    Code = Code + "ASIGNAR " + b.resultado + ",MUL\n";
                    break;

                case 106:
                    Data = Data + "Declarar " +  "float" + b.resultado + "\n";
                    Code = Code + "DIV " + b.op1 + "," + b.op2 + "\n";
                    Code = Code + "ASIGNAR " + b.resultado + ",DIV\n";
                    break;
                default:
                    Code = Code + "ASIGNAR " + b.resultado + "," + b.op1 +"\n";
                    break;
            }
        }
    }

    public String CreateVarT()
    {
        String temp = "t" + Temporales;
        Temporales++;
        return temp;
    }

    public void CrearIntermedio()
    {
        SegmentData();
        for (Hoja b : tree) {
            GenerarCuadruplos(b);
        }
        SegmentCode();
        System.out.println("\n");
        System.out.println(Data + "\n");
        System.out.println(Code + "\n");
    }

    public void GenerarCuadruplos(Hoja a)
    {
        if(a != null)
        {
            GenerarCuadruplos(a.izq);
            GenerarCuadruplos(a.der);

            if(a.getNodo().getToken() == 100 || a.getNodo().getToken() == 101 || a.getNodo().getToken() == 102 ||
            a.getNodo().getToken() == 122 || a.getNodo().getToken() == 210 ||  a.getNodo().getToken() == 211){
            
                valores.push(a.getNodo().getLexema());
            }
            else if(a.getNodo().getToken() == 103 || a.getNodo().getToken() == 104 || a.getNodo().getToken() == 105 ||
                    a.getNodo().getToken() == 106){
                String temp = CreateVarT();
                String a2 = valores.pop();
                String a1 = valores.pop();
                Cuadruplo cuad = new Cuadruplo(a.getNodo().getToken(), a1, a2, temp);
                cuadruplo.add(cuad);
                valores.add(temp);   
            }
            else{
                String a1 = valores.pop();
                Cuadruplo cuad = new Cuadruplo(a.getNodo().getToken(), a1, null, a.getIzq().getNodo().getLexema());
                cuadruplo.add(cuad);
            }
        }
        

    }

    public class Cuadruplo{
        int operador;
        String op1;
        String op2;
        String resultado;

        public Cuadruplo(int operacion, String operando1, String operando2, String resultado) {
            this.operador = operacion;
            this.op1 = operando1;
            this.op2 = operando2;
            this.resultado = resultado;
        }


    }

}


