/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectohawkins.java;

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
        
        
        System.out.println("\n HAWKINS NATIONAL LABORATOR ");
        System.out.println("Ingrese su nombre");
        operadorActual = scanner.nextLine();
        
        
        
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
                    System.out.println("El muestreo es:");
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
    
    public static double leerLecturaSegura(Scanner scanner, String mensaje){
        System.out.print(mensaje);
        double lectura = scanner.nextDouble();
        return lectura;
    }
    
    public static int evaluarNivelRiesgo(double evaluar){
        System.out.print(evaluar);
        int evaluar = scanner.nextInt();
        return 0;
    }
    
    public static int calcularPorcentajeTruncado(Scanner scanner, int mensaje){
        System.out.print(mensaje);
        int calcular = scanner.nextInt();
        return calcular;
    }
    
    public static boolean ejecutarProtocoloAccion(Scanner scanner, int mensaje){
        System.out.print(mensaje);
        boolean ejecutar = scanner.nextBoolean();
        return ejecutar;
    }
}
