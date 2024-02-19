/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findByPaymentid", query = "SELECT p FROM Payment p WHERE p.paymentid = :paymentid"),
    @NamedQuery(name = "Payment.findByPaymentcomplete", query = "SELECT p FROM Payment p WHERE p.paymentcomplete = :paymentcomplete"),
    @NamedQuery(name = "Payment.findByTotal", query = "SELECT p FROM Payment p WHERE p.total = :total")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAYMENTID")
    private Integer paymentid;
    @Size(max = 10)
    @Column(name = "PAYMENTCOMPLETE")
    private String paymentcomplete;
    @Column(name = "PAYMENTDATE")
    private Date paymentdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL")
    private int total;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @ManyToOne(optional = false)
    private Clientorder orderid;

    public Payment() {
    }

    public Payment(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public Payment(Integer paymentid, int total) {
        this.paymentid = paymentid;
        this.total = total;
    }

    public Integer getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public String getPaymentcomplete() {
        return paymentcomplete;
    }

    public void setPaymentcomplete(String paymentcomplete) {
        this.paymentcomplete = paymentcomplete;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Clientorder getOrderid() {
        return orderid;
    }

    public void setOrderid(Clientorder orderid) {
        this.orderid = orderid;
    }
    
    public Date getPaymentdate(){
        return paymentdate;
    }
    
    public void setPaymentdate(Date pd){
        this.paymentdate = pd;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentid != null ? paymentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.paymentid == null && other.paymentid != null) || (this.paymentid != null && !this.paymentid.equals(other.paymentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.liraz.tickets.jpa.Payment[ paymentid=" + paymentid + " ]";
    }
    
}
