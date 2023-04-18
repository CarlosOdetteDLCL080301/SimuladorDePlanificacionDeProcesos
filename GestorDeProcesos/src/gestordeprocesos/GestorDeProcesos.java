package gestordeprocesos;
import java.util.Scanner;
   
public class GestorDeProcesos {
    
    boolean agregarAColaRespectiva(FIFO cola_listoEjecucion, FIFO cola_procesoListo,boolean listoEjecucionDisponible, int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada,int[] vecesDeAcceso){
        if(cola_listoEjecucion.almacenamientoDisponible - tamanioProceso >= 0 && listoEjecucionDisponible){
            cola_listoEjecucion.agregar(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada, vecesDeAcceso);
            return listoEjecucionDisponible;
        }else{
            cola_procesoListo.agregar(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada, vecesDeAcceso);
            return listoEjecucionDisponible = false;
        }
    }
    
    int contarElementosDeCola(FIFO cola){
        int contador = 0;
        Proceso elemento;
        FIFO auxiliar = new FIFO(cola.almacenamientoDisponible);
        while(!cola.estaVacia()){
            elemento = cola.eliminar();
            auxiliar.agregar(elemento.idProceso, elemento.nombreProceso, elemento.tamanioProceso, elemento.tiempoEjecución, elemento.prioridadProceso, elemento.tiempoOperacionES, elemento.tiempoLlegada, elemento.vecesDeAcceso);
            contador++;
        }
        while(!auxiliar.estaVacia()){
            elemento = auxiliar.eliminar();
            cola.agregar(elemento.idProceso, elemento.nombreProceso, elemento.tamanioProceso, elemento.tiempoEjecución, elemento.prioridadProceso, elemento.tiempoOperacionES, elemento.tiempoLlegada, elemento.vecesDeAcceso);
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
            cola.agregar(array[j].idProceso, array[j].nombreProceso, array[j].tamanioProceso, array[j].tiempoEjecución, array[j].prioridadProceso, array[j].tiempoOperacionES, array[j].tiempoLlegada,array[j].vecesDeAcceso);
        
        //cola.almacenamientoDisponible = alm;
    }
    
    void desplazarListas(FIFO destino, FIFO emisor){
        Proceso desplazar;
        if(!emisor.estaVacia() && (destino.almacenamientoDisponible - emisor.frente.tamanioProceso >=0 )){
            desplazar = emisor.eliminar();
            destino.agregar(desplazar.idProceso, desplazar.nombreProceso, desplazar.tamanioProceso, desplazar.tiempoEjecución, desplazar.prioridadProceso, desplazar.tiempoOperacionES, desplazar.tiempoLlegada, desplazar.vecesDeAcceso);
        }
    }
    
    public static void main(String[] args) {
        //Variables para calcular los tiempos respectivos
            int n_elementos;
        Scanner scanner = new Scanner(System.in);  // Crear un objeto de la clase Scanner
        int almacenamiento = 16;
        FIFO cola_listoEjecucion = new FIFO(almacenamiento);
        FIFO cola_procesoListo = new FIFO(1024);
        FIFO recepcion = new FIFO(almacenamiento);
        int procesoIngresar;
        String pregCont, teclado_nombreProceso;
        int teclado_idProceso, teclado_tamanioProceso, teclado_tiempoEjecución, teclado_prioridadProceso, teclado_tiempoOperacionES, teclado_tiempoLlegada;
        boolean listoEjecucionDisponible = true, continuamos;
        GestorDeProcesos gestor = new GestorDeProcesos();
        int[] inicio = {0};
        //Aqui habrá una función para agregar los valores a nuestra cola
       recepcion.agregar(5,    "Proceso 5",   5,5,5,5,0,inicio);
       recepcion.agregar(4,    "Proceso 4",   4,4,4,4,1,inicio);
       recepcion.agregar(2,     "Proceso 2",    2,2,2,      2,2,inicio);
       recepcion.agregar(1,     "Proceso 1",    1,  1,1,1,3,inicio);
       recepcion.agregar(6,     "Proceso 6",    6,  6,6,6,4,inicio);
       n_elementos = gestor.contarElementosDeCola(recepcion);
       gestor.ordenarCola(recepcion);
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
    int tiempo_ms=0,quantum = 3;
    Proceso acomodar;
    boolean continuar = true;
       do{
           System.out.println("*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#\nTiempo ms: " + tiempo_ms);
           //Vamos a ingresar a la cola de ejecución respecto a su tiempo de llegada //NOTA PERSONAL: ADICIONAR QUE SE AGREGUE SI SU tiempo_ms==recepcion.eliminar().tiempoLlegada
           if(!recepcion.estaVacia() && recepcion.frente.tiempoLlegada == tiempo_ms){//Mientras no este vacia
               acomodar = recepcion.eliminar();
               continuar = gestor.agregarAColaRespectiva(cola_listoEjecucion,cola_procesoListo,continuar,acomodar.idProceso, acomodar.nombreProceso, acomodar.tamanioProceso, acomodar.tiempoEjecución, acomodar.prioridadProceso, acomodar.tiempoOperacionES, acomodar.tiempoLlegada, acomodar.vecesDeAcceso);
           }
           
           if(!cola_listoEjecucion.estaVacia()){//Si la cola de ejecución no esta vacía
               System.out.println("------------> Ejecución <------------");
               cola_listoEjecucion.imprimir();
               System.out.println("------------> Listo <------------");
               cola_procesoListo.imprimir();;
               if(cola_listoEjecucion.frente.tamanioProceso!=0){
                    cola_listoEjecucion.modificarRafaga(quantum,tiempo_ms);
                    gestor.desplazarListas(cola_procesoListo, cola_listoEjecucion);
               }
               //Si la cola "ejecución" tiene espacio suficiente, permite añadir el siguiente elemento formado en la cola "listo",
               //realizaremos un desplazamiento del frente de "listo" a la cola de "ejecución"
               gestor.desplazarListas(cola_listoEjecucion,cola_procesoListo);
           }   
           tiempo_ms++;
       }while(!recepcion.estaVacia() || (!cola_procesoListo.estaVacia() || !cola_listoEjecucion.estaVacia()));
       
        System.out.println("Tiempo de respuesta promedio: " + (cola_listoEjecucion.sumatoriaDeRespuesta/n_elementos));
        System.out.println("Tiempo de ejecución promedio: " + (cola_listoEjecucion.sumatoriaDeEjecución/n_elementos));
        System.out.println("Tiempo de espera promedio: " + (cola_listoEjecucion.sumatoriaDeEspera/n_elementos));
        System.out.println("gestordeprocesos.GestorDeProcesos.main() " + n_elementos);
    }
    
    //Procederemos a calcular  los tiempos promedio con los valores que obtuvimos 
    
    
}
