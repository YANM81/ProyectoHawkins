/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectohawkins;

import java.util.Scanner;

/**
 *
 * @author yezid
 */
public class ProyectoHawkins {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        final double MAX_ENERGIA = 200.0;
        final double LIMITE_RIESGO = 150.0;
        String operadorActual;
        double sensorMedido = 0;
        int codigoRiesgo;
        int porcentajeEstado;
        boolean portalAbierto;
        int opcionMenu = 0;
        
    
        System.out.println("HAWKINS NATIONAL LABORATORY - ACCESO RESTRINGIDO");
        System.out.print("\nIngrese su nombre:   ");
        operadorActual = scanner.nextLine();   
        
        mostrarEncabezado(operadorActual);    
        
        do{
            System.out.println("\n Hola " + operadorActual + " --- SISTEMA HAWKINS --- ");
            System.out.println("1. Inspeccionar Sensor Individual");
            System.out.println("2. Iniciar Muestreo Continuo");
            System.out.println("0. Salir de la Consola");
            opcionMenu = scanner.nextInt();
            
            switch (opcionMenu) {
                
                case 1:
                    sensorMedido = leerLecturaSegura(scanner, "Introduce lectura μT: ");
                    codigoRiesgo = evaluarNivelRiesgo(sensorMedido);
                    porcentajeEstado = calcularPorcentajeTruncado(sensorMedido, MAX_ENERGIA);
                    portalAbierto = ejecutarProtocoloAccion(codigoRiesgo);
                    System.out.println("\n--- Informe ---");
                    System.out.println("Capacidad" + porcentajeEstado + "%");
                    System.out.println("portalAbierto " + portalAbierto);
                    break;
                case 2:
                    simularMuestreoContinuo(scanner, LIMITE_RIESGO);
                    break;
                case 0:
                    System.out.println(" Saliendo, hasta  pronto ");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
                    
            }
   
        } while (opcionMenu != 0);
    }
    
    public static void mostrarEncabezado(String operador){
        System.out.println("\n\t==================================================\n");
        System.out.println("\tLABORATORIO NACIONAL DE HAWKINS - SISTEMA DE CONTROL\n");
        System.out.printf("\tOperario en turno:\t%s\n", operador);
        System.out.println("\t==================================================\n\n");
    }

    public static double leerLecturaSegura(Scanner scanner, String mensaje){
        double lectura = 0;
        boolean entradaValida = false;
        while(!entradaValida){    
            try{

               System.out.print(mensaje);
                lectura = scanner.nextDouble();
                entradaValida = true;
            }catch (java.util.InputMismatchException e){    
                System.out.println("Error: Debes introducir un valor numérico válido.");
                scanner.next();
            }
        }
        return lectura;
    }
    
    public static int evaluarNivelRiesgo(double microTeslas){
        String etiqueta = (microTeslas < 50.0) ? "Seguro" : (microTeslas <= 150.0) ? "Alerta" : "Crítico";
        
        if (microTeslas < 50.0) {
            return 1; 
        } else if (microTeslas <= 150.0) {
            return 2;
        } else {
            return 3; 
        }
    }
    
    
    public static int calcularPorcentajeTruncado(double lectura , double maxEnergia){
        int porcentaje = (int )((lectura / maxEnergia) * 100);
        return porcentaje;
    }
    
    public static boolean ejecutarProtocoloAccion(int codigoRiesgo){
        switch(codigoRiesgo){
            case 1:
                System.out.print("Modo Estable");
                return false;
            case 2:
                System.out.print("Anomalía Detectada");
                return false;
            case 3:
                System.out.print("¡ALERTA MÁXIMA!");
                return true;
            default:
                System.out.print("Código no válido");
                return false;
        }
    }
    
    public static void simularMuestreoContinuo(Scanner teclado, double limite) {
        double suma = 0;
        int contador = 0;
        double lectura;

        System.out.println("\n--- INICIANDO MUESTREO CONTINUO ---");
        System.out.println("Introduce lecturas (Negativo para salir, 0 para saltar ciclo):");

        while (true) {

            lectura = leerLecturaSegura(teclado, "Introduce lectura μT: ");


            if (lectura < 0) {
                System.out.println("Muestreo interrumpido por el operador.");
                break;
            }


            if (lectura == 0) {
                System.out.println("Lectura en 0. Saltando este ciclo...");
                continue;
            }


            if (lectura > limite) {
                System.out.println(" ¡ADVERTENCIA! Umbral de riesgo superado.");
            }


            suma += lectura;
            contador++;
        }


        if (contador > 0) {
            double media = suma / contador;
            System.out.printf("\nMedia de las lecturas válidas: %.2f μT\n", media);
        } else {
            System.out.println("\nNo se registraron lecturas válidas.");
        }
    }
}
 