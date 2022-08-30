/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.Holder;
import model.ModelPgConecction;
import model.ModeloPersona;
import model.Persona;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.ViewPersonas;

/**
 *
 * @author Patricio
 */
public class ControllerPersona {
    private ModeloPersona modelo;
    private ViewPersonas vista;
    private JFileChooser jfc;
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
    vista.getBtnExaminar().addActionListener(l->examinaFoto());
    vista.getBtnImprimir().addActionListener(l->imprimeReporte());
    }
    private void imprimeReporte(){
    //Instanciamos la conexion proyecto
        ModelPgConecction con=new ModelPgConecction();
        
        try {
            JasperReport jr=(JasperReport)JRLoader.loadObject(getClass().getResource("/view/reportes/ReportePersonas.jasper"));
            Map<String,Object> parametros=new HashMap<String, Object>();
            parametros.put("titulo","REPORTE DE VENTAS DEL MES MAYO");
            parametros.put("sueldo", 300d);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros,con.getCon());//llena el reporte con datos.
            JasperViewer jv = new JasperViewer(jp,false);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void examinaFoto(){
        jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado=jfc.showOpenDialog(vista);
        if(estado==JFileChooser.APPROVE_OPTION){
            try {
                Image imagen=ImageIO.read(jfc.getSelectedFile()).getScaledInstance(
                        vista.getLblFoto().getWidth(),
                        vista.getLblFoto().getHeight(),
                        Image.SCALE_DEFAULT);
                
                Icon icono=new ImageIcon(imagen);
                vista.getLblFoto().setIcon(icono);
                vista.getLblFoto().updateUI();
            } catch (IOException ex) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                
            try {
                FileInputStream img=
                        new FileInputStream(jfc.getSelectedFile());
                int largo=(int)jfc.getSelectedFile().length();
                persona.setImageFile(img);
                persona.setLength(largo);
                
            } catch (IOException ex) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
                //...
                if(persona.setPersonaFoto()){//Grabamos.
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
        vista.getTblPersonas().setDefaultRenderer(Object.class, new ImagenTabla());
        vista.getTblPersonas().setRowHeight(50);
        DefaultTableModel estructuraTabla;
        estructuraTabla=(DefaultTableModel)
                vista.getTblPersonas().getModel();
        estructuraTabla.setNumRows(0);
        List<Persona> listap= modelo.getPersonas();
        Holder<Integer> i = new Holder<>(0);
        listap.stream().forEach(persona->{
            estructuraTabla.addRow(new Object[4]);
                vista.getTblPersonas()
                        .setValueAt(persona.getIdPersona(),
                                i.value,0);
                vista.getTblPersonas()
                        .setValueAt(persona.getNombre(),
                                i.value,1);
                vista.getTblPersonas()
                        .setValueAt(persona.getApellido(),
                                i.value,2);
                Image foto= persona.getFoto();
                if(foto!=null){
                    foto=foto.getScaledInstance(50, 75, Image.SCALE_SMOOTH);
                    ImageIcon icono=new ImageIcon(foto);
                    DefaultTableCellRenderer dtcr=new DefaultTableCellRenderer();
                    dtcr.setIcon(icono);
                    vista.getTblPersonas().setValueAt(new JLabel(icono), i.value,3);
                }else{//no venga foto.
                vista.getTblPersonas().setValueAt(null, i.value,3);
                }
        i.value++;
        });
      
    }
}

































