/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Seat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class SeatManager extends AbstractManager<Seat> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public SeatManager(){
        super(Seat.class);
    }
    
    public List<Seat> getListByRowAndHall(int row, int hallid){
        List<Seat> results = getEntityManager().createNamedQuery("Seat.findByRowAndHall")
                .setParameter("seatrow", row).setParameter("hallid", hallid).getResultList();
        return results;
    }
    
    public List<Seat> getListByHallid(int hallid){
        List<Seat> results = getEntityManager().createNamedQuery("Seat.findByHallid")
                .setParameter("hallid", hallid).getResultList();
        return results;
    }
    
    
}

