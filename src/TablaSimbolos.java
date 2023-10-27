public class TablaSimbolos {
    private int Token; //token del tipo de dato
    private String TipoDato; //nombre del tipo de dato: int,string,bool,float
    private String Id;      //Id de la variable
    private String Renglon; //Renglon donde se encuentra la variable

    //No importan mucho
    private Object valor;

        
    public TablaSimbolos(String tipoDato,int token, String id, String renglon)
    {
        this.TipoDato = tipoDato;
        this.Token = token;
        this.Id= id;
        this.Renglon = renglon;
    }
    
    public TablaSimbolos()
    {
       
    }
    
     //Encapsulamiento
    protected String getDato()
    {
        return TipoDato;
    }
    
    protected void setDato(String a)
    {
        this.TipoDato  = a;
    }

    protected int getToken()
    {
        return Token;
    }
    
    protected void setToken(int a)
    {
        this.Token  = a;
    }
    
    protected String getId()
    {
        return Id;
    }
   
    protected void setId (String a)
    {
        this.Id = a;
    }
    
    protected String getRenglon()
    {
        return Renglon;
    }
   
    protected void setRenglon (String a)
    {
        this.Renglon = a;
    }
    
    public Object getValor() {
        return valor;
    }

    public void setValor(Object a) {
        this.valor = a;
    }

    //metodos


    class vString extends TablaSimbolos {
        public vString() {
            // Constructor sin argumentos
        }

        @Override
        public String getValor() {
            return (String) super.getValor();
        }
    }

    class vInt extends TablaSimbolos {
        public vInt() {
            // Constructor sin argumentos
        }

        @Override
        public Object getValor() {
            return (int) super.getValor();
        }
    }

    class vFloat extends TablaSimbolos {
        public vFloat() {
            // Constructor sin argumentos
        }

        @Override
        public Object getValor() {
            return (float) super.getValor();
        }
    }

    class vBool extends TablaSimbolos {
        public vBool() {
            // Constructor sin argumentos
        }

        @Override
        public Object getValor() {
            return (boolean) super.getValor();
        }
    }
}
