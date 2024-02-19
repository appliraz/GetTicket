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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */

@Named("clientController")
@ViewScoped

public class ClientController implements Serializable {
    private Client current;
    private List<Client> clients = null;
    private Client selectedClient;
    private List<Client> selectedClients;
    @EJB
    private ClientManager cm;
    
    public ClientController(){
    }
    
    /**getters and setters*/
    private ClientManager getClientManager(){
        return cm;
    }   
    
    public Client getCurrent(){
        if(current==null){
            current = new Client();
        }
        return current;
    }
    
    public List getClients(){
        clients = getClientManager().findAll();
        return clients;
    }
    
    public Client getSelectedClient(){
        return selectedClient;
    }
    
    public void setSelectedClient(Client c){
        this.selectedClient = c;
    }
    
    public List<Client> getSelectedClients(){
        return selectedClients;
    }
    
    public void setSelectedClients(List<Client> sc){
        this.selectedClients = sc;
    }
    
    public boolean hasSelectedClients(){
        ajaxUpdate();
        return this.selectedClients != null && !this.selectedClients.isEmpty();
    }
    
    
    /**working with ClientManager - create, read, update, delete**/

    
    public void save(){
        Client sc = getSelectedClient();
        if(sc!=null)
        {
            try{
                getClientManager().create(sc);
                message("הלקוח נוסף בהצלחה");
            }
            catch(Exception e)
            {
                message(e.toString());
            }
        }
        else
            message("no selectedClient");
        PrimeFaces.current().executeScript("PF('manageClientDialog').hide()");
        ajaxUpdate();
    }
    
    public void update(){
        getClientManager().edit(getSelectedClient());
        message("הלקוח נערך");
        PrimeFaces.current().executeScript("PF('manageClientDialog').hide()");
        ajaxUpdate();
    }
    
    public void deleteClient(){
        getClientManager().remove(getSelectedClient());
        setSelectedClient(null);
        message("הלקוח הוסר");
        ajaxUpdate();
    }
    
    
    public void deleteSelectedClients(){
        List<Client> clist = getSelectedClients();
        for(Client c:clist){
            getClientManager().remove(c);
        }
        this.selectedClients.clear();
        message("הלקוחות הוסרו בהצלחה");
        //PrimeFaces.current().executeScript("PF('dtClients').clearFilters()");
        ajaxUpdate();
    }

    /**view flow*/
    public void newClient(){
        this.selectedClient = new Client();
    }
    
    public void clientDialogClosed(){
        if(getSelectedClient().getClientid()==null)
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
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clients");
    }
    


}
