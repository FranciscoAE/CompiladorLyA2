import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;



public class Intermedio {
    private ArrayList<Hoja>  tree; 
    private ArrayList<TablaSimbolos> var;
    private Nodo p;
    ArrayList<Cuadruplo> cuadruploData = new ArrayList<Cuadruplo>();
    ArrayList<Cuadruplo> cuadruploCode = new ArrayList<Cuadruplo>();
    Stack<String> valores = new Stack<String>();
    Stack<String> etiquetas = new Stack<String>();

    int Temporales, EtiquetasT;

    //variables locales.
    String Data = "Segmento Data \n";
    String Code = "Segmento Code \n";

    String DataMaquina = ".model small\r\n" + //
                         ".stack \n\n";
    String CodeMaquina="";

    public Intermedio(Nodo P,ArrayList<Hoja> a, ArrayList<TablaSimbolos> b) {
        this.p = P;
        this.tree= a;
        this.var = b;
    }

    public Intermedio() {
        
    }

    public Nodo getNodo()
    {
        return p;
    }

    public void setNodo(Nodo a){
        this.p = a;
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


    //metodos pa hacer el codigo intermedio.
    public void Avanzar()
    {
        p = p.getUnion();
        while(p.getToken() != 204 && p.getToken() != 206 && p.getToken() != 201 && p.getToken() != 100 && p.getToken() != 202 && 
        p.getToken() != 120 && p != null )
        {
            if(p.getToken() == 207)
            {
                while(p.getToken() != 125)
                {
                    p = p.getUnion();
                }
            }

            if(p.getToken() == 117)
            {
               while(p.getToken() != 118)
                {
                    p = p.getUnion();
                } 
            }
            p = p.getUnion();
        }
    }

    public void leer(){
        while(p != null)
        {
            switch(p.getToken())
            {
                case 201:
                case 202:
                if(p.getToken() == 201)
                {
                    CuadArbol(tree.get(0));
                    tree.remove(0);
                    Cuadruplo cuad = new Cuadruplo("if", cuadruploCode.get(cuadruploCode.size() - 1).resultado,null , CreateEtiqueta());
                    etiquetas.push(cuad.resultado);
                    cuadruploCode.add(cuad);
                    while(p.getToken() != 120)
                    {
                        Avanzar();
                        leer();
                        
                    }
                    if(p.getToken() == 120)
                    {
                        if(p.getUnion().getToken() == 202)
                        {
                            cuad = new Cuadruplo("goto", null,null, CreateEtiqueta());
                            cuadruploCode.add(cuad);
                            String label = etiquetas.pop();
                            etiquetas.push(cuad.resultado);
                            cuad = new Cuadruplo("Label", null, null, label);
                            cuadruploCode.add(cuad);
                        }
                        else{
                            cuad = new Cuadruplo("Label", null, null, etiquetas.pop());
                            cuadruploCode.add(cuad);
                            
                        }
            
                    }
                }
                else{
                    while(p.getToken() != 120)
                    {
                        Avanzar();
                        leer();
                        
                    }
                    if(p.getToken() == 120)
                    {
                        Cuadruplo cuad = new Cuadruplo("Label", null, null, etiquetas.pop());
                        cuadruploCode.add(cuad);
                        return;
                        
                    }
                }  
                    break;
                case 204:
                    Cuadruplo cuad2 = new Cuadruplo("Label", null, null, CreateEtiqueta());
                    cuadruploCode.add(cuad2);
                    etiquetas.push(cuad2.resultado);
                    CuadArbol(tree.get(0));
                    tree.remove(0);
                    cuad2 = new Cuadruplo("while", cuadruploCode.get(cuadruploCode.size() - 1).resultado,null , CreateEtiqueta());
                    etiquetas.push(cuad2.resultado);
                    cuadruploCode.add(cuad2);
                    while(p.getToken() != 120)
                    {
                       Avanzar();
                       leer();
                    }
                    if(p.getToken() == 120)
                    {
                        String label = etiquetas.pop();
                        cuad2 = new Cuadruplo("goto", null, null, etiquetas.pop());
                        cuadruploCode.add(cuad2);
                        cuad2 = new Cuadruplo("Label", null, null, label);
                        cuadruploCode.add(cuad2);
                        return;
                    }
                    break;
                case 206:
                    Cuadruplo cuad3 = new Cuadruplo("Print", String.valueOf(p.getUnion().getUnion().getToken()), null, p.getUnion().getUnion().getLexema());
                    cuadruploCode.add(cuad3);
                    Avanzar();
                    if(p.getToken() == 120)
                    {
                        return;
                    }
                    break;
                case 100:
                    CuadArbol(tree.get(0));
                    tree.remove(0);
                    Avanzar();
                    if(p.getToken() == 120)
                    {
                        return;
                    }
                    break;    
                default:
                if(p.getUnion() != null){
                    Avanzar();
                }
                else{
                    p = p.getUnion();
                }
                    break;
            }
        }
    }

    public void CuadArbol(Hoja a){
        if(a != null)
        {
            CuadArbol(a.izq);
            CuadArbol(a.der);

            if(a.getNodo().getToken() == 100 || a.getNodo().getToken() == 101 || a.getNodo().getToken() == 102 ||
            a.getNodo().getToken() == 122 || a.getNodo().getToken() == 210 ||  a.getNodo().getToken() == 211){
            
                valores.push(a.getNodo().getLexema());
            }
            else if(a.getNodo().getToken() == 108 || a.getNodo().getToken() == 109 || a.getNodo().getToken() == 110 ||
            a.getNodo().getToken() == 111 || a.getNodo().getToken() == 112 ||  a.getNodo().getToken() == 113 ||
            a.getNodo().getToken() == 103 || a.getNodo().getToken() == 104 || a.getNodo().getToken() == 105 || a.getNodo().getToken() == 106)
            {
                String temp = CreateVarT();
                String a2 = valores.pop();
                String a1 = valores.pop();
                Cuadruplo cuad = new Cuadruplo(a.getNodo().getLexema(), a1, a2, temp);
                cuadruploCode.add(cuad);
                valores.push(cuad.resultado);

            }
            else{
                String a1 = valores.pop();
                String resultado = valores.pop();
                Cuadruplo cuad = new Cuadruplo(a.getNodo().getLexema(),a1, null,resultado);
                cuadruploCode.add(cuad);
                
            }
        }
    }

    public void Declare()
    {
        for (TablaSimbolos b : var) {
            Cuadruplo cuad = new Cuadruplo(" DECLARE",b.getDato(),String.valueOf(b.getValor()) ,b.getId());
            cuadruploData.add(cuad);
        }

        for(Cuadruplo b : cuadruploData)
        {
            if(b.op2 == "null")
            {
                Data = Data + b.operador + " " + b.op1 + " " + b.resultado + " ? " + "\n";
            }
            else
            {
                Data = Data + b.operador + " " + b.op1 + " " + b.resultado + " " + b.op2 +" \n";
            }
        }
    }


    public void SegmentoCodigo()
    {
        for(Cuadruplo b: cuadruploCode)
        {
            switch (b.operador) {
                case "+":
                    Data = Data + " DECLARE" +  " dd " + b.resultado + " ? " + "\n";
                    Code = Code + " SUMA " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " ASIGNAR " + b.resultado + " , SUMA\n";
                    break;
                case "-":
                    Data = Data + " DECLARE " +  " dd " + b.resultado + " ? " + "\n";
                    Code = Code + " SUB " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " ASIGNAR " + b.resultado + " , SUB\n";
                    break;
                case "*":
                    Data = Data + " DECLARE " +  " dd " + b.resultado + " ? " + "\n";
                    Code = Code + " MUL " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " ASIGNAR " + b.resultado + " , MUL\n";
                    break;
                case "/":
                    Data = Data + " DECLARE " +  " dd " + b.resultado + " ? " + "\n";
                    Code = Code + " DIV " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " ASIGNAR " + b.resultado + " , DIV\n";
                    break;
                case "=":
                if(!b.op1.matches(".*\"[^\"]+\".*")){
                    Code = Code + " ASIGNAR " + b.resultado + " , " + b.op1 +"\n";
                    
                }
                    break;
                case "<":
                    Code = Code + " COMPARAR " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " JG " + "\n";
                    break;
                case ">":
                    Code = Code + " COMPARAR " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " JL " + "\n";
                    break;
                case "<=":
                    Code = Code + " COMPARAR " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " JGE " + "\n";
                    break;
                case ">=":
                    Code = Code + " COMPARAR " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " JLE " + "\n";
                    break;
                case "==":
                    Code = Code + " COMPARAR " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " JNE " + "\n";
                    break;
                case "!=":
                    Code = Code + " COMPARAR " + b.op1 + " , " + b.op2 + "\n";
                    Code = Code + " JE " + "\n";
                    break;
                case "if":
                    Code = Code + " " + b.operador +  " Saltar " + " Falso " + b.resultado + "\n";
                    break;
                case "while":
                    Code = Code + " " +b.operador + " Saltar " + " Falso " + b.resultado + "\n";
                    break;
                case "Print":
                    Code = Code + " " + b.operador + " " + b.resultado.trim();
                    break;
                case "goto":
                    Code = Code + " " + b.operador + " " + b.resultado + "\n";
                    break;
                case "Label":
                    Code = Code + " Label " + b.resultado + ":";
                    Code = Code + "\n";
                    break;
                default:
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

    public String CreateEtiqueta()
    {
        String temp = "L" + EtiquetasT;
        EtiquetasT++;
        return temp;
    }

    public void CrearIntermedio()
    {
        leer();
        Declare();
        for(Cuadruplo b: cuadruploData)
        {
            System.out.println(b.operador + " " + b.op1 + " " + b.op2 + " " + b.resultado);
        }
        for(Cuadruplo b: cuadruploCode)
        {
            System.out.println(b.operador + " " + b.op1 + " " + b.op2 + " " + b.resultado);
        }
        System.out.println(" ");
        SegmentoCodigo();
        System.out.println("\n");
        System.out.println(Data + "\n");
        System.out.println(Code + "\n");
        GenerarASM();
    }

    private void GenerarASM() {
        String result = Data + "\n\n" + Code;
        String rutaArchivo = "expresion.asm";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(rutaArchivo)))) {
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GenerarMaquina(){
        String[] palabras = Data.split(" ");

        for (int i = 0; i < palabras.length; i++) {
            switch (palabras[i]) {
                case "Data":
                    DataMaquina = DataMaquina + ".data" + "\n";

                    break;
                case "DECLARE":
                    i++;
                    if(palabras[i].equals("string"))
                    {
                        i++;
                        DataMaquina = DataMaquina + "\t" + palabras[i] + " db ";
                        i++;
                        DataMaquina = DataMaquina +  palabras[i] + ",10,13,'$' \n";
                    }
                    else{
                        i++;
                        DataMaquina = DataMaquina + "\t" + palabras[i] + " db ";
                        i++;
                        DataMaquina = DataMaquina +  palabras[i] + "\n";
                        
                    }
                    break;
                default:
                    break;
            }
        }
        
        palabras = Code.split(" ");

        for (int i = 0; i < palabras.length; i++) {
            switch (palabras[i]) {
                case "Code":
                    CodeMaquina = CodeMaquina + ".code" + "\n\n";
                    CodeMaquina = CodeMaquina + "\t" + "main PROC" + "\n\n";
                    CodeMaquina = CodeMaquina + "\t\tMOV ax, @data" + "\n";
                    CodeMaquina = CodeMaquina + "\t\tMOV ds, ax" + "\n\n";
                    break;
                case "ASIGNAR":
                    CodeMaquina = CodeMaquina + "\t\tMOV ";
                    i++;
                    for(Cuadruplo a: cuadruploData){
                        if(palabras[i].equals(a.resultado))
                        {

                        }
                    }
                    CodeMaquina = CodeMaquina + palabras[i];
                    i++;
                    CodeMaquina = CodeMaquina + palabras[i] + " ";
                    i++;
                    CodeMaquina = CodeMaquina + palabras[i] + " ";
                    break;
                case "SUMA":
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV al, " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tADD al, " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV " + palabras[i] + ", al" + "\n";
                    i++;
                    i++;
                    break;
                case "DIV":
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV al, " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tDIV " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV " + palabras[i] + ", al" + "\n";
                    i++;
                    i++;
                    break;
                case "SUB":
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV al, " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tSUB al, " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV " + palabras[i] + ", al" + "\n";
                    i++;
                    i++;
                    break;
                case "MUL":
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV al, " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMUL " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV " + palabras[i] + ", al" + "\n";
                    i++;
                    i++;
                    break;
                case "COMPARAR":
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tMOV al, " + palabras[i] +  "\n";
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tCMP al, " + palabras[i] +  "\n";
                    i++;
                    CodeMaquina = CodeMaquina + "\t\t" + palabras[i] +  " ";
                    i++;
                    i++;
                    i++;
                    i++;
                    i++;
                    i++;
                    CodeMaquina = CodeMaquina + palabras[i] +  "\n";
                    break;
                case "Print":
                    i++;
                    palabras[i] = palabras[i].replace("\n","");
                    for(Cuadruplo a: cuadruploData){
                        if(palabras[i].equals(a.resultado))
                        {
                            switch(a.op1)
                            {
                                case "int":
                                    CodeMaquina = CodeMaquina + "\t\tMOV ax, 0000h" +  "\n";
                                    CodeMaquina = CodeMaquina + "\t\tMOV al, " + palabras[i] +  "\n";
                                    CodeMaquina = CodeMaquina + "\t\tAAA " +  "\n";
                                    CodeMaquina = CodeMaquina + "\t\tMOV cx, ax" + "\n";
                                    CodeMaquina = CodeMaquina + "\t\tMOV cx, 3030h" + "\n";
                                    CodeMaquina = CodeMaquina + "\t\tMOV ah, 09h" + "\n";
                                    CodeMaquina = CodeMaquina + "\t\tMOV ah, 02h" + "\n";
                                    CodeMaquina = CodeMaquina + "\t\tMOV dl, ch" + "\n";
                                    CodeMaquina = CodeMaquina + "\t\tint 21h" + "\n";
                                    CodeMaquina = CodeMaquina + "\t\tMOV ah, 02h" + "\n";
                                    CodeMaquina = CodeMaquina + "\t\tMOV dl, cl" + "\n";
                                    CodeMaquina = CodeMaquina + "\t\tint 21h" + "\n";
                                break;
                                case "string":
                                    CodeMaquina = CodeMaquina + "\t\tmov dx, offset " + palabras[i] +  "\n";
                                    CodeMaquina = CodeMaquina + "\t\tmov ah, 9 " +   "\n";
                                    CodeMaquina = CodeMaquina + "\t\tint 21h" + "\n";
                                break;
                                default:
                                    break;
                            }
                        }
                    }
                    break;
                case "Label":
                    i++;
                    CodeMaquina = CodeMaquina + "\t" + palabras[i] + "\n";
                    break;
                case "goto":
                    i++;
                    CodeMaquina = CodeMaquina + "\t\tJMP " + palabras[i] + "\n";
                    break;
                default:
                    break;
            }
        }
        CodeMaquina = CodeMaquina + "\t\t.exit" + "\n\n";
        CodeMaquina = CodeMaquina + "\tmain ENDP" + "\n\n";
        CodeMaquina = CodeMaquina + "end main" + "\n\n";
    }

    public void CrearMaquina()
    {
        String result = DataMaquina + "\n\n" + CodeMaquina;
        String rutaArchivo = "Ejemplo.asm";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(rutaArchivo)))) {
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public class Cuadruplo{
        String operador;
        String op1;
        String op2;
        String resultado;

        public Cuadruplo(String operacion, String operando1, String operando2, String resultado) {
            this.operador = operacion;
            this.op1 = operando1;
            this.op2 = operando2;
            this.resultado = resultado;
        }


    }

}


