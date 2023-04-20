package gestordeprocesos;

public class Proceso {
    int     idProceso;//
    String  nombreProceso;//"Word"
    int     tamanioProceso;//
    int     tiempoEjecución;
    int     tiempoEjecución_clon;
    int     prioridadProceso;
    int     tiempoOperacionES;
    int     tiempoLlegada;
    Proceso siguienteProceso;//
    int     inactivoPor;//Round Robin
    int[] vecesDeAcceso;//Guardar registro de las veces que sube a CPU

    public Proceso(int idProceso, String nombreProceso,int tamanioProceso,int tiempoEjecución, int prioridadProceso, int tiempoOperacionES, int tiempoLlegada,int[] vecesDeAcceso){
        this.idProceso = idProceso;
        this.nombreProceso = nombreProceso;
        this.tamanioProceso = tamanioProceso;
        this.tiempoEjecución = tiempoEjecución;
        this.prioridadProceso = prioridadProceso;
        this.tiempoOperacionES = tiempoOperacionES;
        this.tiempoLlegada = tiempoLlegada;
        this.siguienteProceso = null;
        this.vecesDeAcceso = vecesDeAcceso;
        this.tiempoEjecución_clon = this.tiempoEjecución;
    }
}