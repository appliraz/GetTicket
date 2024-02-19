/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Show;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author User
 */
@Stateless
public class ShowManager extends AbstractManager<Show> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public ShowManager(){
        super(Show.class);
    }
    
    public Show getShowByName(String name){
        return (Show) getEntityManager().createNamedQuery("Show.findByShowtitle")
                .setParameter("showtitle", name).getSingleResult();
    }
    
    public Show getShowByID(String sid){
        return (Show) getEntityManager().createNamedQuery("Show.findByShowid")
                .setParameter("showid", Integer.parseInt(sid)).getSingleResult();
    }
    
    public List<Show> getAll(){
          return getEntityManager().createNamedQuery("Show.findAll")
                .getResultList();
    }
    
}

