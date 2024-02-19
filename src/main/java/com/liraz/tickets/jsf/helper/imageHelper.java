/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.GraphicImageBean;
import org.primefaces.model.StreamedContent;


/**
 *
 * This bean is just a cleaner look for FileUploadController -
 * for when you need only the getImg Method
 */
@Named
@ApplicationScoped
public class imageHelper implements Serializable {
    
    @Inject
    private FileUploadController fuc;
    
    public StreamedContent getImg(){
        
        return fuc.getImg();
    }
    /*
    public StreamedContent getImg(String imgname){
        
        return fuc.getImg(imgname);
    }   */
   
    
}
