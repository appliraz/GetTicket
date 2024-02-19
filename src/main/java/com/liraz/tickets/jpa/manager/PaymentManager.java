/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;

import com.liraz.tickets.jpa.Payment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class PaymentManager extends AbstractManager<Payment> {
    
    @PersistenceContext(name = "com.liraz_Tickets_war_1.0-SNAPSHOTPU" )
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    public PaymentManager(){
        super(Payment.class);
    }
    
    public Payment getPaymentByID(Integer id){
        return (Payment) getEntityManager().createNamedQuery("Payment.findByPaymentid")
                .setParameter("paymentid", id).getSingleResult();
    }
    
    public Integer createAndGetID(Payment payment){
        getEntityManager().persist(payment);
        getEntityManager().flush();
        return payment.getPaymentid();
    }
    
    
}

