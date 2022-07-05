/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patricio
 */
public class ModeloPersona extends Persona{
    
    ModelPgConecction mpgc=new ModelPgConecction();

    public ModeloPersona() {
    }

    public ModeloPersona(String idPersona, String nombre, String apellido) {
        super(idPersona, nombre, apellido);
    }
    //MANEJAR DATOS DE LA BASE DE DATOS.
    
    public List<Persona> getPersonas(){
    
       List<Persona> listaPersonas= new ArrayList<Persona>();
       
        String sql="select * from personas";
        ResultSet rs= mpgc.consulta(sql);
        try {
            while(rs.next()){
                Persona persona=new Persona();
                persona.setIdPersona(rs.getString("idpsersona"));
                persona.setNombre(rs.getString("nombres"));
                persona.setApellido(rs.getString("apellidos"));
                listaPersonas.add(persona);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs.close();//cierro conexion BD
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
       return listaPersonas;
    }
    
}










































