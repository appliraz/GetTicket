/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Run;
import com.liraz.tickets.jpa.Runseat;
import com.liraz.tickets.jpa.Seat;
import com.liraz.tickets.jpa.manager.RunManager;
import com.liraz.tickets.jpa.manager.RunseatManager;
import com.liraz.tickets.jpa.manager.SeatManager;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */

@Named("runController")
@ViewScoped

public class RunController implements Serializable {
    private Run current;
    private List<Run> runs = null;
    private Run selectedRun;
    private List<Run> selectedRuns;
    @EJB
    private RunManager rm;
    @EJB
    private RunseatManager rsm;
    @EJB
    private SeatManager sm;
    private LocalDate mindate;
    private LocalTime selectedTime;
    
    public RunController(){
    }
    
    /**getters and setters*/
    private RunManager getRunManager(){
        return rm;
    }   
    
    public Run getRun(){
        if(current==null){
            current = new Run();
        }
        return current;
    }
    
    public List<Run> getRuns(){
        runs = getRunManager().findAll();
        return runs;
    }
    
    public Run getSelectedRun(){
        return selectedRun;
    }
    
    public void setSelectedRun(Run r){
        this.selectedRun = r;
    }
    
    public List<Run> getSelectedRuns(){
        return selectedRuns;
    }
    
    public void setSelectedRuns(List<Run> sr){
        this.selectedRuns = sr;
    }
    
    public boolean hasSelectedRuns(){
        ajaxUpdate();
        return this.selectedRuns != null && !this.selectedRuns.isEmpty();
    }
    
    
    public LocalDate getMindate(){
        this.mindate = LocalDate.now();
        return mindate;
    }
    
    public LocalTime getSelectedTime(){
        return selectedTime;
    }
    
    public void setSelectedTime(LocalTime lt){
        this.selectedTime = lt;
    }
    
    public RunseatManager getRunseatManager(){
        return rsm;
    }
    
    public SeatManager getSeatManager(){
        return sm;
    }

    
    
    /**working with ClientManager - create, read, update, delete**/

    
    public void save(){
        Run sr = getSelectedRun();
        sr.setActive("false");
        if(sr!=null)
        {
            try{
                getRunManager().create(sr);
                message("ההרצה נוספה בהצלחה");
            }
            catch(Exception e)
            {
                message(e.toString());
            }
        }
        else
            message("no selectedRun");
        PrimeFaces.current().executeScript("PF('manageRunDialog').hide()");
        ajaxUpdate();
    }
    
    public void update(){
        getRunManager().edit(getSelectedRun());
        message("ההרצה נערכה");
        PrimeFaces.current().executeScript("PF('manageRunDialog').hide()");
        ajaxUpdate();
    }
    
    public void deleteRun(){
        getRunManager().remove(getSelectedRun());
        setSelectedRun(null);
        message("ההרצה הוסרה");
        ajaxUpdate();
    }
    
    
    public void deleteSelectedRuns(){
        List<Run> rlist = getSelectedRuns();
        for(Run r:rlist){
            getRunManager().remove(r);
        }
        this.selectedRuns.clear();
        message("הלקוחות הוסרו בהצלחה");
        //PrimeFaces.current().executeScript("PF('dtRuns').clearFilters()");
        ajaxUpdate();
    }
    
    /*meaning of making active is:
        1. the run's 'active' attribute sets to true
        2. run-seats (aka tickets) will be made available in db
        3. clients are seeing active runs (and don't see non-active)*/
    public void makeActive(){
        //check if active
        Run r = getSelectedRun();
        if(r.getActiveBool()){
            message("מופע זה כבר פעיל");
            ajaxUpdate();
        }
        else{//if not - create run-seats for the show
            Runseat rs = new Runseat();
            rs.setRunid(r);
            rs.setAvailable("true");
            List<Seat> seats = getSeatsList(r);
            RunseatManager manager = getRunseatManager();
            int count=0;
            for(Seat s:seats){
                rs.setSeatid(s);
                manager.create(rs);
                count++;
            }
            message(String.valueOf(count)+" new tickets");
            r.setActive("true");
            getRunManager().edit(r);
            ajaxUpdate();
            
        }      
    }
    
    private List<Seat> getSeatsList(Run r){
        List<Seat> sl = getSeatManager().getListByHallid(r.getHallid().getHallid());
        return sl;
    }
    
    public void changeActivityStatus(){
        Run r = getSelectedRun();
        if(r.getActive().equals("true"))
            r.setActive("false");
        else
            r.setActive("true");
        getRunManager().edit(r);
        ajaxUpdate();
   
    }

    /**view flow*/
    public void newRun(){
        this.selectedRun = new Run();
    }
    
    public void runDialogClosed(){
        if(getSelectedRun().getRunid()==null)
            save();
        else
            update();
        ajaxUpdate();
    }
    
    
   
    
    /**class utility */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }
    
    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages", "form:dt-runs");
    }
    


}
