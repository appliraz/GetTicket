/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jpa.manager;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author User
 * this class was made after 'AbstractFacade' class created my netbeans
 */
public abstract class AbstractManager<T> {
    
    private Class<T> entityClass;
    
    public AbstractManager(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();
    
    public void create(T entity){
        try{
            getEntityManager().persist(entity);
        }
        catch(Exception e)
        {
            message(e.toString());
        }
    }
    
    public void flush(){
        try{
            getEntityManager().flush();
        }
        catch(Exception e){
            message(e.toString());
        }
    }
    
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public void message(String msg){
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", msg));
    }
    
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
}
/*

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
*/