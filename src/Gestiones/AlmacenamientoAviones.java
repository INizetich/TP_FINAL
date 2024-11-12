package Gestiones;

import Excepciones.HangarNoExistenteException;
import GestionAviones.Avion;
import GestionAviones.Hangar;

import java.util.*;
import java.util.Scanner;
public class AlmacenamientoAviones {
    Scanner scanner = new Scanner(System.in);
    private List<Hangar<Avion>> listaHangares;

    public AlmacenamientoAviones() {
        this.listaHangares = new ArrayList();
    }

    // Generar hangares automáticamente
    public void generarHangares(int cantidadHangares) {
        for (int i = 1; i <= cantidadHangares; i++) {
            Hangar<Avion> hangar = new Hangar<>(i);
            this.listaHangares.add(hangar);
        }

    }

    // Generar aviones automáticamente y asignarlos a hangares
    public void generarAviones(int cantidadAviones) {
        if (listaHangares.isEmpty()) {
            System.out.println("No hay hangares creados. Primero genera los hangares.");
            return;
        }

        Random random = new Random();
        for (int i = 1; i <= cantidadAviones; i++) {
            // Crear un avión con datos aleatorios
            String nombre = "Boeing-" + (745 + i);
            int capacidad = random.nextInt(300) + 50; // Capacidad entre 50 y 350
            String motor = random.nextBoolean() ? "Motor a reacción" : "Motor turbofan";
            String modelo = "Modelo-" + "A" + random.nextInt(100);
            String codigoAvion = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Avion avion = new Avion(nombre, capacidad, motor, modelo, codigoAvion);

            // Asignar el avión a un hangar aleatorio
            Hangar<Avion> hangar = listaHangares.get(random.nextInt(listaHangares.size()));
            hangar.agrearAvion(avion);
        }

    }

    public List<Hangar<Avion>> getListaHangares() {
        return listaHangares;
    }

    public void setListaHangares(List<Hangar<Avion>> listaHangares) {
        AlmacenamientoAviones.this.listaHangares = listaHangares;
    }

    // Mostrar la lista de hangares y los aviones en cada hangar
    public void mostrarHangares() {
        if (listaHangares.isEmpty()) {
            System.out.println("No hay hangares disponibles.");
            return;
        }

        for (Hangar<Avion> hangar : listaHangares) {
            System.out.println("Hangar Número: " + hangar.getNumeroHangar());
            System.out.println("Aviones en el hangar:");
            for (Avion avion : hangar.ObtenerListaAviones()) {
                System.out.println(" - " + avion);
            }
        }


    }

    public void eliminarAvionPorID(String codigoAvion) {
        if (listaHangares.isEmpty()) {
            System.out.println("La lista de hangares está vacía.");
            return;
        }

        boolean avionEncontrado = false;
        Scanner scanner = new Scanner(System.in);

        for (Hangar<Avion> hangar : listaHangares) {
            // Buscar el avión en el hangar actual
            Optional<Avion> avionEncontradoEnHangar = hangar.ObtenerListaAviones()
                    .stream()
                    .filter(avion -> avion.getCodigoAvion().equals(codigoAvion))
                    .findFirst();

            if (avionEncontradoEnHangar.isPresent()) {
                avionEncontrado = true;
                System.out.println("Avión encontrado en el hangar: " + hangar.getNumeroHangar());
                System.out.println("Desea retirar el avion para que realice un vuelo? (s: Sí / n: No)");
                String eleccion = scanner.nextLine().trim().toLowerCase();

                if (eleccion.equalsIgnoreCase("s")) {
                    hangar.ObtenerListaAviones().remove(avionEncontradoEnHangar.get());
                    System.out.println("Avión retirado para un vuelo correctamente.");
                } else {
                    System.out.println("El avión no fue retirado del hangar.");
                }
                break; // Salir del bucle porque el avión ya fue encontrado
            }
        }

        if (!avionEncontrado) {
            System.out.println("No se encontró un avión con el código: " + codigoAvion);
        }
    }

    /*public void agregarAvionalHangar(int numeroHangar, Avion avion) throws HangarNoExistenteException {
        if (this.numeroHangar !=) {
            listaHangares.add(avion);
            System.out.println("El Hangar seleccion" + listaHangares + "no tiene lugar");
        }else {
            throw new HangarNoExistenteException("El hangar seleccionado tiene lugar");
        }
    }*/

}

/*
//* SISTEMA DE CHECK IN YA ESTA BIEN, FALTAN BOLUDECES NOMAS PERO LO ESENCIAL YA ESTA HECHO, FALTAN HACER VALIDACIONES COMO POR EJEMPLO PEDIR POR PANTALLA EL MOSTAR MAS CHECK INS
*
*
* DESPUES EN SISTEMA DE HANGARES QUE AGREGUE UN AVION AL HANGAR ESPECIFICO QUE LE PASES POR PARAMETRO EL NUMERO DE HANGAR Y EL AVION A AGREGAR Y DESPUES SI SE LES OCURRE OTRA COSA HAGANLO
* TAMBIEN HAGAN VALIDACIONES MIENTRAS HACEN ESTO ASI LO SACAN DE ENCIMA Y EXCEPCIONES DONDE NECESITEN
*
*
* LUEGO SIGAN CON OTRA COSA QUE QUIERAN HACER, COMO EL CONTROL DE PISTAS, O OTRA COSA QUE SE LES OCURRA, QUE VUELE SU CREATIVIDAD PERO HAGAN COSAS QUE SIRVAN Y QUE NO SEAN MEDIO BOLUDAS ASI NO NOS TOMAMOS TANTO TIEMPO CON BOLUDECES
*
*
* HAGAN TODO LO ESENCIAL PARA EL PREMBARQUE Y DESPUES AHI SI QUIEREN ARRANQUEN CON LO DE LAS TIENDAS Y DESPUES CUANDO VUELVA EMPEZAMOS A SERIALIZAR Y DESCERIALIZAR
*
* SEGURO USEMOS JACKSON O GSON ASI ES MAS FACIL
*
* */




