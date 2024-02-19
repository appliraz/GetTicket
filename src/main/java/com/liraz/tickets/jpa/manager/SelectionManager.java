/* * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Clientorder;
import com.liraz.tickets.jpa.Selection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.liraz.tickets.jpa.Selection;
import java.util.List;


/**
 *
 * @author User
 */
@Stateless
public class SelectionManager extends AbstractManager<Selection> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public SelectionManager(){
        super(Selection.class);
    }
     
    public List<Selection> getSeatsByOrder(Clientorder order){
        return getEntityManager().createNamedQuery("Selection.findByOrderid")
                .setParameter("orderid", order).getResultList();
    } 
    
    public Selection getSelectionByID(String id){
        return (Selection)getEntityManager().createNamedQuery("Selection.findBySelectionid")
                .setParameter("selectionid", id).getSingleResult();
    }
    
    
}

