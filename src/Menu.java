import Personas.Pasajero;
import java.util.Random;
import java.util.Scanner;
/*
public class Menu {

    private String menu;
        private Scanner scanner = new Scanner(System.in);

        Random random = new Random();

        public void iniciar() {
            int opcion;
            do {
                mostrarMenuPrincipal();
                opcion = scanner.nextInt();
                scanner.nextLine();
                boolean checkInCompletado = false;
                switch (opcion) {
                    case 1:
                        irAlBanioSimulacion();
                    break;

                    case 2:
                        mostrarMenuCheckIn();
                        break;

                    case 3:
                        System.out.println("Estás en la zona de tiendas.");
                        break;

                    case 4:
                        mostrarInformacionVuelos();
                        break;

                    case 5:
                        System.out.println("Servicios de comida y bebida disponibles.");
                        break;

                    case 6:
                        System.out.println("Estás en la zona de descanso.");
                        break;

                    case 7:
                        verificarSeguridad();
                        break;

                    case 8:
                        System.out.println("¡Gracias por usar el sistema! ¡Buen viaje!");
                        break;

                    default:
                        System.out.println("Opción no válida, por favor seleccione una opción válida.");
                        break;
                }
            } while (opcion != 8);

            scanner.close();
        }

        private void mostrarMenuPrincipal() {
            System.out.println("--------------------------------------------------------");
            System.out.println("                  MENÚ PRINCIPAL                        ");
            System.out.println("--------------------------------------------------------");
            System.out.println("1. Ir al baño");
            System.out.println("2. Menú Check-In");
            System.out.println("3. Ir a Tiendas");
            System.out.println("4. Información de Vuelos");
            System.out.println("5. Servicios de Comida y Bebida");
            System.out.println("6. Zona de Descanso");
            System.out.println("7. Verificar Seguridad");
            System.out.println("8. Salir");
            System.out.println("--------------------------------------------------------");
            System.out.print("Seleccione una opción (1-8): ");
        }

        private void irAlBanioSimulacion(){
            System.out.println("Has ido al baño..");
            System.out.println("Regresas al lugar donde dejaste tus valijas...");

            // Genera un valor aleatorio para determinar si las valijas están o no
            boolean valijasEstan = random.nextBoolean();

            if (valijasEstan) {
                System.out.println("¡Uff! Estaban aquí...");
            } else {
                System.out.println("Eh.. (Notas que tus valijas no estan).. ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Donde estan??...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A aca!!, jejeje");
            }
            try {
                System.out.println("...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }


        private void mostrarMenuCheckIn() {
            int opcionCheckIn;
            boolean checkInCompletado = false;
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("--------------------------------------------------------");
                System.out.println("                  MENÚ DE CHECK-IN                     ");
                System.out.println("--------------------------------------------------------");
                System.out.println("1. Confirmar Boleto");
                System.out.println("2. Despachar Equipaje");
                System.out.println("3. Entregar Ticket del Boleto");
                System.out.println("4. Salir");
                System.out.println("--------------------------------------------------------");
                System.out.print("Seleccione una opción (1-4): ");
                opcionCheckIn = scanner.nextInt();
                scanner.nextLine();

                switch (opcionCheckIn) {
                    case 1:


                    case 2:
                        // Despachar Equipaje
                        System.out.println("--------------------------------------------------------");
                        System.out.println("¡Equipaje despachado exitosamente!");
                        break;

                    case 3:
                        // Entregar Ticket del Boleto
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Ticket del Boleto Entregado.");
                        break;

                    default:
                        System.out.println("Opción no válida, por favor seleccione entre 1 y 4.");
                        break;
                }

            } while (opcionCheckIn != 4);


        private void mostrarInformacionVuelos() {
                Random random = new Random();
            System.out.println("---------- Pantalla de Información de Vuelos ----------");
            String[] airports = {"Buenos Aires", "NYC", "Madrid", "Paris", "Londres"};
            String[] gates = {"A1", "B2", "C3", "D4", "E5"};
            String[] statuses = {"A tiempo", "Retrasado", "Cancelado", "En espera"};

            for (int i = 0; i < 10; i++) {
                String origin = airports[random.nextInt(airports.length)];
                String destination;
                do {
                    destination = airports[random.nextInt(airports.length)];
                } while (origin.equals(destination));

                String gate = gates[random.nextInt(gates.length)];
                String status = statuses[random.nextInt(statuses.length)];
                String time = String.format("%02d:%02d", random.nextInt(24), random.nextInt(60));

                System.out.printf("Vuelo: %-5s -> %-5s  |  Hora de salida: %-5s  |  Puerta: %-5s  |  Estado: %-10s\n",
                        origin, destination, time, gate, status);
            }
        }

            private void simularZonaDeDescanso(){
                int tiempoRestante = 30; // Tiempo en minutos

                Random random = new Random();

                // Mientras haya tiempo disponible
                while (tiempoRestante > 0) {
                    System.out.println("Actividad en zona de descanso:");

                    // Seleccionar actividad aleatoria
                    switch (random.nextInt(3)) {
                        case 0:
                            System.out.println("- Revisando redes sociales en el celular.");
                            break;
                        case 1:
                            System.out.println("- Paseando por el aeropuerto y mirando las tiendas.");
                            break;
                        case 2:
                            System.out.println("- Leyendo una revista en una cómoda silla.");
                            break;
                        default:
                            break;
                    }

                    // Restar 10 minutos después de cada actividad
                    tiempoRestante -= 10;
                    System.out.println("Tiempo restante en la zona de descanso: " + tiempoRestante + " minutos.\n");

                    // Pausa de 1 segundo entre actividades
                    try {
                        Thread.sleep(1000); // Pausa de 1 segundo entre actividades
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Mensaje cuando el tiempo ha acabado
                System.out.println("Se ha acabado el tiempo en la zona de descanso. Regresando al menú principal...");
            }



            private void verificarSeguridad() {

                // Verificar Seguridad
                if (checkInCompletado) {
                    // Información de la persona que pasa por seguridad
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Bienvenido al aeropuerto, " + nombre + ".");
                    System.out.println("Destino: " + destino);
                    System.out.println("Tipo de Boleto: " + tipoDeBoleto);
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Por favor, coloque sus pertenencias en la bandeja.");
                    System.out.println("___________");
                    System.out.println("| POLICÍA |");
                    System.out.println("     ()  ");  // Cabeza
                    System.out.println("    /|\\  "); // Cuerpo y brazos
                    System.out.println("     |    "); // Cuerpo
                    System.out.println("    / \\   "); // Piernas
                    // Simulación de colocación de objetos
                    System.out.println("Colocando: Laptop, teléfono móvil, zapatos, cinturón...");
                    System.out.println("Revisando las pertenencias...");

                    // Revisión de líquidos
                    Random random = new Random();
                    boolean tieneLiquidos = random.nextBoolean();
                    if (tieneLiquidos) {
                        System.out.println("¡Atención! Se han detectado líquidos. Se le pedirá que los retire.");
                        System.out.println("Quitando líquidos de su maleta...");
                    } else {
                        System.out.println("No se detectaron líquidos.");
                    }

                    // Revisión de objetos prohibidos
                    boolean tieneObjetosProhibidos = random.nextBoolean();
                    if (tieneObjetosProhibidos) {
                        System.out.println("¡Alerta! Se ha encontrado un objeto prohibido en su equipaje.");
                        System.out.println("Por favor, retire el objeto o proceda a la revisión adicional.");
                    } else {
                        System.out.println("No se encontraron objetos prohibidos.");
                    }

                    // Simulación de escaneo
                    System.out.println("Escaneando su equipaje en el detector de rayos X...");
                    System.out.println("Escaneando su persona en el detector de metales...");

                    // Resultado final
                    System.out.println("---------------------------------------------------------");
                    System.out.println("¡Todo en orden! Puede proceder al embarque :) .");
                    System.out.println("Gracias por su cooperación y que tenga un buen viaje!.");
                    System.out.println("---------------------------------------------------------");
                } else {
                    System.out.println("¡Por favor, complete el Check-In primero!");
                }
        }
    }
*/
