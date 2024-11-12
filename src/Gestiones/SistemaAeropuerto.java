package Gestiones;



import Enums.CodigoInternacional;
import GestionAeropuerto.Aeropuerto;

import java.util.*;

public class SistemaAeropuerto {
    private static Set<Aeropuerto> listaAeropuertos;

    

    public static Set<Aeropuerto> getListaAeropuertos() {
        return listaAeropuertos;
    }

    public static Aeropuerto buscarAeropuertoPorCodigo(CodigoInternacional codigo) {
        return listaAeropuertos.stream()
                .filter(aeropuerto -> aeropuerto.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public static void mostrarAeropuertos() {
        System.out.println("Aeropuertos disponibles:");
        for (Aeropuerto aeropuerto : listaAeropuertos) {
            System.out.println("Código: " + aeropuerto.getCodigo() + " - " + aeropuerto.getNombre()+ " - "+aeropuerto.getDireccion());
        }
    }

    public static void cargarAeropuertos() {
        listaAeropuertos = agregarAeropuertos();
    }

    public static Set<Aeropuerto> agregarAeropuertos() {
        Set<Aeropuerto> listaAeropuerto = new HashSet<>();
        listaAeropuerto.add(new Aeropuerto("Aeropuerto Internacional Ezeiza",
                "AU Tte. Gral. Pablo Riccheri Km 33,5, B1802 Ezeiza, Provincia de Buenos Aires",
                CodigoInternacional.EZE));
        listaAeropuerto.add(new Aeropuerto("Miami International Airport",
                "2100 NW 42nd Ave, Miami, FL 33142, Estados Unidos",
                CodigoInternacional.MIA));
        listaAeropuerto.add(new Aeropuerto("Frankfurter Flughafen",
                "60547 Frankfurt am Main, Alemania.",
                CodigoInternacional.FRA));
        listaAeropuerto.add(new Aeropuerto("Aeropuerto Internacional El Dorado",
                "Av. El Dorado #103-09, Bogotá, Colombia",
                CodigoInternacional.BOG));
        listaAeropuerto.add(new Aeropuerto("北京首都国际机场",
                "3JH3+W6X, Shunyi District, Beijing, China",
                CodigoInternacional.PEK));
        listaAeropuerto.add(new Aeropuerto("Aeropuerto Internacional de la Ciudad de México",
                "Av. Capitán Carlos León S/N, Peñón de los Baños, Venustiano Carranza, 15620 Ciudad de México, CDMX, México",
                CodigoInternacional.MEX));
        listaAeropuerto.add(new Aeropuerto("John F. Kennedy International Airport",
                "Queens, NY 11430, Estados Unidos",
                CodigoInternacional.JFK));
        listaAeropuerto.add(new Aeropuerto("Los Angeles International Airport",
                "1 World Way, Los Ángeles, CA 90045, Estados Unidos",
                CodigoInternacional.LAX));
        listaAeropuerto.add(new Aeropuerto("Dubai International Airport",
                "Dubai - United Arab Emirates",
                CodigoInternacional.DXB));
        listaAeropuerto.add(new Aeropuerto("Sydney Kingsford Smith International Airport",
                "Mascot NSW 2020, Australia",
                CodigoInternacional.SYD));
        listaAeropuerto.add(new Aeropuerto("Aeropuerto Internacional São Paulo-Guarulhos",
                "Rod. Hélio Smidt, s/n - Cumbica, Guarulhos - SP, 07190-100, Brasil",
                CodigoInternacional.GRU));
        listaAeropuerto.add(new Aeropuerto("Amsterdam Airport Schiphol",
                "Evert van de Beekstraat 202, 1118 CP Schiphol, Países Bajos",
                CodigoInternacional.AMS));
        listaAeropuerto.add(new Aeropuerto("Hong Kong International Airport",
                "1 Sky Plaza Rd, Chek Lap Kok, Hong Kong",
                CodigoInternacional.HKG));
        return listaAeropuerto;
    }

}