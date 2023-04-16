package gestordeprocesos;
public class FIFO {
    
    Proceso frente;
    Proceso fin;
    int almacenamientoDisponible;
    
    FIFO(int almacenamientoDisponible) {

        this.frente = null;
        this.fin = null;
        this.almacenamientoDisponible = almacenamientoDisponible;
    }

    void agregar(int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada) {
        Proceso nuevo = new Proceso(idProceso, nombreProceso, tamanioProceso, tiempoEjecución, prioridadProceso, tiempoOperacionES, tiempoLlegada);
        if(frente != null){
            fin.siguienteProceso = nuevo;
            fin = nuevo;
        }else{
            frente = nuevo;
            fin = nuevo;
        }
        almacenamientoDisponible -= tamanioProceso;
        System.out.printf("Subió el proceso %s y restan %d unidades de memoria\n",nuevo.nombreProceso,almacenamientoDisponible);
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
    
    void modificarRafaga(int quamtum){//el momento de ejecutar esta función, programa que la rafaga sea mayor a cero, si no, generaria un problema
        if(frente != null){
            int temp = frente.tamanioProceso; 
            frente.tamanioProceso -=quamtum;
            System.out.printf("Rafaga: %d\n", frente.tamanioProceso);
            almacenamientoDisponible += temp - frente.tamanioProceso;
        }
    }
    
    boolean estaVacia(){
        return (frente == null);
    }
    
    void imprimir() {
        Proceso actual = frente;
        while (actual != null) {
            System.out.print("ID: " + actual.idProceso + "\n");
            actual = actual.siguienteProceso;
        }
        System.out.println("Almacenamiento Dinamico: " + almacenamientoDisponible + "\n");
    }
    
}
