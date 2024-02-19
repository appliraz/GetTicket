/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;

import com.liraz.tickets.jpa.Venue;
import com.liraz.tickets.jpa.manager.VenueManager;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * This helper bean is made to assist get the current Venues in database
 * Usage is in SelectOneMenu - where venues are foreign keys
 */
@Named
@ApplicationScoped

public class VenueSelectorHelper {
    
    private Venue selectedVenue;
    private int selectedVenueid;
    private List<Venue> venues;
    @Inject
    private VenueManager vm;
    
    public Venue getSelectedVenue(){
        return selectedVenue;
    }
    
    public void setSelectedVenue(Venue v){
        this.selectedVenue = v;
    }
    
    public VenueManager getVenueManager(){
        return vm;
    }
    
    public List<Venue> getVenues(){
        venues = getVenueManager().findAll();
        return venues;
    }
    
}
