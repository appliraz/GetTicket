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
@Table(name = "SHOW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Show.findAll", query = "SELECT s FROM Show s"),
    @NamedQuery(name = "Show.findByShowid", query = "SELECT s FROM Show s WHERE s.showid = :showid"),
    @NamedQuery(name = "Show.findByShowtitle", query = "SELECT s FROM Show s WHERE s.showtitle = :showtitle"),
    @NamedQuery(name = "Show.findByShowlength", query = "SELECT s FROM Show s WHERE s.showlength = :showlength"),
    @NamedQuery(name = "Show.findByShowtype", query = "SELECT s FROM Show s WHERE s.showtype = :showtype"),
    @NamedQuery(name = "Show.findByShowgenre", query = "SELECT s FROM Show s WHERE s.showgenre = :showgenre"),
    @NamedQuery(name = "Show.findByShowposter", query = "SELECT s FROM Show s WHERE s.showposter = :showposter"),
    @NamedQuery(name = "Show.findByShowdescription", query = "SELECT s FROM Show s WHERE s.showdescription = :showdescription"),
    @NamedQuery(name = "Show.findByShowproducer", query = "SELECT s FROM Show s WHERE s.showproducer = :showproducer"),
    @NamedQuery(name = "Show.findByShowrating", query = "SELECT s FROM Show s WHERE s.showrating = :showrating")})
public class Show implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SHOWID")
    private Integer showid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 600)
    @Column(name = "SHOWTITLE")
    private String showtitle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHOWLENGTH")
    private int showlength;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SHOWTYPE")
    private String showtype;
    @Size(max = 300)
    @Column(name = "SHOWGENRE")
    private String showgenre;
    @Size(max = 600)
    @Column(name = "SHOWPOSTER")
    private String showposter;
    @Size(max = 600)
    @Column(name = "SHOWDESCRIPTION")
    private String showdescription;
    @Size(max = 300)
    @Column(name = "SHOWPRODUCER")
    private String showproducer;
    @Column(name = "SHOWRATING")
    private Integer showrating;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "showid")
    private List<Review> reviewList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "showid")
    private List<Run> runList;

    public Show() {
    }

    public Show(Integer showid) {
        this.showid = showid;
    }

    public Show(Integer showid, String showtitle, int showlength, String showtype) {
        this.showid = showid;
        this.showtitle = showtitle;
        this.showlength = showlength;
        this.showtype = showtype;
    }

    public Integer getShowid() {
        return showid;
    }

    public void setShowid(Integer showid) {
        this.showid = showid;
    }

    public String getShowtitle() {
        return showtitle;
    }

    public void setShowtitle(String showtitle) {
        this.showtitle = showtitle;
    }

    public int getShowlength() {
        return showlength;
    }

    public void setShowlength(int showlength) {
        this.showlength = showlength;
    }

    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype;
    }

    public String getShowgenre() {
        return showgenre;
    }

    public void setShowgenre(String showgenre) {
        this.showgenre = showgenre;
    }

    public String getShowposter() {
        return showposter;
    }

    public void setShowposter(String showposter) {
        this.showposter = showposter;
    }

    public String getShowdescription() {
        return showdescription;
    }

    public void setShowdescription(String showdescription) {
        this.showdescription = showdescription;
    }

    public String getShowproducer() {
        return showproducer;
    }

    public void setShowproducer(String showproducer) {
        this.showproducer = showproducer;
    }

    public Integer getShowrating() {
        return showrating;
    }

    public void setShowrating(Integer showrating) {
        this.showrating = showrating;
    }

    @XmlTransient
    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
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
        hash += (showid != null ? showid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Show)) {
            return false;
        }
        Show other = (Show) object;
        if ((this.showid == null && other.showid != null) || (this.showid != null && !this.showid.equals(other.showid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return showid + " " + showtitle;
    }
    
}
