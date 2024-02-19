/* * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Run;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.liraz.tickets.jpa.Runseat;
import java.util.List;


/**
 *
 * @author User
 */
@Stateless
public class RunseatManager extends AbstractManager<Runseat> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public RunseatManager(){
        super(Runseat.class);
    }
    
    public List<Runseat> getSeatsByRun(Run run){
        return getEntityManager().createNamedQuery("Runseat.findByRunid")
                .setParameter("runid", run).getResultList();
    }
    
    public List<Runseat> getAvailableSeatsByRun(Run run){
        return getEntityManager().createNamedQuery("Runseat.findAvailableByRunid")
                .setParameter("runid", run).getResultList();
    } 
    
    
}

