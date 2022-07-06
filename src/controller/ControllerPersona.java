/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.Holder;
import model.ModeloPersona;
import model.Persona;
import view.ViewPersonas;

/**
 *
 * @author Patricio
 */
public class ControllerPersona {
    private ModeloPersona modelo;
    private ViewPersonas vista;

    public ControllerPersona(ModeloPersona modelo, ViewPersonas vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
    }
    
    public void iniciaControl(){
    vista.getBtnBuscar().addActionListener(l->cargaDatos());
    }
    
    private void cargaDatos(){
        DefaultTableModel estructuraTabla;
        estructuraTabla=(DefaultTableModel)
                vista.getTblPersonas().getModel();
        estructuraTabla.setNumRows(0);
        List<Persona> listap= modelo.getPersonas();
        Holder<Integer> i = new Holder<>(0);
        listap.stream().forEach(persona->{
            estructuraTabla.addRow(new Object[3]);
                vista.getTblPersonas()
                        .setValueAt(persona.getIdPersona(),
                                i.value,0);
                vista.getTblPersonas()
                        .setValueAt(persona.getNombre(),
                                i.value,1);
                vista.getTblPersonas()
                        .setValueAt(persona.getApellido(),
                                i.value,2);
        i.value++;
        });
      
    }
}

































