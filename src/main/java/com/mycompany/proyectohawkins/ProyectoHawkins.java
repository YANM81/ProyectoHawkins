/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectohawkins;

import java.util.Scanner;

/**
 * Práctica UD2: Sintaxis, Estructuras de Control y Excepciones
 * @author yezid
 */
public class ProyectoHawkins {

    public static void main(String[] args) {
        // Inicializo el Scanner para poder leer lo que el usuario escriba por la consola
        Scanner scanner = new Scanner(System.in);
        
        // Constantes fijas que nos dio el enunciado para los límites del sensor
        final double MAX_ENERGIA = 200.0;
        final double LIMITE_RIESGO = 150.0;
        
        // Variables que vamos a usar en el flujo principal del programa
        String operadorActual;
        double sensorMedido = 0;
        int codigoRiesgo;
        int porcentajeEstado;
        boolean portalAbierto;
        int opcionMenu = 0;
        
        // Bienvenida al sistema e ingreso del nombre del operario
        System.out.println("-----------HAWKINS NATIONAL LABORATORY - ACCESO RESTRINGIDO------------");
        System.out.print("\nIngrese su nombre:   ");
        operadorActual = scanner.nextLine();    
        
        // Bucle do-while: necesito que el menú se muestre al menos una vez 
        // y se repita hasta que el usuario elija salir pulsando el 0.
        do{
            mostrarEncabezado(operadorActual);
            
            // Leo la opción numérica del menú
            opcionMenu = scanner.nextInt();
            
            // Estructura switch para decidir qué hacer según la opción del usuario
            switch (opcionMenu) {
                
                case 1:
                    // Opción 1: Análisis puntual de un sensor individual
                    sensorMedido = leerLecturaSegura(scanner, "Introduce lectura μT: ");
                    codigoRiesgo = evaluarNivelRiesgo(sensorMedido, LIMITE_RIESGO);
                    porcentajeEstado = calcularPorcentajeTruncado(sensorMedido, MAX_ENERGIA);
                    portalAbierto = ejecutarProtocoloAccion(codigoRiesgo);
                    
                    // Muestro el mini informe con los resultados obtenidos
                    System.out.println("\n--- Informe ---");
                    System.out.println("Capacidad " + porcentajeEstado + "%");
                    System.out.println("portalAbierto " + portalAbierto);
                    break;
                    
                case 2:
                    // Opción 2: Llamo al método que simula un bucle continuo de lecturas
                    simularMuestreoContinuo(scanner, LIMITE_RIESGO);
                    break;
                    
                case 0:
                    // Opción para salir del programa
                    System.out.println(" Saliendo, hasta  pronto ");
                    break;
                    
                default:
                    // Si meten un número que no está en el menú
                    System.out.println("Opción no válida.");
                    break;
                    
            }
   
        } while (opcionMenu != 0);
        
        // Cierramos el scanner al terminar para buena práctica de recursos
        scanner.close();
    }
    
    // Método para mostrar el menú usando formato (printf) y tabulaciones (\t) como pedía la práctica
    public static void mostrarEncabezado(String operadorActual){
        System.out.printf("\n%s\n", "==================================================");
        System.out.printf(" Hola, %s\t--- SISTEMA HAWKINS ---\n", operadorActual);
        System.out.printf("%s\n", "==================================================");
        System.out.printf("\t1. Inspeccionar Sensor Individual\n");
        System.out.printf("\t2. Iniciar Muestreo Continuo\n");
        System.out.printf("\t0. Salir de la Consola\n");
        System.out.printf("%s\n", "==================================================");
    }

    // Método para leer números de forma segura controlando errores si meten letras
    public static double leerLecturaSegura(Scanner scanner, String mensaje){
        double lectura = 0;
        boolean entradaValida = false;
        
        // Usamos un while con try-catch para obligar a que metan un número válido sí o sí
        while(!entradaValida){    
            try{
               System.out.print(mensaje);
               lectura = scanner.nextDouble();
               entradaValida = true; // Si no da error, salimos del bucle
            }catch (java.util.InputMismatchException e){    
                System.out.println("Error: Debes introducir un valor numérico válido.");
                scanner.next(); // Limpiamos el búfer para evitar bucles infinitos
            }
        }
        return lectura;
    }
    
    // Método para evaluar el nivel de riesgo según la lectura en microTeslas
    public static int evaluarNivelRiesgo(double microTeslas, double LIMITE_RIESGO){
        // Uso un operador ternario para asignar la etiqueta en una sola línea como pedía el ejercicio
        String etiqueta = (microTeslas < 50.0) ? "Seguro" : (microTeslas <= LIMITE_RIESGO) ? "Alerta" : "Crítico";
        
        // Devuelvo el código numérico correspondiente según los rangos
        if (microTeslas < 50.0) {
            return 1;  
        } else if (microTeslas <= LIMITE_RIESGO) {
            return 2;
        } else {
            return 3;  
        }
    }
    
    // Método para calcular el porcentaje respecto a la energía máxima
    public static int calcularPorcentajeTruncado(double lectura , double MAX_ENERGIA){
        // Hago un casting explícito a (int) para quitar los decimales (truncar)
        int porcentaje = (int)((lectura / MAX_ENERGIA) * 100);
        return porcentaje;
    }
    
    // Método para evaluar la acción según el código de riesgo devuelto
    public static boolean ejecutarProtocoloAccion(int codigoRiesgo){
        switch(codigoRiesgo){
            case 1:
                System.out.print("Modo Estable");
                return false; // El portal está cerrado / seguro
            case 2:
                System.out.print("Anomalía Detectada");
                return false; // Alerta pero controlado
            case 3:
                System.out.print("¡ALERTA MÁXIMA!");
                return true;  // Peligro, el portal está abierto
            default:
                System.out.print("Código no válido");
                return false;
        }
    }
    
    // Método que simula un muestreo continuo con condiciones de parada (break) y salto (continue)
    public static void simularMuestreoContinuo(Scanner teclado, double limite) {
        double suma = 0;
        int contador = 0;
        double lectura;

        System.out.println("\n--- INICIANDO MUESTREO CONTINUO ---");
        System.out.println("Introduce lecturas (Negativo para salir, 0 para saltar ciclo): ");

        // Bucle infinito que rompemos manualmente con break cuando se requiera
        while (true) {
            lectura = leerLecturaSegura(teclado, "Introduce lectura μT: ");

            // Si es negativo, rompemos el bucle y salimos
            if (lectura < 0) {
                System.out.println("Muestreo interrumpido por el operador.");
                break;
            }

            // Si es cero, saltamos esta iteración actual con continue
            if (lectura == 0) {
                System.out.println("Lectura en 0. Saltando este ciclo...");
                continue;
            }

            // Si supera el límite permitido, avisamos al operador
            if (lectura > limite) {
                System.out.println(" ¡ADVERTENCIA! Umbral de riesgo superado.");
            }

            // Acumulamos valores para calcular la media al final
            suma += lectura;
            contador++;
        }

        // Si se registraron datos válidos, calculamos y mostramos la media a 2 decimales
        if (contador > 0) {
            double media = suma / contador;
            System.out.printf("\nMedia de las lecturas válidas: %.2f μT\n", media);
        } else {
            System.out.println("\nNo se registraron lecturas válidas.");
        }
    }
    
}