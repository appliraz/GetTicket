/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf;

import com.liraz.tickets.jpa.Client;
import com.liraz.tickets.jpa.manager.ClientManager;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */
@Named("clientSessionController")
@SessionScoped

public class ClientSessionController implements Serializable{
    
    /*client */
    @EJB
    private ClientManager cm;
    private Client currentclient;
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean manager = false;
    
        /**client specific **/
    public ClientManager getClientManager(){
        return cm;
    }
    
    public Client getCurrentClient(){
        return currentclient;
    }
    
    public void setCurrentClient(Client c){
        this.currentclient = c;
    }
    
    public String getCurrentClientName(){
        if(currentclient==null)
            return "אורח";
        else
            return currentclient.getClientfirstname();
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String un){
        this.username = un;
    }
    
    public String getEmail(){
        return email;
    }
       
    public void setEmail(String eml){
        this.email = eml;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(String phon){
        this.phone = phon;    
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String pass){
        this.password = pass;    
    }
    
    public boolean isManager(){
        return manager;
    }
    
    private boolean isManagerEmail(String email){
        return email.equals("admin@admin.admin");
    }
    
    
    /**client methods**/
    public void connect(){
        Client db = new Client();
        String cemail = getEmail();
        String cpassword = getPassword();
        if(cemail.isEmpty() || cpassword.isEmpty())
        {
            message("אנא הכניסו מייל וסיסמה");
            ajaxUpdate();
        }
        else{
            List<Client> exist = getClientManager().getClientByEmail(cemail);
            if(!exist.isEmpty()) //check if client exist in db
            {
                db = exist.get(0); //there's only one element in the list
                if(db.getClientpassword()==null)//specific case
                {
                    message("משתמש נוסף על ידי מנהל, יש להגדיר סיסמה");
                    ajaxUpdate();
                }
                else if(db.getClientpassword().equals(cpassword))
                {
                    setCurrentClient(db);
                    if(isManagerEmail(cemail))
                        manager = true;
                    else
                        manager = false;
                }
                else{
                    message("סיסמה שגויה, קורה");
                    ajaxUpdate();
                }
            }
            else{
                message("לא נמצא משתמש עם מייל תואם במערכת");
                ajaxUpdate();
            }  
        }
    }
    
    public void register(){
        String cemail = getEmail();
        List<Client> exist = getClientManager().getClientByEmail(cemail);
        Client db = new Client();
        if(!exist.isEmpty()){
            message("יש כבר משתמש עם כתובת מייל זו, אנא בחר כתובת מייל אחרת");
            ajaxUpdate();
        }
        else{
            db = new Client();
            db.setClientemail(cemail);
            db.setClientpassword(getPassword());
            db.setClientfirstname(getUsername());
            db.setClientphone(getPhone());
            try{
                getClientManager().create(db);
                message("המשתמש נוצר בהצלחה");
                ajaxUpdate();
                setCurrentClient(db);
            }
            catch(Exception e){
                message(e.toString());
                ajaxUpdate();
            }
        }
            
    }
    
    public String redirectManager(){
        return manager ? "/manager/managerindex.xhtml?faces-redirect=true&i=0" : "";
    }
    
    
    
        /**class utility */
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(msg));
    }
    
    public void ajaxUpdate(){
        PrimeFaces.current().ajax().update("connect-form:connectmsgs");
    }
    
}
