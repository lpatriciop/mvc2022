/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

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
    public boolean setPersona(){
    String sql;//INSERT INTO persona(idpersona)VALUES('010352888')
    sql="INSERT INTO persona (idpersona,nombres,apellidos) "
            + "VALUES ('"+ getIdPersona() +"'"
            + ",'"+ getNombre()+"'"
            + ",'"+ getApellido() +"')";
           return mpgc.accion(sql); //EJECUTAMOS EL INSERT
    }
    public List<Persona> getPersonas(){
    
       List<Persona> listaPersonas= new ArrayList<Persona>();
       
        String sql="select * from persona";
        ResultSet rs= mpgc.consulta(sql);
        byte[] bytea;
        try {
            while(rs.next()){
                Persona persona=new Persona();
                persona.setIdPersona(rs.getString("idpersona"));
                persona.setNombre(rs.getString("nombres"));
                persona.setApellido(rs.getString("apellidos"));
                
                bytea=rs.getBytes("foto");
                
                try {
                    if(bytea!=null) persona.setFoto(getImage(bytea));
                } catch (IOException ex) {
                    Logger.getLogger(ModeloPersona.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    
    private Image getImage(byte [] bytes) throws IOException{
    
        ByteArrayInputStream bais= new ByteArrayInputStream(bytes);
        Iterator it = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader imageReader=(ImageReader) it.next();
        Object source=bais;
        ImageInputStream iis= ImageIO.createImageInputStream(source);
        imageReader.setInput(iis,true);
        ImageReadParam param=imageReader.getDefaultReadParam();
        param.setSourceSubsampling(1, 1, 0, 0);
        
        return imageReader.read(0,param);
    }
    
}

































































