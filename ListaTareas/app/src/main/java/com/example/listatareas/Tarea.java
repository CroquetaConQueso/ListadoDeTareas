package com.example.listatareas;

public class Tarea {

    String nombre;
    String descripcion;
    float valoracion;

    /*
        Representa el Modelo de una tarea, es un POJO

        nombre da titulo a la tarea
        descripcion describe/explica lo que es
        valoracion es el rating que se le proporciona

        Tengo un constructor vacio para que no pete en el caso de que no añada nada, tengo getters y setters ya que los uso en otras clases.

        Su uso:

        El repo crea una instancia de tarea para la lista inicia
        El fragmento NuevaTareaFr crea las nuevas tareas cuando el usuario rellena el formulario (con el botoncito de "+")
        El ViewModel y el repo pasan objetos a Tarea para que se gestionen
     */


    // Constructor sin prioridad para que no vuelva a petarse
    public Tarea(String nombre, String descripcion) {
        this(nombre, descripcion, 0f);
    }

    // Constructor con prioridad
    public Tarea(String nombre, String descripcion, float valoracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valoracion = valoracion;
    }

    // Getters / setters por si acaso
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }
}
