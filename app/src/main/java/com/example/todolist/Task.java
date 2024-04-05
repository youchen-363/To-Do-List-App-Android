package com.example.todolist;

import android.content.Context;

import java.io.Serializable;
import java.sql.Date;

public class Task implements Serializable {
    private int id;
    private String nom;
    private String desc;
    private int priorite;
    private String date;
    private boolean statut;

    public Task(int id, String nom, String desc, int priorite, String date){
        this.id = id;
        this.nom = nom;
        this.desc = desc;
        this.date = date;
        this.priorite = priorite;
        this.statut = false;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public String prioriteString(Context context){
        int priorite = this.getPriorite();
        String prioString = "";
        switch(priorite){
            case 2:
                prioString = context.getString(R.string.high);
                break;
            case 1:
                prioString = context.getString(R.string.normal);
                break;
            case 0:
                prioString = context.getString(R.string.low);
                break;
        }
        return prioString;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString(){
        return this.nom + '\n' + this.date + " | " + this.priorite;
    }
}