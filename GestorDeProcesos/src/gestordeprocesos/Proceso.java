package gestordeprocesos;
public class Proceso {
    int     idProceso;
    String  nombreProceso;
    int     tamanioProceso;
    int     tiempoEjecución;
    int     prioridadProceso;
    int     tiempoOperacionES;
    int     tiempoLlegada;
    Proceso    siguienteProceso;

    public Proceso(int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada){
        this.idProceso = idProceso;
        this.nombreProceso = nombreProceso;
        this.tamanioProceso = tamanioProceso;
        this.tiempoEjecución = tiempoEjecución;
        this.prioridadProceso = prioridadProceso;
        this.tiempoOperacionES = tiempoOperacionES;
        this.tiempoLlegada = tiempoLlegada;
        this.siguienteProceso = null;
    }
}
