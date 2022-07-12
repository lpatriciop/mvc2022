/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.swing.JOptionPane;
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
    vista.getBtnCrear().addActionListener(l->abrirDialogo(1));
    vista.getBtnEditar().addActionListener(l->abrirDialogo(2));
    vista.getBtnAceptar().addActionListener(l->crearEditarPersona());
    }
    private void abrirDialogo( int op){
        String titulo;
        if (op==1){
            titulo="Crear Persona";
            vista.getDlgPersona().setName("C");
            vista.getTxtIdPersona().setEnabled(true);
        }else{
            titulo="Editar Persona";
            vista.getDlgPersona().setName("E");
            vista.getTxtIdPersona().setEnabled(false);
        }
        vista.getDlgPersona().setTitle(titulo);
        vista.getDlgPersona().setSize(600,300);
        vista.getDlgPersona().setLocationRelativeTo(vista);
        vista.getDlgPersona().setVisible(true);
    }
    private void crearEditarPersona(){
        if(vista.getDlgPersona().getName().contentEquals("C")){
                //INSERT
                String identificacion=vista.getTxtIdPersona().getText();
                String nombres=vista.getTxtNombre().getText();
                String apellidos=vista.getTxtApellidos().getText();
                
                ModeloPersona persona=new ModeloPersona();
                persona.setIdPersona(identificacion);
                persona.setNombre(nombres);
                persona.setApellido(apellidos);
                //...
                if(persona.setPersona()){//Grabamos.
                    JOptionPane.showMessageDialog(vista
                            ,"Persona creada satisfactoriamente." );
                }else{
                    JOptionPane.showMessageDialog(vista
                            ,"No se pudo crear persona" );
                }
        }else{
                //UPDATE
        }
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

































