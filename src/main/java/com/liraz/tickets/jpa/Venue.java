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
@Table(name = "VENUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venue.findAll", query = "SELECT v FROM Venue v"),
    @NamedQuery(name = "Venue.findByVenueid", query = "SELECT v FROM Venue v WHERE v.venueid = :venueid"),
    @NamedQuery(name = "Venue.findByVenuename", query = "SELECT v FROM Venue v WHERE v.venuename = :venuename"),
    @NamedQuery(name = "Venue.findByVenueaddress", query = "SELECT v FROM Venue v WHERE v.venueaddress = :venueaddress"),
    @NamedQuery(name = "Venue.findByVenuecapacity", query = "SELECT v FROM Venue v WHERE v.venuecapacity = :venuecapacity")})
public class Venue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "VENUEID")
    private Integer venueid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "VENUENAME")
    private String venuename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "VENUEADDRESS")
    private String venueaddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VENUECAPACITY")
    private int venuecapacity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venueid")
    private List<Hall> hallList;

    public Venue() {
    }

    public Venue(Integer venueid) {
        this.venueid = venueid;
    }

    public Venue(Integer venueid, String venuename, String venueaddress, int venuecapacity) {
        this.venueid = venueid;
        this.venuename = venuename;
        this.venueaddress = venueaddress;
        this.venuecapacity = venuecapacity;
    }

    public Integer getVenueid() {
        return venueid;
    }

    public void setVenueid(Integer venueid) {
        this.venueid = venueid;
    }

    public String getVenuename() {
        return venuename;
    }

    public void setVenuename(String venuename) {
        this.venuename = venuename;
    }

    public String getVenueaddress() {
        return venueaddress;
    }

    public void setVenueaddress(String venueaddress) {
        this.venueaddress = venueaddress;
    }

    public int getVenuecapacity() {
        return venuecapacity;
    }

    public void setVenuecapacity(int venuecapacity) {
        this.venuecapacity = venuecapacity;
    }

    @XmlTransient
    public List<Hall> getHallList() {
        return hallList;
    }

    public void setHallList(List<Hall> hallList) {
        this.hallList = hallList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (venueid != null ? venueid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venue)) {
            return false;
        }
        Venue other = (Venue) object;
        if ((this.venueid == null && other.venueid != null) || (this.venueid != null && !this.venueid.equals(other.venueid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return venueid + " " + venuename;
    }
    
}
