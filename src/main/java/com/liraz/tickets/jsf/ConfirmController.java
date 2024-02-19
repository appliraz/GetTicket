/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Client;
import com.liraz.tickets.jpa.Clientorder;
import com.liraz.tickets.jpa.Payment;
import com.liraz.tickets.jpa.Run;
import com.liraz.tickets.jpa.Runseat;
import com.liraz.tickets.jpa.Selection;
import com.liraz.tickets.jsf.helper.ReceiptSessionBean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */
@Named("confirmController")
@ViewScoped
public class ConfirmController implements Serializable {
    
    
    @Inject
    private ReceiptSessionBean rsb;
    @Inject
    private ClientSessionController csc;
    
    /**params **/
    private String runid;
    private String clientid;
    private String orderid;
    
    private String paymentid;
    private String selectionid;
    
    private Run run;
    private Client client;
    private Clientorder order;
    private Payment payment;
    private Selection selection;
    
    private List<Runseat> selectedseats;


    public ConfirmController() {}
    
   
    
    /**initializations **/
    @PostConstruct
    public void init(){
        this.payment = rsb.getPayment();
        this.run = rsb.getRun();
        this.client = csc.getCurrentClient();
        this.order = rsb.getOrder();
        this.selectedseats = rsb.getSelectedSeats();
    
    }
    /*
    public void initAttributes(){
        this.order = payment.getOrderid();
        this.client = order.getClientid();
        this.selectedseats = order.getSelectionList();
        this.selection = selectedseats.get(0);
        this.run = selection.getRunseatid().getRunid();
    }*/
    
    

    
    
    /**getters and setters **/
    /**params **/
    public String getRunid(){
        return runid;
    }
    
    public void setRunid(String id){
        this.runid = id;
    }
    
    public String getClientid(){
        return clientid;
    }
    
    public void setClientid(String id){
        this.clientid = id;
    }
    
    public String getOrderid(){
        return orderid;
    }
    
    public void setOrderid(String id){
        this.orderid = id;
    }
    
    public String getPaymentid(){
        return paymentid;
    }
    
    public void setPaymentid(String id){
        this.paymentid = id;
    }
    
    /*managers */
    


    /**main objects **/
    
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
    
    
    /**more getters and setters **/
    public int getTotalSeats(){
        return selectedseats.size();
    }
    
    public boolean isSitting(){
        return getRun().getHallid().getHalltype().equals("ישיבה");
    }
    
    /*
    public String getCurrentDate(){
       return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
*/
    public String getRunDate(){
        Date d = getRun().getRundate();
        if(d==null)
          return "";
        return getDateFormat(d);
    }
    
    public String getRunHour(){
        Date d = getRun().getRundate();
        if(d==null)
          return "";
        return getHourFormat(d);
    }
    
    public String getDetails(){
        return "כרטיס זה לא למכירה חוזרת, למעשה כרטיס זה הוא כרטיס דמה, כרטיס בכאילו, כרטיס למטרת יופי,"
                + "אין לו שום ערך (למעט רגשי) ופראאיירר מי שמשלם עליו גרוש. נא להגיע למופע בזמן.";
    }
            
    public void getPaymentidParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        this.paymentid = (String) myRequest.getParameter("paymentid");
    }
    
    
  
    /**view flow **/
   

    

    public String getDateFormat(Date d){
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
    
    public void ajaxUpdate1(){
        PrimeFaces.current().ajax().update("form:confirmmsgs");
    }
    
    public void ajaxUpdate2(){
        PrimeFaces.current().ajax().update("mapform:mapmsgs");
    }
    
    public void ajaxUpdate3(){
        PrimeFaces.current().ajax().update("connect-form:connectmsgs");
    }
    
    public void ajaxUpdateMenu(){
        PrimeFaces.current().ajax().update("menu");
    }
    
    
    public List<String> listToString(List<? extends Object> objectlist){
        List<String> stringlist = new ArrayList<String>();
        for(Object o:objectlist){
            stringlist.add(o.toString());
        }
        return stringlist;
    }

    
    

    
    
}
