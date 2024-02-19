/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper.converter;

import com.liraz.tickets.jpa.Venue;
import com.liraz.tickets.jpa.manager.VenueManager;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named ("venueSelectConverter")
@RequestScoped

public class VenueSelectConverter implements Converter {

    @EJB
    private VenueManager vm;
    
    private VenueManager getVenueManager(){
        return vm;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getAsVenue(value);
    }
    
    public Venue getAsVenue(String vname){
        if(vname.isEmpty())
            return new Venue();
        Venue v = getVenueManager().getVenueByName(vname);
        return v;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o ==null)
            return "";
        if(o instanceof Venue)
            return ((Venue)o).getVenuename();
        else
            return "";
    }
    
    public Integer getSelectedVenueID(String venue){
        String s = venue.substring(0, venue.indexOf(" "));
        return Integer.parseInt(s);
    }
    
   
}
