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
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByClientid", query = "SELECT c FROM Client c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "Client.findByClientfirstname", query = "SELECT c FROM Client c WHERE c.clientfirstname = :clientfirstname"),
    @NamedQuery(name = "Client.findByClientphone", query = "SELECT c FROM Client c WHERE c.clientphone = :clientphone"),
    @NamedQuery(name = "Client.findByClientemail", query = "SELECT c FROM Client c WHERE c.clientemail = :clientemail"),
    @NamedQuery(name = "Client.findByClientpassword", query = "SELECT c FROM Client c WHERE c.clientpassword = :clientpassword")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CLIENTID")
    private Integer clientid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CLIENTFIRSTNAME")
    private String clientfirstname;
    @Size(max = 20)
    @Column(name = "CLIENTPHONE")
    private String clientphone;
    @Size(max = 255)
    @Column(name = "CLIENTEMAIL")
    private String clientemail;
    @Size(max = 20)
    @Column(name = "CLIENTPASSWORD")
    private String clientpassword;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientid")
    private List<Review> reviewList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientid")
    private List<Clientorder> clientorderList;

    public Client() {
    }

    public Client(Integer clientid) {
        this.clientid = clientid;
    }

    public Client(Integer clientid, String clientfirstname) {
        this.clientid = clientid;
        this.clientfirstname = clientfirstname;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public String getClientfirstname() {
        return clientfirstname;
    }

    public void setClientfirstname(String clientfirstname) {
        this.clientfirstname = clientfirstname;
    }

    public String getClientphone() {
        return clientphone;
    }

    public void setClientphone(String clientphone) {
        this.clientphone = clientphone;
    }

    public String getClientemail() {
        return clientemail;
    }

    public void setClientemail(String clientemail) {
        this.clientemail = clientemail;
    }

    public String getClientpassword() {
        return clientpassword;
    }

    public void setClientpassword(String clientpassword) {
        this.clientpassword = clientpassword;
    }

    @XmlTransient
    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @XmlTransient
    public List<Clientorder> getClientorderList() {
        return clientorderList;
    }

    public void setClientorderList(List<Clientorder> clientorderList) {
        this.clientorderList = clientorderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientid != null ? clientid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.clientid == null && other.clientid != null) || (this.clientid != null && !this.clientid.equals(other.clientid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.liraz.tickets.jpa.Client[ clientid=" + clientid + " ]";
    }
    
}
