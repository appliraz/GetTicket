/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper.converter;

import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.manager.ShowManager;
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
@Named ("showConverter")
@RequestScoped

public class ShowConverter implements Converter {

    @EJB
    private ShowManager sm;
    
    private ShowManager getShowManager(){
        return sm;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getAsShow(value);
    }
    
    public Show getAsShow(String sid){
        if(sid.isEmpty())
            return new Show();
        Integer showid = Integer.parseInt(sid);
        Show s = getShowManager().find(showid);
        return s;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o instanceof String)
            return o.toString();
        return String.valueOf(((Show)o).getShowid());
    }
    
}
