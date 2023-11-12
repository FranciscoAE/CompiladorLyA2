public class Hoja {
    //Atributos
    protected Nodo n;
    protected Hoja izq, der;

    //Constructores
    public Hoja(Nodo N) {
        this.n = N;
    }

    //Encapsulamiento 
    public Hoja getIzq()
    {
        return izq;
    }

    public void setIzq(Hoja a){
        this.izq = a;
    }

    public Hoja getDer(){
        return der;
    }

    public void setDer(Hoja a){
        this.der = a;
    }

    public Nodo getNodo(){
        return n;
    }
}
