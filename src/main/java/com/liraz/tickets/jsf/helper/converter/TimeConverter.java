/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import sun.util.calendar.CalendarDate;

/**
 *
 * @author User
 */
@Named ("timeConverter")
@RequestScoped

public class TimeConverter implements Converter {

   
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getAsDate(value);
    }
    
    public Date getAsDate(String date){
        Date d;
        if(date.isEmpty())
            return new Date();
        try{
            d = new Date(date);
        }
        catch(Exception e){
            d = new Date();
        }
        return d;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o instanceof String)
            return o.toString();
        Date date = (Date) o;  
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String strDate = dateFormat.format(date);  
        return strDate;
    }
    

    
}
