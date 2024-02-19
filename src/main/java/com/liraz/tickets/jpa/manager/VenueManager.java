/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Venue;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class VenueManager extends AbstractManager<Venue> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public VenueManager(){
        super(Venue.class);
    }
    
    public Venue getVenueByName(String name){
        return (Venue) getEntityManager().createNamedQuery("Venue.findByVenuename")
                .setParameter("venuename", name).getSingleResult();
    }
    
    public Venue getVenueByID(String vid){
        return (Venue) getEntityManager().createNamedQuery("Venue.findByVenueid")
                .setParameter("venueid", Integer.parseInt(vid)).getSingleResult();
    } 
    
}

