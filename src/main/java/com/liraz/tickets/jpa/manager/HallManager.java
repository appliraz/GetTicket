/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Hall;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class HallManager extends AbstractManager<Hall> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public HallManager(){
        super(Hall.class);
    }
    
    
}

