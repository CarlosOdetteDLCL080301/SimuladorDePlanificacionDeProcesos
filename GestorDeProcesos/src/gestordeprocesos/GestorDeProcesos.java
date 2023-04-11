package gestordeprocesos;

public class GestorDeProcesos {
    

    public static void main(String[] args) {
    FIFO cola = new FIFO(1024);
    cola.agregar(1,"hola",200,1,1,1,1);
    cola.imprimir(); // imprime "15 10 5"
    System.out.println("**************\n");
    cola.agregar(2,"hola",300,1,1,1,1);
    cola.imprimir(); // imprime "15 10 5"
    System.out.println("**************\n");
    cola.agregar(3,"hola",150,1,1,1,1);
    cola.imprimir(); // imprime "15 10 5"    
    System.out.println("**************\n");
    
    cola.agregar(4,"hola",500,1,1,1,1);
    cola.imprimir(); // imprime "15 10 5"    
    System.out.println("**************\n");
    
    cola.agregar(5,"hola",100,1,1,1,1);
    cola.imprimir(); // imprime "15 10 5"    
    System.out.println("**************\n");
    
    cola.agregar(6,"hola",350,1,1,1,1);
    cola.imprimir(); // imprime "15 10 5"    
    System.out.println("**************\n");
    /*
    cola.eliminar(10);

    cola.imprimir(); // imprime "15 10 5"
    
    System.out.println("------------------\n");
    cola.eliminar(5);

    cola.imprimir(); // imprime "15 10 5"
    System.out.println("------------------\n");
    cola.eliminar(15);

    cola.imprimir(); // imprime "15 10 5"
    System.out.println("------------------\n");
    */
    }
}
