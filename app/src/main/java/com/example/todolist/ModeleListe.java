package com.example.todolist;

public class ModeleListe {
    private String nomTache;
    private boolean TacheTermine;

    public ModeleListe(String nomTache, boolean TacheTermine){
        this.nomTache = nomTache;
        this.TacheTermine = TacheTermine;
    }

    public String getNomTache(){
        return this.nomTache;
    }

    public boolean getTacheTermine(){
        return this.TacheTermine;
    }

    public void setTacheTermine(boolean etat){
        this.TacheTermine = etat;
    }
}
