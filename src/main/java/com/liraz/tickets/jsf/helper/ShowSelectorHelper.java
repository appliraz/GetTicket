/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;

import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.manager.ShowManager;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This helper bean is made to assist get the current Shows in database
 * Usage is in SelectOneMenu - where shows are foreign keys
 */
@Named ("showSelector")
@ApplicationScoped

public class ShowSelectorHelper {
    
    private Show selectedShow;
    private int selectedShowid;
    private List<Show> shows;
    @Inject
    private ShowManager sm;
    
    public Show getSelectedShow(){
        return selectedShow;
    }
    
    public void setSelectedShow(Show s){
        this.selectedShow = s;
    }
    
    public ShowManager getShowManager(){
        return sm;
    }
    
    public List<Show> getShows(){
        shows = getShowManager().findAll();
        return shows;
    }
    
}
