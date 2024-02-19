/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Venue;
import com.liraz.tickets.jpa.manager.VenueManager;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;


@Named("venueController")
@ViewScoped

public class VenueController implements Serializable {
    private Venue current;
    private List<Venue> venues = null;
    private Venue selectedVenue;
    private List<Venue> selectedVenues;
    @EJB
    private VenueManager vm;
    
    public VenueController(){
    }
    
    /**getters and setters*/
    private VenueManager getVenueManager(){
        return vm;
    }   
    
    public Venue getCurrent(){
        if(current==null){
            current = new Venue();
        }
        return current;
    }
    
    public List getVenues(){
        venues = getVenueManager().findAll();
        return venues;
    }
    
    public Venue getSelectedVenue(){
        return selectedVenue;
    }
    
    public void setSelectedVenue(Venue v){
        this.selectedVenue = v;
    }
    
    public List<Venue> getSelectedVenues(){
        return selectedVenues;
    }
    
    public void setSelectedVenues(List<Venue> sv){
        this.selectedVenues = sv;
    }
    
    public boolean hasSelectedVenues(){
        ajaxUpdate();
        return this.selectedVenues != null && !this.selectedVenues.isEmpty();
    }
    
    
    /**working with VenueManager - create, read, update, delete**/

    
    public void save(){
        Venue sv = getSelectedVenue();
        if(sv!=null)
        {
            try{
                getVenueManager().create(sv);
                message("המתחם נוסף בהצלחה");
            }
            catch(Exception e)
            {
                message(e.toString());
            }
        }
        else
            message("no selectedVenue");
        PrimeFaces.current().executeScript("PF('manageVenueDialog').hide()");
        ajaxUpdate();
    }
    
    public void update(){
        getVenueManager().edit(getSelectedVenue());
        message("המתחם נערך");
        PrimeFaces.current().executeScript("PF('manageVenueDialog').hide()");
        ajaxUpdate();
    }
    
    public void deleteVenue(){
        getVenueManager().remove(getSelectedVenue());
        setSelectedVenue(null);
        message("המתחם הוסר");
        ajaxUpdate();
    }
    
    
    public void deleteSelectedVenues(){
        List<Venue> vlist = getSelectedVenues();
        for(Venue v:vlist){
            getVenueManager().remove(v);
        }
        this.selectedVenues.clear();
        message("המתחמים הוסרו בהצלחה");
        //PrimeFaces.current().executeScript("PF('dtVenues').clearFilters()");
        ajaxUpdate();
    }

    /**view flow*/
    public void newVenue(){
        this.selectedVenue = new Venue();
    }
    
    public void venueDialogClosed(){
        if(getSelectedVenue().getVenueid()==null)
            save();
        else
            update();
        ajaxUpdate();
    }
   
    
    /**class utility */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }
    
    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages", "form:dt-venues");
    }
    
    public String listToString(List<Venue> vl){
        String list = "here: ";
        for (Venue v : vl) {
            list.concat(" " + v.getVenuename());
        }
        return list;
    }


}
