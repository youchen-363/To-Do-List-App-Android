package com.example.todolist;

public class ListModel {
    private Task tache;
    private boolean tacheTermine;

    public ListModel(Task tache, boolean tacheTermine){
        this.tache = tache;
        this.tacheTermine = tacheTermine;
    }

    public String getNomTache(){
        return this.tache.toString();
    }

    public boolean getTacheTermine(){
        return this.tacheTermine;
    }

    public Task getTache(){
        return this.tache;
    }

    public void setTacheTermine(boolean etat){
        this.tacheTermine = etat;
    }
}
