package com.example.todolist;

import java.io.Serializable;
import java.sql.Date;

public class Task implements Serializable {
    private int id;
    private String nom;
    private String desc;
    private String priorite;
    private String date;
    private boolean statut;

    public Task(int id, String nom, String desc, String priorite, String date){
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

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
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

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString(){
        return this.nom + '\n' + this.date + " | " + this.priorite.toString();
    }

}