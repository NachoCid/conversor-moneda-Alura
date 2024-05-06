import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.*;
import java.net.URI;

public class Calculo {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner teclado = new Scanner(System.in);
        List<Transaccion> listaTransacciones = new ArrayList<>();
        var consumoApi = new ConsumoAPI();

        int seleccion = 0;
        int valorIngresado = 0;
        String codigoPaisIngresado = "";
        String codigoPaisConversion = "";

        System.out.println("\n*******************************************************************************");
        System.out.println("Bienvenido al conversor de monedas, seleccione una de las siguientes opciones");

        boolean enviarConsulta;
        boolean continua = true;
        do {

            menu();
            enviarConsulta = true;
            try {

                seleccion = teclado.nextInt();

                switch (seleccion) {
                    case 1 -> {
                        System.out.println("Ingrese la cantidad de dólares para transformarlos en pesos chilenos: ");

                        try {
                            valorIngresado = teclado.nextInt();
                            codigoPaisIngresado = "USD";
                            codigoPaisConversion = "CLP";
                        } catch (InputMismatchException ex) {
                            enviarConsulta = false;
                            System.out.println("No se pudo realizar la conversión.");
                            /*System.out.println(ex.getMessage());*/
                            teclado.next();
                        }
                    }
                    case 2 -> {
                        System.out.println("Ingrese la cantidad de pesos chilenos para transformarlos en dólares: ");

                        try {
                            valorIngresado = teclado.nextInt();
                            codigoPaisIngresado = "CLP";
                            codigoPaisConversion = "USD";
                        } catch (InputMismatchException ex) {
                            enviarConsulta = false;
                            System.out.println("No se pudo realizar la conversión.");
                            /*System.out.println(ex.getMessage());*/
                            teclado.next();
                        }
                    }
                    case 3 -> {
                        System.out.println("Ingrese la cantidad de dólares para transformarlos a pesos argentinos: ");

                        try {
                            valorIngresado = teclado.nextInt();
                            codigoPaisIngresado = "USD";
                            codigoPaisConversion = "ARS";
                        } catch (InputMismatchException ex) {
                            enviarConsulta = false;
                            System.out.println("No se pudo realizar la conversión.");
                            /*System.out.println(ex.getMessage());*/
                            teclado.next();
                        }
                    }
                    case 4 -> {
                        System.out.println("Ingrese la cantidad de pesos argentinos para transformarlos en dólares :");

                        try {
                            valorIngresado = teclado.nextInt();
                            codigoPaisIngresado = "ARS";
                            codigoPaisConversion = "USD";
                        } catch (InputMismatchException ex) {
                            enviarConsulta = false;
                            System.out.println("No se pudo realizar la conversión.");
                            /*System.out.println(ex.getMessage());*/
                            teclado.next();
                        }
                    }
                    case 5 -> {
                        System.out.println("Ingrese la cantidad de dólares para transformarlos en reales: ");

                        try {
                            valorIngresado = teclado.nextInt();
                            codigoPaisIngresado = "USD";
                            codigoPaisConversion = "BRL";
                        } catch (InputMismatchException ex) {
                            enviarConsulta = false;
                            System.out.println("No se pudo realizar la conversión.");
                            /*System.out.println(ex.getMessage());*/
                            teclado.next();
                        }
                    }
                    case 6 -> {
                        System.out.println("Ingrese la cantidad de reales para transformarlos en dólares: ");

                        try {
                            valorIngresado = teclado.nextInt();
                            codigoPaisIngresado = "BRL";
                            codigoPaisConversion = "USD";
                        } catch (InputMismatchException ex) {
                            enviarConsulta = false;
                            System.out.println("No se pudo realizar la conversión.");
                            /*System.out.println(ex.getMessage());*/
                            teclado.next();
                        }
                    }
                    case 7 -> {

                        try {
                            if (listaTransacciones.isEmpty()) {
                                System.out.println("\nNo hay transacciones para mostrar.");
                                enviarConsulta = false;
                            } else {

                                for (int i = 0; i < listaTransacciones.size(); i++) {
                                    System.out.println("\nTransacción : " + (i + 1));
                                    System.out.println(listaTransacciones.get(i).toString());
                                    enviarConsulta = false;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error inesperado. No se ha podido " +
                                    "mostrar el listado de transacciones");
                            /*System.out.println(ex.getMessage());*/
                        }

                    }
                    case 9 -> {
                        System.out.println("\nMuchas gracias por utilizar nuestro programa de conversión.");
                        continua = false;
                        enviarConsulta = false;
                        break;
                    }

                    default -> {
                        System.out.println("\nIngrese el valor correspondiente a una de las 7 opciones, si desea salir ingrese 9.");
                        enviarConsulta = false;
                    }

                }

                var json = ConsumoAPI.obtenerDatos("https://v6.exchangerate-api.com/v6/9799b8108bdbde2e925d4528/pair/" +
                        codigoPaisIngresado+ "/" +codigoPaisConversion+"/"+ valorIngresado);
                /*System.out.println(json);*/
                Gson gson = new Gson();

                if (enviarConsulta) {

                    try {
                        Transaccion nuevaAPIConsulta = gson.fromJson(json, Transaccion.class);

                        nuevaAPIConsulta.setValorInicial(valorIngresado);
                        nuevaAPIConsulta.setFecha(fecha());

                        System.out.println("\n Detalles del cambio: \n- " + nuevaAPIConsulta + "\n");

                        listaTransacciones.add(nuevaAPIConsulta);
                    } catch (InputMismatchException e) {
                        System.out.println("No se pudo realizar la consulta a la API.");
                        System.out.println(e.getMessage());
                    }

                }

                if (seleccion == 9 && listaTransacciones.size() > 0) {
                    try {
                        FileWriter escritura = new FileWriter("historicoTransacciones.json");
                        escritura.write(gson.toJson(listaTransacciones));
                        escritura.close();
                        System.out.println("Se ha guardado un Json con transacciones");
                    } catch (Exception ex) {
                        System.out.println("No se ha podido guardar el archivo Json de transacciones.");
                    }

                }
            } catch (InputMismatchException ex) {
                System.out.println("Favor ingresar una opción numérica.");
                teclado.next();
            }
 
        } while (continua);

        teclado.close();
    }

    private static void menu() {

        String menu = """
                1) Dólar =>> Peso chileno
                2) Peso chileno =>> Dólar 
                3) Dólar =>> Peso argentino
                4) Peso argentino =>> Dólar
                5) Dólar =>> Real brasileño
                6) Real brasileño =>> Dólar  
                7) Obtener historial actual de conversiones
                9) Salir       
                *******************************************************************************
                                
                Elija una opción válida: """;

        System.out.println("*******************************************************************************");
        System.out.println(menu);

    }

    private static String fecha() {
        String fechaActual = "";

        LocalDateTime ahora = LocalDateTime.now();
        String year = Integer.toString(ahora.getYear());
        String mes = Integer.toString(ahora.getMonthValue());
        String dia = Integer.toString(ahora.getDayOfMonth());
        String hora = Integer.toString(ahora.getHour());
        String minutos = Integer.toString(ahora.getMinute());
        String segundos = Integer.toString(ahora.getSecond());

        fechaActual = hora + ":" + minutos + ":" + segundos + " - " + dia + "/" + mes + "/" + year + ".";

        return fechaActual;
    }

}
