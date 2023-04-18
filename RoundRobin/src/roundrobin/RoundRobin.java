package roundrobin;
import java.util.ArrayList;

class Proceso {
    String nombre;
    int tiempoLlegada;
    int tiempoRafaga;
    int tiempoRestante;

    public Proceso(String nombre, int tiempoLlegada, int tiempoRafaga) {
        this.nombre = nombre;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoRestante = tiempoRafaga;
    }
}

class Nodo {
    Proceso proceso;
    Nodo siguiente;

    public Nodo(Proceso proceso) {
        this.proceso = proceso;
        this.siguiente = null;
    }
}

class ColaProcesos {
    Nodo primero;
    Nodo ultimo;

    public ColaProcesos() {
        this.primero = null;
        this.ultimo = null;
    }

    public void encolar(Proceso proceso) {
        Nodo nuevoNodo = new Nodo(proceso);
        if (this.primero == null) {
            this.primero = nuevoNodo;
            this.ultimo = nuevoNodo;
        } else {
            this.ultimo.siguiente = nuevoNodo;
            this.ultimo = nuevoNodo;
        }
    }

    public Proceso desencolar() {
        if (this.primero == null) {
            return null;
        } else {
            Proceso proceso = this.primero.proceso;
            this.primero = this.primero.siguiente;
            if (this.primero == null) {
                this.ultimo = null;
            }
            return proceso;
        }
    }

    public void imprimir() {
        Nodo actual = this.primero;
        while (actual != null) {
            System.out.print(actual.proceso.nombre + " ");
            actual = actual.siguiente;
        }
        System.out.println();
    }
}

class RoundRobin {
    ArrayList<Proceso> listaProcesos;
    ColaProcesos colaProcesos;
    int quantum;
    int tiempoActual;

    public RoundRobin(ArrayList<Proceso> listaProcesos, int quantum) {
        this.listaProcesos = listaProcesos;
        this.colaProcesos = new ColaProcesos();
        this.quantum = quantum;
        this.tiempoActual = 0;
    }

    public void planificarProcesos() {
        while (!listaProcesos.isEmpty() || colaProcesos.primero  != null) {
            while (!listaProcesos.isEmpty() && listaProcesos.get(0).tiempoLlegada == tiempoActual) {
                Proceso proceso = listaProcesos.remove(0);
                colaProcesos.encolar(proceso);
                System.out.print("Cola de procesos: ");
                colaProcesos.imprimir();
            }

            if (colaProcesos.primero != null) {
                Proceso proceso = colaProcesos.desencolar();
                int tiempoPlanificado = Math.min(quantum, proceso.tiempoRestante);
                proceso.tiempoRestante -= tiempoPlanificado;
                tiempoActual += tiempoPlanificado;
                if (proceso.tiempoRestante > 0) {
                    colaProcesos.encolar(proceso);
                } else {
                    System.out.println("Proceso " + proceso.nombre + " terminado en tiempo " + tiempoActual);
                }
                System.out.print("Cola de procesos:");                
                colaProcesos.imprimir();
            } else {
                tiempoActual++;
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Proceso> listaProcesos = new ArrayList<>();
        listaProcesos.add(new Proceso("P1", 0, 8));
        listaProcesos.add(new Proceso("P2", 1, 4));
        listaProcesos.add(new Proceso("P3", 2, 9));
        listaProcesos.add(new Proceso("P4", 3, 5));

        RoundRobin roundRobin = new RoundRobin(listaProcesos, 3);
        roundRobin.planificarProcesos();
    }
}

