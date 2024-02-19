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
@Table(name = "SEAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seat.findAll", query = "SELECT s FROM Seat s"),
    @NamedQuery(name = "Seat.findBySeatid", query = "SELECT s FROM Seat s WHERE s.seatid = :seatid"),
    @NamedQuery(name = "Seat.findBySeatnumber", query = "SELECT s FROM Seat s WHERE s.seatnumber = :seatnumber"),
    @NamedQuery(name = "Seat.findBySeatrow", query = "SELECT s FROM Seat s WHERE s.seatrow = :seatrow"),
    @NamedQuery(name = "Seat.findByHallid", query = "SELECT s FROM Seat s WHERE s.hallid.hallid = :hallid"),
    @NamedQuery(name = "Seat.findByRowAndHall", query = "SELECT s FROM Seat s WHERE s.seatrow = :seatrow AND s.hallid.hallid = :hallid")})
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SEATID")
    private Integer seatid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEATNUMBER")
    private int seatnumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEATROW")
    private int seatrow;
    @JoinColumn(name = "HALLID", referencedColumnName = "HALLID")
    @ManyToOne(optional = false)
    private Hall hallid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seatid")
    private List<Runseat> runseatList;

    public Seat() {
    }

    public Seat(Integer seatid) {
        this.seatid = seatid;
    }

    public Seat(Integer seatid, int seatnumber, int seatrow) {
        this.seatid = seatid;
        this.seatnumber = seatnumber;
        this.seatrow = seatrow;
    }

    public Integer getSeatid() {
        return seatid;
    }

    public void setSeatid(Integer seatid) {
        this.seatid = seatid;
    }

    public int getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(int seatnumber) {
        this.seatnumber = seatnumber;
    }

    public int getSeatrow() {
        return seatrow;
    }

    public void setSeatrow(int seatrow) {
        this.seatrow = seatrow;
    }

    public Hall getHallid() {
        return hallid;
    }

    public void setHallid(Hall hallid) {
        this.hallid = hallid;
    }

    @XmlTransient
    public List<Runseat> getRunseatList() {
        return runseatList;
    }

    public void setRunseatList(List<Runseat> runseatList) {
        this.runseatList = runseatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seatid != null ? seatid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seat)) {
            return false;
        }
        Seat other = (Seat) object;
        if ((this.seatid == null && other.seatid != null) || (this.seatid != null && !this.seatid.equals(other.seatid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.liraz.tickets.jpa.Seat[ seatid=" + seatid + " ]";
    }
    
}
