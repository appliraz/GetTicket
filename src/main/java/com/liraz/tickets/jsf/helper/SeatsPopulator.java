/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;

import com.liraz.tickets.jpa.Hall;
import com.liraz.tickets.jpa.Seat;
import com.liraz.tickets.jpa.manager.SeatManager;
import com.liraz.tickets.jsf.HallController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


/**
 *
 * This class is made specific to add seats for a selected Hall to database
 */
@Named
@ViewScoped

public class SeatsPopulator implements Serializable{
    
    private Hall selectedHall;
    @EJB
    private SeatManager sm;
    private int rows; //number of rows of the selected hall
    private List<TempSeat> seatslist;
    private boolean mapReady = false;
    private int sum = 0;
    private boolean standing = false;
    private boolean pocc = true;
   
    @Inject
    private HallController hc;
    
    
    public SeatsPopulator(){}
    
    /**** getters and setters ****/
    
    public Hall getSelectedHall(){
        return selectedHall;
    }
    
    public void setSelectedHall(Hall h){
        this.selectedHall = h;
    }
   
    private SeatManager getSeatManager(){
        return sm;
    }
    
        public int getRows(){
        return rows;
    }
    
    public void setRows(int nrows){
        this.rows = nrows;
    }
    
    public boolean getMapReady(){
        return mapReady;
    }
    
    public void setMapReady(boolean b){
        this.mapReady=b;
    }
    
    
    public List<TempSeat> getSeatsList(){
        return seatslist;
    }
    
    public void setSeatsList(List<TempSeat> sl){
        this.seatslist = sl;
    }
    
    public int getSum(){
        return sum;
    }
    
    public void setSum(int s){
        this.sum=s;
    }
    
    public boolean allowAddingSeats(){
        return !(getHallOccupied(getSelectedHall().getHallid()) == getSelectedHall().getHallcapacity());
    }
    
    public int getHallOccupied(int hid){
        List<Seat> ls = getSeatManager().getListByHallid(hid);
        return ls.size();
    }
    
    
    /****methods and functions ****/
    /** 
    @PostConstruct
    public void init(){
        this.selectedHall =(Hall) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedHall");
        if(this.selectedHall.getHalltype().equals("עמידה"))
            setStanding(true);
    }
    **/
    
    @PostConstruct
    public void init(){
        this.selectedHall = hc.getSelectedHall();
        if(this.selectedHall.getHalltype().equals("עמידה"))
            setStanding(true);
    }
    
    public void initMap(){
        int r = getRows();
        List<TempSeat> sl = new ArrayList<TempSeat>();
        for(int i =0;i<r;i++)
        {
            TempSeat ts = new TempSeat();
            ts.setHall(getSelectedHall());
            ts.setRow(i+1);
            ts.setSeat(0);
            sl.add(ts);
        }
        setSeatsList(sl);
        setMapReady(true);
    }
    
    public void calcSum(){
        int s = 0;
        List<TempSeat> sl = getSeatsList();
        for(TempSeat ts:sl){
            s+=ts.getSeat();
        }
        setSum(s);
    }
    
    public boolean isPreOccupied(){
        return pocc;
    }
    
    public void setPreOcuppied(boolean po){
        this.pocc = po;
    }
    
    public void populateStandingHall(){
        int count=0;
        Hall sh = getSelectedHall();
        int cap = sh.getHallcapacity();
        for(int i=0;i<cap;i++){
            Seat s = new Seat();
            s.setHallid(sh);
            s.setSeatnumber(i+1);
            s.setSeatrow(0);
            try{
                getSeatManager().create(s);
                count++;
            }
            catch(Exception e){
                message("תקלה בתהליך הוספת הכיסאות לבסיס הנתונים");
                ajaxUpdate();
                //message failure
            }
        }
        message("תהליך הוספת הכיסאות לבסיס הנתונים הסתיים");
        ajaxUpdate();
        //success message
    }
    
    public void populateSittingHall(){
        if(valid()){
            int count=0;
            List<TempSeat> sl = getSeatsList();
            for(TempSeat ts:sl){
                int seats=ts.getSeat();
                for(int i =0;i<seats;i++){
                    Seat s = new Seat();
                    s.setHallid(ts.getHall());
                    s.setSeatrow(ts.getRow());
                    s.setSeatnumber(i+1);
                    try{
                        getSeatManager().create(s);
                        count++;
                    }
                    catch(Exception e){
                        message(e.toString());
                        ajaxUpdate();
                    }
                }
            }
            message("תהליך הוספת הכיסאות לבסיס הנתונים הסתיים");
            ajaxUpdate();
        }
        else{
            message("לא נוספו כיסאות לבסיס הנתונים");
            ajaxUpdate();
        }
    }
    
    public boolean isStanding(){
        return standing;
    }
    
    public void setStanding(boolean stand){
        this.standing = stand;
    }
    
    public String redirectHalls(){
       // FacesContext.getCurrentInstance().getExternalContext().getFlash().put("i", "3");
        return "/manager/halls.xhtml?faces-redirect=true&i=3";
    }
    
    public boolean valid(){
        if(getSum()<=0){
            message("צריך להוסיף לפחות כיסא אחד לאולם...");
            ajaxUpdate();
            return false;
        }
        else if(getSum()>getSelectedHall().getHallcapacity()){
            message("לא ייתכן יותר כיסאות מתכולה");
            ajaxUpdate();
            return false;
        }
        return true;
    }
    
    
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }

    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages");
    }
    
    
}
