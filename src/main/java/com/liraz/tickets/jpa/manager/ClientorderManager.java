/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Clientorder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class ClientorderManager extends AbstractManager<Clientorder> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public ClientorderManager(){
        super(Clientorder.class);
    }
    
    public List<Clientorder> getClientorderByEmail(String email){
        return getEntityManager().createNamedQuery("Clientorder.findByClientorderemail").setParameter("clientorderemail",email).getResultList();
    }
    
    public Integer createAndGetID(Clientorder order){
        getEntityManager().persist(order);
        getEntityManager().flush();
        return order.getOrderid();
    }
    
    
}

