/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.commons.io.input.BufferedFileChannelInputStream;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultStreamedContent.Builder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;


/**
 *
 * @author User
 */

@Named("fileUploadController")
@ApplicationScoped

public class FileUploadController implements Serializable{
    private UploadedFile file;
    private String path = "D:/Program Files/NetBeans-14/NetBeansProjects/Tickets/src/main/resources/images/";
    
    public FileUploadController(){}
    /*getters and setters */
    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public String getUploadedFileName(){
        return file.getFileName();
    }
    
    public String getFilePath(){
        String p = getPath() + getFile().getFileName();
        return p;
    }
        
         
    
    /*file controller main methods*/
    public void upload(FileUploadEvent event) {
        file = event.getFile();
        try{
            if (file != null) {
                File save = new File(getFilePath());
                Files.write(save.toPath(), file.getContent());
                message("הקובץ הועלה בהצלחה");
                ajaxUpdate();
            }
            else{
                message("ההעלאה נכשלה - הקובץ ריק");
                ajaxUpdate();                
                }
           
        }
        catch(Exception e){
            message(e.toString());
            ajaxUpdate();
        }
    }
    
    public StreamedContent getImg(){
        String fname = getPosternameParam();
        String fpath = getPath();
        File f = new File(fpath+fname);
        BufferedImage bImage;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bImage = ImageIO.read(f);
            ImageIO.write(bImage, "jpg", bos );
        } catch (Exception e) {
            message(e.toString());
            ajaxUpdate();
        }  
        byte [] data = bos.toByteArray();
        StreamedContent streamedContent = DefaultStreamedContent.builder()
                    .name(fname)
                    .contentType("image/jpeg")
                    .stream(() -> new ByteArrayInputStream(data)).build();
        return streamedContent;
    }
    
    public byte[] getImg(String imgname){
        String fname = imgname;
        String fpath = getPath();
        File f = new File(fpath+fname);
        BufferedImage bImage;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bImage = ImageIO.read(f);
            ImageIO.write(bImage, "jpg", bos );
        } catch (Exception e) {
            message(e.toString());
            ajaxUpdate();
        }  
        byte [] data = bos.toByteArray();
        return data;/*
        StreamedContent streamedContent = DefaultStreamedContent.builder()
                    .name(fname)
                    .contentType("image/jpeg")
                    .stream(() -> new ByteArrayInputStream(data)).build();
        return streamedContent;*/
    }
        
    
    /*class utilities */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }

    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages");
    }
    
    public String getPosternameParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String posternameParam = (String) myRequest.getParameter("postername");
        return posternameParam;
    }

}
