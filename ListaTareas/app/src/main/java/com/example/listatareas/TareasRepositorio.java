package com.example.listatareas;

import java.util.ArrayList;
import java.util.List;

public class TareasRepositorio {

    List<Tarea> tareas = new ArrayList<>();

    /*
        La clase funcoina como repo, simula una base de datos en memoria con su array (está justamente declarado arriba)

        Interface Callback: se usa para notificar al ViewModel cuando la lista cambia
        Hacemos CRUD (obtiene, inserta, elimina y actualiza)


        En el constructor se añaden cuatro tareas para que no este vacia

        Para acordarme, se relaciona con el ViewModel (TareaViewModel)  cuando se hace un crud y llama al Callback
        que luego actualiza el LiveData de la lista de tareas

     */
    interface Callback {
        void notificarCambios(List<Tarea> tareasActualizadas);
    }

    public TareasRepositorio() {
        // Tareas de ejemplo con prioridad
        tareas.add(new Tarea("Estudiar PMDM", "Repasar MVVM y LiveData", 3f));
        tareas.add(new Tarea("Hacer la compra", "Leche, pan, huevos", 2f));
        tareas.add(new Tarea("Proyecto DAM", "Avanzar en el PFC", 4f));
        tareas.add(new Tarea("Descansar", "Ver una serie o jugar", 1f));
    }

    // Devuelve la lista completa
    List<Tarea> obtener() {
        return tareas;
    }

    // Operaciones CRUD
    void insertar(Tarea elemento, Callback callback) {
        tareas.add(elemento);
        if (callback != null) {
            callback.notificarCambios(tareas);
        }
    }


    void eliminar(Tarea elemento, Callback callback) {
        tareas.remove(elemento);
        if (callback != null) {
            callback.notificarCambios(tareas);
        }
    }

    void actualizar(Tarea elemento, float valoracion, Callback callback) {
        elemento.valoracion = valoracion;
        if (callback != null) {
            callback.notificarCambios(tareas);
        }
    }


    void actualizar(Tarea elemento,
                    String nuevoNombre,
                    String nuevaDescripcion,
                    float nuevaValoracion,
                    Callback callback) {

        elemento.nombre = nuevoNombre;
        elemento.descripcion = nuevaDescripcion;
        elemento.valoracion = nuevaValoracion;

        if (callback != null) {
            callback.notificarCambios(tareas);
        }
    }
}
