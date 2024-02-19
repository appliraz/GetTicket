/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "CLIENTORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientorder.findAll", query = "SELECT c FROM Clientorder c"),
    @NamedQuery(name = "Clientorder.findByOrderid", query = "SELECT c FROM Clientorder c WHERE c.orderid = :orderid"),
    @NamedQuery(name = "Clientorder.findBySumtopay", query = "SELECT c FROM Clientorder c WHERE c.sumtopay = :sumtopay")})
public class Clientorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERID")
    private Integer orderid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUMTOPAY")
    private int sumtopay;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderid")
    private List<Payment> paymentList;
    @JoinColumn(name = "CLIENTID", referencedColumnName = "CLIENTID")
    @ManyToOne(optional = false)
    private Client clientid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderid")
    private List<Selection> selectionList;

    public Clientorder() {
    }

    public Clientorder(Integer orderid) {
        this.orderid = orderid;
    }

    public Clientorder(Integer orderid, int sumtopay) {
        this.orderid = orderid;
        this.sumtopay = sumtopay;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public int getSumtopay() {
        return sumtopay;
    }

    public void setSumtopay(int sumtopay) {
        this.sumtopay = sumtopay;
    }

    @XmlTransient
    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public Client getClientid() {
        return clientid;
    }

    public void setClientid(Client clientid) {
        this.clientid = clientid;
    }

    @XmlTransient
    public List<Selection> getSelectionList() {
        return selectionList;
    }

    public void setSelectionList(List<Selection> selectionList) {
        this.selectionList = selectionList;
    }
    
    public Run getRun(){
        return getSelectionList().get(0).getRunseatid().getRunid();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientorder)) {
            return false;
        }
        Clientorder other = (Clientorder) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.liraz.tickets.jpa.Clientorder[ orderid=" + orderid + " ]";
    }
    
}
