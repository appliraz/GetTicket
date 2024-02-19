/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;

import com.liraz.tickets.jpa.Hall;
import com.liraz.tickets.jpa.manager.HallManager;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * This helper bean is made to assist get the current Halls in database
 * Usage is in SelectOneMenu - where halls are foreign keys
 */
@Named ("hallSelector")
@ApplicationScoped

public class HallSelectorHelper {
    
    private Hall selectedHall;
    private int selectedHallid;
    private List<Hall> halls;
    @Inject
    private HallManager hm;
    
    public Hall getSelectedHall(){
        return selectedHall;
    }
    
    public void setSelectedHall(Hall h){
        this.selectedHall = h;
    }
    
    public HallManager getHallManager(){
        return hm;
    }
    
    public List<Hall> getHalls(){
        halls = getHallManager().findAll();
        return halls;
    }
    
}
