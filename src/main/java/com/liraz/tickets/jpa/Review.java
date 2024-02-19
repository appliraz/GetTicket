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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "REVIEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r"),
    @NamedQuery(name = "Review.findByReviewid", query = "SELECT r FROM Review r WHERE r.reviewid = :reviewid"),
    @NamedQuery(name = "Review.findByShowid", query = "SELECT r FROM Review r WHERE r.showid = :showid"),
    @NamedQuery(name = "Review.findByReviewcontent", query = "SELECT r FROM Review r WHERE r.reviewcontent = :reviewcontent")})
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REVIEWID")
    private Integer reviewid;
    @Size(max = 555)
    @Column(name = "REVIEWCONTENT")
    private String reviewcontent;
    @Column
    private Integer rating;
    @JoinColumn(name = "CLIENTID", referencedColumnName = "CLIENTID")
    @ManyToOne(optional = false)
    private Client clientid;
    @JoinColumn(name = "SHOWID", referencedColumnName = "SHOWID")
    @ManyToOne(optional = false)
    private Show showid;

    public Review() {
    }

    public Review(Integer reviewid) {
        this.reviewid = reviewid;
    }

    public Integer getReviewid() {
        return reviewid;
    }

    public void setReviewid(Integer reviewid) {
        this.reviewid = reviewid;
    }

    public String getReviewcontent() {
        return reviewcontent;
    }

    public void setReviewcontent(String reviewcontent) {
        this.reviewcontent = reviewcontent;
    }
    
    public Integer getRating(){
        return rating;
    }
    
    public void setRating(Integer rating){
        this.rating = rating;
    }

    public Client getClientid() {
        return clientid;
    }

    public void setClientid(Client clientid) {
        this.clientid = clientid;
    }

    public Show getShowid() {
        return showid;
    }

    public void setShowid(Show showid) {
        this.showid = showid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewid != null ? reviewid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewid == null && other.reviewid != null) || (this.reviewid != null && !this.reviewid.equals(other.reviewid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.liraz.tickets.jpa.Review[ reviewid=" + reviewid + " ]";
    }
    
}
