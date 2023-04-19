package gestordeprocesos;
import java.util.Scanner;
   
public class GestorDeProcesos {
    /*
    El siguiente metodo, tiene como proposito, agregar principalmente en la cola de ejecución, en caso de que este
    se encuentre lleno por el almacenamiento, tiene como proposito, mandarlo directamente a la cola de "listo"
    */
    boolean agregarAColaRespectiva(FIFO cola_listoEjecucion, FIFO cola_procesoListo,boolean listoEjecucionDisponible, int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada,int[] vecesDeAcceso){
        if(cola_listoEjecucion.almacenamientoDisponible - tamanioProceso >= 0 && listoEjecucionDisponible){
            cola_listoEjecucion.agregar(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada, vecesDeAcceso);
            return listoEjecucionDisponible;
        }else{
            cola_procesoListo.agregar(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada, vecesDeAcceso);
            return listoEjecucionDisponible = false;
        }
    }
    /*
    Se desarrolló un metodo en el que pudiera replicar la función de "len()" como en otros lenguajes como Python
    El cual su proposito es darnos la longitud de una cola, sin necesidad de pedirlo desde un inicio
    */
    int contarElementosDeCola(FIFO cola){
        int contador = 0;
        Proceso elemento;
        //Crea una copia de nuestro cola, 
        FIFO auxiliar = new FIFO(cola.almacenamientoDisponible);
        while(!cola.estaVacia()){//Repetira hasta que la cola este vacia
            elemento = cola.eliminar();
            auxiliar.agregar(elemento.idProceso, elemento.nombreProceso, elemento.tamanioProceso, elemento.tiempoEjecución, elemento.prioridadProceso, elemento.tiempoOperacionES, elemento.tiempoLlegada, elemento.vecesDeAcceso);
            contador++;//Se realiza el conteo, de cada elemento eliminado
        }
        while(!auxiliar.estaVacia()){//Se repetira hasta que la cola clon se vacie, pero regresando todos los valores como estaban al inicio
            elemento = auxiliar.eliminar();
            cola.agregar(elemento.idProceso, elemento.nombreProceso, elemento.tamanioProceso, elemento.tiempoEjecución, elemento.prioridadProceso, elemento.tiempoOperacionES, elemento.tiempoLlegada, elemento.vecesDeAcceso);
        }
        return contador;//Retornara, la cantidad de elementos que encontro nuestra función
    }
    
    /*
    Se crea una función en la cual, nos regresará nuestra cola que se ingreso, pero ordenada, tomando como referencia
    el tiempo de llegada
    */
    void ordenarCola(FIFO cola){
        int n = contarElementosDeCola(cola),i=0;
        Proceso[] array = new Proceso[n];
        /*
        Se usa un arreglo, pero unicamente para facilitar el ordenamiento de nuestra cola de entrada, pero esto no
        choca con las condiciones que se nos pidio en el documento y menos con los que se nos ccomento durante la
        sesion del dia 11 de abril
        */
        Proceso elemento;
        //Vaciar la cola y almacenar sus elementos en el arreglo
        while(!cola.estaVacia()){//Se vaciara todo la cola en un arreglo
            elemento = cola.eliminar();
            array[i] = elemento;
            i++;
        }
        
        //Aplicar el algoritmo de ordenamiento de seleccion al arreglo
        //El algoritmo que escogimos, fue Bubble Sort
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
    /*
    La siguiente función, tiene como proposito, lograr mover el proceso que se encuentra al inicio de nuestra
    cola que consideremos como emisor, y la enviaremos a una cola destinatario. al igual, se considerará recuperar
    toda la información de nuestro proceso, sin generar la perdida de alguno de ellos
    */
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
        int almacenamiento = 1024;
        FIFO cola_listoEjecucion = new FIFO(almacenamiento);
        FIFO cola_procesoListo = new FIFO(1024);
        FIFO recepcion = new FIFO(almacenamiento);
        int procesoIngresar;
        String pregCont, teclado_nombreProceso;
        int teclado_idProceso, teclado_tamanioProceso, teclado_tiempoEjecución, teclado_prioridadProceso, teclado_tiempoOperacionES, teclado_tiempoLlegada;
        boolean listoEjecucionDisponible = true, continuamos;
        GestorDeProcesos gestor = new GestorDeProcesos();
        int[] inicio = {0};
        /*
        Para facilitarnos el desarrollo del programa, decidimos mandarlos directamente, sin necesidad de pedirselo
        al usuario (por ahora), para  hacer más rapido las pruebas, el caso que nosotros utilizamos como referencia, es el
        ejercicio que se realizo un dia antes del examen sobre Round Robin
        */
    recepcion.agregar(1,    "Proceso 1",   6,6,6,6,1,inicio);
    recepcion.agregar(2,    "Proceso 2",   18,18,18,6,4,inicio);
    recepcion.agregar(3,    "Proceso 3",   12,12,12,12,6,inicio);
    recepcion.agregar(4,    "Proceso 4",   17,17,17,17,7,inicio);
       n_elementos = gestor.contarElementosDeCola(recepcion);//Almacenamos los elementos que tiene nuestra cola
       gestor.ordenarCola(recepcion);//Ordenamos la cola
       /*
       En caso de querer, que nuestro usuario tiene la intencion de ingresar los valores manualmente, solo se requiere desactivar
       las siguientes lineas del codigo que tiene como proposito recopilar todos los propositos deseados
       */
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
    int tiempo_ms=0,quantumDeseado = 4,quantum = quantumDeseado,repite = 0, tiemposQueSube;
    Proceso acomodar;
    boolean continuar = true;
    //Al menos siempre se ejecutará una vez, por eso se consideró usar un Do - While   
    do{
           tiemposQueSube = tiempo_ms;
           System.out.println("*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#\nTiempo ms: " + tiempo_ms );
           //Vamos a ingresar a la cola de ejecución respecto a su tiempo de llegada //NOTA PERSONAL: ADICIONAR QUE SE AGREGUE SI SU tiempo_ms==recepcion.eliminar().tiempoLlegada
           
           int t,resta;
           if(!cola_listoEjecucion.estaVacia()){//Si la cola de ejecución no esta vacía
               t = cola_listoEjecucion.frente.tamanioProceso;
               resta = t -quantum;
               System.out.println("------------> Ejecución <------------");
               cola_listoEjecucion.imprimir();
               System.out.println("------------> Listo <------------quamtum: " + quantum);
               cola_procesoListo.imprimir();
               
               if(resta>0){
                   tiempo_ms += quantum;
                   cola_listoEjecucion.modificarRafaga(resta,quantum, tiemposQueSube);
                   gestor.desplazarListas(cola_procesoListo, cola_listoEjecucion);
                   while(!recepcion.estaVacia() && recepcion.frente.tiempoLlegada <= tiempo_ms){//Mientras no este vacia
                    acomodar = recepcion.eliminar();
                    continuar = gestor.agregarAColaRespectiva(cola_listoEjecucion,cola_procesoListo,continuar,acomodar.idProceso, acomodar.nombreProceso, acomodar.tamanioProceso, acomodar.tiempoEjecución, acomodar.prioridadProceso, acomodar.tiempoOperacionES, acomodar.tiempoLlegada, acomodar.vecesDeAcceso);
                     }
               }else{
                    tiempo_ms += (quantum + resta);
                    cola_listoEjecucion.modificarRafaga(0, quantum, tiemposQueSube);
               }
               gestor.desplazarListas(cola_listoEjecucion, cola_procesoListo);
               //Si la cola "ejecución" tiene espacio suficiente, permite añadir el siguiente elemento formado en la cola "listo",
               //realizaremos un desplazamiento del frente de "listo" a la cola de "ejecución"
               while(!recepcion.estaVacia() && recepcion.frente.tiempoLlegada <= tiempo_ms){//Mientras no este vacia
               acomodar = recepcion.eliminar();
               continuar = gestor.agregarAColaRespectiva(cola_listoEjecucion,cola_procesoListo,continuar,acomodar.idProceso, acomodar.nombreProceso, acomodar.tamanioProceso, acomodar.tiempoEjecución, acomodar.prioridadProceso, acomodar.tiempoOperacionES, acomodar.tiempoLlegada, acomodar.vecesDeAcceso);
                }
           }else{
                tiempo_ms++;/*En caso de que no se realice una operación con el Quantum, igual se hará el incremento para que se simule el tiempo
                en cuestion de progreso
                */
                /*
                Siempre se preguntará si el hay algun Proceso, pendiente por ingresar, en caso de que sí, se añadirá a nuestra cola de ejecucion
                para que sea procesada
                */
                if(!recepcion.estaVacia() && recepcion.frente.tiempoLlegada <= tiempo_ms){//Mientras no este vacia
               acomodar = recepcion.eliminar();
               continuar = gestor.agregarAColaRespectiva(cola_listoEjecucion,cola_procesoListo,continuar,acomodar.idProceso, acomodar.nombreProceso, acomodar.tamanioProceso, acomodar.tiempoEjecución, acomodar.prioridadProceso, acomodar.tiempoOperacionES, acomodar.tiempoLlegada, acomodar.vecesDeAcceso);
           }
                }   
           //tiempo_ms++;
    /*
        El ciclo terminará cuando nuestras tres colas esten totalmente vacia, ya que significa que ya habremos finalizado
           todas las operaciones necesarias para la actividad
    */   
    }while((!recepcion.estaVacia() || (!cola_procesoListo.estaVacia() || !cola_listoEjecucion.estaVacia())));
   /*
    Siempre que existía una interacción directa con nuestro proceso, de nuestra cola de ejecución, siempre se
    rescataba, el tiempo en que llegaba y se iba, pero el momento mas importante, es cuando realizabamos la suma
    acumulativa para obtener las respectivos tiempos promedios de respuesta, ejecución y espera, unicamente aqui
    esa suma acumulada será dividida por todos los elementos ingresados, para obtener el promedio
    */
        System.out.println("Tiempo de respuesta promedio: " + (cola_listoEjecucion.sumatoriaDeRespuesta/n_elementos));//LISTO!!!
        System.out.println("Tiempo de ejecución promedio: " + (cola_listoEjecucion.sumatoriaDeEjecución/n_elementos));//LISTO!!!
        System.out.println("Tiempo de espera promedio: " + (cola_listoEjecucion.sumatoriaDeEspera/n_elementos));
    }

}