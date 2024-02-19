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
import com.liraz.tickets.jpa.Show;
import com.liraz.tickets.jpa.Venue;
import com.liraz.tickets.jpa.manager.ClientManager;
import com.liraz.tickets.jpa.manager.ClientorderManager;
import com.liraz.tickets.jpa.manager.PaymentManager;
import com.liraz.tickets.jpa.manager.RunManager;
import com.liraz.tickets.jpa.manager.RunseatManager;
import com.liraz.tickets.jpa.manager.SelectionManager;
import com.liraz.tickets.jsf.helper.ReceiptSessionBean;
import com.liraz.tickets.jsf.helper.imageHelper;
import java.io.Serializable;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Date;
import java.time.LocalDate;

import java.util.List;
import java.util.Objects;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.StreamedContent;


/**
 *
 * @author User
 */
@Named("orderController")
@ViewScoped
public class OrderController implements Serializable {
    
    @EJB
    private RunManager rm;
    @EJB
    private RunseatManager rsm;
    @EJB
    private ClientManager cm;
    @EJB
    private ClientorderManager com;
    @EJB
    private SelectionManager sm;
    @EJB
    private PaymentManager pm;
    
    @Inject
    private imageHelper ih;
    @Inject
    private ClientSessionController csc;
    @Inject
    private ReceiptSessionBean rsb;
    
    /*run and init attributes */
    private String runid;
    private Run selectedrun;
    private List<Runseat> runseatslist;
    private List<Integer> rowslist;
    private List<Integer> seatslist;
    private String type;
    private boolean mapready = false;
    private int cols = 0;
    private StreamedContent runimg;
    /*seat select attributes*/
    private boolean isaseat = false;
    private int currentrow = 1;
    private int currentcol = 1;
    private int maxrow = 0;
    private int maxseat = 0;
    private Runseat currentseat;
    private List<Runseat> selectlist;
    private List<Runseat> maplist;
    private double scale = 1.0;
    
    /*stand select */
    private int seatsamount;
    private List<Runseat> unmarkedseats;
    
    /*client *//*
    private Client currentclient;
    private String username;
    private String password;
    private String email;
    private String phone;*/
    
    /*payment*/
    private int sum;
    private Clientorder currentorder;
    private Payment currentpayment;
    private boolean paymentcomplete;
    
    
    /*tabs*/
    private int currenttab=1;
    
    /**initializations **/
    public void init(){
        //this.runid = (String)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedHall");
        this.selectedrun = getRunManager().find(Integer.parseInt(getRunid()));
        this.type = this.selectedrun.getHallid().getHalltype();
        if(this.type.equals("עמידה"))
            initUnmarkedList();
    }
    
    public void initMapList(){
        if(!getType().equals("עמידה")){            
            initMaxRow();
            initMaxSeat();
            List<Runseat> map = new ArrayList<Runseat>();
            int maxcol = getMaxSeat();
            int maxrow = getMaxRow();
            for(int i=1; i<=maxrow;i++){
                for(int j=1; j<=maxcol; j++){
                    Runseat r = getCurrentSeat(i,j);
                    map.add(r);
                }
            }
            this.maplist = new ArrayList<Runseat>();
            this.maplist.addAll(map);
        }
    }
    
    public void initMapParameters(){
        initRunSeatsList();
        initMapList();
        this.mapready = true;
        this.selectlist = new ArrayList<Runseat>();
    }
    
    public void initRunSeatsList(){
        this.runseatslist = getRunseatManager().getSeatsByRun(getSelectedRun());
    }
    
    public void initUnmarkedList(){
        this.unmarkedseats = getRunseatManager().getAvailableSeatsByRun(getSelectedRun());
    }
    
    public void initRowsList(){
        this.rowslist = getRows(getRunseatsList());
    }
    
    public void initSeatsList(){
        this.seatslist = getSeats(getRunseatsList());
    }
    
    public void initSum(){
        List<Runseat> rs = getSelectList();
        setSumToPay(rs.size()*getSelectedRun().getRunprice());
    }
    
    
    /**getters and setters **/
    
    /**run specific**/
    public RunManager getRunManager(){
        return rm;
    }
    
    public RunseatManager getRunseatManager(){
        return rsm;
    }
    
    public String getRunid(){
        return runid;
    }
    
    public void setRunid(String id){
        this.runid = id;
    }
    
    public Run getSelectedRun(){
        return selectedrun;
    }
    
    public imageHelper getImageHelper(){
        return ih;
    }
    
    public StreamedContent getRunPoster(){
        return runimg;
    }
    
    public void setRunPoster(StreamedContent img){
        this.runimg = img;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String t){
        this.type = t;
    }
    

    
    
    /**map specific**/
    public List<Runseat> getRunseatsList(){
        return runseatslist;
    }
    
    public List<Integer> getRowsList(){
        return rowslist;
    }
    
    public List<Integer> getSeatsList(){
        return seatslist;
    }
    
    public int getCols(){
        return cols;
    }
    
    public int getCurrentRow(){
        return currentrow;
    }
    
    public void setCurrentRow(int r){
        this.currentrow = r;
    }
    
    public int getCurrentCol(){
        return currentcol;
    }
    
    public void setCurrentCol(int c){
        this.currentrow = c;
    }

    
    public Runseat getCurrentSeat(){
        return currentseat;
    }
    
    public void setCurrentSeat(Runseat rs){
        this.currentseat = rs;
    }
    
    public List<Runseat> getSelectList(){
        return selectlist;
    }
    
    public int getMaxRow(){
        return maxrow;
    }
    
    public void setMaxRow(int mr){
        this.maxrow=mr;
    }
    
    public int getMaxSeat(){
        return maxseat;
    }
    
    public void setMaxSeat(int ms){
        this.maxseat=ms;
    }
    
    public List<Runseat> getMapList(){
        return maplist;
    }
    
    public void setMapList(List<Runseat> map){
        this.maplist = map;
    }
    
    public Runseat getCurrentSeat(int row, int col){
        List<Runseat> list = getRunseatsList();
        for(Runseat seat:list){
            if(seat.getSeatid().getSeatrow()==row && seat.getSeatid().getSeatnumber()==col){
                return seat;
            }
        }
        Runseat r = new Runseat();
        r.setRunseatid(0);
        return r;
    }
    
    public double getScale(){
        return scale;
    }
    
    public void increaseScale(){
        if(scale<1.2)
            scale = scale + 0.05;
    }
    
    public void decreaseScale(){
        if(scale>0.05)
            scale = scale-0.05;
    }
    
    
    /**standing specific **/

    public int getSeatsAmount(){
        return seatsamount;
    }
    
    public void setSeatsAmount(int sa){
        this.seatsamount = sa;
    }
    
    public int getAvailableAmount(){
        return getUnmarkedList().size();
    }
    
    public List<Runseat> getUnmarkedList(){
        return unmarkedseats;
    }
    
        
    
    
    /**client specific **/
    private ClientSessionController getClientSessionController(){
        return csc;
    }
    
    public ClientManager getClientManager(){
        return cm;
    }
    
    public String getClientName(){
        return getClientSessionController().getCurrentClient().getClientfirstname();
    }
    
    
    
    
    /**payemnt specific **/
    public PaymentManager getPaymentManager(){
        return pm;
    }
    
    public ClientorderManager getClientorderManager(){
        return com;
    }
    
    public SelectionManager getSelectionManager(){
        return sm;
    }
    
    public int getSumToPay(){
        return sum;
    }
    
    public void setSumToPay(int s){
        this.sum = s;
    }
    
    public Clientorder getCurrentOrder(){
        return currentorder;
    }
    
    public void setCurrentOrder(Clientorder id){
        this.currentorder=id;
    }
    
    public boolean getPaymentcomplete(){
        return paymentcomplete;
    }
    
    public void setPaymentcomplete(boolean p){
        this.paymentcomplete=p;
    }
    
    public Payment getCurrentPayment(){
        return currentpayment;
    }
    
    public void setCurrentPayment(Payment p){
        this.currentpayment=p;
    }
    

    
    /**tab specific**/
    public int getCurrentTab(){
        return currenttab;
    }
    
    public void setCurrentTab(int t){
        this.currenttab=t;
    }
    
    
    
    /**booleans **/
    public boolean isPlaceHolder(Runseat seat){
        return (seat==null);
    }
    public boolean isMapReady(){
        return mapready;
    }
    
    public boolean isAseat(){
        return isaseat;
    }
    
    public boolean isInSelectList(Runseat r){
        List<Runseat> select = getSelectList();
        if(select.isEmpty())
        {
            return false;
        }
        return select.contains(r);
    }
    
    public boolean isInSelectList(int row, int col){
        Runseat r = getCurrentSeat(row, col);
        List<Runseat> select = getSelectList();
        if(select.isEmpty())
        {
            message("list empty");
            ajaxUpdate2();
            return false;
        }
        for(Runseat rs:select){
            if(r.getSeatid().getSeatrow()==rs.getSeatid().getSeatrow()
                    && r.getSeatid().getSeatnumber()==rs.getSeatid().getSeatnumber())
            {
                message("found it");
                ajaxUpdate2();
                return true;}
        }
        return false;
    }
    
    public boolean hasSelected(){
        return !(getSelectList().isEmpty());
    }
    
    public void isInSeatsList(){
        int row = getCurrentRow();
        int col = getCurrentCol();
        boolean is = false;
        List<Runseat> list = getRunseatsList();
        for(Runseat seat:list){
            if(seat.getSeatid().getSeatrow()==row && seat.getSeatid().getSeatnumber()==col){
                this.isaseat=true;
                is=true;
                break;
            }
        }
        if(!is)
            this.isaseat=false;
    }
    
    public boolean isInSeatsList(int row, int col){
        List<Runseat> list = getRunseatsList();
        for(Runseat seat:list){
            if(seat.getSeatid().getSeatrow()==row && seat.getSeatid().getSeatnumber()==col){
                return true;
            }
        }
        return false;
    }
    
    public boolean isInSeatsList(Runseat r){
        return getRunseatsList().contains(r);
    }
    

    
    
    
    /**client methods**/
  
    
    public void addUnmarkedToSelect(){
        List<Runseat> seats = getUnmarkedList();
        int selection = getSeatsAmount();
        if(selection>getAvailableAmount())
        {
            message("בחרתם יותר מידי כרטיסים, אנא נסו שוב");
            ajaxUpdate2();
        }
        else{
            int count=0;
            for(int i=0;i<selection;i++){
                addCurrentToSelect(seats.get(i));
                count++;
            }
        }
    }
    
    public void removeUnmarkedFromSelect(){
        List<Runseat> select = getSelectList();
        if(select.isEmpty()){
            message("לא נבחרו כרטיסים");
            ajaxUpdate2();
        }
        else{
            int selection = getSeatsAmount();
            if(selection>select.size()){
                message("יותר מידי כרטיסים להסרה");
                ajaxUpdate2();
            }
            else{
                for(int i=0; i<selection; i++){
                    select.remove(i);
                }
            }
        }
    }
    
    
    /**payment methods **/
    
    
    public String processPayment(){
        //check if the selected seats got sold until reaching here
        if(checkAllAvailable()){
            //edit all seats as sold
            setSelectedAsSold();
            //create a new order and commit to memory
            createNewOrder();
            //commit to db all the select list
            saveSelectToDB();
            //set payment completed
            setPaymentcomplete(true);
            //commit payment to db
            commitPayment();
            return redirectEnd();
        }
        else{
            message("סליחה, אבל ניראה שחלק הכיסאות שבחרת כבר נמכרו...");
            message("אנא חזרו לבחירת המושבים ובחרו מושבים אחרים");
            ajaxUpdate4();
            return "";
        }

    }
    
    public String redirectEnd(){
        if(paymentcomplete){
            rsb.setPayment(getCurrentPayment());
            rsb.setOrder(getCurrentOrder());
            rsb.setRun(getSelectedRun());
            rsb.setSelectedSeats(getSelectList());
            return "/client/receipt.xhtml?faces-redirect=true";
        }
        else
            return "";
    }
    
    public boolean checkAllAvailable(){
        List<Runseat> updated =  getRunseatManager().getSeatsByRun(getSelectedRun());
        List<Runseat> current = getSelectList();
        for(Runseat c:current){
            for(Runseat u:updated){
                if(Objects.equals(c, u)){
                    if(u.getAvailable().equals("FALSE"))
                        return false;
                    else
                        break;
                }
            }
        }
        return true;
    }
    
    public void setSelectedAsSold(){
        List<Runseat> current = getSelectList();
        for(Runseat c:current){
            c.setAvailable("FALSE");
            getRunseatManager().edit(c);
        }
    }
    
    public void createNewOrder(){
        Clientorder current = new Clientorder();
        current.setClientid(getClientSessionController().getCurrentClient());
        current.setSumtopay(getSumToPay());
        getClientorderManager().create(current);
        getClientorderManager().flush();
        setCurrentOrder(current);
        
    }
    
    public void saveSelectToDB(){
        List<Runseat> current = getSelectList();
        Selection selection = new Selection();
        selection.setOrderid(getCurrentOrder());
        for(Runseat c:current){
            selection.setRunseatid(c);
            getSelectionManager().create(selection);
        }
        
    }
    public void commitPayment(){
        Payment p = new Payment();
        p.setTotal(getSumToPay());
        p.setOrderid(getCurrentOrder());
        p.setPaymentdate(Date.from(LocalDate.now().atStartOfDay(ZoneId.of("Asia/Jerusalem")).toInstant()));
        getPaymentManager().create(p);
        getPaymentManager().flush();
        setCurrentPayment(p);
        p.setPaymentcomplete("true");
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
    
    
    public void addCurrentToSelect(Runseat rs){
        getSelectList().add(rs);
    }
    
    public void removeCurrentFromSelect(Runseat rs){
        getSelectList().remove(rs);
    }
    
    public void currentToSelectList(Runseat rs){
        if(!rs.getAvailable().equals("FALSE")){
            if(isInSelectList(rs))
                removeCurrentFromSelect(rs);
            else
                addCurrentToSelect(rs);
        }
    }
    
    public void addCurrentToSelect(int row, int col){
        getSelectList().add(getCurrentSeat(row, col));
    }
    
    public boolean isLast(int s, int i){
        return s==i;
    }
    
    public void nextTab(){
        if(getCurrentTab()==2){
            if(!hasSelected()) //selectionTab - enforce selecting seats
            {
                message("יש לבחור מושבים כדי להמשיך");
                ajaxUpdate2();
            }
            if(getClientSessionController().getCurrentClient()==null){
                message("יש להתחבר כדי להמשיך");
                ajaxUpdate2();
            }
            else{
                initSum();
                this.currenttab++;
            }
        }
        else
            this.currenttab++;
        
    }
    
    public void prevTab(){
        if(getCurrentTab()>1)
            this.currenttab--;
    }
    
    public int getMenuTab(){
        return (currenttab-1);
    }
    
    public String redirectHome(){
        return "/client/clientindex.xhtml?faces-redirect=true";
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
        PrimeFaces.current().ajax().update("standing-form:standmsgs");
    }
    
    public void ajaxUpdate3(){
        PrimeFaces.current().ajax().update("connect-form:connectmsgs");
    }
    
    public void ajaxUpdate4(){
        PrimeFaces.current().ajax().update("payment-form:paymentmsgs");
    }
    

    
    
    public List<String> listToString(List<? extends Object> objectlist){
        List<String> stringlist = new ArrayList<String>();
        for(Object o:objectlist){
            stringlist.add(o.toString());
        }
        return stringlist;
    }
    
    public void initMaxRow(){
        int row=0;
        int current=0;
        List<Runseat> rs = getRunseatsList();
        for(Runseat r: rs){
            current = r.getSeatid().getSeatrow();
            if(current>row)
                row=current;
        }
        this.maxrow = row;
    }
    
    public void initMaxSeat(){
        int seat=0;
        int current=0;
        List<Runseat> rs = getRunseatsList();
        for(Runseat r: rs){
            current = r.getSeatid().getSeatnumber();
            if(current>seat)
                seat=current;
        }
        this.maxseat=seat;
    }
    
    public List<Integer> getRows(List<Runseat> rs){
        int rows = getMaxRow();
        List<Integer> rowsList = new ArrayList<Integer>();
        for(int i=1; i<=rows;i++){
            rowsList.add(i);
        }
        return rowsList;
    }
    
    public List<Integer> getSeats(List<Runseat> rs){
        int seats = getMaxSeat();
        List<Integer> seatsList = new ArrayList<Integer>();
        for(int i=1; i<=seats;i++){
            seatsList.add(i);
        }
        return seatsList;
    }
    
    public void rowPlus(){
        setCurrentRow(getCurrentRow()+1);
    }
    
    public void colPlus(){
        setCurrentCol(getCurrentCol()+1);
    }
    
    public void resetCol(){
        setCurrentCol(1);
    }
    
    

    
    
}
