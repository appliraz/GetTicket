/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper.converter;

import com.liraz.tickets.jpa.Hall;
import com.liraz.tickets.jpa.manager.HallManager;
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
@Named ("hallConverter")
@RequestScoped

public class HallConverter implements Converter {

    @EJB
    private HallManager sm;
    
    private HallManager getHallManager(){
        return sm;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getAsHall(value);
    }
    
    public Hall getAsHall(String sid){
        if(sid.isEmpty())
            return new Hall();
        Integer hallid = Integer.parseInt(sid);
        Hall s = getHallManager().find(hallid);
        return s;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o instanceof String)
            return o.toString();
        return String.valueOf(((Hall)o).getHallid());
    }
    
}
