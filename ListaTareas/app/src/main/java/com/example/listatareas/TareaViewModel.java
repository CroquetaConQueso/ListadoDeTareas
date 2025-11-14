package com.example.listatareas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TareaViewModel extends AndroidViewModel {

    TareasRepositorio listTareas;
    MutableLiveData<List<Tarea>> listTareasMutableLiveData = new MutableLiveData<>();

   /*
    Mantiene una instacia de TareasRepositorio que luego expone a la lista de tareas con MutableLiveData
    para que los fragments puedan observar y actualizarse. Luego gestiona la tarea seleccionada a traves de este.
    */
    MutableLiveData<Tarea> tareaSeleccionada = new MutableLiveData<>();

    public TareaViewModel(@NonNull Application application) {
        super(application);

        // Inicializo la lista
        listTareas = new TareasRepositorio();
        listTareasMutableLiveData.setValue(listTareas.obtener());
    }

    // Exponer la lista
    public MutableLiveData<List<Tarea>> obtener() {
        return listTareasMutableLiveData;
    }

    // Insertar
    public void insertar(Tarea elemento) {
        listTareas.insertar(elemento, new TareasRepositorio.Callback() {
            @Override
            public void notificarCambios(List<Tarea> elementos) {
                listTareasMutableLiveData.setValue(elementos);
            }
        });
    }

    // Eliminar
    public void eliminar(Tarea elemento) {
        listTareas.eliminar(elemento, new TareasRepositorio.Callback() {
            @Override
            public void notificarCambios(List<Tarea> elementos) {
                listTareasMutableLiveData.setValue(elementos);
            }
        });
    }

    public void actualizar(Tarea elemento, float valoracion) {
        listTareas.actualizar(elemento, valoracion, new TareasRepositorio.Callback() {
            @Override
            public void notificarCambios(List<Tarea> elementos) {
                listTareasMutableLiveData.setValue(elementos);
            }
        });
    }


    public void actualizar(Tarea elemento,
                           String nuevoNombre,
                           String nuevaDescripcion,
                           float nuevaValoracion) {

        listTareas.actualizar(elemento, nuevoNombre, nuevaDescripcion, nuevaValoracion,
                new TareasRepositorio.Callback() {
                    @Override
                    public void notificarCambios(List<Tarea> elementos) {
                        listTareasMutableLiveData.setValue(elementos);
                    }
                });
    }


    public void seleccionar(Tarea tarea) {
        tareaSeleccionada.setValue(tarea);
    }

    public MutableLiveData<Tarea> seleccionado() {
        return tareaSeleccionada;
    }
}
