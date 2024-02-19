/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.manager.ShowManager;
import com.liraz.tickets.jsf.helper.FileUploadController;
import com.liraz.tickets.jsf.helper.imageHelper;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author User
 */

@Named("showController")
@ViewScoped

public class ShowController implements Serializable {
    private Show current;
    private List<Show> shows = null;
    private Show selectedShow;
    private List<Show> selectedShows;
    
    @EJB
    private ShowManager sm;
    
    @Inject
    private FileUploadController fuc;
    @Inject
    private imageHelper imageHandler;
    
    /**show page **/
    private String showid;
    
    
    public ShowController(){
    }
    
    /**getters and setters*/
    private ShowManager getShowManager(){
        return sm;
    }
    
    public Show getCurrent(){
        if(current==null){
            current = new Show();
        }
        return current;
    }
    
    public List getShows(){
        shows = getShowManager().findAll();
        return shows;
    }
    
    public Show getSelectedShow(){
        return selectedShow;
    }
    
    public void setSelectedShow(Show v){
        this.selectedShow = v;
    }
    
    public List<Show> getSelectedShows(){
        return selectedShows;
    }
    
    public void setSelectedShows(List<Show> sv){
        this.selectedShows = sv;
    }
    
    public String getShowid(){
        return showid;
    }
    
    public void setShowid(String sid){
        this.showid = sid;
    }
    
    
    public boolean hasSelectedShows(){
        ajaxUpdate();
        return this.selectedShows != null && !this.selectedShows.isEmpty();
    }
    
    /**show page specific **/
    public void initShowpage(){
        setSelectedShow(getShowManager().getShowByID(getShowid()));
    }
           
    
    /**working with ShowManager - create, read, update, delete**/
    
    
    public void save(){
        Show ss = getSelectedShow();
        if(ss!=null)
        {
            try{
                getShowManager().create(ss);
                message("המופע נוסף בהצלחה");
            }
            catch(Exception e)
            {
                message(e.toString());
            }
        }
        else
            message("לא נבחרה הופעה");
        PrimeFaces.current().executeScript("PF('manageShowDialog').hide()");
        ajaxUpdate();
    }
    
    public void update(){
        getShowManager().edit(getSelectedShow());
        message("המופע נערך");
        PrimeFaces.current().executeScript("PF('manageShowDialog').hide()");
        ajaxUpdate();
    }
    
    public void deleteShow(){
        getShowManager().remove(getSelectedShow());
        setSelectedShow(null);
        message("המופע הוסר");
        ajaxUpdate();
    }
    
    
    public void deleteSelectedShows(){
        List<Show> slist = getSelectedShows();
        for(Show s:slist){
            getShowManager().remove(s);
        }
        this.selectedShows.clear();
        message("המתחמים הוסרו בהצלחה");
        //PrimeFaces.current().executeScript("PF('dtShows').clearFilters()");
        ajaxUpdate();
    }
    
    public StreamedContent getImg()
    {
        return imageHandler.getImg();
    }

    /**view flow*/
    public void newShow(){
        this.selectedShow = new Show();
    }
    
    public void showDialogClosed(){
        if(getSelectedShow().getShowid()==null)
            save();
        else
            update();
        ajaxUpdate();
    }
    
    public void uploadImage(FileUploadEvent event){
        fuc.upload(event);
        UploadedFile uf = event.getFile();
        getSelectedShow().setShowposter(uf.getFileName());
    }
    
    public boolean hasPoster(){
        return !(getSelectedShow().getShowposter() == null);
    }
   
    
    /**class utility */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }
    
    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages", "form:dt-shows");
    }
    
    
    
    
    
    public String listToString(List<Show> sl){
        String list = "here: ";
        for (Show s : sl) {
            list.concat(" " + s.getShowtitle());
        }
        return list;
    }
    
    public String stringListPrint(List<String> ls){
        String list = "";
        list.concat(ls.get(0));
        list.concat(ls.get(1));
        list.concat(ls.get(2));
        return list;
    }


}
