/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Review;
import com.liraz.tickets.jpa.Run;
import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.manager.ReviewManager;
import com.liraz.tickets.jpa.manager.RunManager;
import com.liraz.tickets.jpa.manager.ShowManager;
import com.liraz.tickets.jsf.helper.FileUploadController;
import com.liraz.tickets.jsf.helper.imageHelper;
import java.io.ByteArrayOutputStream;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author User
 */

@Named("showpageController")
@ViewScoped

public class ShowpageController implements Serializable {
    private Show current;
    private List<Show> shows = null;
    private Show selectedShow;
    private List<Show> selectedShows;
    
    @EJB
    private ShowManager sm;
    @EJB
    private RunManager rm;
    @EJB
    private ReviewManager rvm;
    
    @Inject
    private FileUploadController fuc;
    @Inject
    private imageHelper imageHandler;
    @Inject
    private ClientSessionController csc;
    
    private String showid;
    private Run selectedrun;
    private boolean reviewtoggle = false;
    private String newreview;
    private int newrating;
    
    public ShowpageController(){
    }
    
    /**getters and setters*/
    private ShowManager getShowManager(){
        return sm;
    }
    
    private RunManager getRunManager(){
        return rm;
    }
    
    public Show getCurrent(){
        if(current==null){
            current = new Show();
        }
        return current;
    }
    
    public List getShows(){
        shows = getShowManager().findAll();
        return shows;
    }
    
    public Show getSelectedShow(){
        return selectedShow;
    }
    
    public void setSelectedShow(Show v){
        this.selectedShow = v;
    }
    
    public List<Show> getSelectedShows(){
        return selectedShows;
    }
    
    public void setSelectedShows(List<Show> sv){
        this.selectedShows = sv;
    }
    
    public String getShowid(){
        return showid;
    }
    
    public void setShowid(String sid){
        this.showid = sid;
    }
    

    
    
    public boolean hasSelectedShows(){
        ajaxUpdate();
        return this.selectedShows != null && !this.selectedShows.isEmpty();
    }
    
    /**show page specific **/
    public void initShowpage(){
        setSelectedShow(getShowManager().getShowByID(getShowid()));
    }    

    public StreamedContent getImg()
    {
        return imageHandler.getImg();
    }
    
    /**runs **/
    public List<Run> getRuns(){
        return getRunManager().getActiveRunsByShow(getSelectedShow());
    }
    
    public Run getSelectedRun(){
        return selectedrun;
    }
    
    public void setSelectedRun(Run r){
        this.selectedrun = r;
    }
    
    public String orderRedirect(){
        if(getSelectedRun()==null)
            return "";
        else
            return ("/client/orderwizard.xhtml?faces-redirect=true&i=3&runId="+String.valueOf(getSelectedRun().getRunid()));
    }
    
    /**reviews **/
    private ReviewManager getReviewManager(){
        return rvm;
    }
    
    public List<Review> getReviews(){
        return getReviewManager().getReviewsByShow(getSelectedShow());
    }
    
    public boolean getReviewToggle(){
        return reviewtoggle;
    }
    
    public void setReviewToggle(boolean rt){
        this.reviewtoggle = rt;
    }
    
    public String getNewReview(){
        return newreview;
    }
    
    public void setNewReview(String nr){
        this.newreview = nr;
    }
    
    public int getNewRating(){
        return newrating;
    }
    
    public void setNewRating(int nr){
        this.newrating = nr;
    }
    
    public void toggle(){
        if(!getReviewToggle() && csc.getCurrentClient()==null){
            message("יש להתחבר על מנת להשאיר ביקורת");
            ajaxUpdate();
        }
        else{
            setReviewToggle(!getReviewToggle());
        }
    }
    
    public void addReview(){
        if(getNewReview().isEmpty()){
            message("תכתבו לפחות משהו...");
            ajaxUpdate();
        }
        else{
            Review r = new Review();
            r.setClientid(csc.getCurrentClient());
            r.setShowid(getSelectedShow());
            r.setReviewcontent(getNewReview());
            r.setRating(getNewRating());
            getReviewManager().create(r);
            calcRating();
        }
            
    }
    
    public void calcRating(){
        List<Review> rl = getReviews();
        int sum=0;
        for(Review r:rl){
            sum+=r.getRating();
        }
        int avg = sum/(rl.size());
        getSelectedShow().setShowrating(avg);
        getShowManager().edit(getSelectedShow());
    }
    

    /**view flow*/
    public void newShow(){
        this.selectedShow = new Show();
        message("called " + this.selectedShow.getShowid());
    }
    
    
    public void uploadImage(FileUploadEvent event){
        fuc.upload(event);
        UploadedFile uf = event.getFile();
        getSelectedShow().setShowposter(uf.getFileName());
    }
    
    public boolean hasPoster(){
        return !(getSelectedShow().getShowposter() == null);
    }
   
    
    /**class utility */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }
    
    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("form:messages", "form:dt-shows");
    }
    
    
    
    
    
    public String listToString(List<Show> sl){
        String list = "here: ";
        for (Show s : sl) {
            list.concat(" " + s.getShowtitle());
        }
        return list;
    }
    
    public String stringListPrint(List<String> ls){
        String list = "";
        list.concat(ls.get(0));
        list.concat(ls.get(1));
        list.concat(ls.get(2));
        return list;
    }
    
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


}
