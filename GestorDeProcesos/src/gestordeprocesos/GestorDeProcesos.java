package gestordeprocesos;
import java.util.Scanner;
   
public class GestorDeProcesos {
    
    boolean agregarAColaRespectiva(FIFO cola_listoEjecucion, FIFO cola_procesoListo,boolean listoEjecucionDisponible, int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada){
        if(cola_listoEjecucion.almacenamientoDisponible - tamanioProceso >= 0 && listoEjecucionDisponible){
            cola_listoEjecucion.agregar(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada);
            return listoEjecucionDisponible;
        }else{
            cola_procesoListo.agregar(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada);
            return listoEjecucionDisponible = false;
        }
    }
    
    void mandarAFormar(FIFO listo, FIFO ejecucion){
        Proceso temporal;
        temporal = ejecucion.eliminar();
        listo.agregar(temporal.idProceso, temporal.nombreProceso, temporal.tamanioProceso, temporal.tiempoEjecución, temporal.prioridadProceso, temporal.tiempoOperacionES, temporal.tiempoLlegada);
    }
    
    int contarElementosDeCola(FIFO cola){
        int contador = 0;
        Proceso elemento;
        FIFO auxiliar = new FIFO(cola.almacenamientoDisponible);
        while(!cola.estaVacia()){
            elemento = cola.eliminar();
            auxiliar.agregar(elemento.idProceso, elemento.nombreProceso, elemento.tamanioProceso, elemento.tiempoEjecución, elemento.prioridadProceso, elemento.tiempoOperacionES, elemento.tiempoLlegada);
            contador++;
        }
        while(!auxiliar.estaVacia()){
            elemento = auxiliar.eliminar();
            cola.agregar(elemento.idProceso, elemento.nombreProceso, elemento.tamanioProceso, elemento.tiempoEjecución, elemento.prioridadProceso, elemento.tiempoOperacionES, elemento.tiempoLlegada);
        }
        return contador;
    }
    
    void ordenarCola(FIFO cola){
        int n = contarElementosDeCola(cola),i=0;
        Proceso[] array = new Proceso[n];
        Proceso elemento;
        //Vaciar la cola y almacenar sus elementos en el arreglo
        while(!cola.estaVacia()){
            elemento = cola.eliminar();
            array[i] = elemento;
            i++;
        }
        
        //Aplicar el algoritmo de ordenamiento de seleccion al arreglo
        for(int j=0;j<n-1;j++){
            int minimo = j;
            for(int k = j + 1; k < n; k++){
                if(array[k].tiempoLlegada < array[minimo].tiempoLlegada)
                    minimo = k;
                
            }
            Proceso temp = array[j];
            array[j] = array[minimo];
            array[minimo] = temp;

            
        }
        
        //Llenar la cola con los elementos ordenados
        for(int j = 0;j<n;j++)
            cola.agregar(array[j].idProceso, array[j].nombreProceso, array[j].tamanioProceso, array[j].tiempoEjecución, array[j].prioridadProceso, array[j].tiempoOperacionES, array[j].tiempoLlegada);
        
        //cola.almacenamientoDisponible = alm;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Crear un objeto de la clase Scanner
        int almacenamiento = 20;
        FIFO cola_listoEjecucion = new FIFO(almacenamiento);
        FIFO cola_procesoListo = new FIFO(120);
        FIFO recepcion = new FIFO(almacenamiento);
        int procesoIngresar;
        String pregCont, teclado_nombreProceso;
        int teclado_idProceso, teclado_tamanioProceso, teclado_tiempoEjecución, teclado_prioridadProceso, teclado_tiempoOperacionES, teclado_tiempoLlegada;
        boolean listoEjecucionDisponible = true, continuamos;
        GestorDeProcesos gestor = new GestorDeProcesos();
        //Aqui habrá una función para agregar los valores a nuestra cola
       recepcion.agregar(10,"Proceso 10", 10,10,10,10,10);
       recepcion.agregar(9,"Proceso 9", 9,9,9,9,9);
       recepcion.agregar(8,"Proceso 8", 8,8,8,8,8);
       recepcion.agregar(7,"Proceso 7", 7,7,7,7,7);
       recepcion.imprimir();
       gestor.ordenarCola(recepcion);
       recepcion.imprimir();
       /*
        System.out.println("Ingresa cuantos valores ingresarás");
        procesoIngresar = scanner.nextInt();
        do{
            System.out.println("Ingresa el ID del proceso");
            teclado_idProceso = scanner.nextInt();
            System.out.println("Ingresa el nombre del proceso");
            teclado_nombreProceso = scanner.nextLine();
            System.out.println("Ingresa el tamaño del proceso");
            teclado_tamanioProceso = scanner.nextInt();
            System.out.println("Ingresa el tiempo de ejecución");
            teclado_tiempoEjecución = scanner.nextInt();
            System.out.println("Ingresa la prioridad del proceso");
            teclado_prioridadProceso = scanner.nextInt();
            System.out.println("Ingresa el tiempo de Operacion E/S del proceso");
            teclado_tiempoOperacionES = scanner.nextInt();
            System.out.println("Ingresa el tiempo de LLegada");
            teclado_tiempoLlegada = scanner.nextInt();
            System.out.println("Quieres ingresar más valores? [Y/n]");
            pregCont = scanner.nextLine();
            continuamos = (pregCont.equalsIgnoreCase("Y")) ? true : false;
        }while(continuamos!=false);}
        */
       
       /*
       Segun el tiempo[ms] que transcurra, este agregara la solicitud de
       de valores, conforme su tiempo de llegada previamente definido
       */
       
    }
    
}
