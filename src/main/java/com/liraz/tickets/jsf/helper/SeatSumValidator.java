/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * This is a validator bean, made to validate the amount of seats added to a Hall
 */
@FacesValidator ("sumValidator")
public class SeatSumValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
       validate(fc, uic);
    }
    
    public void validate(FacesContext fc, UIComponent uic){
        HttpServletRequest myRequest = (HttpServletRequest) fc.getExternalContext().getRequest();
        String sumstr = (String) myRequest.getParameter("sum");
        int sum = Integer.parseInt(sumstr);
        String capstr = (String) myRequest.getParameter("cap");
        int cap = Integer.parseInt(capstr);
        if(sum<=0){
            FacesMessage message = new FacesMessage(
					"צריך להוסיף לפחות כיסא אחד לאולם");
			fc.addMessage(uic.getClientId(fc), message);
        }
        else if(sum>cap){
            FacesMessage message = new FacesMessage(
					"לא ייתכן יותר כיסאות מתכולה");
			fc.addMessage(uic.getClientId(fc), message);
        }
    }
   
    
}
