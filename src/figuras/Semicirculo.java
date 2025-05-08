/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package figuras;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author ebenezer
 */
public class Semicirculo extends Figura {
    private double radio;

    
    public Semicirculo(double radio) {
        this.radio = radio;
    }

    
    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    
    public double calcularArea() {
        return (Math.PI * radio * radio) / 2;
    }

    
    public double calcularPerimetro() {
                return Math.PI * radio;
    }

    public void mostrarInfo() {
        System.out.println("Semi-Círculo con radio: " + radio);
        System.out.println("Área: " + calcularArea());
        System.out.println("Perímetro (curva): " + calcularPerimetro());
    }

    @Override
    public void dibujar(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar(Point puntoActual) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}
    
