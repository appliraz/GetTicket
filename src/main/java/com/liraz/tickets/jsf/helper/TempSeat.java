/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.liraz.tickets.jsf.helper;

import com.liraz.tickets.jpa.Hall;

/**
 *
 * @author User
 */
public class TempSeat {
    private Hall hall;
    private int seat;
    private int row;
    
    public TempSeat(){
        this.hall = new Hall();
        this.seat = 0;
        this.row = 0;
    }
    
    public Hall getHall(){
        return this.hall;
    }
    
    public void setHall(Hall h){
        this.hall = h;
    }
    
    public int getSeat(){
        return this.seat;
    }
    
    public void setSeat(int s){
        this.seat = s;
    }
    
    public int getRow(){
        return this.row;
    }
    
    public void setRow(int r){
        this.row=r;
    }
    
    
}
