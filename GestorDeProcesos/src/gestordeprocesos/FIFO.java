package gestordeprocesos;
public class FIFO {
    
    Proceso cabeza;
    int almacenamientoDisponible;
    
    FIFO(int almacenamientoDisponible) {

        this.cabeza = null;
        this.almacenamientoDisponible = almacenamientoDisponible;
    }

    void agregar(int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada) {
        if(almacenamientoDisponible - tamanioProceso >= 0){
            Proceso nuevoProceso = new Proceso(idProceso,nombreProceso,tamanioProceso,tiempoEjecución,prioridadProceso, tiempoOperacionES, tiempoLlegada);    
            nuevoProceso.siguienteProceso = cabeza;
            cabeza = nuevoProceso;
            almacenamientoDisponible -= nuevoProceso.tamanioProceso;
        }
        
    }

    void eliminar(int idProceso){
        
        Proceso actual = cabeza;
        Proceso anterior = null;

        while(actual != null && actual.idProceso != idProceso){
            anterior = actual;
            actual = actual.siguienteProceso;
            almacenamientoDisponible += actual.tamanioProceso;
        }

        if(actual == null){
            return;
        }

        if (anterior == null) {
            cabeza = actual.siguienteProceso;
            almacenamientoDisponible += actual.tamanioProceso;
        } else {
            anterior.siguienteProceso = actual.siguienteProceso;
        }

        actual.siguienteProceso = null;
        
        
    }

    void imprimir() {

        Proceso actual = cabeza;
        while (actual != null) {
            System.out.print("ID: " + actual.idProceso + "\n");
            actual = actual.siguienteProceso;
        }
        System.out.println("Almacenamiento Dinamico: " + almacenamientoDisponible + "\n");
    }

}
