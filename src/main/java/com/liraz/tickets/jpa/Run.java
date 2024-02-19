/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "RUN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Run.findAll", query = "SELECT r FROM Run r"),
    @NamedQuery(name = "Run.findDistinctShows", query = "SELECT DISTINCT r.showid FROM Run r WHERE r.active='true'"),
    @NamedQuery(name = "Run.findVenuesByType", query = "SELECT DISTINCT r.hallid.venueid FROM Run r WHERE r.active='true' AND r.showid.showtype = :showtype"),
    @NamedQuery(name = "Run.findShowsByVenueAndType", query = "SELECT DISTINCT r.showid FROM Run r WHERE r.active='true' AND r.showid.showtype = :showtype AND r.hallid.venueid = :venueid"),
    @NamedQuery(name = "Run.findHallsbyShowid", query = "SELECT DISTINCT r.showid FROM Run r WHERE r.active='true'"),
    @NamedQuery(name = "Run.findDateByShowVenue", query = "SELECT DISTINCT r.rundate FROM Run r WHERE r.active='true' AND r.showid = :showid AND r.hallid.venueid = :venueid"),
    @NamedQuery(name = "Run.findByShowVenueDate", query = "SELECT r FROM Run r WHERE r.active='true' AND r.showid = :showid AND r.hallid.venueid = :venueid AND r.rundate = :rundate"),
    @NamedQuery(name = "Run.findByRunid", query = "SELECT r FROM Run r WHERE r.runid = :runid"),
    @NamedQuery(name = "Run.findActiveByShowid", query = "SELECT r FROM Run r WHERE r.showid = :showid AND r.active='true'"),
    @NamedQuery(name = "Run.findByActive", query = "SELECT r FROM Run r WHERE r.active = :active"),
    @NamedQuery(name = "Run.findByRundate", query = "SELECT r FROM Run r WHERE r.rundate = :rundate"),
    @NamedQuery(name = "Run.findByRunprice", query = "SELECT r FROM Run r WHERE r.runprice = :runprice"),
    @NamedQuery(name = "Run.findByActive", query = "SELECT r FROM Run r WHERE r.active = :active"),
    @NamedQuery(name = "Run.findByRuninfo", query = "SELECT r FROM Run r WHERE r.runinfo = :runinfo")})
public class Run implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RUNID")
    private Integer runid;
    @Column(name = "RUNDATE")
    @Temporal(TemporalType.DATE)
    private Date rundate;
    @Column(name = "RUNHOUR")
    @Temporal(TemporalType.TIME)
    private Date runhour;
    @NotNull
    @Column(name = "RUNPRICE")
    private int runprice;
    @Size(max = 600)
    @Column(name = "RUNINFO")
    private String runinfo;
    @Column(name = "ACTIVE")
    private String active;
    @JoinColumn(name = "HALLID", referencedColumnName = "HALLID")
    @ManyToOne(optional = false)
    private Hall hallid;
    @JoinColumn(name = "SHOWID", referencedColumnName = "SHOWID")
    @ManyToOne(optional = false)
    private Show showid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "runid")
    private List<Runseat> runseatList;

    public Run() {
    }

    public Run(Integer runid) {
        this.runid = runid;
    }

    public Run(Integer runid, int runprice) {
        this.runid = runid;
        this.runprice = runprice;
    }

    public Integer getRunid() {
        return runid;
    }

    public void setRunid(Integer runid) {
        this.runid = runid;
    }

    public Date getRundate() {
        return rundate;
    }

    public void setRundate(Date rundate) {
        this.rundate = rundate;
    }
    
    public Date getRunhour() {
        return runhour;
    }

    public void setRunhour(Date runhour) {
        this.runhour = runhour;
    }

    public int getRunprice() {
        return runprice;
    }

    public void setRunprice(int runprice) {
        this.runprice = runprice;
    }

    public String getRuninfo() {
        return runinfo;
    }

    public void setRuninfo(String runinfo) {
        this.runinfo = runinfo;
    }
    
    public String getActive() {
        return active;
    }

    public void setActive(String act) {
        this.active = act;
    }
    
    public Boolean getActiveBool() {
        return active.equals("true");
    }


    public Hall getHallid() {
        return hallid;
    }

    public void setHallid(Hall hallid) {
        this.hallid = hallid;
    }

    public Show getShowid() {
        return showid;
    }

    public void setShowid(Show showid) {
        this.showid = showid;
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
        hash += (runid != null ? runid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Run)) {
            return false;
        }
        Run other = (Run) object;
        if ((this.runid == null && other.runid != null) || (this.runid != null && !this.runid.equals(other.runid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return showid.getShowtitle() + " " + runid;
    }
    
}
