public class Nodo {
     //Atributos
     private String Lexema;
     private int  Token;
     private int Renglon;
     private Nodo Union;
 
     
 //Constructor
    public Nodo(String lexema, int token, int renglon)
    {
        this.Lexema = lexema;
        this.Token = token;
        this.Renglon = renglon;
    }
 
 
 
 //Encapsulamiento
    protected String  getLexema()
    {
         return Lexema;
    }
 
    protected void setLexema(String a)
    {
         this.Lexema  = a;
    }
 
    protected int getToken()
    {
        return Token;
    }

    protected void setToken (int a)
    {
        this.Token = a;
    }
 
    protected int getRenglon()
    {
        return Renglon;
    }

    protected void setRenglon (int a)
    {
        this.Renglon = a;
    }
 
    protected Nodo getUnion()
    {
        return Union;
    }

    protected void setUnion (Nodo a)
    {
        this.Union = a;
    }
}