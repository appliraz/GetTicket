/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Payment;
import com.liraz.tickets.jpa.Seat;
import com.liraz.tickets.jpa.Venue;
import com.liraz.tickets.jpa.manager.PaymentManager;
import com.liraz.tickets.jpa.manager.SeatManager;
import com.liraz.tickets.jpa.manager.VenueManager;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */

@Named("paymentController")
@SessionScoped

public class PaymentController implements Serializable {
    private Payment current;
    private List<Payment> payments = null;
    private Payment selectedPayment;
    private List<Payment> selectedPayments;
    @EJB
    private PaymentManager pm;
    @EJB
    private SeatManager sm;
    
    public PaymentController(){
    }
    
    /**getters and setters*/
    private PaymentManager getPaymentManager(){
        return pm;
    }
    
    
    public Payment getCurrent(){
        if(current==null){
            current = new Payment();
        }
        return current;
    }
    
    public List getPayments(){
        payments = getPaymentManager().findAll();
        return payments;
    }
    
    public Payment getSelectedPayment(){
        return selectedPayment;
    }
    
    public void setSelectedPayment(Payment h){
        this.selectedPayment = h;
    }
    
    public List<Payment> getSelectedPayments(){
        return selectedPayments;
    }
    
    public void setSelectedPayments(List<Payment> sh){
        this.selectedPayments = sh;
    }
    
    public boolean hasSelectedPayments(){
        ajaxUpdate();
        return this.selectedPayments != null && !this.selectedPayments.isEmpty();
    }
    
    private SeatManager getSeatManager(){
        return sm;
    }
   
    
    /**working with PaymentManager - create, read, update, delete**/

    
    public void save(){
        Payment sh = getSelectedPayment();
        if(sh!=null)
        {
            try{
                getPaymentManager().create(sh);
                message("האולם נוסף בהצלחה");
            }
            catch(Exception e)
            {
                message(e.toString());
            }
        }
        else
            message("no selectedPayment");
        PrimeFaces.current().executeScript("PF('managePaymentDialog').hide()");
        ajaxUpdate();
    }
    
    public void update(){
        getPaymentManager().edit(getSelectedPayment());
        message("המתחם נערך");
        PrimeFaces.current().executeScript("PF('managePaymentDialog').hide()");
        ajaxUpdate();
    }
    
    public void deletePayment(){
        getPaymentManager().remove(getSelectedPayment());
        setSelectedPayment(null);
        message("המתחם הוסר");
        ajaxUpdate();
    }
    
    
    public void deleteSelectedPayments(){
        List<Payment> hlist = getSelectedPayments();
        for(Payment h:hlist){
            getPaymentManager().remove(h);
        }
        this.selectedPayments.clear();
        message("המתחמים הוסרו בהצלחה");
        //PrimeFaces.current().executeScript("PF('dtPayments').clearFilters()");
        ajaxUpdate();
    }
    

    /**view flow*/
    public void newPayment(){
        this.selectedPayment = new Payment();
    }
    
    
    public void paymentDialogClosed(){
        if(getSelectedPayment().getPaymentid()==null)
            save();
        else
            update();
        ajaxUpdate();
    }
    
    public String getDateFormat(Date d){
        if(d==null)
            return "";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(d);  
        return strDate;
    }
    
    public String getHourFormat(Date d){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String strDate = dateFormat.format(d);  
        return strDate;
    }
    
    
   
    
    /**class utility */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }
    
    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages", "form:dt-payments");
    }
    


}
