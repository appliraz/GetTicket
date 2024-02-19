/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Review;
import com.liraz.tickets.jpa.Run;
import com.liraz.tickets.jpa.Show;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class ReviewManager extends AbstractManager<Review> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public ReviewManager(){
        super(Review.class);
    }
    
    public List<Review> getReviewsByShow(Show s){
        List<Review> results = getEntityManager().createNamedQuery("Review.findByShowid").
                setParameter("showid", s).getResultList();
        return results;
    }
    
}

