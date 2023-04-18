package gestordeprocesos;

import java.util.Arrays;

public class FIFO {
    
    Proceso frente;
    Proceso fin;
    int sumatoriaDeEspera = 0, sumatoriaDeEjecución = 0, sumatoriaDeRespuesta = 0;
    int almacenamientoDisponible;
    
    FIFO(int almacenamientoDisponible) {

        this.frente = null;
        this.fin = null;
        this.almacenamientoDisponible = almacenamientoDisponible;
    }

    void agregar(int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada,int[] vecesDeAcceso) {
        Proceso nuevo = new Proceso(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada,vecesDeAcceso);
        if(frente != null){
            fin.siguienteProceso = nuevo;
            fin = nuevo;
        }else{
            frente = nuevo;
            fin = nuevo;
        }
        almacenamientoDisponible -= tamanioProceso;
        //System.out.printf("Subió el proceso %s y restan %d unidades de memoria\n",nuevo.nombreProceso,almacenamientoDisponible);
    }

    Proceso eliminar(){
        if(frente == null){
            return null;//Este caso se realiza cuando la cola esta vacía
        }else{
            Proceso desencolado = frente;
            frente = frente.siguienteProceso;
            if(frente==null){
                fin = null;
            }
            almacenamientoDisponible += desencolado.tamanioProceso;
            return desencolado;
        }
    }
    
    int[] agregarElemento(int[] arreglo, int elemento) {
        // Crear una copia del arreglo con un tamaño mayor
        int[] nuevoArreglo = Arrays.copyOf(arreglo, arreglo.length + 1);
        // Agregar el nuevo elemento al final del arreglo
        nuevoArreglo[nuevoArreglo.length - 1] = elemento;
        return nuevoArreglo;
    }
    
    void modificarRafaga(int tiempo_ms){//el momento de ejecutar esta función, programa que la rafaga sea mayor a cero, si no, generaria un problema
        //Primero se debe comprobar que existe la rafaga suficiente para decrementarla con él Quantum
        if(frente.tamanioProceso>0){
            almacenamientoDisponible ++;
            frente.tamanioProceso--;
        }
            frente.vecesDeAcceso = agregarElemento(frente.vecesDeAcceso, tiempo_ms);
        //En caso de que nuestra rafaga del proceso ya sea cero, no nos servirá de nada, así que procederá a ser eliminada, esta linea se conectará
        //con la parte del GestorDeProceso, donde si aun no es cero, lo que hará es encolar a la "listo" para que vuelva a pelear por el espacio
        if(frente.tamanioProceso == 0){
            
            //Tiempo de respuesta de un proceso:    Tiempo de respuesta = Tiempo de inicio de la ejecución - Tiempo de llegada
            sumatoriaDeRespuesta += (frente.vecesDeAcceso[1]-frente.tiempoLlegada);
            //Tiempo de ejecución de un proceso:    Tiempo de ejecución = Tiempo de finalización - Tiempo de llegada
            sumatoriaDeEjecución += (tiempo_ms - frente.tiempoLlegada);
            //Tiempo de espera de un proceso:       Tiempo de espera = Tiempo de finalización - Tiempo de llegada - Tiempo de ejecución
            sumatoriaDeEspera += (sumatoriaDeEjecución - frente.tiempoLlegada - sumatoriaDeRespuesta);
            eliminar();
        }
        
    }
    
    boolean estaVacia(){
        return (frente == null);
    }
    
    void imprimir() {
        Proceso actual = frente;
        while (actual != null) {
            System.out.print("ID: " + actual.idProceso + "\tRafaga: " + actual.tamanioProceso + "\n");
            actual = actual.siguienteProceso;
        }
        System.out.println("Almacenamiento Dinamico: " + almacenamientoDisponible + "\n");
    }
    
}