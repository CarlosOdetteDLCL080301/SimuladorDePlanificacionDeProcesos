package gestordeprocesos;

import java.util.Arrays;

public class FIFO {
    /*
    Declaramos los siguientes atributos que nos permitiran tener registro
    de toda nuestra cola FIFO, ya que nos permite ver que Procesos se mantienen
    hasta delante y hasta atras, las siguientes variables de tipo entero y float
    nos funciona para llevar registro de toda la información.
    */
    Proceso frente;
    Proceso fin;
    float sumatoriaDeEspera = 0, sumatoriaDeEjecución = 0, sumatoriaDeRespuesta = 0;
    int almacenamientoDisponible;
    
    /*En el momento que se declará nuestra cola, solo requerimos que se le asigne la memoria individual por cada cola*/
    FIFO(int almacenamientoDisponible) {

        this.frente = null;
        this.fin = null;
        this.almacenamientoDisponible = almacenamientoDisponible;
    }
    /*
    Creamos un metodo en el que nos permite ingresar un elemento, configurando su enlazamiento en estructura de cola
    */
    void agregar(int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada,int[] vecesDeAcceso) {
        Proceso nuevo = new Proceso(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada,vecesDeAcceso);
        /*
        En caso de haya mas de un valor, se va a colocar en la parte final de la cola, haciendo que se muestre que el
        siguiente elemento del ultimo, sea nuestro nuevo elemento que ingresamos y modificamos la información de nuestra cola
        donde nuestro ultimo elemento, es el nuevo elemento ingresado
        */
        if(frente != null){
            fin.siguienteProceso = nuevo;
            fin = nuevo;
        }else{//En caso que no haya ningun elemento, haremos que sea el frente de nuestra cola y a la vez el final
            frente = nuevo;
            fin = nuevo;
        }
        almacenamientoDisponible -= tamanioProceso;//Modificamos el tamaño diponible de la memora de nuestra cola
        //System.out.printf("Subió el proceso %s y restan %d unidades de memoria\n",nuevo.nombreProceso,almacenamientoDisponible);
    }

    Proceso eliminar(){
        if(frente == null){
            return null;//Este caso se realiza cuando la cola esta vacía y avisa que no hay nada
        }else{/*Si hay un elemento desvinculará este proceso, pero una vez desvinculado, nos regresará 
            este mismo proceso, nos servirá para procesarlo en otro metodo, o simplemente dejamos que se pierda*/
            Proceso desencolado = frente;
            frente = frente.siguienteProceso;
            if(frente==null){
                fin = null;
            }
            almacenamientoDisponible += desencolado.tamanioProceso;//Cuando se elimina el proceso, se libera la memoria
            return desencolado;
        }
    }
    /*
        Es una función que nos servirá como un "append" en python, esto con el proposito, de recolectar, todos los momentos
        en el que el proceso subio a CPU
    */
    int[] agregarElemento(int[] arreglo, int elemento) {
        // Crear una copia del arreglo con un tamaño mayor
        int[] nuevoArreglo = Arrays.copyOf(arreglo, arreglo.length + 1);//Crea una copia de nuestro arreglo pero con un indice extra
        // Agregar el nuevo elemento al final del arreglo
        nuevoArreglo[nuevoArreglo.length - 1] = elemento;//Se asigna en el indice final
        return nuevoArreglo;//Retorna la nueva lista
    }
    /*
        En este metodo, tiene como proposito afectar nuestro proceso con un Quantum (Es el planificador de corto plazo)
    */
    void modificarRafaga(int nuevoTiempo,int quantum,int tiempo_ms){
        int adicionar = 0;
        /*En caso de que el quantum sea menor a la rafaga, modificará la rafaga, con la afectación del quamtum y liberará parte de la rafaga*/
        if(frente.tamanioProceso>quantum){
            almacenamientoDisponible += frente.tamanioProceso - nuevoTiempo;
            frente.tamanioProceso=nuevoTiempo;
        }else{
            /*
                En caso de que la rafaga sea menor que el Quantum, automaticamente la siguiente vez, lo hará cero
                y retomaremos la memoria que nos quedaba en uso 
            */
            almacenamientoDisponible += frente.tamanioProceso;
            adicionar += frente.tamanioProceso;
            frente.tamanioProceso = 0;
        }
        /*
            posteriormente, mandaremos nuestro proceso afectado por el Quantum a la cola de listo
        */
            frente.vecesDeAcceso = agregarElemento(frente.vecesDeAcceso, tiempo_ms);
        //En caso de que nuestra rafaga del proceso ya sea cero, no nos servirá de nada, así que procederá a ser eliminada, esta linea se conectará
        //con la parte del GestorDeProceso, donde si aun no es cero, lo que hará es encolar a la "listo" para que vuelva a pelear por el espacio
        if(frente.tamanioProceso == 0){
            System.out.println("\033[0;35m" + frente.nombreProceso+"///Tiempos de que subio a CPU" + Arrays.toString(frente.vecesDeAcceso) + "\033[0m");
            //Tiempo de respuesta de un proceso:    Tiempo de respuesta = Tiempo de inicio de la ejecución - Tiempo de llegada
            sumatoriaDeRespuesta += (frente.vecesDeAcceso[1]-frente.tiempoLlegada);
            //Tiempo de ejecución de un proceso:    Tiempo de ejecución = Tiempo de finalización - Tiempo de llegada
            sumatoriaDeEjecución += ((tiempo_ms + adicionar) - frente.tiempoLlegada);
            //Tiempo de espera de un proceso:       Tiempo de espera = Tiempo de finalización - Tiempo de llegada - Tiempo de ejecución
            sumatoriaDeEspera += ((tiempo_ms + adicionar) - frente.tiempoLlegada - frente.tiempoEjecución);
            eliminar();
        }
        
    }
    
    //Es un sencillo metodo que nos funcionará solo para saber que esta vacia nuestra cola
    boolean estaVacia(){
        return (frente == null);
    }
    
    //Es un metodo que nos ayudará a imprimir TODOS los procesos que existen en cada cola
    void imprimir() {
        Proceso actual = frente;
        while (actual != null) {
            System.out.print("ID: " + actual.idProceso + "\tRafaga: " + actual.tamanioProceso + "\n");
            actual = actual.siguienteProceso;
        }
        System.out.println("Almacenamiento Dinamico: " + almacenamientoDisponible + "\n");
    }
    
}