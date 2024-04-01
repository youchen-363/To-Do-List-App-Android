package com.example.todolist;

import java.sql.Date;

public class Task {

    public enum Priorite {
        URGENT, NORMALE, FAIBLE
    }

    private Priorite priorite;
    private String nom;
    private String desc;
    private Date date;
    private boolean statut;

    public Task(String nom, String desc, Date date, Priorite priorite){
        this.nom = nom;
        this.desc = desc;
        this.date = date;
        this.priorite = priorite;
        this.statut = false;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString(){
        return this.nom + '\n' + this.date + " | " + this.priorite.toString();
    }
}
