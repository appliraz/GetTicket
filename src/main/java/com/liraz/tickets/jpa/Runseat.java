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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "RUNSEAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Runseat.findAll", query = "SELECT r FROM Runseat r"),
    @NamedQuery(name = "Runseat.findByRunseatid", query = "SELECT r FROM Runseat r WHERE r.runseatid = :runseatid"),
    @NamedQuery(name = "Runseat.findByRunid", query = "SELECT r FROM Runseat r WHERE r.runid = :runid"),
    @NamedQuery(name = "Runseat.findAvailableByRunid", query = "SELECT r FROM Runseat r WHERE r.runid = :runid AND r.available='true'"),
    @NamedQuery(name = "Runseat.findByAvailable", query = "SELECT r FROM Runseat r WHERE r.available = :available")})
public class Runseat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RUNSEATID")
    private Integer runseatid;
    @Size(max = 10)
    @Column(name = "AVAILABLE")
    private String available;
    @JoinColumn(name = "RUNID", referencedColumnName = "RUNID")
    @ManyToOne(optional = false)
    private Run runid;
    @JoinColumn(name = "SEATID", referencedColumnName = "SEATID")
    @ManyToOne(optional = false)
    private Seat seatid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "runseatid")
    private List<Selection> selectionList;

    public Runseat() {
    }

    public Runseat(Integer runseatid) {
        this.runseatid = runseatid;
    }

    public Integer getRunseatid() {
        return runseatid;
    }

    public void setRunseatid(Integer runseatid) {
        this.runseatid = runseatid;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Run getRunid() {
        return runid;
    }

    public void setRunid(Run runid) {
        this.runid = runid;
    }

    public Seat getSeatid() {
        return seatid;
    }

    public void setSeatid(Seat seatid) {
        this.seatid = seatid;
    }

    @XmlTransient
    public List<Selection> getSelectionList() {
        return selectionList;
    }

    public void setSelectionList(List<Selection> selectionList) {
        this.selectionList = selectionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (runseatid != null ? runseatid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Runseat)) {
            return false;
        }
        Runseat other = (Runseat) object;
        if ((this.runseatid == null && other.runseatid != null) || (this.runseatid != null && !this.runseatid.equals(other.runseatid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.liraz.tickets.jpa.Runseat[ runseatid=" + runseatid + " ]";
    }
    
}
