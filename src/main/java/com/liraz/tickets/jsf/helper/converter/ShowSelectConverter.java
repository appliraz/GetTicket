/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper.converter;

import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.Venue;
import com.liraz.tickets.jpa.manager.ShowManager;
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
@Named ("showSelectConverter")
@RequestScoped

public class ShowSelectConverter implements Converter {

    @EJB
    private ShowManager sm;
    
    private ShowManager getShowManager(){
        return sm;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getAsShow(value);
    }
    
    public Show getAsShow(String sname){
        if(sname.isEmpty())
            return new Show();
        Show s = getShowManager().getShowByName(sname);
        return s;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o ==null)
            return "";
        if(o instanceof Show)
            return ((Show)o).getShowtitle();
        else
            return "";
    }
    
    public Integer getSelectedVenueID(String venue){
        String s = venue.substring(0, venue.indexOf(" "));
        return Integer.parseInt(s);
    }
}
