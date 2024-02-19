/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa;

import com.liraz.tickets.jpa.manager.SeatManager;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "HALL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hall.findAll", query = "SELECT h FROM Hall h"),
    @NamedQuery(name = "Hall.findByHallid", query = "SELECT h FROM Hall h WHERE h.hallid = :hallid"),
    @NamedQuery(name = "Hall.findByHallname", query = "SELECT h FROM Hall h WHERE h.hallname = :hallname"),
    @NamedQuery(name = "Hall.findByHallcapacity", query = "SELECT h FROM Hall h WHERE h.hallcapacity = :hallcapacity"),
    @NamedQuery(name = "Hall.findByHalltype", query = "SELECT h FROM Hall h WHERE h.halltype = :halltype")})
public class Hall implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "HALLID")
    private Integer hallid;
    @Size(max = 600)
    @Column(name = "HALLNAME")
    private String hallname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HALLCAPACITY")
    private int hallcapacity;
    @Size(max = 100)
    @Column(name = "HALLTYPE")
    private String halltype;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallid")
    private List<Seat> seatList;
    @JoinColumn(name = "VENUEID", referencedColumnName = "VENUEID")
    @ManyToOne(optional = false)
    private Venue venueid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallid")
    private List<Run> runList;

    public Hall() {
    }

    public Hall(Integer hallid) {
        this.hallid = hallid;
    }

    public Hall(Integer hallid, int hallcapacity) {
        this.hallid = hallid;
        this.hallcapacity = hallcapacity;
    }

    public Integer getHallid() {
        return hallid;
    }

    public void setHallid(Integer hallid) {
        this.hallid = hallid;
    }

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }

    public int getHallcapacity() {
        return hallcapacity;
    }

    public void setHallcapacity(int hallcapacity) {
        this.hallcapacity = hallcapacity;
    }

    public String getHalltype() {
        return halltype;
    }

    public void setHalltype(String halltype) {
        this.halltype = halltype;
    }

    @XmlTransient
    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public Venue getVenueid() {
        return venueid;
    }

    public void setVenueid(Venue venueid) {
        this.venueid = venueid;
    }

    @XmlTransient
    public List<Run> getRunList() {
        return runList;
    }

    public void setRunList(List<Run> runList) {
        this.runList = runList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hallid != null ? hallid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hall)) {
            return false;
        }
        Hall other = (Hall) object;
        if ((this.hallid == null && other.hallid != null) || (this.hallid != null && !this.hallid.equals(other.hallid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return venueid.getVenuename() + " " + hallname;
    }
    
}
