/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.manager.ShowManager;
import com.liraz.tickets.jsf.helper.FileUploadController;
import com.liraz.tickets.jsf.helper.imageHelper;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author User
 */
@Named("randomController")
@ViewScoped
public class RandomController implements Serializable{
    
    public RandomController(){}
    
    @EJB
    private ShowManager sm;
    @Inject
    private FileUploadController fuc;
    
    private Show randomshow;
    private List<Show> shows;
    private Random random = new Random();
    
    @PostConstruct
    public void init(){
        initShows();
        rollRandomShow();
    }
    
    public void initShows(){
        this.shows = getShowManager().getAll();
    }
    
    private ShowManager getShowManager(){
        return sm;
    }
    
    public List<Show> getShows(){
        return shows;
    }
    
    private FileUploadController getFileUploadController(){
        return fuc;
    }
    /*
    public StreamedContent getPoster(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE)
            return new DefaultStreamedContent();
        return getFileUploadController().getImg(getShow().getShowposter());
    }*/
    
    public int getRandom(){
        return random.nextInt(getShows().size());
    }
    
    public void rollRandomShow(){
        int i;
        i = getRandom();
        while(i>(getShows().size()-1))
            i=getRandom();
        
        setShow(getShows().get(i));
    }
    
    public Show getShow(){
        return randomshow;
    }
    
    public void setShow(Show s){
        this.randomshow = s;
    }
    
    public String redirectShowPage(){
        return "/client/showpage.xhtml?faces-redirect=true&selectedshow=" +
                String.valueOf(getShow().getShowid()) + "&i=3";
    }
    
    
}
