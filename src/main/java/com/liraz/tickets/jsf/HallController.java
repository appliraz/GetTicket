/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Hall;
import com.liraz.tickets.jpa.Seat;
import com.liraz.tickets.jpa.Venue;
import com.liraz.tickets.jpa.manager.HallManager;
import com.liraz.tickets.jpa.manager.SeatManager;
import com.liraz.tickets.jpa.manager.VenueManager;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */

@Named("hallController")
@SessionScoped

public class HallController implements Serializable {
    private Hall current;
    private List<Hall> halls = null;
    private Hall selectedHall;
    private List<Hall> selectedHalls;
    @EJB
    private HallManager hm;
    @EJB
    private SeatManager sm;
    
    public HallController(){
    }
    
    /**getters and setters*/
    private HallManager getHallManager(){
        return hm;
    }
    
    
    public Hall getCurrent(){
        if(current==null){
            current = new Hall();
        }
        return current;
    }
    
    public List getHalls(){
        halls = getHallManager().findAll();
        return halls;
    }
    
    public Hall getSelectedHall(){
        return selectedHall;
    }
    
    public void setSelectedHall(Hall h){
        this.selectedHall = h;
    }
    
    public List<Hall> getSelectedHalls(){
        return selectedHalls;
    }
    
    public void setSelectedHalls(List<Hall> sh){
        this.selectedHalls = sh;
    }
    
    public boolean hasSelectedHalls(){
        ajaxUpdate();
        return this.selectedHalls != null && !this.selectedHalls.isEmpty();
    }
    
    private SeatManager getSeatManager(){
        return sm;
    }
   
    
    /**working with HallManager - create, read, update, delete**/

    
    public void save(){
        Hall sh = getSelectedHall();
        if(sh!=null)
        {
            try{
                getHallManager().create(sh);
                message("האולם נוסף בהצלחה");
            }
            catch(Exception e)
            {
                message(e.toString());
            }
        }
        else
            message("לא נבחר אולם");
        PrimeFaces.current().executeScript("PF('manageHallDialog').hide()");
        ajaxUpdate();
    }
    
    public void update(){
        getHallManager().edit(getSelectedHall());
        message("המתחם נערך");
        PrimeFaces.current().executeScript("PF('manageHallDialog').hide()");
        ajaxUpdate();
    }
    
    public void deleteHall(){
        getHallManager().remove(getSelectedHall());
        setSelectedHall(null);
        message("המתחם הוסר");
        ajaxUpdate();
    }
    
    
    public void deleteSelectedHalls(){
        List<Hall> hlist = getSelectedHalls();
        for(Hall h:hlist){
            getHallManager().remove(h);
        }
        this.selectedHalls.clear();
        message("המתחמים הוסרו בהצלחה");
        //PrimeFaces.current().executeScript("PF('dtHalls').clearFilters()");
        ajaxUpdate();
    }
    

    /**view flow*/
    public void newHall(){
        this.selectedHall = new Hall();
    }
    
    
    public void hallDialogClosed(){
        if(getSelectedHall().getHallid()==null)
            save();
        else
            update();
        ajaxUpdate();
    }
    
    public String redirectSeats(){
        /*
        if(!(getSelectedHall()==null))
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedHall", getSelectedHall());
        */
        return "/manager/addseats.xhtml?faces-redirect=true&i=6";
    }
    
   
    
    /**class utility */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }
    
    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages", "form:dt-halls");
    }
    


}
