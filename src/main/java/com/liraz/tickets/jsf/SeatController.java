/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Hall;
import com.liraz.tickets.jpa.Seat;
import com.liraz.tickets.jpa.manager.SeatManager;
import com.liraz.tickets.jsf.helper.TempSeat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */

@Named
@ViewScoped

public class SeatController implements Serializable {
    @EJB
    private SeatManager sm;
    private List<Seat> seatsInRow; //list of seats in a specific row in db
    private int selectedHallid;
    
    public SeatController(){}
    
    private SeatManager getSeatManager(){
        return sm;
    }
    
    public int getSelectedHallId(){
        return selectedHallid;
    }
    
    public void setSelectedHallid(int hid){
        this.selectedHallid = hid;
    }
    

    
    

    
    
    public int getSelectedHallParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String hallparam = (String) myRequest.getParameter("hallid");
        int hid = Integer.parseInt(hallparam);
        return hid;
    }
    
    public int getRowParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String rowparam = (String) myRequest.getParameter("row");
        int row = Integer.parseInt(rowparam);
        return row;
    }
    
    public int getNumberOfRowsParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String rowparam = (String) myRequest.getParameter("rows");
        int rows = Integer.parseInt(rowparam);
        return rows;
    }
    
    public void setSeatsInRow(){
        int row = getRowParam();
        int hid = getSelectedHallParam();
        this.seatsInRow = getSeatManager().getListByRowAndHall(row, hid);
    }
    
    public List<Seat> getSeatsInRow(){
        return seatsInRow;
    }
    /*
    public List<Map.Entry<Integer,Integer>> getSeatsList()
    {
        Set<Map.Entry<Integer,Integer>> seatsset = getSeatsMap().entrySet();
        return new ArrayList<Map.Entry<Integer,Integer>>(seatsset);
    }
    
    public int[][] getSeatsMap()
    {
        return seatsmap;
    }
    
    public void setSeatsMap(int[][] smap){
        this.seatsmap = smap; 
    }*/
    

    

    /*
    public void createMap(){
        int r = getRows();
        message(String.valueOf(r));
        ajaxUpdate();
        if(r>0){
            for(int i=1;i<=r;i++){
                getSeatsMap().put(i,0);
            }
        }
        setMapReady(true);
        message(String.valueOf(getMapReady()));
        ajaxUpdate();
    }
    */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }

    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages");
    }
    
    
}
