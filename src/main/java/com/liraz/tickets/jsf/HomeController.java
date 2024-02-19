/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Run;
import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.Venue;
import com.liraz.tickets.jpa.manager.RunManager;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */
@Named("homeController")
@ViewScoped
public class HomeController implements Serializable {
    
    @EJB
    private RunManager rm;
    private String selectedtype = new String();
    private Venue selectedvenue = new Venue();
    private Show selectedshow = new Show();
    private Date selecteddate;
    private boolean filterrequest = false;
    private List<Run> runs;
    private Run selectedrun;
    private String showid;
    
    /**getters and setters **/
    public RunManager getRunManager(){
        return rm;
    }
    
    public String getSelectedType(){
        return selectedtype;
    }
    
    public void setSelectedType(String type){
        this.selectedtype = type;
    }
    
    public Venue getSelectedVenue(){
        return selectedvenue;
    }
    
    public void setSelectedVenue(Venue venue){
        this.selectedvenue = venue;
    }
    
    public Show getSelectedShow(){
        return selectedshow;
    }
    
    public void setSelectedShow(Show show){
        this.selectedshow = show;
    }
    
    public Date getSelectedDate(){
        return selecteddate;
    }
    
    public void setSelectedDate(Date date){
        this.selecteddate = date;
    }
    

    
    public boolean getFilterRequest(){
        return filterrequest;
    }
    
    public void requestFilter(){
        this.filterrequest = true;
    }
    
    public Run getSelectedRun(){
        return selectedrun;
    }
    
    public void setSelectedRun(Run r){
        this.selectedrun = r;
    }
    
    public String getShowid(){
        return showid;
    }
    
    public void setShowid(String sid){
        this.showid = sid;
    }
    
    
    
    /**working with managers **/
    
    public List<Show> getActiveShows(){
        List<Show> ls = new ArrayList<Show>();
        ls = getRunManager().getDistinctActiveShows();
        return ls;
    }
    
    public List<Venue> getVenuesFilterByType(String type){
        List<Venue> lv = new ArrayList<Venue>();
        lv = getRunManager().getVenuesByType(type);
        return lv;
    }
    
    public List<Show> getShowsFilterByTypeAndVenue(String type, Venue venue){
        List<Show> ls = new ArrayList<Show>();
        ls = getRunManager().getShowsFilterByTypeAndVenue(type, venue);
        return ls;
    }
    
    public List<Date> getDatesFilterByVenueShow(Venue venue, Show show){
        List<Date> ld = new ArrayList<Date>();
        ld = getRunManager().getDatesByShowVenue(show, venue);
        return ld;
    }
    
    /**view flow **/
    
    public String orderRedirect(){
        if(getSelectedRun()==null)
            return "";
        else
            return ("/client/orderwizard.xhtml?faces-redirect=true&i=3&runId="+String.valueOf(getSelectedRun().getRunid()));
    }
    
    public String showRedirect(){
        return "/client/showpage.xhtml?faces-redirect=true&i=3&selectedshow="+getShowid();
    }
    
    public List<Venue> getVenuesSelectList(){
        List<Venue> s = new ArrayList<Venue>();
        if(isTypeSelected()){
            s = getVenuesFilterByType(getSelectedType());
        }
        return s;
    }
    
    public List<Show> getShowsSelectList(){      
        List<Show> s = new ArrayList<Show>();
        if(isVenueSelected() && isTypeSelected()){
            s = getShowsFilterByTypeAndVenue(getSelectedType(),getSelectedVenue());
        }
        return s;
        
    }
    
    public List<Date> getDatesSelectList(){
        List<Date> d = new ArrayList<Date>();
        if(isVenueSelected() && isShowSelected()){
            d = getDatesFilterByVenueShow(getSelectedVenue(), getSelectedShow());
        }
        return d;
    }
    
    public List<Run> getRuns(){
        if(isVenueSelected() && isShowSelected() && isDateSelected())
            runs = getRunManager().getRunsByShowVenueDate(getSelectedShow(), getSelectedVenue(), getSelectedDate());
        return runs;
    }   
    
    public boolean isFilterReady(){
        return (isDateSelected());
    }
    
    public boolean isFilterRequested(){
        return getFilterRequest();
    }
    
    public String getDateFormat(Date d){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(d);  
            return strDate;
    }
    
    public String getHourFormat(Date d){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String strDate = dateFormat.format(d);  
            return strDate;
    }
    
    

    
    /**class utility */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }
    
    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages");
    }
    
    public List<String> listToString(List<? extends Object> objectlist){
        List<String> stringlist = new ArrayList<String>();
        for(Object o:objectlist){
            stringlist.add(o.toString());
        }
        return stringlist;
    }

    
    public boolean isTypeSelected(){
        return !(getSelectedType().isEmpty());
    }
    
    public boolean isVenueSelected(){
        return getSelectedVenue()!=null;
    }
    
    public boolean isShowSelected(){
        return getSelectedShow()!=null;
    }
    
    public boolean isDateSelected(){
        return getSelectedDate()!=null;
    }
    
    
}
