/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "SELECTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Selection.findAll", query = "SELECT s FROM Selection s"),
    @NamedQuery(name = "Selection.findBySelectionid", query = "SELECT s FROM Selection s WHERE s.selectionid = :selectionid"),
    @NamedQuery(name = "Selection.findByOrderid", query = "SELECT s FROM Selection s WHERE s.orderid = :orderid")})

public class Selection implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SELECTIONID")
    private Integer selectionid;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @ManyToOne(optional = false)
    private Clientorder orderid;
    @JoinColumn(name = "RUNSEATID", referencedColumnName = "RUNSEATID")
    @ManyToOne(optional = false)
    private Runseat runseatid;

    public Selection() {
    }

    public Selection(Integer selectionid) {
        this.selectionid = selectionid;
    }

    public Integer getSelectionid() {
        return selectionid;
    }

    public void setSelectionid(Integer selectionid) {
        this.selectionid = selectionid;
    }

    public Clientorder getOrderid() {
        return orderid;
    }

    public void setOrderid(Clientorder orderid) {
        this.orderid = orderid;
    }

    public Runseat getRunseatid() {
        return runseatid;
    }

    public void setRunseatid(Runseat runseatid) {
        this.runseatid = runseatid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (selectionid != null ? selectionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Selection)) {
            return false;
        }
        Selection other = (Selection) object;
        if ((this.selectionid == null && other.selectionid != null) || (this.selectionid != null && !this.selectionid.equals(other.selectionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.liraz.tickets.jpa.Selection[ selectionid=" + selectionid + " ]";
    }
    
}
