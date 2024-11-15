/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package menucito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menucito {
   private static final int MAX_COLUMNAS = 3; // numero de columnas
    private static final int MAX_FILAS = 10;   // numero de filas 
    private static final int MAX_ELEMENTOS = MAX_COLUMNAS * MAX_FILAS; // maximo de elementos permitidos
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       JPanel panel = new JPanel(new BorderLayout());

        
        JPanel panelEntrada = new JPanel();
        JTextField campoNombre = new JTextField(10); 
        JButton botonAgregar = new JButton("Agregar"); 

       
        panelEntrada.add(new JLabel("Nombre:"));
        panelEntrada.add(campoNombre);
        panelEntrada.add(botonAgregar);

        
        DefaultTableModel modeloTabla = new DefaultTableModel(MAX_FILAS, MAX_COLUMNAS);
        JTable tabla = new JTable(modeloTabla) {
            public boolean isCellEditable(int fila, int columna) {
                return false; 
            }
        };

        // eliminar con un click el cuadro seleccionado
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                int columna = tabla.getSelectedColumn();
                Object nombre = tabla.getValueAt(fila, columna);

                if (nombre != null) {
                    eliminarNombreDeTabla(modeloTabla, fila, columna); 
                    JOptionPane.showMessageDialog(null, "Nombre eliminado: " + nombre);
                }
            }
        });

        // Acción del botón para agregar un nombre
        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (contarElementos(modeloTabla) < MAX_ELEMENTOS) {
                        agregarNombreATabla(nombre, modeloTabla); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Solo se admiten " + MAX_ELEMENTOS + " elementos.");
                    }
                }
                campoNombre.setText("");
            }
        });

        
        panel.add(panelEntrada, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        
        JOptionPane.showMessageDialog(null, panel, "Lista de Nombres", JOptionPane.PLAIN_MESSAGE);
    }

   
    private static int contarElementos(DefaultTableModel modelo) {
        int contador = 0;
        for (int fila = 0; fila < modelo.getRowCount(); fila++) {
            for (int columna = 0; columna < modelo.getColumnCount(); columna++) {
                if (modelo.getValueAt(fila, columna) != null) {
                    contador++;
                }
            }
        }
        return contador;
    }

   
    private static void agregarNombreATabla(String nombre, DefaultTableModel modelo) {
        for (int columna = MAX_COLUMNAS - 1; columna >= 0; columna--) {
            for (int fila = MAX_FILAS - 1; fila >= 0; fila--) {
                if (columna == 0 && fila == 0) {
                    modelo.setValueAt(nombre, fila, columna); 
                } else if (fila == 0) {
                    modelo.setValueAt(modelo.getValueAt(MAX_FILAS - 1, columna - 1), fila, columna); 
                } else {
                    modelo.setValueAt(modelo.getValueAt(fila - 1, columna), fila, columna); 
                }
            }
        }
    }

    //
    private static void eliminarNombreDeTabla(DefaultTableModel modelo, int fila, int columna) {
        for (int f = fila; f < MAX_FILAS - 1; f++) {
            modelo.setValueAt(modelo.getValueAt(f + 1, columna), f, columna);
        }
        modelo.setValueAt(null, MAX_FILAS - 1, columna);

        for (int c = columna + 1; c < MAX_COLUMNAS; c++) {
            modelo.setValueAt(modelo.getValueAt(0, c), MAX_FILAS - 1, c - 1);
            for (int f = 0; f < MAX_FILAS - 1; f++) {
                modelo.setValueAt(modelo.getValueAt(f + 1, c), f, c);
            }
            modelo.setValueAt(null, MAX_FILAS - 1, c);
        }
    }
}
