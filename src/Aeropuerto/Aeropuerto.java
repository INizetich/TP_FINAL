package Aeropuerto;

import Aviones.Avion;
import Aviones.Hangar;
import Enums.CodigoInternacional;

import Gestiones.AlmacenamientoAviones;
import Personas.Empleado;


import java.io.Serializable;
import java.util.*;

public  class Aeropuerto implements Serializable {

    private  String nombre;

    private  String direccion;

    private  CodigoInternacional Codigo;


    private  AlmacenamientoAviones almacenamiento = new AlmacenamientoAviones();



    public Aeropuerto(){
        this.nombre = "";
        this.direccion = "";
        this.Codigo = null;
        this.almacenamiento = new AlmacenamientoAviones();

    }

    public Aeropuerto(String nombre, String direccion, CodigoInternacional codigo){
        this.nombre = nombre;
        this.direccion = direccion;
        this.Codigo = codigo;
    }



    public  String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CodigoInternacional getCodigo() {
        return Codigo;
    }

    public void setCodigo(CodigoInternacional codigo) {
        Codigo = codigo;
    }






    public void cargarHangaresAeropuerto(Set<Empleado> listaEmpleados) {
        // Verifica que los hangares sean cargados correctamente
        List<Hangar> listaHangares = new ArrayList<>();
        listaHangares.addAll(almacenamiento.getListaHangares());

        // Asegúrate de que los hangares estén poblados con aviones
        for (Hangar hangar : listaHangares) {
            if (hangar.getListaAviones().isEmpty()) {
                // Agregar aviones automáticamente si es necesario
                almacenamiento.generarAviones(5,listaEmpleados); // Suponiendo que tienes este método en AlmacenamientoAviones
                hangar.agregarAvion(new Avion("Avion-" + UUID.randomUUID().toString().substring(0,4).toUpperCase(), 200, "Motor A", "Modelo A"+UUID.randomUUID().toString().substring(0,4).toUpperCase(), UUID.randomUUID().toString().substring(0,6).toUpperCase()));
            }
        }


    }



    @Override
    public String toString() {
        return "Aeropuerto{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", Codigo Internacional=" + Codigo +
                '}';
    }



}