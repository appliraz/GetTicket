/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;

import com.liraz.tickets.jpa.Client;
import com.liraz.tickets.jpa.Clientorder;
import com.liraz.tickets.jpa.Payment;
import com.liraz.tickets.jpa.Run;
import com.liraz.tickets.jpa.Runseat;
import com.liraz.tickets.jpa.Selection;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named ("receiptHandler")
@SessionScoped
public class ReceiptSessionBean implements Serializable {
    
    private Run run;
    private Client client;
    private Clientorder order;
    private Payment payment;
    private Selection selection;
    private List<Runseat> selectedseats;
    
    public ReceiptSessionBean(){}
    
    public Run getRun(){
        return run;
    }
    
    public void setRun(Run r){
        this.run = r;
    }
    
    public Client getClient(){
        return client;
    }
    
    public void setClient(Client c){
        this.client= c;
    }
    
    public Clientorder getOrder(){
        return order;
    }
    
    public void setOrder(Clientorder co){
        this.order = co;
    }
    
    public Payment getPayment(){
        return payment;
    }
    
    public void setPayment(Payment p){
        this.payment = p;
    }
    
    public List<Runseat> getSelectedSeats(){
        return selectedseats;
    }
    
    public void setSelectedSeats(List<Runseat> sl){
        this.selectedseats = sl;
    }
    
}
