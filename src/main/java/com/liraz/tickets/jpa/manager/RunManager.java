/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Hall;
import com.liraz.tickets.jpa.Run;
import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.Venue;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class RunManager extends AbstractManager<Run> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
       
    public RunManager(){
        super(Run.class);
    }
    
    public List<Run> getActiveRunsByShow(Show s){
        List<Run> results = getEntityManager().createNamedQuery("Run.findActiveByShowid").
                setParameter("showid", s).getResultList();
        return results;
    }
    
    public List<Show> getDistinctActiveShows(){
        List<Show> results = getEntityManager().createNamedQuery("Run.findDistinctShows")
                .getResultList();
        return results;
    }
    
    public List<Venue> getVenuesByType(String type){
        List<Venue> results = getEntityManager().createNamedQuery("Run.findVenuesByType")
                .setParameter("showtype",type).getResultList();
        return results;
    }
    
    public List<Show> getShowsFilterByTypeAndVenue(String type, Venue vid){
        List<Show> results = getEntityManager().createNamedQuery("Run.findShowsByVenueAndType")
                .setParameter("showtype", type).setParameter("venueid",vid).getResultList();
        return results;
    }
    
    public List<Date> getDatesByShowVenue(Show show, Venue venue){
        List<Date> results = getEntityManager().createNamedQuery("Run.findDateByShowVenue")
                .setParameter("showid", show).setParameter("venueid",venue).getResultList();
        return results;
    }
    
    public List<Run> getRunsByShowVenueDate(Show show, Venue venue, Date date){
        List<Run> results = getEntityManager().createNamedQuery("Run.findByShowVenueDate")
                .setParameter("showid", show).setParameter("venueid",venue).setParameter("rundate",date).getResultList();
        return results;
    }
    
}

