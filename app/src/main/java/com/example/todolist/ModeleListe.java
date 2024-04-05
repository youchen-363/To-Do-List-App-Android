package com.example.todolist;

public class ModeleListe {
    private Task tache;
    private boolean tacheTermine;

    public ModeleListe(Task tache, boolean tacheTermine){
        this.tache = tache;
        this.tacheTermine = tacheTermine;
    }

    public String getNomTache(){
        return this.tache.getNom();
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
